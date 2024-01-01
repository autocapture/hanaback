package com.aimskr.ac2.kakao.backend.common.enums.image;

public enum ImageFit {
    ALL("전체 이미지 적합"),
    SOME("일부 이미지 적합"),
    NOTHING("전체 이미지 부적합");

    private String message;

    ImageFit(String message) {
        this.message = message;
    }
}
