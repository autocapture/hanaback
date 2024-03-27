package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairDetailResponseDto;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: 삭제
public class ResultSubItem {

    // 표준코드
    private String stdCd;
    // 부품코드
    private String accdCd;
    // 부품명
    private String accdNm;
    // 금액
    private Integer accdAmt;
    // 심사의견
    private String accdType;
    // 사유
    private String accdTypeNm;

    public static ResultSubItem of(PhoneRepairDetailResponseDto phoneRepairDetailResponseDto){

        if (phoneRepairDetailResponseDto == null) {
            return null;
        }

        return ResultSubItem.builder()
                .stdCd(Optional.ofNullable(phoneRepairDetailResponseDto.getStdCd()).orElse(""))
                .accdCd(Optional.ofNullable(phoneRepairDetailResponseDto.getAccdCd()).orElse(""))
                .accdNm(Optional.ofNullable(phoneRepairDetailResponseDto.getAccdNm()).orElse(""))
                .accdAmt(Optional.ofNullable(phoneRepairDetailResponseDto.getAccdAmt()).orElse(0))
                .accdType(Optional.ofNullable(phoneRepairDetailResponseDto.getAccdType()).orElse(""))
                .build();
    }

}
