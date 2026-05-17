package com.ashish.ai_assistant.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.ashish.ai_assistant.controller.*.*(..))")
    public void beforeExecution(){
        log.info("Method execution started");
    }

    @After("execution(* com.ashish.ai_assistant.controller.*.*(..))")
    public void afterExecution(){
        log.info("Method execution ended.");
    }

    @Around("execution(* com.ashish.ai_assistant.controller.*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // actual method call

        long time = System.currentTimeMillis() - start;
        log.info("Execution time: " + time);

        return result;
    }
}
