package com.aimskr.ac2.hana.backend.core.image.dto;

import lombok.*;

/**
 * 항공권 수정 DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoETCS {
    // COMMON
    private String fileName;
    private String korDocType;
}
