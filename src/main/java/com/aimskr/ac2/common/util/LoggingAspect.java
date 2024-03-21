package com.aimskr.ac2.common.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    // com.aimskr.ac2.kakao 패키지의 Controller로 끝나는 클래스의 모든 Method에 적용할 Aspect
    // 24.01.09 -> ChannelController에만 적용하도록 변경 (화면 조회 결과 로그 등 불필요한 로그가 많아서 다른 Controller 제외)
    @Pointcut("execution( * com.aimskr.ac2.hana..ChannelController.*(..)) || execution( * com.aimskr.ac2.hana..HelloController.*(..) ) || execution( * com.aimskr.ac2.hana..PhoneController.*(..) )")
    public void controllerPointcut() {}

    @Before("controllerPointcut()")
    public void beforeRequest(JoinPoint joinPoint) {
        log.info("###Start request {}", joinPoint.getSignature().toShortString());
        Arrays.stream(joinPoint.getArgs())
                .map(Object::toString)
                .map(str -> "\t" + str)
                .forEach(log::info);
    }

    @AfterReturning(pointcut="controllerPointcut()", returning="returnValue")
    public void afterReturningLogging(JoinPoint joinPoint, Object returnValue) {
        log.info("###End request {}", joinPoint.getSignature().toShortString());
        if (returnValue == null) return;
        log.debug("\t{}", returnValue.toString());
    }

    @AfterThrowing(pointcut="controllerPointcut()", throwing="e")
    public void afterThrowingLogging(JoinPoint joinPoint, Exception e) {
        log.error("###Occured error in request {}", joinPoint.getSignature().toShortString());
        log.error("\t{}", e.getMessage());
        e.printStackTrace();
    }
}
