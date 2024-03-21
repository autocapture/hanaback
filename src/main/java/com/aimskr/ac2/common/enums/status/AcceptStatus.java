package com.aimskr.ac2.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// 접수 상태
@Getter
public enum AcceptStatus {
    OK("200", "OK"),
    INVALID("400", "INVALID"), // 성공
    DUPLICATE("400", "DUPLICATE"), // 성공
    ERROR("500", "ERROR");   // 에러


    private String code;
    private String message;

    AcceptStatus(String accidentCd, String message){
        this.code = accidentCd;
        this.message = message;
    }
    public static String getMessage(String code) {
        return Arrays.stream(AcceptStatus.values())
                .filter(x -> x.code.equals(code))
                .findFirst()
                .orElse(OK)
                .getMessage();
    }

    public static AcceptStatus getEnum(String code){
        for (AcceptStatus acceptStatus: values()){
            if (acceptStatus.code.equals(code)){
                return acceptStatus;
            }
        }
        return null;
    }
}
