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
@ConfigurationProperties(prefix = "vision")
public class VisionConfig {

    private boolean autoInputMode;
    private boolean drawMode;
    private String drawRange;
}
