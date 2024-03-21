package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CropInfo {
    private Integer pieces;
    private Boolean isError;
    private Integer sliceHeight;
    private Integer width;
}
