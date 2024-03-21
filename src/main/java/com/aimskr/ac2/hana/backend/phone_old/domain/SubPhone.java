package com.aimskr.ac2.hana.backend.phone_old.domain;

import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SubPhone {
    // 구분 공임비, 부품비

    private String division;

    // 부품코드 GH82-24597A, 661-22309, 공백

    private String itemCode;

    // 부품명 수리비/SM-G998N/손상/파손 관련 수리, Display, iPhone 13 Pro Max

    private String itemName;

    // 공급가액 22728

    private String supplyPrice;

    // 세액 2272

    private String tax;

    // 금액 499000

    private String amount;
}
