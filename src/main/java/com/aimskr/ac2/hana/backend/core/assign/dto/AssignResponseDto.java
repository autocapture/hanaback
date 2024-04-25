package com.aimskr.ac2.hana.backend.core.assign.dto;


import com.aimskr.ac2.common.enums.AccidentCause;
import com.aimskr.ac2.common.enums.ClaimType;
import com.aimskr.ac2.common.util.AES256Cipher;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.common.enums.assign.RequestType;
import com.aimskr.ac2.common.enums.doc.AccidentType;
import com.aimskr.ac2.common.enums.status.AcceptStatus;
import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.enums.status.ResultAcceptCode;
import com.aimskr.ac2.common.enums.status.Step;
import com.aimskr.ac2.common.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ComponentScan(basePackages = "com.aimskr.ac2.common")
public class AssignResponseDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime modifiedTime;

    /**
     * 기본 정보 Section (HANA에서 접수한 전문)
     */
    private String accrNo;
    private String dmSeqno;
    private String rqsReqId;
    private ClaimType clmTpCd;     // 사고유형
    private AccidentCause acdCausLctgCd; // 사고원인대분류코드
    private String acdDt;
    private String nrdNm;       // 피보험자명 (복호화)
    private String nrdBirth;    // 생년월일 (복호화)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime rqstTime;

    private Integer imgCnt;
    private String requestJson;
    private String responseJson;
    private AccidentType accidentType;

    /**
     * 접수완료 Section
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime acceptTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime downloadTime;
    private AcceptStatus acceptStatus;
    private String acceptMessage;

    /**
     * 진행 상태 Section (Autocapture에서 처리하는 기록)
     */
    private Step step;
    private Boolean autoReturn;
    private String qaOwner;
    private LocalDateTime qaAssignTime;

    /**
     * 처리완료 Section (Autocapture에서 처리하는 기록)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime resultDeliveryTime;
    private ProcessResponseCode processResponseCode;

    /**
     * 수신결과 Section (KAKAO에서 전달하는 기록)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATETIME, timezone = "Asia/Seoul")
    private LocalDateTime resultAcceptTime;
    private ResultAcceptCode resultAcceptCode;


    public static AssignResponseDto of(Assign assign){
        if (assign == null) return null;
        return AssignResponseDto.builder()
                .id(assign.getId())
                // 기본정보
                .accrNo(assign.getAccrNo())
                .dmSeqno(assign.getDmSeqno())
                .rqsReqId(assign.getRqsReqId())
                .clmTpCd(assign.getClmTpCd())
                .acdCausLctgCd(assign.getAcdCausLctgCd())
                .acdDt(assign.getAcdDt())
                .nrdNm(AES256Cipher.decrypt(assign.getNrdNm(), assign.getRqsReqId()))
                .nrdBirth(AES256Cipher.decrypt(assign.getNrdBirth(), assign.getRqsReqId()))
                .rqstTime(assign.getRqstTime())
                .imgCnt(assign.getImgCnt())
                .accidentType(assign.getAccidentType())
                .requestJson(assign.getRequestJson())
                .responseJson(assign.getResponseJson())
                .acceptTime(assign.getAcceptTime())
                .downloadTime(assign.getDownloadTime())
                .acceptStatus(assign.getAcceptStatus())
                .acceptMessage(assign.getAcceptMessage())
                // 진행상태
                .step(assign.getStep())
                .autoReturn(assign.getAutoReturn())
                .qaOwner(assign.getQaOwner())
                .qaAssignTime(assign.getQaAssignTime())
                // 처리완료
                .resultDeliveryTime(assign.getResultDeliveryTime())
                .processResponseCode(assign.getProcessResponseCode())
                // 수신결과
                .resultAcceptTime(assign.getResultAcceptTime())
                .resultAcceptCode(assign.getResultAcceptCode())
                .build();
    }

    public AssignResponseDto(Assign assign){
        this.id = assign.getId();
        this.accrNo = assign.getAccrNo();
        this.dmSeqno = assign.getDmSeqno();
//        this.rqstType = assign.getRqstType();
        this.rqsReqId = assign.getRqsReqId();
        this.clmTpCd = assign.getClmTpCd();
        this.acdCausLctgCd = assign.getAcdCausLctgCd();
        this.acdDt = assign.getAcdDt();
        this.nrdNm = AES256Cipher.decrypt(assign.getNrdNm(), assign.getRqsReqId());
        this.nrdBirth = AES256Cipher.decrypt(assign.getNrdBirth(), assign.getRqsReqId());
        this.rqstTime = assign.getRqstTime();
        this.accidentType = assign.getAccidentType();
        this.imgCnt = assign.getImgCnt();
        this.requestJson = assign.getRequestJson();
        this.responseJson = assign.getResponseJson();
        this.acceptTime = assign.getAcceptTime();
        this.downloadTime = assign.getDownloadTime();
        this.acceptStatus = assign.getAcceptStatus();
        this.acceptMessage = assign.getAcceptMessage();
        this.step = assign.getStep();
        this.autoReturn = assign.getAutoReturn();
        this.qaOwner = assign.getQaOwner();
        this.qaAssignTime = assign.getQaAssignTime();
        this.resultDeliveryTime = assign.getResultDeliveryTime();
        this.processResponseCode = assign.getProcessResponseCode();
        this.resultAcceptTime = assign.getResultAcceptTime();
        this.resultAcceptCode = assign.getResultAcceptCode();
    }

    public String getStepKorName() {
        return this.step == null ? "" : this.step.getKorName();
    }
    public String getClaimType() {
        return this.clmTpCd == null? "" : this.clmTpCd.getName();
    }
    public String getAccidentCause() {
        return this.acdCausLctgCd.getName();
    }

}
