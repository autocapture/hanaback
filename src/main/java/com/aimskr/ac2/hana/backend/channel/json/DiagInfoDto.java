package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DiagInfoDto {

    // 주진단여부코드
    @Column
    private String dtMdcsId;
    // 질병코드
    @Column
    private String rtxAmtEqalYncd;
    // 질병명
    @Column
    private String dscrdSpccd;

    public DiagInfoDto(DiagInfo diagInfo) {
        this.dtMdcsId = diagInfo.getDtMdcsId();
        this.rtxAmtEqalYncd = diagInfo.getRtxAmtEqalYncd();
        this.dscrdSpccd = diagInfo.getDscrdSpccd();
    }

}
