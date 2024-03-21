package com.aimskr.ac2.hana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@EnableCaching
@EnableFeignClients
@EnableRetry
@EnableAspectJAutoProxy
@SpringBootApplication
public class AutocaptureApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutocaptureApplication.class, args);
    }
}
