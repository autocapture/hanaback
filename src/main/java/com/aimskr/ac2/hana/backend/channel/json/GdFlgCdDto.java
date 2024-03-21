package com.aimskr.ac2.hana.backend.channel.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GdFlgCdDto {

    @Schema(description = "상품코드")
    private String gdFlgCd;

}
