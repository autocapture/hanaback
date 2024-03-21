package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageSearchRequestDto {
    private String accrNo;
    private String dmSeqno;
}
