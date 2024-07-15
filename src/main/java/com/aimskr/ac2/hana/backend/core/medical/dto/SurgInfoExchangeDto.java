package com.aimskr.ac2.hana.backend.core.medical.dto;

import com.aimskr.ac2.hana.backend.channel.json.ResultItem;
import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfo;
import com.aimskr.ac2.hana.backend.core.medical.domain.SurgInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class SurgInfoExchangeDto {

    private String surgName;
    // 수술일자
    private String surgDate;
    private String diagCode;

    public SurgInfoExchangeDto(SurgInfo surgInfo) {
        this.surgName = surgInfo.getSurgName();
        this.surgDate = surgInfo.getSurgDate();
        this.diagCode = surgInfo.getDiagCode();
    }

    public SurgInfo toEntity() {
        return SurgInfo.builder()
                .surgName(this.surgName)
                .surgDate(this.surgDate)
                .diagCode(this.diagCode)
                .build();
    }

    public List<ResultItem> toResultItems() {

        return List.of(
                ResultItem.builder()
                        .trmCd("surgDate")
                        .trmVal(this.surgDate)
                        .build(),
                ResultItem.builder()
                        .trmCd("surgType1")
                        .trmVal("")
                        .build(),
                ResultItem.builder()
                        .trmCd("surgType2")
                        .trmVal("")
                        .build(),
                ResultItem.builder()
                        .trmCd("surgName")
                        .trmVal(this.surgName)
                        .build(),
                ResultItem.builder()
                        .trmCd("diagCode")
                        .trmVal(this.diagCode)
                        .build()
        );

    }
}
