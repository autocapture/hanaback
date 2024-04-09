package com.aimskr.ac2.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// 수신 결과
@Getter
public enum ResultAcceptCode {
    OK("R00", "정상처리"),
    ERR_SERVER("R99", "수신오류");

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
