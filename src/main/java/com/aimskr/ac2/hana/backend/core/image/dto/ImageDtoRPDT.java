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
public class ImageDtoRPDT {
    // COMMON
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private Boolean isDup;
    private String imageProcessingResultCode;
    private String duppedFile;

    // DETAIL
    private String ra0001; // 수리비영수증 - 발행일자
    private String ra0002; // 수리비영수증 - 총합계
    private String ra0003; // 수리비영수증 - 제조번호
    private String ra0004; // 수리비영수증 - 일련번호
    private String ra0005; // 수리비영수증 - 모델코드
    private String ra0006; // 수리비영수증 - IMEI
    private String ra0007; // 수리비영수증 - 부품비합계
    private String ra0008; // 수리비영수증 - 수리비합계

}
