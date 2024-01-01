package com.aimskr.ac2.kakao.backend.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Autocapture-Kakao API 명세서",
                version = "v2",
                description = "카카오페이손해보험용 오토캡처 서비스 API 명세서"
        )
)

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi autocaptureApi() {
        String[] paths = {"/v2/kakao/**"};
        return GroupedOpenApi.builder()
                .group("Autocapture-Kakao")
                .pathsToMatch(paths)
                .build();
    }
}
