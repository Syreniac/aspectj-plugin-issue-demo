package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Aspecting {
    @Around("execution(* org.example.AspectedClass.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Method " + joinPoint.getSignature().getName() + " is about to be called");

        Object result = joinPoint.proceed();

        System.out.println("Method " + joinPoint.getSignature().getName() + " has been called");

        return result;
    }
}