package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDto {
    // 사고번호 202301A0102
    private String accrNo;

    // 이미지명 1672647244634.jpeg
    private String imageName;

    // 제조사 SAMSUNG, APPLE
    private String manufacturer;

    // 모델코드 SM-G998N, IPHONE 13 PRO MAX GPH 128GBKO
    private String modelCode;

    // 구분 공임비, 부품비
    private String division;

    // 부품코드 GH82-24597A, 661-22309, 공백
    private String itemCode;

    // 부품명 수리비/SM-G998N/손상/파손 관련 수리, Display, iPhone 13 Pro Max
    private String itemName;

    // 공급가액 22728
    private int supplyPrice;

    // 세액 2272
    private int tax;

    // 금액 499000
    private int amount;
}
