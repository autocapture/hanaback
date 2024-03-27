package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.common.util.DateUtil;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportDto {
    public static final String VALID = "VALID";
    public static final String DUP = "DUP";

    @JsonProperty("RQS_REQ_ID")
    private String rqsReqId;
    @JsonProperty("ACD_NO")
    private String acdNo;
    @JsonProperty("RCT_SEQ")
    private String rctSeq;
    @JsonProperty("ACD_DT")
    private String acdDt;
    @JsonProperty("CLM_TP_CD")
    private String clmTpCd;
    @JsonProperty("ACD_CAUS_LCTG_CD")
    private String acdCausLctgCd;
    @JsonProperty("RQS_TP_CD")
    private String rqsTpCd;
    @JsonProperty("DMPE_NM")
    private String dmpeNm;
    @JsonProperty("INSPE_BDT")
    private String inspeBdt;
    @JsonProperty("REQ_DTM")
    private String reqDtm;
    @JsonProperty("IMG_CNT")
    private int imgCnt;

    @JsonProperty("IMG_LST")
    private List<ImgFileInfoDto> imgLst;
    @JsonProperty("ADD_OFR_INF")
    private List<DescriptionDto> addOfrInf;

    public String calcReqDate() {
        if (reqDtm != null && reqDtm.length() > 10) {
            return reqDtm.substring(0, 4) + reqDtm.substring(5, 7) + reqDtm.substring(8, 10);
        } else {
            return "99999999";
        }
    }

    public String checkValid() {
        boolean isValid = true;
        String result = "[요청전문오류] ";

        if (!StringUtils.hasText(acdNo)) {
            isValid = false;
            result += "/ 접수번호 없음";
        }
        if (!StringUtils.hasText(rctSeq)) {
            isValid = false;
            result += "/ 접수회차 없음";
        }
        if (!StringUtils.hasText(reqDtm)) {
            isValid = false;
            result += "/ 호출시간 없음";
        } else if (!DateUtil.checkDateFormat(reqDtm, DateUtil.DATETIME_HANA)) {
            isValid = false;
            result += "/ 호출시간 형식오류 - " + reqDtm;
        }
        if (imgCnt < 1) {
            isValid = false;
            result += "/ 이미지수 없음";
        }
        if (imgLst == null || imgLst.isEmpty()) {
            isValid = false;
            result += "/ 이미지목록 없음";
        } else if (imgCnt != imgLst.size()) {
            isValid = false;
            result += "/ 이미지수 불일치 - " + imgCnt + " vs " + imgLst.size();
        }

        //기관명 검증

        if (isValid) return ImportDto.VALID;

        return result;
    }

    // DTO to Entity
    public Assign toEntity() {

        //요청 접수 후 최초 접수상태의 Assign Entity 생성
        return Assign.builder()
                .rqsReqId(rqsReqId)
                .accrNo(acdNo)
                .dmSeqno(rctSeq)
                .clmTpCd(clmTpCd)
                .acdCausLctgCd(acdCausLctgCd)
                .acdDt(acdDt)
                .rqstTime(DateUtil.convertLocalDateTimeFromNano(reqDtm))
                .nrdNm(dmpeNm)
                .nrdBirth(inspeBdt)
                .imgCnt(imgCnt)
                .build();
    }

    public String getKey() {
        return this.acdNo + "_" + this.rctSeq;
    }

}
