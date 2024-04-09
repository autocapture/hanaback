package com.aimskr.ac2.common.enums.assign;

// 사고유형(청구유형) CD_DMN_ID
public enum ClaimType {
    // 일반
    NORMAL("C00", "일반"),
    // 긴급
    URGENT("C10", "긴급");

    private String code;
    private String message;

    ClaimType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ClaimType of(String code) {
        for (ClaimType claimType : values()) {
            if (claimType.code.equals(code)) {
                return claimType;
            }
        }
        // TODO: 예외처리를 Catched Exception과 Unchecked Exception으로 나누어서 설계하고 처리
        // or RuntimeException을
        throw new IllegalArgumentException("Unknown description: " + code);
    }
}
