package com.aimskr.ac2.hana.backend.core.image.dto;

import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponseDto {
    private Long id;
    // 접수번호
    private String accrNo;
    // 접수회차
    private String dmSeqno;
    private String rqsReqId;
    // 원본파일명
    private String imgId;
    // 파일명 : /images/ac/ 에서 관리
    private String fileName;
    private String originFileName;

    private String imgDcmNo;
    private String imgDcmflNo;
    // sequence
    private Integer sequence;
    // hash 값
    private String hashValue;
    // 중복 대상 이미지
    private String duppedFile;
    // QA여부
    private Boolean isQa;
    // QA상태
    private Boolean qaStatus;
    // 이미지 중복 여부 -- 중복 입력과는 다름, 중복 이미지
    private Boolean isDup;
    // 서류 유형
    private DocType docType;
    // 서류 유형
    private String rawData;
    // 이미지 정확도
    private Double imageAccuracy;
    // 이미지 처리 결과
    private ImageProcessingResultCode imageProcessingResultCode;

    private String duppedAccrNo;
    private String qaReason;


    public ImageResponseDto(Image image) {
        this.id = image.getId();
        this.accrNo = image.getAccrNo();
        this.dmSeqno = image.getDmSeqno();
        this.rqsReqId = image.getRqsReqId();
        this.imgDcmNo = image.getImgDcmNo();
        this.imgDcmflNo = image.getImgDcmflNo();
        this.imgId = image.getImgId();
        this.fileName = image.getFileName();
        this.originFileName = image.getOriginFileName();
        this.sequence = image.getSequence();
        this.hashValue = image.getHashValue();
        this.duppedFile = image.getDuppedFile();
        this.isQa = image.getIsQa();
        this.qaStatus = image.getQaStatus();
        this.isDup = image.getIsDup();
        this.docType = image.getImgType();
        this.rawData = image.getRawData();
        this.imageAccuracy = image.getImageAccuracy();
        this.imageProcessingResultCode = image.getImageProcessingResultCode();
        this.qaReason = image.getQaReason();
    }

    // Entity to DTO
    public static ImageResponseDto of(Image image) {
        if (image == null) return null;
        return ImageResponseDto.builder()
                .id(image.getId())
                .accrNo(image.getAccrNo())
                .dmSeqno(image.getDmSeqno())
                .imgId(image.getImgId())
                .fileName(image.getFileName())
                .originFileName(image.getOriginFileName())
                .sequence(image.getSequence())
                .hashValue(image.getHashValue())
                .duppedFile(image.getDuppedFile())
                .isQa(image.getIsQa())
                .qaStatus(image.getQaStatus())
                .isDup(image.getIsDup())
                .docType(image.getImgType())
                .rawData(image.getRawData())
                .imageAccuracy(image.getImageAccuracy())
                .imageProcessingResultCode(image.getImageProcessingResultCode())
                .qaReason(image.getQaReason())
                .build();
    }

    public String getKorDocType() {
        if (this.docType == null) return "";
        return this.docType.getKorName();
    }

    public String getResultCode() {
        if (this.imageProcessingResultCode == null) return "";
        return this.imageProcessingResultCode.getName();
    }
}
