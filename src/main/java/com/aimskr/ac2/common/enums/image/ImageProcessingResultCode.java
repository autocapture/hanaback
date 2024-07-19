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
    FIBO_ERROR("P31", "피보험자불일치"),
    NOT_SUPPORT("P40", "처리불가확장자");


    private String code;
    private String name;

    ImageProcessingResultCode(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static ImageProcessingResultCode findByCode(String code) {
        for (ImageProcessingResultCode imageProcessingResultCode: values()){
            if (imageProcessingResultCode.code.equals(code)){
                return imageProcessingResultCode;
            }
        }
        return null;
    }

    public static ImageProcessingResultCode findByName(String name) {
        for (ImageProcessingResultCode imageProcessingResultCode: values()){
            if (imageProcessingResultCode.name.equals(name)){
                return imageProcessingResultCode;
            }
        }
        return null;
    }
}
