package com.aimskr.ac2.common.config;

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
    private String sftpIp;
    private String sftpPort;
    private String sftpUser;
    private String sftpKey;
    private String sftpPwd;
    private String sftpPath;

    private String apiServer;
    private String resultPath;
    private String credential;
    private String qaPath;

    private String frontUrl;
}
