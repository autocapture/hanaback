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
        name = "ai_phone_repair"
)
public class AiPhoneRepair extends BaseTimeEntity {
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

    public static AiPhoneRepair build(Image image) {
        return AiPhoneRepair.builder()
                .accrNo(image.getAccrNo())
                .dmSeqno(image.getDmSeqno())
                .fileName(image.getFileName())
                .build();
    }

    public void updateKey(String accrNo, String dmSeqno, String fileName) {
        this.accrNo = accrNo;
        this.dmSeqno =dmSeqno;
        this.fileName = fileName;
    }

    public void updateValue(String itemValue){
        this.itemValue = itemValue;
    }

    public void updateCodeName(ItemType itemType){
        this.itemCode = itemType.getItemCode();
        this.itemName = itemType.getItemName();
    }

    public void updateAccuracy(Double accuracy){
        this.accuracy = accuracy;
    }

    public void updateItemCode(String itemCode){
        this.itemCode = itemCode;
    }

    public PhoneRepair toDetail(){
        return PhoneRepair.builder()
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .itemCode(itemCode)
                .itemName(itemName)
                .itemValue(itemValue)
                .accuracy(accuracy).build();
    }
}
