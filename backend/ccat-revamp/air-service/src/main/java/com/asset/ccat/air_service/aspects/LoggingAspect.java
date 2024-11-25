package com.asset.ccat.air_service.aspects;

import com.asset.ccat.air_service.logger.CCATLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * @author Assem.Hassan
 */
@Aspect
@Configuration
public class LoggingAspect {

    @Around("@annotation(com.asset.ccat.air_service.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        long executionTime = 0;
        long start = 0;
        String className = "";
        String methodName = "";
        Object[] methodArguments;
        try {
            start = System.currentTimeMillis();
            className = joinPoint.getSignature().getDeclaringTypeName();
            methodName = joinPoint.getSignature().getName();
            proceed = joinPoint.proceed();
            executionTime = System.currentTimeMillis() - start;
            } catch (Throwable th) {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Method [ " + methodName + "]  in class - [ " + className + " ] -  executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.error("Method [ " + methodName + "]  in class - [ " + className + " ] -  Faild ", th);
            throw th;
        } finally {
            CCATLogger.DEBUG_LOGGER.info("Method [ " + methodName + "]  in class - [ " + className + " ] -  executed in " + executionTime + "ms");
        }
        return proceed;
    }
}