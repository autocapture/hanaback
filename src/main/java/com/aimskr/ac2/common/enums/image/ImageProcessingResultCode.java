package com.aimskr.ac2.common.enums.image;

import lombok.Getter;

/**
 * 이미지 처리 결과 코드
 */
@Getter
public enum ImageProcessingResultCode {

    NORMAL("P00", "정상이미지"),
    DUPLICATE("P10", "중복이미지"),
    BAD_IMAGE("P20", "입력불가"),
    FTP_ERROR("P30", "이미지오류"),
    NOT_SUPPORT("P40", "처리불가확장자");


    private String code;
    private String name;

    ImageProcessingResultCode(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static String getNameFromCode(String stepName) {
        for (ImageProcessingResultCode step: values()){
            if (step.code.equals(stepName)){
                return step.name;
            }
        }
        return null;
    }
}
