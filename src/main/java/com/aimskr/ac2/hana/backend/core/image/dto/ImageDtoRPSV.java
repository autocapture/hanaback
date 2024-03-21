package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

/**
 * 카드영수증 수정 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoRPSV {
    // COMMON
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private Boolean isDup;
    private String imageProcessingResultCode;
    private String duppedFile;

    // DETAIL
    private String rc0001; // 부가서비스내역서 - 타보험가입여부
}
