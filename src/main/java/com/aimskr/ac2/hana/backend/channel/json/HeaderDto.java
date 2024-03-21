package com.aimskr.ac2.hana.backend.channel.json;

import lombok.*;

/**
 * 결과전문
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class HeaderDto {


    private String tlmSpecd;
    // 팩스일련번호
    private String txCode;
    // AIMS 처리결과 코드
    private String stfno;
    // AIMS 처리결과코드 상세
    private String trsOrgcd;
    // 결과전송시간
    private String rcvOrgcd;

    private String trsDthms;

    private String fnCd;


    @Builder
    public HeaderDto(String tlmSpecd, String txCode, String stfno, String trsOrgcd, String rcvOrgcd, String trsDthms, String fnCd) {
        this.tlmSpecd = tlmSpecd;
        this.txCode = txCode;
        this.stfno = stfno;
        this.trsOrgcd = trsOrgcd;
        this.rcvOrgcd = rcvOrgcd;
        this.trsDthms = trsDthms;
        this.fnCd = fnCd;
    }

}
