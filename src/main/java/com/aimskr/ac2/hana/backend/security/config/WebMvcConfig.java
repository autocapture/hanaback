package com.aimskr.ac2.hana.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 대응
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final long MAX_AGE_SECONDS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://dev.aimskr.com:4433/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("session_id")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECONDS);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
