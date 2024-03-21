package com.aimskr.ac2.hana.backend.channel.json;


import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RdDetailDto {

    // 영수증ID
    private String rtxId;
    // 세부의료비ID
    private String dtMdcsId;
    // 영수증금액일치여부코드
    private String rtxAmtEqalYncd;
    // 불일치내역코드
    private String dscrdSpccd;
    // 병원의료비항목명
    private String hspMdcsItnm;
    // 진료일자
    private LocalDateTime trmtDt;
    // EDI코드세부
    private String ediCdDt;
    // EDI코드명칭
    private String ediCdnm;
    // 기준단가
    private Integer stUnprc;
    // 기준횟수
    private Integer stCt;
    // 기준일수
    private Integer stDays;
    // EDI총액
    private Integer ediTamt;
    // 급여전액본인부담금액
    private Integer slrTamtSeleChamt;
    // 비급여선택금액
    private Integer nslySlcAmt;
    // 비급여선택외금액
    private Integer nslySlcOutAmt;
    // 표준코드
    private String stcd;
    // 표준코드명
    private String stdCdnm;
    // 심평원급여구분코드
    private String hiraSlrFlgcd;
    // 당사급여구분코드
    private String thcpSlrFlgcd;
    // 상위분류코드
    private String pprCsfcd;
    // 하위분류코드
    private String sbdCsfcd;
    // 심사의견코드
    private String udCtncd;


}
