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
        name = "medical_receipt"
)
public class MedicalReceipt extends BaseTimeEntity {

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
    // 영수증구분코드
    @Column
    private String rtxFlgCd;
    // 피보험자일치여부코드
    @Column
    private String nrdAreaEqlYncd;
    // 데이터정합성여부코드
    @Column
    private String datConstYncd;
    // 사업자등록번호
    @Column
    private String bprNo;
    // 사업자명
    @Column
    private String bzpNm;
    // 병원종별코드
    @Column
    private String hspSpecd;
    // 입통원구분코드
    @Column
    private String hagthFlgcd;
    // 진료과목코드
    @Column
    private String trmtSbjcd;
    // 보험처리구분코드
    @Column
    private String insDlFlgcd;
    // 급여일부본인부담금액총액
    @Column
    private Long slrPartSeleChamtTamt;
    // 급여공단부담금액총액
    @Column
    private Long slrPartPbcdChamtTamt;
    // 급여전액본인부담금액총액
    @Column
    private Long slrTamtSeleChamtTamt;
    // 비급여선택진료금액총액
    @Column
    private Long nslySlcTrmtamtTamt;
    // 비급여선택진료외금액총액
    @Column
    private Long nslySlcTrmtOutamtTamt;
    // 진료비총금액
    @Column
    private Long trpaTamt;
    // 환자부담금액합계
    @Column
    private Long ptChamtSm;
    // 기납부금액
    @Column
    private Long prvPyamt;
    // 할인금액
    @Column
    private Long dcAmt;
    // 수납금액
    @Column
    private Long rvAmt;
    // 치료시작일자
    @Column
    private LocalDateTime rmdyStrdt;
    // 치료종료일자
    @Column
    private LocalDateTime rmdyNddt;

}
