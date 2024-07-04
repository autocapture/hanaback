package com.aimskr.ac2.hana.backend.core.medical.dto;

import com.aimskr.ac2.hana.backend.core.medical.domain.Kcd;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KcdResponseDto {
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 파일송수신일련번호
    // 입통원구분코드
    private String kcdCd;
    // 진료과목
    private String kcdName;

    public KcdResponseDto(Kcd kcd){
        this.kcdCd = kcd.getKcdCd();
        this.kcdName = kcd.getKcdName();
    }

}
