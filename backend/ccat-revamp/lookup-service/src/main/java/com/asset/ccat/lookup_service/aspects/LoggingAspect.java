package com.asset.ccat.lookup_service.aspects;

import com.asset.ccat.lookup_service.logger.CCATLogger;
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

    @Around("@annotation(com.asset.ccat.lookup_service.annotation.LogExecutionTime)")
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
            CCATLogger.DEBUG_LOGGER.info("Method [ " + methodName + "]  in class - [ " + className + " ] - Started in" + start + "ms");
            proceed = joinPoint.proceed();
            CCATLogger.DEBUG_LOGGER.info("Arguments of Method [ " + methodName + "]  in class - [ " + className + " ] are:" + Arrays.toString(methodArguments));
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Method [ " + methodName + "]  in class - [ " + className + " ] -  executed in " + executionTime + "ms");
        } catch (Throwable th) {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Method [ " + methodName + "]  in class - [ " + className + " ] -  executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.error("Method [ " + methodName + "]  in class - [ " + className + " ] -  Faild ", th);
            throw th;
        }
        return proceed;
    }
}
