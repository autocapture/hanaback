package com.aimskr.ac2.common.enums;

import lombok.Getter;

@Getter
public enum ClaimType {
    CT1("1", "상해"),
    CT2("2", "질병"),
    CT3("3", "재물"),
    CT4("4", "배상"),
    CT5("5", "근재"),
    CT6("6", "해상");

    String code;
    String name;

    ClaimType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ClaimType findByCode(String code) {
        for (ClaimType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static ClaimType findByName(String name) {
        for (ClaimType value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }
}
