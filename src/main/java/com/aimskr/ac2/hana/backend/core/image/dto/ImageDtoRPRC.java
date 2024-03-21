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
public class ImageDtoRPRC {
    // COMMON
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private Boolean isDup;
    private String imageProcessingResultCode;
    private String duppedFile;

    // DETAIL
    private String rb0001; // 카드영수증 - 접수일자
    private String rb0002; // 카드영수증 - 발행일자
    private String rb0003; // 카드영수증 - 합계금액
}
