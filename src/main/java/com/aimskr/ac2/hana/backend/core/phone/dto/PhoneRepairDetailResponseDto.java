package com.aimskr.ac2.hana.backend.core.phone.dto;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetail;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRepairDetailResponseDto {

    private String accrNo;
    // 접수회차
    private String dmSeqno;
    // 파일명
    private String fileName;

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
    // 심사의견
    private String accdType;
    // 심사의견상세
    private String accdTypeDtl;
    // 사유
    private String accdTypeNm;

    public PhoneRepairDetail toEntity(){

        return PhoneRepairDetail.builder()
                .accrNo(accrNo)
                .dmSeqno(dmSeqno)
                .fileName(fileName)
                .stdCd(stdCd)
                .accdCd(accdCd)
                .accdNm(accdNm)
                .accdPriceAmt(accdPriceAmt)
                .accdTaxAmt(accdTaxAmt)
                .accdAmt(accdAmt)
                .accdType(accdType)
                .accdTypeNm(accdTypeNm)
                .build();

    }

    public PhoneRepairDetailResponseDto(PhoneRepairDetail entity) {
        this.accrNo = entity.getAccrNo();
        this.dmSeqno = entity.getDmSeqno();
        this.fileName = entity.getFileName();
        this.stdCd = entity.getStdCd();
        this.accdCd = entity.getAccdCd();
        this.accdNm = entity.getAccdNm();
        this.accdPriceAmt = entity.getAccdPriceAmt();
        this.accdTaxAmt = entity.getAccdTaxAmt();
        this.accdAmt = entity.getAccdAmt();
        this.accdType = entity.getAccdType();
        this.accdTypeNm = entity.getAccdTypeNm();
    }

}
