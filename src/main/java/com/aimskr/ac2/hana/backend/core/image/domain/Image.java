package com.aimskr.ac2.hana.backend.core.image.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "image"
)
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 기본 정보 Section (KB에서 접수한 전문의 공통 KEY)
     */
    // 전문 Key : 접수번호, 접수회차
    // 접수번호
    @Column(length = 16)
    private String accrNo;
    // 접수회차
    @Column(length = 5)
    private String dmSeqno;
    @Column
    private String rqsReqId;
    // 이미지번호
    @Column
    private String imgDcmNo;
    // 이미지문서철번호
    @Column
    private String imgDcmflNo;
    // 이미지문서 ID
    @Column(length = 255)
    private String imgId;
    // 파일명
    @Column(length = 255)
    private String fileName;
    // 파일명
    @Column(length = 255)
    private String originFileName;
    /**
     * 기술적 처리 영역
     */
    // Sequence
    @Column
    private Integer sequence;
    // hash 값
    @Column(length = 32)
    private String hashValue;
    // 이미지 중복 여부 -- 중복 입력과는 다름, 중복 이미지
    @Column(columnDefinition = "boolean default false")
    private Boolean isDup;
    // 중복 대상 이미지
    @Column(length = 50)
    private String duppedFile;

    /**
     * Biz 처리 영역
     */
    // QA여부
    @Column(columnDefinition = "boolean default false")
    private Boolean isQa;
    // 입력필요여부
    @Column(columnDefinition = "boolean default true")
    private Boolean isInputRequired;

    /**
     * 처리 결과값
     */
    // 서류 입력 정확도
    @Column
    private Double imageAccuracy;
    // 서류 유형
    @Enumerated(value = EnumType.STRING)
    @Column
    private DocType imgType;

    @Enumerated(value = EnumType.STRING)
    @Column
    private DocType imageDocumentTypeOcr;

    // QA 상태
    @Column(columnDefinition = "boolean default false")
    private Boolean qaStatus;
    @Enumerated(EnumType.STRING)
    @Column
    private ImageProcessingResultCode imageProcessingResultCode;
    @Column(length = 50)
    private String imageProcessingContent;

    @Column(columnDefinition = "TEXT")
    private String rawData;

    @Column(columnDefinition = "TEXT")
    private String qaReason;

    public void updateImageAccuracy(Double imageAccuracy) {
        this.imageAccuracy = imageAccuracy;
    }
    public void updateDocType(DocType docType) {
        this.imgType = docType;
    }

    public void updateQaStatus(Boolean qaStatus) {
        this.qaStatus = qaStatus;
    }

    public void updateImageProcessingResultCode(ImageProcessingResultCode imageProcessingResultCode){
        this.imageProcessingResultCode = imageProcessingResultCode;
        if (imageProcessingResultCode == ImageProcessingResultCode.DUPLICATE) {
            this.isDup = true;
        }
    };

    public void updateIsDup(Boolean isDup){
        this.isDup = isDup;
    }

    public void updateQaReason(String qaReason){
        this.qaReason = qaReason;
    }

    public void updateNormal(){
        this.isDup = false;
        this.duppedFile = null;
        this.imageProcessingResultCode = ImageProcessingResultCode.NORMAL;
        this.qaReason = null;
    }

    public void updateDuppedFile(String duppedFile){
        this.duppedFile = duppedFile;
    }

}
