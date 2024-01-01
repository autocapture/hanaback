package com.aimskr.ac2.kakao.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    /**
     * 2차 정보입력(진료비 세부내역서)은 월 1만건으로 시작하여 내년 초 월 5만건까지 물량 확대 예정입니다.
     * 월 1만건은 영업일수 고려 시, 하루 500건 처리량이며, 건 당 평균 3장의 이미지가 있다고 분석되었기 때문에 하루 이미지 송수신량은 약 1,500장 정도입니다.
     * 따라서 한화손보의 2차 정보입력 기준 Max volume은 월 5만건, 하루 2,500건, 이미지 기준 약 7,500장 정도입니다.
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor taskExecutor() {
        // 2022.04.16 Core 1->2, MAX 2 -> 4, Queue 3000 -> 5000으로 변경
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(5000);
        executor.setThreadNamePrefix("Executor-");
        executor.initialize();
        return executor;
    }
}