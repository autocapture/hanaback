package com.aimskr.ac2.hana.backend.core.image.service;


import com.aimskr.ac2.hana.backend.channel.domain.ImageHash;
import com.aimskr.ac2.hana.backend.channel.domain.ImageHashRepository;
import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import com.aimskr.ac2.hana.backend.core.detail.domain.DetailRepository;
import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import com.aimskr.ac2.hana.backend.core.image.domain.ImageRepository;
import com.aimskr.ac2.hana.backend.core.image.dto.*;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.SurgInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.service.DiagInfoService;
import com.aimskr.ac2.hana.backend.core.medical.service.SurgInfoService;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageHashRepository imageHashRepository;
    private final DetailRepository detailRepository;
    private final DiagInfoService diagInfoService;
    private final SurgInfoService surgInfoService;

    /**
     * 접수 시점 이미지 최초 저장 : 중복여부 / 입력대상 여부 저장
     * ClaimProcessManager.processImages()에서 호출
     */
    @Transactional
    public void saveImage(ImportDto importDto,
                          ImgFileInfoDto imgFileInfoDto,
                          boolean isDup,
                          String duppedFile,
                          String hashValue,
                          VisionResult visionResult,
                          ImageProcessingResultCode imageProcessingResultCode,
                          Integer sequence){

        String fileName = FileUtil.changeExtToJpg(imgFileInfoDto.getImgFileNm());

        Image prevRd = imageRepository.findByKeyAndFileName(
                importDto.getRqsReqId(),
                importDto.getAcdNo(),
                importDto.getRctSeq(),
                fileName
        ).orElse(null);

        if (!isDup){
            duppedFile = "";
        }
        if (visionResult.getDocType() == null){
            visionResult.setDocType(DocType.ETCS);
        }

        DocType ocrDocType = visionResult.getDocType();
        DocType docType = visionResult.getDocType();

//        if (ocrDocType.equals(DocType.MULT) || ocrDocType.equals(DocType.FLIP)){
//            docType = DocType.ETCS;
//        }



        if (prevRd == null) {
            Image image = Image.builder()
                    .accrNo(importDto.getAcdNo())
                    .dmSeqno(importDto.getRctSeq())
                    .rqsReqId(importDto.getRqsReqId())
                    .imgId(imgFileInfoDto.getImgId())
                    .fileName(fileName)
                    .originFileName(imgFileInfoDto.getImgFileNm())
                    .isDup(isDup)
                    .duppedFile(duppedFile)
                    .hashValue(hashValue)
                    .isQa(visionResult.isQa())
                    .qaStatus(false)
                    .isInputRequired(visionResult.isInputRequired())
                    .imgType(docType)
                    .imageDocumentTypeOcr(ocrDocType)
                    .rawData(visionResult.getContent())
                    .imageProcessingResultCode(imageProcessingResultCode)
                    .sequence(sequence)
                    .build();
            imageRepository.save(image);
            log.info("[ImageService - saveImages] RdImage 데이터 생성 : {}", image.toString());
        } else {
            log.info("[ImageService - saveImages] RdImage 데이터 존재, 생성하지 않음, {}", imgFileInfoDto.getImgFileNm());
        }
    }

    @Transactional
    public void updateQaStatus(String receiptNo, String receiptSeq, boolean qaStatus){
        List<Image> images = imageRepository.findByKey(receiptNo, receiptNo, receiptSeq);

        for (Image image: images){
            image.updateQaStatus(qaStatus);
        }
    }

    @Transactional
    public void updateDuplicity(String rqsReqId, String receiptNo, String receiptSeq, String fileName, String duppedFile){
        Image image = imageRepository.findByKeyAndFileName(rqsReqId, receiptNo, receiptSeq, fileName).orElse(null);

        if (image != null){
            image.updateImageProcessingResultCode(ImageProcessingResultCode.DUPLICATE);
            image.updateDuppedFile(duppedFile);
        }

    }

