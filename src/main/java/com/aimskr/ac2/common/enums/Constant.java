package com.aimskr.ac2.common.enums;

import java.util.HashMap;
import java.util.Map;

// TODO: enum으로 전환
public class Constant {

    /**
     * 휴대폰정보 crop 시 margin
     */
    public static final int CROP_MARGIN = 50;
    public static final int PADDING = 30;

    public static final String PHONE_REPAIR = "P";
    public static final String MED = "M";



    /**
     * 로그인 최대 실패 횟수
     */
    public static final int MAX_FAILURE_CNT = 5;

    /**
     * 최대 이미지 접수 허용 장 수
     */
    public static final int MAX_RCP_IMAGE_CNT = 40;

    /**
     * 최대 이미지 다운로드 시도 횟수
     */
    public static final int MAX_DOWNLOAD_RETRY_CNT = 40;


    /**
     * API 불일치 오차 허용값
     */
    public static final double CLINIC_INPUT_SUM_GAP = 20000;

    /**
     * 비밀번호 변경 시 계정이 없을 때, 리턴하는 문자열 (있다면 메일주소를 넘김)
     */
    public static final String NOT_EXIST_ACCOUNT = "NOT_EXIST";
    public static final String NO_EMAIL_ACCOUNT = "NO_EMAIL";
    public static final String WORNG_VERIFICATION_CODE = "잘못된 인증 코드";
    public static final String PASSWORD_POLICY_VIOLATION = "패스워드 정책 위반";
    public static final String PASSWORD_CHANGE_SUCCESS = "OK";
    public static final String VALIDATION_SUCCESS = "SECCESS";

    /**
     * Delay 적용 여부 : 개발계에서는 Delay처리 하지 않기 위함
     */
    public static final String DELAY_YES = "yes";   // 적용

    /**
     * 중복접수건에서 이전 건의 InputOwner가 null 일때 KbAssign을 다시 fetch 시도하는 최대 횟수
     */
    public static final int MAX_FETCH_TRIAL = 3;

    /**
     * ROLE
     */
    public static final String ROLE_QA = "ROLE_QA";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 배당유형
     */
    public static final String ASSIGN_TYPE_QA = "QA";
    public static final String ASSIGN_TYPE_MANAGER = "운영관리";

    /**
     * 처리단계 : 접수 -> 분류중 -> 입력중 -> QA중 -> 회신
     */
    public static final String STEP_ACCEPT = "접수";
    public static final String STEP_INPUT = "입력중";
    public static final String STEP_QA = "QA중";
    public static final String STEP_RETURN = "회신";

    /**
     * 입력처리단계 : 접수 -> (분류중) -> 입력중 -> 입력확정
     */
    public static final String INPUT_STEP_ACCEPT = "접수";
    public static final String INPUT_STEP_INPUT = "입력중";
    public static final String INPUT_STEP_COMPLETE = "입력확정";

    /**
     * 입력결과
     *
     */
    public static final String INPUT_RESULT_200 = "200 ok";
    // 200 ok, 400 wrong rd, 500 error, 900 other format

    /**
     * 처리상태
     */
    public static final String PROCESSING_COMPLETE = "완결";
    public static final String PROCESSING_PREV_COMPLETE = "완결중";
    public static final String PROCESSING_NON_COMPLETE = "미결";

    /**
     * KB 영수증 입통원 구분
     */
    public static final String RECEIPT_IN = "01";
    public static final String RECEIPT_OUT = "02";

    /**
     * KB 반송사유 코드
     * 01	이미지없음
     * 02	이미지품질불량
     * 03	합계금액불일치
     * 99	기타
     */
    public static final String RETURN_NO_IMAGE = "01";
    public static final String RETURN_LOW_IMAGE = "02";
    public static final String RETURN_MISMATCH_MONEY = "03";
    public static final String RETURN_ETC = "99";

    public static final Map<String, String> RETURN_RSN_CD = new HashMap<String, String>() {{
        put("01","이미지없음");
        put("02","이미지품질불량");
        put("03","합계금액불일치");
        put("99","기타");
    }};

