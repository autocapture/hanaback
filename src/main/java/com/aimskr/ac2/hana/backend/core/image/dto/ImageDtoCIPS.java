package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

/**
 * CIPS (자동차보험금지급내역서) DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoCIPS {
    // COMMON
    private String rqsReqId;
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private String resultCode;

    // DETAIL
    private String ca0001; // 보험회사
    private String ca0002; // 처리구분
    private String ca0003; // 부상급항(급)
    private String ca0004; // 부상급항(항)
    private String ca0005; // 피보험자일치여부

}
