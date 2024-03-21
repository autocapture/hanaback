package com.aimskr.ac2.hana.backend.core.phone.domain;

import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import com.aimskr.ac2.common.domain.BaseTimeEntity;

import com.aimskr.ac2.common.enums.detail.ItemType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "phone_repair"
)
public class PhoneRepair extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 파일송수신일련번호
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

    public static PhoneRepair build(Image image) {
        return PhoneRepair.builder()
                .accrNo(image.getAccrNo())
                .dmSeqno(image.getDmSeqno())
                .fileName(image.getFileName())
                .build();
    }

    public static PhoneRepair create(Image image, ItemType itemType, String itemValue) {
        PhoneRepair phoneRepair = PhoneRepair.build(image);
        phoneRepair.updateItemType(itemType);
        phoneRepair.updateItemValue(itemValue);
        phoneRepair.updateAccuracy(1.0);
        return phoneRepair;
    }
    public String calcKey() {
        return "[key] : " + this.accrNo + "/" + this.dmSeqno;
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
