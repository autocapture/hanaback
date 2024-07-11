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

    public SurgInfoExchangeDto(SurgInfo surgInfo) {
        this.surgName = surgInfo.getSurgName();
        this.surgDate = surgInfo.getSurgDate();
    }

    public SurgInfo toEntity() {
        return SurgInfo.builder()
                .surgName(this.surgName)
                .surgDate(this.surgDate)
                .build();
    }

//    public List<ResultItem> toResultItems() {
//
//        return List.of(
//                ResultItem.builder()
//                        .trmCd("dsacd")
//                        .trmVal(this.dsacd)
//                        .build(),
//                ResultItem.builder()
//                        .trmCd("mnDgnYn")
//                        .trmVal(this.mnDgnYn.equals("주진단") ? "1": "0")
//                        .build(),
//                ResultItem.builder()
//                        .trmCd("diagStage")
//                        .trmVal(this.diagStage.equals("최종") ? "1": "2")
//                        .build(),
//                ResultItem.builder()
//                        .trmCd("diagDate")
//                        .trmVal(this.diagDate)
//                        .build()
//        );
//
//    }
}
