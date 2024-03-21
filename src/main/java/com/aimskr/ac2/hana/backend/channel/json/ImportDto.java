package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.common.util.DateUtil;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Schema(description = "카카오페이손보 요청접수 DTO")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class ImportDto {
    public static final String VALID = "VALID";
    public static final String DUP = "DUP";

    @Schema(description = "의뢰요청ID")
    private String RQS_REQ_ID;
    @Schema(description = "사고번호")
    private String ACD_NO;
    @Schema(description = "접수순번")
    private String RCT_SEQ;
    @Schema(description = "사고일자")
    private String ACD_DT;
    @Schema(description = "청구유형코드")
    private String CLM_TP_CD;
    @Schema(description = "사고원인대분류코드")
    private String ACD_CAUS_LCTG_CD;
    @Schema(description = "피해자명")
    private String DMPE_NM;
    @Schema(description = "피보험자생년월일")
    private String INSPE_BDT;
    @Schema(description = "요청일시")
    private String REQ_DTM;
    @Schema(description = "이미지장수")
    private int IMG_CNT;

    @Schema(description = "이미지목록")
    private List<ImgFileInfoDto> IMG_LST;
    @Schema(description = "추가제공정보")
    private List<DescriptionDto> ADD_OFR_INF;


    public String checkValid() {
        boolean isValid = true;
        String result = "[요청전문오류] ";

        if (!StringUtils.hasText(ACD_NO)) {
            isValid = false;
            result += "/ 접수번호 없음";
        }
        if (!StringUtils.hasText(RCT_SEQ)) {
            isValid = false;
            result += "/ 접수회차 없음";
        }
        if (!StringUtils.hasText(REQ_DTM)) {
            isValid = false;
            result += "/ 호출시간 없음";
        } else if (!DateUtil.checkDateFormat(REQ_DTM, DateUtil.DATETIME_HANA)) {
            isValid = false;
            result += "/ 호출시간 형식오류 - " + REQ_DTM;
        }
        if (IMG_CNT < 1) {
            isValid = false;
            result += "/ 이미지수 없음";
        }
//        if (!StringUtils.hasText(nrdNm)) {
//            isValid = false;
//            result += "/ 피보험자명 없음";
//        }
        if (IMG_LST == null || IMG_LST.isEmpty()) {
            isValid = false;
            result += "/ 이미지목록 없음";
        } else if (IMG_CNT != IMG_LST.size()) {
            isValid = false;
            result += "/ 이미지수 불일치 - " + IMG_CNT + " vs " + IMG_LST.size();
        }

        //기관명 검증

        if (isValid) return ImportDto.VALID;

        return result;
    }

    // DTO to Entity
    public Assign toEntity() {

        //요청 접수 후 최초 접수상태의 Assign Entity 생성
        return Assign.builder()
                .rqsReqId(RQS_REQ_ID)
                .accrNo(ACD_NO)
                .dmSeqno(RCT_SEQ)
                .clmTpCd(CLM_TP_CD)
                .acdCausLctgCd(ACD_CAUS_LCTG_CD)
                .acdDt(ACD_DT)
                .rqstTime(DateUtil.convertLocalDateTimeFromNano(REQ_DTM))
                .nrdNm(DMPE_NM)
                .nrdBirth(INSPE_BDT)
                .imgCnt(IMG_CNT)
                .build();
    }


    @Builder
    public ImportDto(String RQS_REQ_ID,
                     String ACD_NO,
                     String RCT_SEQ,
                     String ACD_DT,
                     String CLM_TP_CD,
                     String ACD_CAUS_LCTG_CD,
                     String DMPE_NM,
                     String INSPE_BDT,
                     String REQ_DTM,
                     int IMG_CNT,
                     List<ImgFileInfoDto> IMG_LST,
                     List<DescriptionDto> ADD_OFR_INF) {

        this.RQS_REQ_ID = RQS_REQ_ID;
        this.ACD_NO = ACD_NO;
        this.RCT_SEQ = RCT_SEQ;
        this.ACD_DT = ACD_DT;
        this.CLM_TP_CD = CLM_TP_CD;
        this.ACD_CAUS_LCTG_CD = ACD_CAUS_LCTG_CD;
        this.DMPE_NM = DMPE_NM;
        this.INSPE_BDT = INSPE_BDT;
        this.REQ_DTM = REQ_DTM;
        this.IMG_CNT = IMG_CNT;
        this.IMG_LST = IMG_LST;
        this.ADD_OFR_INF = ADD_OFR_INF;

    }

    public String getKey() {
        return this.ACD_NO + "_" + this.RCT_SEQ;
    }

}
