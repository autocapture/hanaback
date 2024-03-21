package com.aimskr.ac2.common.enums.assign;

import lombok.Getter;

@Getter
public enum RequestType {

    NORMAL("T00", "일반"),
    URGENT("T10", "긴급");

    private String code;
    private String message;

    RequestType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RequestType of(String code) {
        for (RequestType requestType : values()) {
            if (requestType.code.equals(code)) {
                return requestType;
            }
        }
        throw new IllegalArgumentException("Unknown description: " + code);
    }

}
