package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagDocInfoDto {

    // 진단서구분코드
    private DocType dgndcFlagcd;
    // 진단일자
    private String dgndt;
    // 수술일자
    private String opdt;
    // 입원일자
    private String hspDt;
    // 퇴원일자
    private String lehoDt;
    // 의료기관명
    private String mdOrgnm;
    // 치료소견내용
    private String rmdyOpnCn;

    List<DiagInfoDto> stdDsacdInfo;



}
