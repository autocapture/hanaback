package com.aimskr.ac2.kakao.backend.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// AIMS처리결과코드
@Getter
public enum ProcessResponseCode {
    SUCCESS("R00", "정상처리"),
    WRONG("R10", "피보상이"),
    ERROR("R20", "처리불가"),
    TIMEOVER("R99", "시간초과");


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
