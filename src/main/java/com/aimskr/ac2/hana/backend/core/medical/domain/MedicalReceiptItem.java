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
        name = "medical_receipt_item"
)
public class MedicalReceiptItem extends BaseTimeEntity {

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
    // 영수증ID
    @Column
    private String rtxId;
    // 세부의료비ID
    @Column
    private String dtMdcsId;
    // 의료비항목코드
    @Column
    private String mdcsItcd;
    // 급여일부본인부담금액
    @Column
    private Integer slrPartSeleChamt;
    // 급여공단부담금액
    @Column
    private Integer slrPartPbcdChamt;
    // 급여전액본인부담금액
    @Column
    private Integer slrTamtSeleChamt;
    // 비급여선택진료금액
    @Column
    private Integer nslySlcTrmtamt;
    // 비급여선택진료외금액
    @Column
    private Integer nslySlcTrmtOutamt;

}