    /**
     * 입통원 구분 1 = 통원, 2 = 입원
     */
    public static final int DOC_TYPE_OUT = 1;
    public static final int DOC_TYPE_IN = 2;

    /**
     * 응답코드 (ResPonse CoDe)
     */
    public static final String RP_CD_SUCCESS = "0000";          // 성공
    public static final String RP_CD_SEND_BACK = "0100";        // 반송
    public static final String RP_CD_FTP_CONN_FAIL = "0200";    // FTP접속불가
    public static final String RP_CD_FTP_TRAN_ERR = "0201";     // FTP전송실패
    public static final String RP_CD_FAILURE_KB = "0300";       // 요청오류
    public static final String RP_CD_FAILURE_AIMS = "0500";     // 처리오류
    public static final String RP_CD_ETC = "0900";              // 기타

    /**
     * 응답내용 (ResPonse TeXt)
     */
    public static final String RP_CD_SUCCESS_MSG = "성공";          // 성공
    public static final String RP_CD_SEND_BACK_MSG = "반송";        // 반송
    public static final String RP_CD_FTP_CONN_FAIL_MSG = "FTP접속불가";    // FTP접속불가
    public static final String RP_CD_FTP_TRAN_ERR_MSG = "FTP전송실패";     // FTP전송실패
    public static final String RP_CD_FAILURE_KB_MSG = "요청오류";          // 실패
    public static final String RP_CD_FAILURE_AIMS_MSG = "처리오류";          // 실패
    public static final String RP_CD_ETC_MSG = "기타";              // 기타

    /**
     * OCR 서류 형식
     */
    public static final String OCR_FORM_RECEIPT = "CO020304";          // 진료비영수증
    public static final String OCR_FORM_CONFIRM = "CO020306";          // 진료비납입화인서

    /**
     * 미정의 코드
     */
    public static final String NOT_DEFINED = "99999";

    /**
     * Vision 그룹 분류 항목
     */
    public static final String ROW = "row";
    public static final String COLUMN = "column";
    public static final String VERTICAL = "x";
    public static final String HORIZONTAL = "y";

    /**
     * Vision Overlapping mode (박스가 종/횡으로 겹치는 범위 계산 시 활용)
     */
    public static final String SCRATCH = "scratch";
    public static final String HALF = "half";

    /**
     * VisionView DrawMode
     */
    public static final String IMAGE = "image";
    public static final String DETAIL = "detail";

    /**
     * AutoInput 코드
     * USED: 사용됨
     * DEL: 삭제
     */
    public static final String USED = "used";
    public static final String DEL = "deleted";

    /**
     * CheckHeaderMode
     * ROUGH: 최초 refine
     * NEAT:
     */
    public static final String ROUGH = "rough";
    public static final String DEEPEN = "deepen";



    /**
     * QA 점검 결과 오류 유형
     */
    public static final String QA_HISTORY_WRONG_INPUT = "착오입력";
    public static final String QA_HISTORY_MISS_INPUT = "항목누락";
    public static final String QA_HISTORY_DUP_INPUT = "중복입력";

    /**
     * QA 점검 결과 오류 항목
     */
    public static final String QA_HISTORY_ITEM_ALL = "항목전체";
    public static final String QA_HISTORY_ITEM_RD_TYPE = "항목";
    public static final String QA_HISTORY_ITEM_AP_STR_DT = "일자";
    public static final String QA_HISTORY_ITEM_EDI_CO = "코드";
    public static final String QA_HISTORY_ITEM_RD_NAME = "명칭";
    public static final String QA_HISTORY_ITEM_ST_UN_PRC = "금액";
    public static final String QA_HISTORY_ITEM_RD_NO_T = "횟수";
    public static final String QA_HISTORY_ITEM_RD_NO_D = "일수";
    public static final String QA_HISTORY_ITEM_RD_TOTAL_AMOUNT = "총액";
    public static final String QA_HISTORY_ITEM_WHOLE_CHARGE = "전액본인부담";
    public static final String QA_HISTORY_ITEM_RD_NON_PUB = "비급여";


