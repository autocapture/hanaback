package com.aimskr.ac2.kakao.backend.common.enums.web;

public enum ResultResponse {
    R00("정상접수"),
    R10("피보상이"),
    R20("중복"),
    R30("지연아님"),
    R50("처리불가"),
    R99("시간초과");

    private String message;

    ResultResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
