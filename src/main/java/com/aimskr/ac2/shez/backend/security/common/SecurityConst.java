package com.aimskr.ac2.kakao.backend.security.common;
public class SecurityConst {
    /**
     * 비밀번호 변경 시 계정이 없을 때, 리턴하는 문자열 (있다면 메일주소를 넘김)
     */
    public static final String NOT_EXIST_ACCOUNT = "NOT_EXIST";
    public static final String NO_EMAIL_ACCOUNT = "NO_EMAIL";
    public static final String WORNG_VERIFICATION_CODE = "잘못된 인증 코드";
    public static final String PASSWORD_POLICY_VIOLATION = "패스워드 정책 위반";
    public static final String PASSWORD_CHANGE_SUCCESS = "OK";
    public static final String VALIDATION_SUCCESS = "SECCESS";
}
