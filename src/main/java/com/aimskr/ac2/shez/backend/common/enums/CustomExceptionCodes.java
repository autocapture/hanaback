package com.aimskr.ac2.kakao.backend.common.enums;

import lombok.Getter;

@Getter
public enum CustomExceptionCodes {
    FLIP(1001),
    NONAUTO(1002),
    MULTI(2001);

    private int numVal;

    CustomExceptionCodes(int numVal) {
        this.numVal = numVal;
    }

}
