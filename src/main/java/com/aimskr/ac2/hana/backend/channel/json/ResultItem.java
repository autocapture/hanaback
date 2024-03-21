package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairResponseDto;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultItem {

    // 항목코드
    private String itemCd;
    // 항목값
    private String itemValue;
    // 인식률
//    private Double accuracy;

    public static ResultItem of(PhoneRepairResponseDto detail){

        if (detail == null) {
            return null;
        }

        return ResultItem.builder()
                .itemCd(detail.getItemCode())
                .itemValue(detail.getItemValue())
                .build();
    }

}
