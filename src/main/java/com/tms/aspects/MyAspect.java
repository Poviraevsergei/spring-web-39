package com.tms.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class MyAspect {

    @Pointcut("within(com.tms.*)")
    public void myRegexPointcut() {
    }

    @Before("myRegexPointcut()") //Advice + Pointcut
    public void printBefore(JoinPoint jp) {
        log.info("JoinPoint Before :" + jp.getSignature());
        log.info("AspectBefore method");
    }

    @After("myRegexPointcut()") //Advice + Pointcut
    public void printAfter() {
        log.info("AspectAfter method");
    }

    @AfterThrowing(value = "myRegexPointcut()", throwing = "err") //Advice + Pointcut
    public void printAfterThrowing(ArithmeticException err) {
        log.info("Aspect ArithmeticException method: " + err.toString());
    }

    @AfterReturning(value = "myRegexPointcut()", returning = "result") //Advice + Pointcut
    public void printAfterReturning(Object result) {
        log.info("Aspect AfterReturning method: " + result);
    }

    @Around("within(com.tms.*)")
    public Object printAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(LocalDateTime.now() + " IN: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info(LocalDateTime.now() + " OUT: " + joinPoint.getSignature().getName());
        return result;
    }
}