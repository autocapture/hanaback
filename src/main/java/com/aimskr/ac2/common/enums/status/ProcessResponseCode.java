package com.aimskr.ac2.common.enums.status;

import lombok.Getter;

import java.util.Arrays;

// AIMS처리결과코드
@Getter
public enum ProcessResponseCode {
    SUCCESS("R00", "정상 처리"),
    DISMISS("R20", "처리 불가"),
    FTP_ERR("R30", "SFTP 오류"),
    NA("R40", "입력 대상 없음"),
    ERROR("R99", "시스템 오류");


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
