package com.aimskr.ac2.hana.backend.channel.json;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDto {
    private String rpcd;
    private String rspnsText;
    private String rspnsDtlText;

    @Builder
    public ResponseDto(String rpcd, String rspnsText, String rspnsDtlText) {
        this.rpcd = rpcd;
        this.rspnsText = rspnsText;
        this.rspnsDtlText = rspnsDtlText;
    }
}