//    @Transactional
//    public void updateImageAccuracy(String accrNo, String dmSeqno, ImageResultDto imageResultDto){
//
//        Image image = imageRepository.findByKeyAndFileName(accrNo, dmSeqno, imageResultDto.getImageDocumentId()).orElse(null);
//        if (image != null){
//            image.updateImageAccuary(imageResultDto.getImageAccuracy());
//        }
//    }

    @Transactional(readOnly = true)
    public List<ImageResponseDto> search(String rqsReqId, String receiptNo, String receiptSeq) {
        List<ImageResponseDto> imageResponseDtos = imageRepository.findByKey(rqsReqId, receiptNo, receiptSeq)
                .stream()
                .map(ImageResponseDto::new)
                .toList();

        log.debug("[KakaoImageService findByKey] size : {}", imageResponseDtos.size());
        return imageResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<ImageResponseDto> findByKey(String rqsReqId, String accrNo, String dmSeqno) {
        List<ImageResponseDto> imageResponseDtos = imageRepository.findByKey(rqsReqId, accrNo, dmSeqno)
                .stream()
                .map(ImageResponseDto::new)
                .toList();

        log.debug("[KakaoImageService findByKey] size : {}", imageResponseDtos.size());
        return imageResponseDtos;
    }

    @Transactional(readOnly = true)
    public ImageResponseDto findByKeyAndFileName(ImageSingleSearchRequestDto imageSingleSearchRequestDto) {
        return ImageResponseDto.of(imageRepository.findByKeyAndFileName(
                imageSingleSearchRequestDto.getRqsReqId(),
                imageSingleSearchRequestDto.getAccrNo(),
                imageSingleSearchRequestDto.getDmSeqno(),
                imageSingleSearchRequestDto.getFileName()
        ).orElse(null));
    }

    @Transactional(readOnly = true)
    public ImageResponseDto findByFileName(String rqsReqId, String fileName) {

        ImageResponseDto imageResponseDto = ImageResponseDto.of(imageRepository.findByFileName(rqsReqId, fileName).orElse(null));
        if (StringUtils.hasText(imageResponseDto.getDuppedFile())){
            Image duppedImage = imageRepository.findByFileName(rqsReqId, imageResponseDto.getDuppedFile()).orElse(null);
            if (duppedImage != null){
                imageResponseDto.setDuppedAccrNo(duppedImage.getAccrNo());
            }
        }

        return imageResponseDto;
    }

    @Transactional
    public void saveImageHash(ImageHash imageHash) {
        imageHashRepository.save(imageHash);
    }

    @Transactional(readOnly = true)
    public ImageHash findByHash(String hash) {
        return imageHashRepository.findByHash(hash).orElse(null);
    }

    @Transactional
    public void updateImageProcessingResultCode(String rqsReqId, String accrNo, String dmSeqno, String fileName, ImageProcessingResultCode imageProcessingResultCode) {
        Image image = imageRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName).orElse(null);
        if (image != null) {
            image.updateImageProcessingResultCode(imageProcessingResultCode);
        }
    }

    @Transactional
    public void updateCIPS(ImageDtoCIPS imageDtoCIPS) {
        String rqsReqId = imageDtoCIPS.getRqsReqId();
        String accrNo = imageDtoCIPS.getAccrNo();
        String dmSeqno = imageDtoCIPS.getDmSeqno();
        String fileName = imageDtoCIPS.getFileName();
        Image image = imageRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName).orElse(null);

        if (image == null) {
            log.error("[updateCIPS] image is null, fileName : {}", fileName);
        } else {
            if (!imageDtoCIPS.getKorDocType().equals(image.getImgType().getKorName())) {
                log.debug("[updateCIPS] DocType Changed : {}", imageDtoCIPS);
                image.updateDocType(DocType.getEnumByKorName(imageDtoCIPS.getKorDocType()));
            }
            if (!imageDtoCIPS.getResultCode().equals(image.getImageProcessingResultCode().getName())) {
                log.debug("[updateCIPS] ImageProcessingResultCode Changed : {}", imageDtoCIPS);
                image.updateImageProcessingResultCode(ImageProcessingResultCode.findByName(imageDtoCIPS.getResultCode()));
            }
        }

        List<Detail> details = detailRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName);


        // 삭제하고, 다시 저장
        for (Detail detail : details) {
            detailRepository.delete(detail);
        }
        detailRepository.save(Detail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo).dmSeqno(dmSeqno).fileName(fileName)
                .itemCode("CA0001")
                .itemName("자동차보험회사")
                .itemValue(imageDtoCIPS.getCa0001())
                .build());
        detailRepository.save(Detail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo).dmSeqno(dmSeqno).fileName(fileName)
                .itemCode("CA0002")
                .itemName("처리구분")
                .itemValue(imageDtoCIPS.getCa0002())
                .build());
        detailRepository.save(Detail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo).dmSeqno(dmSeqno).fileName(fileName)
                .itemCode("CA0003")
                .itemName("부상급항(급)")
                .itemValue(imageDtoCIPS.getCa0003())
                .build());
        detailRepository.save(Detail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo).dmSeqno(dmSeqno).fileName(fileName)
                .itemCode("CA0004")
                .itemName("부상급항(항)")
                .itemValue(imageDtoCIPS.getCa0004())
                .build());
        detailRepository.save(Detail.builder()
                .rqsReqId(rqsReqId)
                .accrNo(accrNo).dmSeqno(dmSeqno).fileName(fileName)
                .itemCode("CA0005")
                .itemName("피보험자명")
                .itemValue(imageDtoCIPS.getCa0005())
                .build());
    }


    @Transactional
    public void updateDIAG(ImageDtoDIAG imageDtoDIAG) {

        String rqsReqId = imageDtoDIAG.getRqsReqId();
        String accrNo = imageDtoDIAG.getAccrNo();
        String dmSeqno = imageDtoDIAG.getDmSeqno();
        String fileName = imageDtoDIAG.getFileName();
        Image image = imageRepository.findByFileName(rqsReqId, fileName).orElse(null);
        if (image == null) {
            log.error("[updateMDDG] image is null, fileName : {}", fileName);
        } else {
            if (!imageDtoDIAG.getKorDocType().equals(image.getImgType().getKorName())) {
                log.debug("[updateMDDG] DocType Changed : {}", imageDtoDIAG);
                image.updateDocType(DocType.getEnumByKorName(imageDtoDIAG.getKorDocType()));
            }
        }

        List<Detail> details = detailRepository.findByFileName(rqsReqId, fileName);

        // 삭제하고, 다시 저장
        for (Detail detail : details) {
            detailRepository.delete(detail);
        }

        // 진단일자(DA0004)는 진단코드 항목에 포함되기 때문에 따로 저장하지 않음
        detailRepository.save(Detail.create(image, ItemType.HSP_TYPE_CODE, imageDtoDIAG.getHs0001()));
        detailRepository.save(Detail.create(image, ItemType.HSP_BIZ_NO, imageDtoDIAG.getHs0002()));
        detailRepository.save(Detail.create(image, ItemType.HSP_NAME, imageDtoDIAG.getHs0003()));
        detailRepository.save(Detail.create(image, ItemType.HSP_ZIP_CODE, imageDtoDIAG.getHs0004()));
        detailRepository.save(Detail.create(image, ItemType.HSP_ADDRESS, imageDtoDIAG.getHs0005()));
//        detailRepository.save(Detail.create(image, ItemType.MDDG_DIAG_DATE, imageDtoDIAG.getDa0004()));
        detailRepository.save(Detail.create(image, ItemType.MDDG_DOCTOR_NAME, imageDtoDIAG.getDa0005()));
        detailRepository.save(Detail.create(image, ItemType.MDDG_LICENCE_NO, imageDtoDIAG.getDa0006()));

        List<DiagInfoExchangeDto> diagInfoExchangeDtos = imageDtoDIAG.getDiagList();
        for (DiagInfoExchangeDto diagInfoExchangeDto: diagInfoExchangeDtos){
            diagInfoExchangeDto.setDiagDate(imageDtoDIAG.getDa0004());
        }

        List<SurgInfoExchangeDto> surgInfoExchangeDtos = imageDtoDIAG.getSurgList();

        for (SurgInfoExchangeDto surgInfoExchangeDto: surgInfoExchangeDtos){
            surgInfoExchangeDto.setSurgDate(imageDtoDIAG.getEa0001());
        }
        DiagInfoExchangeDto mainDiag = diagInfoExchangeDtos.stream().filter(d -> d.getMnDgnYn().equals("주진단")).findFirst().orElse(null);
        if (mainDiag != null){
            for (SurgInfoExchangeDto surgInfoExchangeDto: surgInfoExchangeDtos){
                surgInfoExchangeDto.setDiagCode(mainDiag.getDsacd());
            }
        }

        diagInfoService.save(rqsReqId, accrNo, dmSeqno, fileName, diagInfoExchangeDtos);
        surgInfoService.save(rqsReqId, accrNo, dmSeqno, fileName, surgInfoExchangeDtos);

    }


    @Transactional
    public void updateETCS(ImageDtoETCS imageDtoETCS) {
        String rqsReqId = imageDtoETCS.getRqsReqId();
        String accrNo = imageDtoETCS.getAccrNo();
        String dmSeqno = imageDtoETCS.getDmSeqno();
        String fileName = imageDtoETCS.getFileName();
        Image image = imageRepository.findByFileName(rqsReqId, fileName).orElse(null);
        if (image == null) {
            log.error("[updateETCS] image is null, fileName : {}", fileName);
        } else {
            if (!imageDtoETCS.getKorDocType().equals(image.getImgType().getKorName())) {
                log.debug("[updateETCS] DocType Changed : {}", imageDtoETCS);
                image.updateDocType(DocType.getEnumByKorName(imageDtoETCS.getKorDocType()));

            }
            if (!imageDtoETCS.getResultCode().equals(image.getImageProcessingResultCode().getName())) {
                log.debug("[updateETCS] ImageProcessingResultCode Changed : {}", imageDtoETCS);
                image.updateImageProcessingResultCode(ImageProcessingResultCode.findByName(imageDtoETCS.getResultCode()));
            }

        }

        // 삭제
        List<Detail> details = detailRepository.findByKeyAndFileName(rqsReqId, accrNo, dmSeqno, fileName);
        for (Detail detail : details) {
            detailRepository.delete(detail);
        }
    }
