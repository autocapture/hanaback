package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalImageResultDto {

    // 영수증ID
    private String rtxId;
    // 영수증구분코드
    private String rtxFlgCd;
    // 피보험자일치여부코드
    private String nrdAreaEqlYncd;
    // 데이터정합성여부코드
    private String datConstYncd;
    // 사업자등록번호
    private String bprNo;
    // 사업자명
    private String bzpNm;
    // 병원종별코드
    private String hspSpecd;
    // 입통원구분코드
    private String hagthFlgcd;
    // 진료과목코드
    private String trmtSbjcd;
    // 보험처리구분코드
    private String insDlFlgcd;
    // 급여일부본인부담금액총액
    private Long slrPartSeleChamtTamt;
    // 급여공단부담금액총액
    private Long slrPartPbcdChamtTamt;
    // 급여전액본인부담금액총액
    private Long slrTamtSeleChamtTamt;
    // 비급여선택진료금액총액
    private Long nslySlcTrmtamtTamt;
    // 비급여선택진료외금액총액
    private Long nslySlcTrmtOutamtTamt;
    // 진료비총금액
    private Long trpaTamt;
    // 환자부담금액합계
    private Long ptChamtSm;
    // 기납부금액
    private Long prvPyamt;
    // 할인금액
    private Long dcAmt;
    // 수납금액
    private Long rvAmt;
    // 치료시작일자
    private LocalDateTime rmdyStrdt;
    // 치료종료일자
    private LocalDateTime rmdyNddt;


    private List<MedicalReceiptItemDto> mdcsItInfo;
    private List<RdDetailDto> dtClcSpcInfo;
    private List<DiagDocInfoDto> dgndcInfo;


}
