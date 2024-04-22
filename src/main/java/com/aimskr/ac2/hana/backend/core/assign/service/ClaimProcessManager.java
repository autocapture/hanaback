package com.aimskr.ac2.hana.backend.core.assign.service;

import com.aimskr.ac2.common.enums.InsCompany;
import com.aimskr.ac2.common.enums.ProcessType;
import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.util.DateUtil;
import com.aimskr.ac2.hana.backend.channel.domain.ImageHash;
import com.aimskr.ac2.hana.backend.channel.json.*;

import com.aimskr.ac2.hana.backend.channel.service.RetryService;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.hana.backend.core.assign.domain.AssignRepository;
import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import com.aimskr.ac2.hana.backend.core.detail.domain.DetailRepository;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetailRepository;
import com.aimskr.ac2.hana.backend.core.phone.service.PhoneRepairService;
import com.aimskr.ac2.hana.backend.member.service.AssignRuleService;
import com.aimskr.ac2.hana.backend.security.service.EmailService;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.hana.backend.vision.util.ImageProcessor;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.config.ControlConfig;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.common.util.FileUtil;
import dev.brachtendorf.jimagehash.hash.Hash;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.aimskr.ac2.common.util.DateUtil.DATETIME_HANA;


@Slf4j
@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.aimskr.ac2.common")

public class ClaimProcessManager {
    private final PhoneRepairDetailRepository phoneRepairDetailRepository;

    private final AssignService assignService;
    private final ImageProcessor imageProcessor;
    private final ImageService imageService;
    private final ControlConfig controlConfig;
    private final FileUtil fileUtil;
    private final PhoneRepairService phoneRepairService;
    private final RetryService retryService;
    private final AutocaptureConfig autocaptureConfig;
    private final AssignRuleService assignRuleService;
    private final EmailService emailService;
    private final AssignRepository assignRepository;
    private final DetailRepository detailRepository;



