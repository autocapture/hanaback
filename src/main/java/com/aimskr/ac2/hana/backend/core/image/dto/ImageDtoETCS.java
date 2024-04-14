package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

/**
 * 기타 이미지 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoETCS {
    // COMMON
    private String rqsReqId;
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private String resultCode;
}
