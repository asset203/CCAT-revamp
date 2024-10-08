package com.asset.ccat.gateway.aspects;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.gateway.util.GatewayUtil;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Aspect
@Configuration
public class FootprintAspect {

    @Autowired
    FootprintService footprintService;

    @Autowired
    LookupsCache lookupsCache;

    @Autowired
    GatewayUtil gatewayUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @Around("@annotation(com.asset.ccat.gateway.annotation.LogFootprint)")
    public Object logFootPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        long executionTime;
        long start = 0;
        Object response = null;
        Throwable throwable = null;
        String className = "";
        String methodName = "";
        Object[] methodArguments = null;
        BaseRequest baseRequest = null;
        FootprintModel footprint;
        try {
            start = System.currentTimeMillis();
            methodArguments = joinPoint.getArgs();
            className = joinPoint.getSignature().getDeclaringTypeName();
            methodName = joinPoint.getSignature().getName();
            response = joinPoint.proceed();
            CCATLogger.DEBUG_LOGGER.info("MethodName is {} In class: {} args = {}", methodName, className, Arrays.toString(methodArguments));
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Execution time for method {} is {}", methodName, executionTime);
        } catch (Throwable th) {
            boolean shouldLog = !(th instanceof GatewayException) || handleInvalidLoginExceptions((GatewayException) th);
            if (shouldLog) {
                CCATLogger.DEBUG_LOGGER.error("Throwable Exception Before enqueueing footprint object.", th);
                CCATLogger.ERROR_LOGGER.error("Throwable Exception Before enqueueing footprint object.", th);
            }
            throwable = th;
            throw th;
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Preparing footprint model for RMQ enqueuing.");
            String msisdn = null;
            if (Objects.nonNull(methodArguments)) {
                baseRequest = getRequestFromArgs(methodArguments);
                msisdn = getMsisdn(methodArguments);
            }
            footprint = prepareFootprintForEnqueue(baseRequest, msisdn, response, className, methodName, throwable);
            enqueueFootprintModel(footprint);
        }
        return response;
    }


    private FootprintModel prepareFootprintForEnqueue(BaseRequest request, String msisdn,
                                                      Object response, String controllerName,
                                                      String methodName, Throwable throwable) {
        FootprintModel footprint = null;
        String pageName;
        String actionName;
        String actionType;
        try {
            if (Objects.isNull(request.getFootprintModel())) {
                footprint = new FootprintModel();
                if(request.getToken() != null) { // Not from Login request Before token generation
                    HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
                    String profileName = tokenData.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
                    String machineName = tokenData.get(Defines.SecurityKeywords.MACHINE_NAME).toString();
                    footprint.setMachineName(machineName);
                    footprint.setProfileName(profileName);
                }
                footprint.setMsisdn(msisdn);
            } else {
                footprint = request.getFootprintModel();
            }
            controllerName = controllerName.substring(controllerName.lastIndexOf(".") + 1);
            pageName = this.lookupsCache.getFootPrintPages().get(controllerName).getPageName();
            actionName = this.lookupsCache.getFootPrintPages()
                    .get(controllerName)
                    .getFootprintPageInfoMap()
                    .get(methodName).getActionName();
            actionType = this.lookupsCache.getFootPrintPages()
                    .get(controllerName)
                    .getFootprintPageInfoMap()
                    .get(methodName).getActionType();

            footprint.setRequestId(request.getRequestId());
            footprint.setSessionId(request.getSessionId());
            footprint.setUserName(request.getUsername());
            footprint.setPageName(Objects.isNull(pageName) || pageName.equals("") ?
                    controllerName.replace("Controller", "") : pageName);
            footprint.setActionName(Objects.isNull(actionName) || actionName.equals("") ? methodName : actionName);
            footprint.setActionType(Objects.isNull(actionType) || actionType.equals("") ? methodName : actionType);
            footprint = footprintStatusHandler(footprint, response, throwable);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while prepare footprint object ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while preparing footprint object ", ex);
        }
        return footprint;
    }


    private FootprintModel footprintStatusHandler(FootprintModel footprint, Object response, Throwable throwable) {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start prepare footprint status");
            if (Objects.nonNull(throwable)) {
                return gatewayUtil.footprintExceptionHandler(throwable, footprint);
            }

            if (response instanceof ResponseEntity) {
                footprint.setStatus(Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS);
                footprint.setErrorMessage(Defines.FOOT_PRINT_STATUS.SUCCESSFUL);
                footprint.setErrorCode(Defines.FOOT_PRINT_STATUS.SUCCESSFUL);
            } else {
                BaseResponse result = (BaseResponse) response;
                footprint.setErrorMessage(result.getStatusMessage());
                footprint.setErrorCode(String.valueOf(result.getStatusCode()));
                if (result.getStatusCode().equals(0)) {
                    footprint.setStatus(Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS);
                } else {
                    footprint.setStatus(Defines.FOOT_PRINT_STATUS.FAILED_STATUS);
                }
            }
            CCATLogger.DEBUG_LOGGER.info("Footprint enqueue is done successfully!!");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Exception while prepare footprint response status " + footprint);
            CCATLogger.DEBUG_LOGGER.error("Exception while prepare footprint response status " + footprint, ex);
        }
        return footprint;
    }


    private void enqueueFootprintModel(FootprintModel footprint) {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start enqueue footprint");
            footprintService.enqueueFootprint(footprint);
            CCATLogger.DEBUG_LOGGER.info("Footprint enqueue is done successfully!!");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.info("Exception while enqueue footprint object " + footprint);
            CCATLogger.DEBUG_LOGGER.error("Exception while enqueue footprint object " + footprint, ex);
        }
    }


    private BaseRequest getRequestFromArgs(Object[] methodArgs) {
        BaseRequest req = null;
        for (Object methodArg : methodArgs) {
            if (methodArg instanceof BaseRequest) {
                req = (BaseRequest) methodArg;
            }
        }
        return req;
    }

    private String getMsisdn(Object[] methodArgs){
        String msisdn = null;
        for (Object methodArg : methodArgs)
            msisdn = extractMsisdnFromMethodArg(methodArg);
        return msisdn;
    }

    private String extractMsisdnFromMethodArg(Object methodArg) {
        try {
            // Check if there's a getMsisdn() method
            Method getMsisdnMethod = methodArg.getClass().getMethod("getMsisdn");
            return (String) getMsisdnMethod.invoke(methodArg);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // If no getMsisdn() method exists, check if there's an msisdn field
            try {
                Field msisdnField = methodArg.getClass().getDeclaredField("msisdn");
                msisdnField.setAccessible(true);  // Allow access to private fields
                return (String) msisdnField.get(methodArg);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                // msisdn field doesn't exist or can't be accessed
                CCATLogger.DEBUG_LOGGER.warn("MSISDN field or method not found in the request object.");
                return null;
            }
        }
    }

    private boolean handleInvalidLoginExceptions(GatewayException ex){
        int errorCode = ex.getErrorCode();
        boolean shouldLog;
        // To avoid false alarms --> customer's request.
        switch (errorCode) {
            case ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD:
            case ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.INVALID_USERNAME_OR_PASSWORD:
            case ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.LDAP_AUTH_FAILED:
                shouldLog = false; // No need to log for these cases
                break;
            default:
                shouldLog = true;
                break;
        }
        return shouldLog;
    }
}