    /**
     * KB 의료비 항목 코드
     */
    public static final Map<String, String> KB_MEDICAL_ITEM_CODE = new HashMap<String, String>() {{
        put("D01" ,"약품비");
        put("D02" ,"조제기본료");
        put("D03" ,"복약지도료");
        put("D04" ,"조제료");
        put("D05" ,"관리료");
        put("D06" ,"65세이상 등 정액(약제)");
        put("D97" ,"약제의료비(구)");
        put("D98" ,"합계 - 약제의료비");
        put("L01" ,"진찰료");
        put("L02" ,"입원료");
        put("L03" ,"병실차액");
        put("L04" ,"식대");
        put("L05" ,"투약/조제료");
        put("L06" ,"주사료");
        put("L07" ,"마취료");
        put("L08" ,"처치/수술료");
        put("L09" ,"검사료");
        put("L10" ,"영산진단/방사선료");
        put("L11" ,"치료재료대");
        put("L12" ,"전액본인부담");
        put("L13" ,"재활/물리치료료");
        put("L14" ,"정신요법료");
        put("L15" ,"CT");
        put("L16" ,"MRI");
        put("L17" ,"초음파");
        put("L18" ,"보철/교정료");
        put("L19" ,"수혈료");
        put("L20" ,"선택진료료");
        put("L21" ,"제증명료");
        put("L22" ,"자보/산재처리의료비");
        put("L23" ,"응급관리료");
        put("L24" ,"처치료");
        put("L25" ,"수술료");
        put("L26" ,"입원실료");
        put("L27" ,"수술비");
        put("L28" ,"입원제비용");
        put("L29" ,"투약/조제료 - 행위료");
        put("L30" ,"투약/조제료 - 약품비");
        put("L31" ,"주사료 - 행위료");
        put("L32" ,"주사료 - 약품비");
        put("L33" ,"영상진단료");
        put("L34" ,"방사선치료료");
        put("L35" ,"PET");
        put("L36" ,"한방물리요법료");
        put("L37" ,"한약(접약)");
        put("L38" ,"65세이상 등 정액(외래)");
        put("L39" ,"정액수가(요양병원)");
        put("L40" ,"포괄수가진료비");
        put("L41" ,"상한액초과금");
        put("L42" ,"입원료(1인실)");
        put("L43" ,"입원료(2,3인실)");
        put("L44" ,"입원료(4인실이상)");
        put("L45" ,"선별급여");
        put("L46" ,"예약진찰료");
        put("L96" ,"할인/공제");
        put("L97" ,"외래의료비(구)");
        put("L98" ,"합계 - 외래의료비");
        put("L99" ,"기타");
    }};

    /**
     * KB 진료 과목 코드
     */
    public static final Map<String, String> KB_DGN_SUBJ_CODE = new HashMap<String, String>() {{
        put("01" ,"내과");
        put("02" ,"신경과");
        put("03" ,"정신과");
        put("04" ,"일반외과");
        put("05" ,"정형외과");
        put("06" ,"신경외과");
        put("07" ,"흉부외과");
        put("08" ,"성형외과");
        put("09" ,"마취과");
        put("10" ,"산부인과");
        put("11" ,"소아과");
        put("12" ,"안과");
        put("13" ,"이비인후과");
        put("14" ,"피부과");
        put("15" ,"비뇨기과");
        put("16" ,"진단방사선과");
        put("17" ,"치료방사선과");
        put("18" ,"해부병리과");
        put("19" ,"결핵과");
        put("20" ,"재활의학과");
        put("21" ,"핵의학과");
        put("22" ,"가정의학과");
        put("23" ,"응급의학과");
        put("24" ,"산업의학과");
        put("25" ,"치과");
        put("26" ,"한방과");
        put("27" ,"임상병리과");
        put("28" ,"예방의학과");
        put("29" ,"중환자의학과");
        put("99" ,"기타");
    }};
}
