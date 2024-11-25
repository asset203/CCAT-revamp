package com.asset.ccat.dynamic_page.aspect;

import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author nour.ihab
 */
@Aspect
@Configuration
public class LoggingAspect {

    @Around("@annotation(com.asset.ccat.dynamic_page.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        long executionTime = 0;
        long start = 0;
        String className = "";
        String methodName = "";
        try {
            start = System.currentTimeMillis();
            className = joinPoint.getSignature().getDeclaringTypeName();
            methodName = joinPoint.getSignature().getName();
            proceed = joinPoint.proceed();
            executionTime = System.currentTimeMillis() - start;
        } finally {
            CCATLogger.DEBUG_LOGGER.info(className + "." + methodName + " executed in [" + executionTime + "] ms");
        }
        return proceed;
    }
}
