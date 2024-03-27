package com.aimskr.ac2.hana.backend.channel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DescriptionDto {
    @JsonProperty("INF_KEY")
    private String infKey;
    @JsonProperty("INF_VAL")
    private String infVal;
}