    /**
     * 이미지 처리
     * 이미지 다운로드 이후 호출됨
     */
    public void processImages(ImportDto importDto) {
        // 1. 다운로드 완료시간 업데이트
        assignService.updateDownloadTime(importDto.getAcdNo(), importDto.getRctSeq());

        // 2. 이미지 처리
        Integer sequence = 1;
        for (ImgFileInfoDto imgFileInfoDto : importDto.getImgLst()) {
            VisionResult visionResult = VisionResult.createInitialResult();
            ImageProcessingResultCode imageProcessingResultCode = ImageProcessingResultCode.NORMAL;

            try {

                // 2.2 이미지 전처리
                log.debug("[processImages] - preProcessImage - imgFileInfoDto : {}", imgFileInfoDto);
                imageProcessor.preProcessImage(importDto, imgFileInfoDto);

                // 2.3 이미지 중복 검사
                Hash hash = imageProcessor.calcHash(fileUtil.calcAcFilePath(importDto, imgFileInfoDto));
                String md5Hash = imageProcessor.generateMD5((hash.getHashValue()).toByteArray());
                boolean isDup = false;
                String duppedFile = "";

                // 중복이미지 처리
                if (controlConfig.isDupCheck()) {
                    // 중복검사 설정이 On일 때만 처리
                    log.debug("[processImages] - checkDup - imgFileInfoDto : {}", imgFileInfoDto);
                    ImageHash existImageHash = imageService.findByHash(md5Hash);
                    if (existImageHash == null) {
                        ImageHash imageHash = ImageHash.builder()
                                .hash(md5Hash.toString())
                                .accrNo(importDto.getAcdNo())
                                .dmSeqno(importDto.getRctSeq())
                                .imageDocumentId(imgFileInfoDto.getImgId())
                                .build();
                        imageService.saveImageHash(imageHash);
                    } else {
                        isDup = true;
                        log.debug("[processImages] - checkDup - imgFileInfoDto, isDUP : {}, {}", imgFileInfoDto, isDup);
                        duppedFile = existImageHash.getImageDocumentId();
                        imageProcessingResultCode = ImageProcessingResultCode.DUPLICATE;
                    }
                }

                // 2.4 OCR 실행
                log.debug("[processImages] - doOCR - imgFileInfoDto : {}", imgFileInfoDto);
                String autocaptureImage = fileUtil.calcAcFilePath(importDto, imgFileInfoDto);

                // 문서 분류와 합쳐져있음
                visionResult = retryService.autoInput(autocaptureImage, importDto, imgFileInfoDto);

//                phoneRepairService.saveDetailFromAiDetails(importDto.getAcdNo(), importDto.getRctSeq(), FileUtil.changeExtToJpg(imgFileInfoDto.getImgFileNm()));

                // 2.5 이미지 정보 및 OCR 결과 저장
                log.debug("[processImages] - saveImage - imgFileInfoDto : {}", imgFileInfoDto);
                imageService.saveImage(importDto, imgFileInfoDto, isDup, duppedFile,
                        md5Hash, visionResult, imageProcessingResultCode, sequence++);

            } catch (Exception e) {
                log.error("[processImages] - error : {}", e.getMessage());

                e.printStackTrace();
                visionResult.setError(true);
                visionResult.setQa(true);
                visionResult.setInputRequired(false);
                visionResult.setDocType(DocType.ETCS);
                // 예외가 나면 에러 이미지로 저장
                imageService.saveImage(importDto, imgFileInfoDto, false, "",
                        "", visionResult, ImageProcessingResultCode.FTP_ERROR, sequence++);
            }
        }

        boolean autoReturnable = false;

        // 중복 체크
//        boolean qaDuplicity = assignService.checkDupImageContentsFromAll(importDto.getAccrNo(), importDto.getDmSeqno());

        // 자동회신 가능 여부 체크
//        try{
//            autoReturnable = assignService.checkAutoReturnable(importDto.getAccrNo(), importDto.getDmSeqno(), qaDuplicity);
//            log.debug("[processImages] - autoReturnable : {}", autoReturnable);
//        } catch(Exception e){
//            e.printStackTrace();
//        }

        // test 모드일 경우, 회신 안함
        if (autocaptureConfig.getSftpIp().equals("test")){
            autoReturnable = false;
            log.debug("[processImages] - testMode : {}", true);
        }

        String qaOwner = assignRuleService.getQaAssign();

        ResultDto resultDto = makeSuccessResultDto(importDto.getRqsTpCd(), importDto.getAcdNo(), importDto.getRctSeq());
        String isResultValid = resultDto.checkValid();
        if (isResultValid.equals(ResultDto.INVALID)){
            log.debug("[processImages] - result is invalid : {}", resultDto);
//            autoReturnable = false;
        }

        // 3. 자동회신 or QA배당
//        if (controlConfig.isAutoReturn() || autoReturnable) {
//            log.info("'[processImages] autoReturn ImportDto : {}", importDto);
//            qaOwner = "AIP";
//            assignService.finishWithAIP(importDto.getAcdNo(), importDto.getRctSeq(), resultDto);
//        } else {
//
//            log.info("[processImages] assign ImportDto : {}, QaOwner : {}", importDto, qaOwner);
//            imageService.updateQaStatus(importDto.getAcdNo(), importDto.getRctSeq(), true);
//            assignService.applyQaAssign(importDto, qaOwner);
//        }

        assignService.applyQaAssign(importDto, qaOwner);

        if (controlConfig.isAlertMode()){
            try{
                log.debug("[processImages] sendAssignAlert - " + importDto.calcKey() + " 메일 발송");
                String email = controlConfig.getAlertEmail();
                emailService.sendAssignAlert(qaOwner, importDto.getAcdNo() + "_" + importDto.getRctSeq(), email);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    @Transactional
    public ResultDto makeSuccessResultDto(String rqsReqId, String accrNo, String dmSeqno){
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno).orElse(null);
        assignService.updateSuccess(accrNo, dmSeqno);

        int cntOfCIPS = 0;

        List<ImageResponseDto> images = imageService.findByKey(rqsReqId, accrNo, dmSeqno);
        List<ImageResultDto> imgList = new ArrayList<>();
        for (ImageResponseDto image: images){
            ImageResultDto imageResponseDto = ImageResultDto.of(image);
            List<ResultItem> resultItems = new ArrayList<>();
            if (image.getDocType().equals(DocType.CIPS)){
                resultItems = makeResultItems(image.getRqsReqId(), image.getAccrNo(), image.getDmSeqno(), image.getFileName());
                cntOfCIPS++;
            }
            imageResponseDto.setPcsRslLst(resultItems);
            imgList.add(imageResponseDto);
        }

        ResultDto resultDto = ResultDto.of(assign);
        resultDto.setPcsDtm(DateUtil.nowWithFormat(DATETIME_HANA));
        ProcessResponseCode result = ProcessResponseCode.SUCCESS;
        if (cntOfCIPS > 0){
            result = ProcessResponseCode.SUCCESS;
        } else {
            result = ProcessResponseCode.NA;
        }
        resultDto.setPcsRslCd(result.getCode());
        resultDto.setPcsRslDtlCd("");
        resultDto.setImgList(imgList);
        log.debug("[makeResultDto] ResultDto : {}", resultDto);
        return resultDto;
    }

    public List<ResultItem> makeResultItems(String rqsReqId, String accrNo, String dmSeqno, String fileName){
        List<ResultItem> resultItems = new ArrayList<>();
        List<Detail> details = detailRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName);

        for (Detail detail: details){
            String itemCode = detail.getItemCode();
            String itemValue = detail.getItemValue();

            if (itemCode.equals("CA0001")) { // 자동차보험회사
                itemValue = InsCompany.findByName(itemValue) == null ? "" : InsCompany.findByName(itemValue).getCode();
            } else if (itemCode.equals("CA0002")) { // 처리구분
                itemValue = ProcessType.findByName(itemValue) == null ? "" : ProcessType.findByName(itemValue).getCode();
            } else if (itemCode.equals("CA0005")) { // 피보험자명
                itemValue = itemValue.equals("일치") ? "1" : "0";
            }

            ResultItem resultItem = ResultItem.builder()
                    .trmCd(itemCode)
                    .trmVal(itemValue)
                    .build();
            resultItems.add(resultItem);
        }
        return resultItems;
    }

}
