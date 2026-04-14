package com.tms.aspects;

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

@Aspect
@Component
public class MyAspect {

    @Pointcut("within(com.tms.*)")
    public void myRegexPointcut() {
    }

    @Before("myRegexPointcut()") //Advice + Pointcut
    public void printBefore(JoinPoint jp) {
        System.out.println("JoinPoint Before :" + jp.getSignature());
        System.out.println("AspectBefore method");
    }

    @After("myRegexPointcut()") //Advice + Pointcut
    public void printAfter() {
        System.out.println("AspectAfter method");
    }

    @AfterThrowing(value = "myRegexPointcut()", throwing = "err") //Advice + Pointcut
    public void printAfterThrowing(ArithmeticException err) {
        System.out.println("Aspect ArithmeticException method: " + err.toString());
    }

    @AfterReturning(value = "myRegexPointcut()", returning = "result") //Advice + Pointcut
    public void printAfterReturning(Object result) {
        System.out.println("Aspect AfterReturning method: " + result);
    }

    @Around("within(com.tms.*)")
    public Object printAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(LocalDateTime.now() + " IN: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        System.out.println(LocalDateTime.now() + " OUT: " + joinPoint.getSignature().getName());
        return result;
    }
}