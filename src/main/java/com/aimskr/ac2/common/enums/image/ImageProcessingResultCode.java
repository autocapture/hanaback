package com.aimskr.ac2.common.enums.image;

import lombok.Getter;

/**
 * 이미지 처리 결과 코드
 */
@Getter
public enum ImageProcessingResultCode {

    NORMAL("P00", "정상이미지"),
    DUPLICATE("P10", "중복이미지"),
    WRONG_BIZNO("P20", "비정상사업자등록번호"),
    WRONG_MD5("P30", "md5sum불일치"),
    NOT_SUPPORT("P40", "처리불가확장자"),
    // P99: API 포함안됨
    ERROR("P99", "SFTP오류");


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
