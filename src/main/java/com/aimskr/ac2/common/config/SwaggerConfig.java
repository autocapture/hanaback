package com.aimskr.ac2.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Autocapture-Carrot API 명세서",
                version = "v2",
                description = "캐롯손해보험용 오토캡처 서비스 API 명세서"
        )
)

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi autocaptureApi() {
        String[] paths = {"/v2/hana/**"};
        return GroupedOpenApi.builder()
                .group("Autocapture-Carrot")
                .pathsToMatch(paths)
                .build();
    }
}