//
//    @Transactional
//    public boolean checkDupImageContentsByImage(String fileName) {
//
//        boolean qaDuplicity = false;
//        boolean isDup = false;
//
//        Image image = imageRepository.findByFileName(fileName).orElse(null);
//
//        if (image == null){
//            log.debug("[checkDupImageContents] Image is null or isDup: filename : {} ", fileName);
//            return qaDuplicity;
//        }
//        String receiptNo = image.getAccrNo();
//        String receiptSeq = image.getDmSeqno();
//
//        List<Detail> details = detailRepository.findByKeyAndFileName(receiptNo, receiptSeq, image.getFileName());
//        Detail dateDetail = details.stream()
//                .filter(d -> d.getItemName().equals(ItemType.BIZ_DATE.getItemName()))
//                .findFirst().orElse(null);
//        Detail timeDetail = details.stream()
//                .filter(d -> d.getItemName().equals(ItemType.BIZ_TIME.getItemName()))
//                .findFirst().orElse(null);
//        Detail amountDetail = details.stream()
//                .filter(d -> d.getItemName().equals(ItemType.BIZ_TOTAL_AMOUNT.getItemName()))
//                .findFirst().orElse(null);
//        Detail acceptNoDetail = details.stream()
//                .filter(d -> d.getItemName().equals(ItemType.BIZ_ACCEPTNO.getItemName()))
//                .findFirst().orElse(null);
//
//        //승인번호, 거래일자, 사용금액 중 하나라도 없는 경우, 다음 영수증으로
//        if (acceptNoDetail == null || amountDetail == null || dateDetail == null){
//            log.debug("[checkDupImageContents] AcceptNo does not exist: receiptNo : {}, receiptSeq : {}, filename : {} ", receiptNo, receiptSeq, image.getFileName());
//            return qaDuplicity;
//        }
//
//        if (StringUtils.hasText(dateDetail.getItemValue()) &&
//                StringUtils.hasText(amountDetail.getItemValue()) &&
//                StringUtils.hasText(timeDetail.getItemValue())){
//            // 시간이 동일한 detail 목록 검색
//            List<Detail> duppedTimeDetails = detailRepository.findByItemNameAndItemValue(timeDetail.getFileName(), timeDetail.getItemName(), timeDetail.getItemValue(), timeDetail.getCreatedDate());
//
//            // 시간 일치 확인
//            for (Detail duppedDetail: duppedTimeDetails){
//
//                List<Detail> dupCandids = detailRepository.findByFileName(duppedDetail.getFileName());
//                boolean dateDup = dupCandids.stream().anyMatch(d -> d.getItemName().equals(ItemType.BIZ_DATE.getItemName()) && d.getItemValue().equals(dateDetail.getItemValue()));
//                boolean amountDup = dupCandids.stream().anyMatch(d -> d.getItemName().equals(ItemType.BIZ_TOTAL_AMOUNT.getItemName()) && d.getItemValue().equals(amountDetail.getItemValue()));
//
//                // 일자, 금액 일치 여부 확인
//                if (dateDup && amountDup){
//                    log.debug("[checkDupImageContents] Dupped contents exist, dupped contents: receiptNo : {}, receiptSeq : {}, filename : {} ", receiptNo, receiptSeq, image.getFileName());
//                    if (!isDup){
//                        updateDuplicity(receiptNo, receiptSeq, image.getFileName(), duppedDetail.getFileName());
//                    }
//                    isDup = true;
//                } else{
//                    continue;
//                }
//
//                Detail duppedAcceptNoDetail = dupCandids.stream()
//                        .filter(d -> d.getItemName().equals(ItemType.BIZ_ACCEPTNO.getItemName()) && d.getItemValue().equals(acceptNoDetail.getItemValue()))
//                        .findFirst().orElse(null);
//
//                // 승인번호가 양쪽에 존재하고, 0.8 이상이고 승인번호가 동일한 경우, 자동회신 가능
//                // 이외에는 중복처리는 하나, QA 배당
//                if (StringUtils.hasText(acceptNoDetail.getItemValue()) &&
//                        acceptNoDetail.getAccuracy() >= 0.8 &&
//                        duppedAcceptNoDetail != null &&
//                        StringUtils.hasText(duppedAcceptNoDetail.getItemValue()) &&
//                        duppedAcceptNoDetail.getAccuracy() >= 0.8 &&
//                        duppedAcceptNoDetail.getItemValue().equals(acceptNoDetail.getItemValue())){
//                    log.debug("[checkDupImageContents] AcceptNo equals: receiptNo : {}, receiptSeq : {}, filename : {} ", receiptNo, receiptSeq, image.getFileName());
//                } else{
//                    log.debug("[checkDupImageContents] AcceptNo not accurate: receiptNo : {}, receiptSeq : {}, filename : {} ", receiptNo, receiptSeq, image.getFileName());
//                    qaDuplicity = true;
//                    image.updateQaReason(QaReason.DUPLICITY.getMessage());
//                }
//            }
//        }
//
//        if (!isDup){
//            log.debug("[checkDupImageContents] No duplicate contents: receiptNo : {}, receiptSeq : {}, filename : {} ", receiptNo, receiptSeq, image.getFileName());
//            image.updateNormal();
//        }
//
//        return qaDuplicity;
//    }
}
