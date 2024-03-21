package com.aimskr.ac2.hana.backend.core.medical.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.common.enums.doc.DocType;
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
        name = "diag_doc_info"
)
public class DiagDocInfo extends BaseTimeEntity {

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
    // 진단서구분코드
    @Enumerated(EnumType.STRING)
    @Column
    private DocType dgndcFlagcd;
    // 진단일자
    @Column
    private LocalDateTime dgndt;
    // 수술일자
    @Column
    private LocalDateTime opdt;
    // 입원일자
    @Column
    private LocalDateTime hspDt;
    // 퇴원일자
    @Column
    private LocalDateTime lehoDt;
    // 의료기관명
    @Column
    private String mdOrgnm;
    // 치료소견내용
    @Lob
    @Column
    private String rmdyOpnCn;

}
