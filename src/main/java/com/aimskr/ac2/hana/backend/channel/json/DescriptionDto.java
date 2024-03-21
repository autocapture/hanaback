package com.aimskr.ac2.hana.backend.channel.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DescriptionDto {
    @Schema(description = "정보key")
    private String key;
    @Schema(description = "정보value")
    private String value;
}
