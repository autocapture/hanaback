package com.aimskr.ac2.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// AIMS처리결과코드
@Getter
public enum ProcessResponseCode {
    SUCCESS("R00", "정상처리"),
    QA("R10", "QA 진행 중"),
    DISMISS("R20", "처리불가"),
    ERROR("R30", "이미지 다운로드 오류"),
    TIMEOVER("R99", "시스템 오류");


    private String code;
    private String message;

    ProcessResponseCode(String accidentCd, String message){
        this.code = accidentCd;
        this.message = message;
    }
    public static String getMessage(String code) {
        return Arrays.stream(ProcessResponseCode.values())
                .filter(x -> x.code.equals(code))
                .findFirst()
                .orElse(SUCCESS)
                .getMessage();
    }

    public static ProcessResponseCode getEnum(String code){
        for (ProcessResponseCode processResponseCode: values()){
            if (processResponseCode.code.equals(code)){
                return processResponseCode;
            }
        }
        return null;
    }
}
