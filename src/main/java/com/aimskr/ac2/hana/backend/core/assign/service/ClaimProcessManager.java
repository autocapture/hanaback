package com.aimskr.ac2.hana.backend.core.assign.service;

import com.aimskr.ac2.hana.backend.channel.domain.ImageHash;
import com.aimskr.ac2.hana.backend.channel.json.*;

import com.aimskr.ac2.hana.backend.channel.service.RetryService;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.hana.backend.core.assign.domain.AssignRepository;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetail;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetailRepository;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairResponseDto;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairDetailResponseDto;
import com.aimskr.ac2.hana.backend.core.phone.service.PhoneRepairService;
import com.aimskr.ac2.hana.backend.member.service.AssignRuleService;
import com.aimskr.ac2.hana.backend.security.service.EmailService;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.hana.backend.vision.util.ImageProcessor;
import com.aimskr.ac2.common.config.AutocaptureConfig;
import com.aimskr.ac2.common.config.ControlConfig;
import com.aimskr.ac2.common.enums.Constant;
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
import java.util.stream.Collectors;


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



    /**
     * 이미지 처리
     * 이미지 다운로드 이후 호출됨
     */
    public void processImages(ImportDto importDto) {
        // 1. 다운로드 완료시간 업데이트
        assignService.updateDownloadTime(importDto.getAccrNo(), importDto.getDmSeqno());

        // 2. 이미지 처리
        Integer sequence = 1;
        for (ImgFileInfoDto imgFileInfoDto : importDto.getImgList()) {
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
                if (imgFileInfoDto.getFileNm().toLowerCase().contains("pdf")){

                    imageProcessingResultCode = ImageProcessingResultCode.NOT_SUPPORT;
                    imageService.saveImage(importDto, imgFileInfoDto, isDup, duppedFile,
                            md5Hash, visionResult, imageProcessingResultCode, sequence++);
                    continue;
                }

                if (controlConfig.isDupCheck()) {
                    // 중복검사 설정이 On일 때만 처리
                    log.debug("[processImages] - checkDup - imgFileInfoDto : {}", imgFileInfoDto);
                    ImageHash existImageHash = imageService.findByHash(md5Hash);
                    if (existImageHash == null) {
                        ImageHash imageHash = ImageHash.builder()
                                .hash(md5Hash.toString())
                                .accrNo(importDto.getAccrNo())
                                .dmSeqno(importDto.getDmSeqno())
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

                visionResult = retryService.autoInput(autocaptureImage, importDto, imgFileInfoDto);
                phoneRepairService.saveDetailFromAiDetails(importDto.getAccrNo(), importDto.getDmSeqno(), FileUtil.changeExtToJpg(imgFileInfoDto.getFileNm()));

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

                imageService.saveImage(importDto, imgFileInfoDto, false, "",
                        "", visionResult, ImageProcessingResultCode.NORMAL, sequence++);
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

        ResultDto resultDto = makeSuccessResultDto(importDto.getACD_NO(), importDto.getRCT_SEQ(), importDto.getApiFlgCd());
        String isResultValid = resultDto.checkValid();
        if (isResultValid.equals(ResultDto.INVALID)){
            log.debug("[processImages] - result is invalid : {}", resultDto);
//            autoReturnable = false;
        }

        // 3. 자동회신 or QA배당
        if (controlConfig.isAutoReturn() || autoReturnable) {
            log.info("'[processImages] autoReturn ImportDto : {}", importDto);
            qaOwner = "AIP";
            assignService.finishWithAIP(importDto.getACD_NO(), importDto.getRCT_SEQ(), resultDto);
        } else {

            log.info("[processImages] assign ImportDto : {}, QaOwner : {}", importDto, qaOwner);
            imageService.updateQaStatus(importDto.getACD_NO(), importDto.getRCT_SEQ(), true);
            assignService.applyQaAssign(importDto, qaOwner);
        }

        if (controlConfig.isAlertMode()){
            try{
                log.debug("[processImages] sendAssignAlert - " + importDto.getKey() + " 메일 발송");
                String email = controlConfig.getAlertEmail();
                emailService.sendAssignAlert(qaOwner, importDto.getACD_NO() + "_" + importDto.getRCT_SEQ(), email);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    @Transactional
    public ResultDto makeSuccessResultDto(String accrNo, String dmSeqno){
        List<PhoneImageResultDto> imageResults = new ArrayList<>();
        List<ImageResponseDto> images = imageService.findByKey(accrNo, dmSeqno);
        for (ImageResponseDto image: images){
            List<ResultItem> items = new ArrayList<>();
            List<ResultSubItem> accdList = new ArrayList<>();
            if (!image.getDocType().equals(DocType.ETCS)){
                items = makePhoneResultItems(image.getAccrNo(), image.getDmSeqno(), image.getFileName());
            }
            if (image.getDocType().equals(DocType.RPDT)){
                accdList = makePhoneResultSubItems(image.getAccrNo(), image.getDmSeqno(), image.getFileName());

            }
        }

        log.debug("[makeResultDto] imageResults : {}", imageResults);

        assignService.updateSuccess(accrNo, dmSeqno);

        ResultDto resultDto = ResultDto.builder().build();
        Assign assign = assignRepository.findByKey(accrNo, dmSeqno).orElse(null);


        if (assign != null){
            resultDto = ResultDto.of(assign);
        } else {
            log.error("[makeResultDto] assign is null - receiptNo : {}, receiptSeq : {}", accrNo, dmSeqno);
        }
        resultDto.setImgList(imageResults);

        log.debug("[makeResultDto] ResultDto : {}", resultDto);
        return resultDto;
    }

    public List<ResultItem> makePhoneResultItems(String accrNo, String dmSeqno, String fileName){
        List<ResultItem> resultItems = new ArrayList<>();
        List<PhoneRepairResponseDto> details = phoneRepairService.findByKeyAndFileName(accrNo, dmSeqno, fileName);

        for (PhoneRepairResponseDto detail: details){
            ResultItem resultItem = ResultItem.of(detail);
            resultItems.add(resultItem);
        }
        return resultItems;
    }

    public List<ResultSubItem> makePhoneResultSubItems(String accrNo, String dmSeqno, String fileName){
        List<ResultSubItem> resultSubItems = new ArrayList<>();

        List<PhoneRepairDetail> details = phoneRepairDetailRepository.findByKeyAndFileName(accrNo, dmSeqno, fileName);
        List<PhoneRepairDetailResponseDto> phoneRepairDetailResponseDtos = details.stream().map(PhoneRepairDetailResponseDto::new).collect(Collectors.toList());

        for (PhoneRepairDetailResponseDto dtos: phoneRepairDetailResponseDtos){
            ResultSubItem resultSubItem = ResultSubItem.of(dtos);
            resultSubItems.add(resultSubItem);
        }
        return resultSubItems;
    }

}
