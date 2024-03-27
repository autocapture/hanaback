package com.aimskr.ac2.common.enums;

import lombok.Getter;

@Getter
public enum RequestCode {

    REQUEST("T00", "처리요청"),
    SUCCESS("R00", "수신성공"),
    FAILURE("R99", "수신오류");

    private final String code;
    private final String name;

    RequestCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RequestCode findByCode(String code) {
        for (RequestCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static RequestCode findByName(String name) {
        for (RequestCode value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
