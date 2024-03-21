package com.aimskr.ac2.hana.backend.core.phone.dto;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepair;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetail;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRepairDetailRequestDto {
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 파일송수신일련번호
    // 표준코드
    private String stdCd;
    // 부품코드
    private String accdCd;
    // 부품명
    private String accdNm;
    // 공급가액
    private int accdPriceAmt;
    // 세액
    private int accdTaxAmt;
    // 금액
    private int accdAmt;
    // 부품코드 분류
    private String accdType;

    public PhoneRepairDetail toEntity(){
        return PhoneRepairDetail.builder()
                .stdCd(stdCd)
                .accdCd(accdCd)
                .accdNm(accdNm)
                .accdPriceAmt(accdPriceAmt)
                .accdTaxAmt(accdTaxAmt)
                .accdAmt(accdAmt)
                .accdType(accdType)
                .build();
    }
}
