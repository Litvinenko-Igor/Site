package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Order(1)
@Aspect
@Component
public class ServiceLoggingAspect {

    @Pointcut("within(com.example.demo.Data..*)")
    public void dataPackage() {}

    @Around("dataPackage()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        long startTime = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            long ms = (System.nanoTime() - startTime) / 1_000_000;
            log.info("{} OK {}ms args={}", method, ms, Arrays.toString(args));
            return result;
        } catch (Throwable e) {
            long ms = (System.nanoTime() - startTime) / 1_000_000;
            log.error("{} FAIL {}ms args={}", method, ms, Arrays.toString(args), e);
            throw e;
        }
    }
}
