package com.aimskr.ac2.hana.backend.core.image.dto;

import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import lombok.*;

import java.util.List;

/**
 * CIPS (자동차보험금지급내역서) DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoMDSR {
    // COMMON
    private String rqsReqId;
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private String resultCode;

    // DETAIL
    private String ea0001; // 보험회사
    private String ea0002; // 처리구분
    private String ea0003; // 부상급항(급)
    private String ea0004; // 부상급항(항)
    private String ea0005; // 피보험자일치여부

    List<DiagInfoExchangeDto> diagList;

}
