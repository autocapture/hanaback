package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 결과전문
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto {

    public static final String VALID = "VALID";
    public static final String INVALID = "INVALID";
    // 의뢰요청ID
    @JsonProperty("RQS_REQ_ID")
    private String rqsReqId;
    // 파일송수신일련번호
    @JsonProperty("ACD_NO")
    private String acdNo;
    // 팩스일련번호
    @JsonProperty("RCT_SEQ")
    private String rctSeq;
    // 사고일자
    @JsonProperty("ACD_DT")
    private String acdDt;
    // 사고유형
    @JsonProperty("CLM_TP_CD")
    private String clmTpCd;
    // 사고원인대분류코드
    @JsonProperty("ACD_CAUS_LCTG_CD")
    private String acdCausLctgCd;
    // 처리일시
    @JsonProperty("PCS_DTM")
    private String pcsDtm;
    // AIMS 처리결과 코드
    @JsonProperty("PCS_RSL_CD")
    private String pcsRslCd;
    // AIMS 처리결과코드 상세
    @JsonProperty("PCS_RSL_DTL_CD")
    private String pcsRslDtlCd;

    @JsonProperty("IMG_LST")
    List<? extends ImageResultDto> imgList;

    public static ResultDto of(Assign assign) {
        return ResultDto.builder()
                .rqsReqId(assign.getRqsReqId())
                .acdNo(assign.getAccrNo())
                .rctSeq(assign.getDmSeqno())
                .acdDt(assign.getAcdDt())
                .clmTpCd(assign.getClmTpCd().getCode())
                .acdCausLctgCd(assign.getAcdCausLctgCd().getCode())
                .pcsRslCd(assign.getProcessResponseCode() == null ? "" : assign.getProcessResponseCode().getCode())
                .pcsRslDtlCd(Optional.ofNullable(assign.getProcessResponseCodeDetail()).orElse(""))
                .pcsDtm(assign.getResultDeliveryTime().format(DateTimeFormatter.ofPattern(DateUtil.DATETIME_HANA)))
                // 이미지는 별도로 추가함
                .imgList(null)
                .build();
    }

    public static ResultDto buildErrorResult(ImportDto importDto) {
        LocalDateTime now = LocalDateTime.now();
        return ResultDto.builder()
                .acdNo(importDto.getAcdNo())
                .rctSeq(importDto.getRctSeq())
                .pcsRslCd(ProcessResponseCode.ERROR.getCode())
                .pcsRslDtlCd(ProcessResponseCode.ERROR.getMessage())
                .pcsDtm(now.format(DateTimeFormatter.ofPattern(DateUtil.DATETIME_HANA)))
                .imgList(new ArrayList<>())
                .build();
    }

    public String checkValid(){

        String valid = ResultDto.VALID;

        if (imgList == null || imgList.size() == 0){
            return ResultDto.INVALID;
        }

        return valid;
    }

    public boolean calcAllEtcs() {
        boolean result = true;

        // 1개라도 ETCS가 아닌게 있으면, false를 리턴
        for (ImageResultDto imageResultDto : imgList){
            if (!imageResultDto.getImgDcmTpCd().equals(DocType.ETCS.getDocCode())){
                return false;
            }
        }

        return result;
    }
}
