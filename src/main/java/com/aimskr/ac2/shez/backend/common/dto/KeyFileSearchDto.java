package com.aimskr.ac2.kakao.backend.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyFileSearchDto {
    private String receiptNo;
    private String receiptSeq;
    private String fileName;
}
