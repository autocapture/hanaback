package com.aimskr.ac2.hana.backend.core.assign.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import com.aimskr.ac2.common.enums.assign.RequestType;
import com.aimskr.ac2.common.enums.doc.AccidentType;
import com.aimskr.ac2.common.enums.status.AcceptStatus;
import com.aimskr.ac2.common.enums.status.ProcessResponseCode;
import com.aimskr.ac2.common.enums.status.ResultAcceptCode;
import com.aimskr.ac2.common.enums.status.Step;
import com.aimskr.ac2.common.util.DateUtil;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(
        name = "assign"
)
public class Assign extends BaseTimeEntity {
    public static final LocalDateTime DEFAULT = LocalDateTime.of(1111, 1, 1, 1, 1, 1);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 기본 정보 Section (접수 전문)
     */
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 접수번호
    @Column(length = 16)
    private String accrNo;
    // 접수회차
    @Column(length = 5)
    private String dmSeqno;

//    // 요청구분
//    @Enumerated(EnumType.STRING)
//    @Column
//    private RequestType rqstType;
    // 외뢰요청ID
    @Column
    private String rqsReqId;
    // 사고유형
    @Column
    private String clmTpCd;
    // 사고원인대분류코드
    @Column
    private String acdCausLctgCd;
    // 요청시간
    @Column
    private LocalDateTime rqstTime;
    // 피보험자명
    @Column(length = 50)
    private String nrdNm;

    // 피보험자 생년월일
    @Column(length = 50)
    private String nrdBirth;
    // 이미지 수
    @Column
    private Integer imgCnt;
    @Column(columnDefinition = "LONGTEXT")
    private String requestJson;
    @Column(columnDefinition = "LONGTEXT")
    private String responseJson;

    // 상품목록
    @Lob
    @Column
    private String gdFlgList;

    /**
     * 접수 완료 Section
     */
    //////////////////////////////////////////////////////////
    // AIMS 접수시간
    // 접수일시: '2020-01-31 02:59:03',
    @Column
    private LocalDateTime acceptTime;
    // 이미지수신시간
    @Column(length = 30)
    private LocalDateTime downloadTime;


    // 접수상태
    @Enumerated(EnumType.STRING)
    @Column
    private AcceptStatus acceptStatus;
    // 접수메시지 from ReturnDto.message
    @Column
    private String acceptMessage;

    // 사고일자
    @Column
    private String acdDt;

    // 사고유형
    @Enumerated(EnumType.STRING)
    @Column
    private AccidentType accidentType;

    /**
     * 진행 상태 Section (Autocapture에서 처리하는 기록)
     */
    //////////////////////////////////////////////////////////
    // 처리단계 : 접수 -> QA -> 회신 -> 완료
    @Enumerated(EnumType.STRING)
    @Column
    private Step step;

    // 자동회신 여부 -- 중복 입력과는 다름, 중복 이미지
    @Column(columnDefinition = "boolean default false")
    private Boolean autoReturn;

    // QA담당자
    @Column(length = 12)
    private String qaOwner;

    // QA배당일시: '2020-01-31 02:59:03',
    @Column
    private LocalDateTime qaAssignTime;

    /**
     * 처리완료 Section (Autocapture에서 처리하는 기록)
     */
    //////////////////////////////////////////////////////////
    //
    @Column
    private LocalDateTime resultDeliveryTime;
    // AIMS처리결과코드
    @Enumerated(EnumType.STRING)
    @Column
    private ProcessResponseCode processResponseCode;

    @Column
    private String processResponseCodeDetail;

    /**
     * 수신결과 Section (KAKAO에서 전달하는 기록)
     */
    //////////////////////////////////////////////////////////
    // KAKAO 결과 수신시간
    @Column
    private LocalDateTime resultAcceptTime;
    // KAKAO수신결과코드
    @Enumerated(EnumType.STRING)
    @Column
    private ResultAcceptCode resultAcceptCode;

    // 호출된 Endpoint
    @Column(length = 50)
    private String endpoint;


    public void updateProcessResponseCode(ProcessResponseCode processResponseCode){
        this.processResponseCode = processResponseCode;
    }

    public void updateResultAcceptTime(LocalDateTime resultAcceptTime){
        this.resultAcceptTime = resultAcceptTime;
    }

    public void updateResultDeliveryTime(LocalDateTime resultDeliveryTime){
        this.resultDeliveryTime = resultDeliveryTime;
    }

    public void updateAutoReturn(Boolean autoReturn){
        this.autoReturn = autoReturn;
    }
    public void updateStep(Step step){
        this.step = step;
    }
    public void updateQaOwner(String qaOwner){
        this.qaOwner = qaOwner;
    }
    public void updateQaAssignTime(LocalDateTime qaAssignTime){
        this.qaAssignTime = qaAssignTime;
    }
    /**
     * FTP 오류로 이미지를 못가져온 경우, OCR하지 않고 Error로 완료 처리
     */
    public void updateFinishWithError() {
        this.step = Step.RETURN;
        this.processResponseCode = ProcessResponseCode.ERROR;
        this.resultDeliveryTime = LocalDateTime.now();
    }

    public void updateComplete(ResultAcceptCode resultAcceptCode, String resultAcceptTime){
        this.step = Step.COMPLETE;
        this.resultAcceptCode = resultAcceptCode;
        this.resultAcceptTime =
                LocalDateTime.parse(resultAcceptTime, DateTimeFormatter.ofPattern(DateUtil.DATETIME_HANA));
    }

    // 접수 시 정보 업데이트
    public void updateAcceptInfo(String requestJson, AcceptStatus acceptStatus){;
        this.requestJson = requestJson;
        this.acceptStatus = acceptStatus;
        this.step = Step.ACCEPT;
        this.acceptTime = LocalDateTime.now();
    }

    public void updateDownloadTime(){
        this.downloadTime = LocalDateTime.now();
    }

    public void updateResponseJson(String responseJson) { this.responseJson = responseJson; }
    public void updateEndpoint(String endpoint) { this.endpoint = endpoint; }

    public LocalDateTime getDownloadTime() {
        if (downloadTime == null) {
            return Assign.DEFAULT;
        } else return downloadTime;
    }

    public LocalDateTime getResultDeliveryTime() {
        if (resultDeliveryTime == null) {
            return Assign.DEFAULT;
        } else return resultDeliveryTime;
    }

    public void updateAccidentType(AccidentType accidentType){
        this.accidentType = accidentType;
    }

}
