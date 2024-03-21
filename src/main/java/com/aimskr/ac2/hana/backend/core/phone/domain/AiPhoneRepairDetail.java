package com.aimskr.ac2.hana.backend.core.phone.domain;


import com.aimskr.ac2.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "ai_phone_repair_detail"
)
public class AiPhoneRepairDetail extends BaseTimeEntity {

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

    // 표준코드
    @Column(length = 10)
    private String stdCd;
    // 부품코드
    @Column(length = 10)
    private String accdCd;
    // 부품명
    @Column(length = 10)
    private String accdNm;
    // 공급가액
    @Column(length = 10)
    private Integer accdPriceAmt;
    // 세액
    @Column(length = 10)
    private Integer accdTaxAmt;
    // 금액
    @Column(length = 10)
    private Integer accdAmt;
    // 심사의견
    @Column
    private String accdType;
    // 심사의견 상세
    @Column
    private String accdTypeDtl;
    // 사유
    @Column
    private String accdTypeNm;


    @Builder
    public AiPhoneRepairDetail(String accrNo,
                               String dmSeqno,
                               String fileName,
                               String stdCd,
                               String accdCd,
                               String accdNm,
                               int accdAmt,
                               String accdType
                      ) {

        this.accrNo = accrNo;
        this.dmSeqno = dmSeqno;
        this.fileName = fileName;
        this.stdCd = stdCd;
        this.accdCd = accdCd;
        this.accdNm = accdNm;
        this.accdAmt = accdAmt;
        this.accdType = accdType;
    }

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
                    .accdTypeDtl(accdTypeDtl)
                    .accdTypeNm(accdTypeNm)
                    .build();
    }


}
