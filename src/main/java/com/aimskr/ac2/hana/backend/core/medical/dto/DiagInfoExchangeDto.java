package com.aimskr.ac2.hana.backend.core.medical.dto;

import com.aimskr.ac2.hana.backend.channel.json.ResultItem;
import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class DiagInfoExchangeDto {

    // 질병코드
    private String dsacd;
    // 주진단여부코드
    private String mnDgnYn;
    // 진단구분 (임상/최종)
    private String diagStage;
    // 질병명
    private String dsnm;
    // 진단일자
    private String diagDate;

    public DiagInfoExchangeDto(DiagInfo diagInfo) {
        this.mnDgnYn = diagInfo.getMnDgnYn().equals("1") ? "주진단": "부진단";
        this.diagStage = diagInfo.getDiagStage().equals("1") ? "최종" : "임상";
        this.dsacd = diagInfo.getDsacd();
        this.dsnm = diagInfo.getDsnm();
        this.diagDate = diagInfo.getDiagDate();
    }

    public DiagInfo toEntity() {
        return DiagInfo.builder()
                .mnDgnYn(this.mnDgnYn.equals("주진단") ? "1": "0")
                .diagStage(this.diagStage.equals("최종") ? "1": "2")
                .dsacd(this.dsacd)
                .dsnm(this.dsnm)
                .diagDate(this.diagDate)
                .build();
    }

    public List<ResultItem> toResultItems() {

        return List.of(
                ResultItem.builder()
                        .trmCd("dsacd")
                        .trmVal(this.dsacd)
                        .build(),
                ResultItem.builder()
                        .trmCd("mnDgnYn")
                        .trmVal(this.mnDgnYn)
                        .build(),
                ResultItem.builder()
                        .trmCd("diagStage")
                        .trmVal(this.diagStage)
                        .build(),
                ResultItem.builder()
                        .trmCd("diagDate")
                        .trmVal(this.diagDate)
                        .build()
        );

    }
}
