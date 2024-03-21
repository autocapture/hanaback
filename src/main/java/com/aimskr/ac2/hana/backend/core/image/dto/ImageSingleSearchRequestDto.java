package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageSingleSearchRequestDto {
    private String accrNo;
    private String dmSeqno;
    private String fileName;
}
