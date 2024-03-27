package com.aimskr.ac2.hana.backend.channel.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultItem {
    // 항목코드
    @JsonProperty("TRM_CD")
    private String trmCd;
    // 항목값
    @JsonProperty("TRM_VAL")
    private String trmVal;
}
