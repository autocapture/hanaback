package com.aimskr.ac2.hana.backend.core.medical.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "diag_info"
)
public class DiagInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String rqsReqId;

    @Column(length = 16)
    private String accrNo;
    // 접수회차
    @Column(length = 5)
    private String dmSeqno;
    // 파일명
    @Column(length = 255)
    private String fileName;
    // 주진단여부코드
    @Column
    private String mnDgnYn;
    // 임상,최종 여부
    @Column
    private String diagStage;
    // 질병코드
    @Column
    private String dsacd;
    // 질병명
    @Column
    private String dsnm;
    // 진단일자
    @Column
    private String diagDate;

    public static DiagInfo of (DiagInfoExchangeDto diagInfoExchangeDto){

        return DiagInfo.builder()
                .dsacd(diagInfoExchangeDto.getDsacd())
                .dsnm(diagInfoExchangeDto.getDsnm())
                .diagStage(diagInfoExchangeDto.getDiagStage().equals("최종") ? "1" : "2")
                .mnDgnYn(diagInfoExchangeDto.getMnDgnYn().equals("주진단") ? "1": "0")
                .build();

    }

    public void updateKey(String rqsReqId, String accrNo, String dmSeqno, String fileName){
        this.rqsReqId = rqsReqId;
        this.accrNo = accrNo;
        this.dmSeqno = dmSeqno;
        this.fileName = fileName;
    }

}
