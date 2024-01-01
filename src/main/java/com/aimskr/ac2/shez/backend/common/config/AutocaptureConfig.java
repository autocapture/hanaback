package com.aimskr.ac2.kakao.backend.common.config;

import com.aimskr.ac2.kakao.backend.common.enums.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * resources/application.yml에 저장된 Autocapture 설정 관리 (예: 외부 API 호출 URL)
 */
@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "autocapture")
public class AutocaptureConfig {
    // 외부 시스템 접속 정보
    private String kakaoSftpIp;
    private String kakaoSftpPort;
    private String kakaoSftpUser;
    private String kakaoSftpKey;
    private String kakaoSftpPwd;
    private String kakaoSftpPath;

    private String kakaoApiServer;
    private String kakaoResultPath;
    private String kakaoCredential;
    private String kakaoQaPath;

    private String frontUrl;
}
