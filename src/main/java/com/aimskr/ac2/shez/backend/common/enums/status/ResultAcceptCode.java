package com.aimskr.ac2.kakao.backend.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// 수신 결과
@Getter
public enum ResultAcceptCode {
    // Note : 카카오 이석영 확인사항 23.08.03
    // R01, R90~98은 에임스 수신결과 전달 후 저희쪽 상태 관리를 위한 코드라 에임스에 나가는 값은 아님
    // 현재 정의된 결과코드 중 아래 코드만 에임스에 전달 : R00,R10,R20,R99
    OK("R00", "처리완료"),                  // 사용
//    OK_REQ("R01", "요청완료"),
    ERR_BASIC("R10", "기본정보오류"),        // 사용
    ERR_IMAGE("R20", "이미지결과처리오류"),    // 사용
//    ERR_CHANNEL("R90", "수신서버오류"),
//    ERR_REQ1("R91", "요청대상오류1"),
//    ERR_REQ2("R92", "요청대상오류2"),
//    ERR_REQ3("R93", "요청대상오류3"),
//    ERR_REQ4("R94", "요청대상오류4"),
//    ERR_REQ5("R95", "요청대상오류5"),
//    ERR_REQ6("R96", "요청대상오류6"),
//    ERR_REQ7("R97", "요청대상오류7"),
//    ERR_REQ8("R98", "요청대상오류8"),
    ERR_SERVER("R99", "서버오류");         // 사용

    private String code;
    private String message;

    ResultAcceptCode(String accidentCd, String message){
        this.code = accidentCd;
        this.message = message;
    }

    public static String getMessage(String code) {
        return Arrays.stream(ResultAcceptCode.values())
                .filter(x -> x.code.equals(code))
                .findFirst()
                .orElse(OK)
                .getMessage();
    }

    public static ResultAcceptCode getEnum(String code){
        for (ResultAcceptCode resultAcceptCode: values()){
            if (resultAcceptCode.code.equals(code)){
                return resultAcceptCode;
            }
        }
        return null;
    }
}
