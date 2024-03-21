package com.aimskr.ac2.hana.backend.core.medical.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String dtMdcsId;
    // 질병코드
    @Column
    private String rtxAmtEqalYncd;
    // 질병명
    @Column
    private String dscrdSpccd;

    @Builder
    public DiagInfo(String accrNo, String dmSeqno, String fileName, String dtMdcsId, String rtxAmtEqalYncd, String dscrdSpccd) {
        this.accrNo = accrNo;
        this.dmSeqno = dmSeqno;
        this.fileName = fileName;
        this.dtMdcsId = dtMdcsId;
        this.rtxAmtEqalYncd = rtxAmtEqalYncd;
        this.dscrdSpccd = dscrdSpccd;
    }

}
