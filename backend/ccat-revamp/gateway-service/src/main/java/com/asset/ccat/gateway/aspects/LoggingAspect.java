package com.asset.ccat.gateway.aspects;

import com.asset.ccat.gateway.logger.CCATLogger;
import java.util.Arrays;
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

    @Around("@annotation(com.asset.ccat.gateway.annotation.LogExecutionTime)")
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
            CCATLogger.DEBUG_LOGGER.warn("ExecutionTime = {} ms.", executionTime);
            throw th;
        }
        return proceed;
    }
}
