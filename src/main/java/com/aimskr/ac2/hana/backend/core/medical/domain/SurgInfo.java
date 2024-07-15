package com.aimskr.ac2.hana.backend.core.medical.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.SurgInfoExchangeDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "surg_info"
)
public class SurgInfo extends BaseTimeEntity {

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
    // 수술명
    @Column
    private String surgName;
    // 수술일자
    @Column
    private String surgDate;
    // 주진단코드
    @Column
    private String diagCode;

    public static SurgInfo of (SurgInfoExchangeDto surgInfoExchangeDto){

        return SurgInfo.builder()
                .surgName(surgInfoExchangeDto.getSurgName())
                .surgDate(surgInfoExchangeDto.getSurgDate())
                .diagCode(surgInfoExchangeDto.getDiagCode())
                .build();

    }


    public void updateKey(String rqsReqId, String accrNo, String dmSeqno, String fileName){
        this.rqsReqId = rqsReqId;
        this.accrNo = accrNo;
        this.dmSeqno = dmSeqno;
        this.fileName = fileName;
    }

}
