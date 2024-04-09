package com.aimskr.ac2.hana.backend.core.detail.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.common.enums.detail.ItemType;
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
        name = "detail"
)
public class Detail extends BaseTimeEntity {
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

    public static Detail build(Image image) {
        return Detail.builder()
                .rqsReqId(image.getRqsReqId())
                .accrNo(image.getAccrNo())
                .dmSeqno(image.getDmSeqno())
                .fileName(image.getFileName())
                .build();
    }

    public static Detail create(Image image, ItemType itemType, String itemValue) {
        Detail detail = Detail.build(image);
        detail.updateItemType(itemType);
        detail.updateItemValue(itemValue);
        detail.updateAccuracy(1.0);
        return detail;
    }
    public String calcKey() {
        return "[key] : " + this.rqsReqId + "/" + this.accrNo + "/" + this.dmSeqno;
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
}
