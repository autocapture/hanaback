package com.aimskr.ac2.hana.backend.vision.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "ai_detail"
)
public class AiDetail extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    // 전문 Key : 의뢰요청ID or 접수번호, 전송회차
    // 외뢰요청ID
    @Column
    private String rqsReqId;
    // 접수번호
    @Column(length = 16)
    private String accrNo;
    // 접수회차
    @Column(length = 5)
    private String dmSeqno;
    // 파일명
    @Column(length = 255)
    private String fileName;
    // 항목코드
    @Column(length = 10)
    private String itemCode;
    // 항목명
    @Column(length = 50)
    private String itemName;
    // 항목값
    @Column
    private String itemValue;
    // 정확도
    @Column
    private Double accuracy;

    public static AiDetail build(Image image) {
        return AiDetail.builder()
                .rqsReqId(image.getRqsReqId())
                .accrNo(image.getAccrNo())
                .dmSeqno(image.getDmSeqno())
                .fileName(image.getFileName())
                .build();
    }
    public void updateItemType(ItemType itemType) {
        this.itemCode = itemType.getItemCode();
        this.itemName = itemType.getItemName();
    }
    public void updateItemValue(String itemValue) { this.itemValue = itemValue; }
    public void updateAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public void addAccuracy(double addition){
        this.accuracy = Math.min(1.0, getAccuracy() + addition);
    }

    public void updateKey(String rqsReqId, String accrNo, String dmSeqno, String fileName) {
        this.rqsReqId = rqsReqId;
        this.accrNo = accrNo;
        this.dmSeqno = dmSeqno;
        this.fileName = fileName;
    }

    public void updateValue(String s) {
        this.itemValue = s;
    }

    public Detail toDetail() {
        return Detail.builder()
                .rqsReqId(this.rqsReqId)
                .accrNo(this.accrNo)
                .dmSeqno(this.dmSeqno)
                .fileName(this.fileName)
                .itemCode(this.itemCode)
                .itemName(this.itemName)
                .itemValue(this.itemValue)
                .accuracy(this.accuracy)
                .build();
    }
}
