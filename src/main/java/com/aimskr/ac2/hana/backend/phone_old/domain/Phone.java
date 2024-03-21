package com.aimskr.ac2.hana.backend.phone_old.domain;

import lombok.*;

import com.aimskr.ac2.common.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(
        name = "phone"
)
public class Phone extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 사고번호 202301A0102
    @Column
    private String accrNo;

    // 이미지명 1672647244634.jpeg
    @Column
    private String imageName;

    // 제조사 SAMSUNG, APPLE
    @Column
    private String manufacturer;

    // 모델코드 SM-G998N, IPHONE 13 PRO MAX GPH 128GBKO
    @Column
    private String modelCode;

    // 구분 공임비, 부품비
    @Column
    private String division;

    // 부품코드 GH82-24597A, 661-22309, 공백
    @Column
    private String itemCode;

    // 부품명 수리비/SM-G998N/손상/파손 관련 수리, Display, iPhone 13 Pro Max
    @Column
    private String itemName;

    // 공급가액 22728
    @Column
    private String supplyPrice;

    // 세액 2272
    @Column
    private String tax;

    // 금액 499000
    @Column
    private String amount;

    @Column(columnDefinition = "TEXT")
    private String labelString;

    public void update(SubPhone subPhone) {
        this.division = subPhone.getDivision();
        this.itemCode = subPhone.getItemCode();
        this.itemName = subPhone.getItemName();
        this.supplyPrice = subPhone.getSupplyPrice();
        this.tax = subPhone.getTax();
        this.amount = subPhone.getAmount();
    }
}
