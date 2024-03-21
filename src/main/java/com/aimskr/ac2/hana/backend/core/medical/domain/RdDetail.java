package com.aimskr.ac2.hana.backend.core.medical.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "rd_detail"
)
public class RdDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16)
    private String accrNo;
    // 접수회차
    @Column(length = 5)
    private String dmSeqno;
    // 파일명
    @Column(length = 255)
    private String fileName;
    // 영수증ID
    @Column
    private String rtxId;
    // 세부의료비ID
    @Column
    private String dtMdcsId;
    // 영수증금액일치여부코드
    @Column
    private String rtxAmtEqalYncd;
    // 불일치내역코드
    @Column
    private String dscrdSpccd;
    // 병원의료비항목명
    @Column
    private String hspMdcsItnm;
    // 진료일자
    @Column
    private LocalDateTime trmtDt;
    // EDI코드세부
    @Column
    private String ediCdDt;
    // EDI코드명칭
    @Column
    private String ediCdnm;
    // 기준단가
    @Column
    private Integer stUnprc;
    // 기준횟수
    @Column
    private Integer stCt;
    // 기준일수
    @Column
    private Integer stDays;
    // EDI총액
    @Column
    private Integer ediTamt;
    // 급여전액본인부담금액
    @Column
    private Integer slrTamtSeleChamt;
    // 비급여선택금액
    @Column
    private Integer nslySlcAmt;
    // 비급여선택외금액
    @Column
    private Integer nslySlcOutAmt;
    // 표준코드
    @Column
    private String stcd;
    // 표준코드명
    @Column
    private String stdCdnm;
    // 심평원급여구분코드
    @Column
    private String hiraSlrFlgcd;
    // 당사급여구분코드
    @Column
    private String thcpSlrFlgcd;
    // 상위분류코드
    @Column
    private String pprCsfcd;
    // 하위분류코드
    @Column
    private String sbdCsfcd;
    // 심사의견코드
    @Column
    private String udCtncd;

}
