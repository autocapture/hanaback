package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.util.DateUtil;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
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
public class ResultItemDto {

    public static final String VALID = "VALID";
    public static final String INVALID = "INVALID";
    // 의뢰요청ID
    private String RQS_REQ_ID;
    // 파일송수신일련번호
    private String ACD_NO;
    // 팩스일련번호
    private String RCT_SEQ;
    // 사고일자
    private String ACD_DT;
    // 사고유형
    private String CLM_TP_CD;
    // 사고원인대분류코드
    private String ACD_CAUS_LCTG_CD;
    // 결과전송시간
    private String PCS_DTM;
    // AIMS 처리결과 코드
    private String PCS_RSL_CD;
    // AIMS 처리결과코드 상세
    private String PCS_RSL_DTL_CD;

    List<? extends ImageResultDto> imgList;


    public static ResultItemDto of(Assign assign) {
        return ResultItemDto.builder()
                .RQS_REQ_ID(assign.getRqsReqId())
                .ACD_NO(assign.getAccrNo())
                .RCT_SEQ(assign.getDmSeqno())
                .ACD_DT(assign.getAcdDt())
                .PCS_RSL_CD(assign.getProcessResponseCode().getCode())
                .PCS_RSL_DTL_CD(Optional.ofNullable(assign.getProcessResponseCodeDetail()).orElse(""))
                .PCS_DTM(assign.getResultDeliveryTime().format(DateTimeFormatter.ofPattern(DateUtil.DATETIME_HANA)))
                // 이미지는 별도로 추가함
                .imgList(null)
                .build();
    }

    public static ResultItemDto buildErrorResult(ImportDto importDto) {
        LocalDateTime now = LocalDateTime.now();
        return ResultItemDto.builder()
                .ACD_NO(importDto.getAcdNo())
                .RCT_SEQ(importDto.getRctSeq())
                .PCS_RSL_CD(ProcessResponseCode.ERROR.getCode())
                .PCS_RSL_DTL_CD(ProcessResponseCode.ERROR.getMessage())
                .PCS_DTM(now.format(DateTimeFormatter.ofPattern(DateUtil.DATETIME_HANA)))
                .imgList(new ArrayList<>())
                .build();
    }

    public String checkValid(){

        String valid = ResultItemDto.VALID;

        if (imgList.size() == 0){
            return ResultItemDto.INVALID;
        }
//
//        for (ImageResultDto imageResultDto : images){
//            if (!imageResultDto.getImgType().equals(DocType.ETCS.getDocCode()) && imageResultDto.getItems().size() == 0){
//                return ResultDto.INVALID;
//            }
//            if (imageResultDto.getItems().size() > 10){
//                return ResultDto.INVALID;
//            }
//        }

        return valid;
    }
}
