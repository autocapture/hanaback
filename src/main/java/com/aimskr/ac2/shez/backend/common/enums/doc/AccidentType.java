package com.aimskr.ac2.kakao.backend.common.enums.doc;

import lombok.Getter;

import java.util.Arrays;

// 서류유형
@Getter
public enum AccidentType {
    COMMON("00", "공통"),         // 공통
    MEDICAL("01", "치료비"),        // 치료비
    DAMAGE("02", "파손"),         // 파손
    DELAY("03", "항공기"),          // 항공기지연

    PHONEINFO("98", "휴대폰청약");    // 휴대폰청약

    private String code;
    private String message;

    AccidentType(String accidentCd, String message){
        this.code = accidentCd;
        this.message = message;
    }
    public static String getMessage(String code) {
        return Arrays.stream(AccidentType.values())
                .filter(x -> x.code.equals(code))
                .findFirst()
                .orElse(COMMON)
                .getMessage();
    }

    public static AccidentType getEnum(String givenAcCd){
        for (AccidentType accidentType: values()){
            if (accidentType.code.equals(givenAcCd)){
                return accidentType;
            }
        }
        return null;
    }

    public static AccidentType getEnumByMessage(String message){
        for (AccidentType accidentType: values()){
            if (accidentType.message.equals(message)){
                return accidentType;
            }
        }
        return null;
    }
}
