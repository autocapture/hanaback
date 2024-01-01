package com.aimskr.ac2.kakao.backend.security.common;

import org.bouncycastle.util.encoders.Base64;

/**
 * Postman에서 API 테스트 하기 위해, Credential을 계산하는 임시 클래스
 */
public class KeyGen {
    public static void main(String[] args) {
        String clientId = "digitexx";
        String secret = "autocapture";
        String credentials = clientId + ":" + secret;
        String encodedCredentials = new String(Base64.encode(credentials.getBytes()));

    }
}
