package com.aimskr.ac2.kakao.backend.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "control")
// TODO : DB, 화면으로 교체 검토
public class ControlConfig {
    /**
     * 자동회신 여부
     */
    private boolean autoReturn;

    /**
     * 중복검사 여부
     */
    private boolean dupCheck;

    /**
     * 전문검증 여부
     */
    private boolean requestValidation;

    /**
     * 배당 이메일 알림 여부
     */
    private boolean alertMode;
    /**
     * 배당 이메일 주소
     */
    private String alertEmail;

    /**
     * 오류발생 시 이메일 주소
     */
    private String errorEmail;


}
