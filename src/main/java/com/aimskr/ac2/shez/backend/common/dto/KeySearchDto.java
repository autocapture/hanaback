package com.aimskr.ac2.kakao.backend.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeySearchDto {
    private String receiptNo;
    private String receiptSeq;

    public String getKey() {
        return receiptNo + "/" + receiptSeq;
    }
}
