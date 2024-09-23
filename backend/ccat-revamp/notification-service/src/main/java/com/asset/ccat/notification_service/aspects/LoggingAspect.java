package com.asset.ccat.notification_service.aspects;


import com.asset.ccat.notification_service.logger.CCATLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class LoggingAspect {

    @Around("@annotation(com.asset.ccat.notification_service.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        long executionTime;
        long start = 0;
        String className = "";
        String methodName = "";
        Object[] methodArguments;
        try {
            start = System.currentTimeMillis();
            methodArguments = joinPoint.getArgs();
            className = joinPoint.getSignature().getDeclaringTypeName();
            methodName = joinPoint.getSignature().getName();
            CCATLogger.INTERFACE_LOGGER.info("Method" + methodName + "In class: " + className + "Started in" + start + "ms");
            proceed = joinPoint.proceed();
            CCATLogger.INTERFACE_LOGGER.info("Arguments of Method" + methodName + " In class:" + className + "are:" + Arrays.toString(methodArguments));
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info(methodName + "In class: " + className + " executed in " + executionTime + "ms");
        } catch (Throwable th) {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info(methodName + "In class: " + className + " executed in " + executionTime + "ms");
            CCATLogger.INTERFACE_LOGGER.error(methodName + "In class: " + className + " Failed ", th);
            throw th;
        }
        return proceed;
    }
}
