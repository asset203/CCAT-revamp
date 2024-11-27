package com.asset.ccat.gateway.aspects;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FootprintPopulationService;
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
import java.util.*;

/**
 * @author Assem.Hassan
 */
@Aspect
@Configuration
public class FootprintAspect implements FootprintPopulationService {
    private final FootprintService footprintService;
    private final LookupsCache lookupsCache;
    private final GatewayUtil gatewayUtil;
    private final JwtTokenUtil jwtTokenUtil;

    private String requestId;
    private String pageName;
    private String actionName;
    private String actionType;
    private String username;
    private String profileName;
    private String msisdn;
    private String status;
    private String errorMessage;
    private String errorCode;
    private String sessionId;
    private String machineName;
    private Integer sendSms = 0;

    @Autowired
    public FootprintAspect(FootprintService footprintService, LookupsCache lookupsCache, GatewayUtil gatewayUtil, JwtTokenUtil jwtTokenUtil) {
        this.footprintService = footprintService;
        this.lookupsCache = lookupsCache;
        this.gatewayUtil = gatewayUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Around("@annotation(com.asset.ccat.gateway.annotation.LogFootprint)")
    public Object logFootPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        long executionTime;
        long start;
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
            CCATLogger.FOOTPRINT_LOGGER.info("MethodName is {} In class: {} args = {}", methodName, className, Arrays.toString(methodArguments));
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Execution time for method {} is {}", methodName, executionTime);
        } catch (Throwable th) {
            CCATLogger.FOOTPRINT_LOGGER.info("This request triggered a throwable exception.");
            boolean shouldLog = !(th instanceof GatewayException) || handleInvalidLoginExceptions((GatewayException) th);
            if (shouldLog)
                CCATLogger.FOOTPRINT_LOGGER.warn("Throwable Exception Before enqueueing footprint object.", th);
            throwable = th;
            throw th;
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Preparing footprint model for RMQ enqueuing.");
            if (Objects.nonNull(methodArguments)) {
                baseRequest = getRequestFromArgs(methodArguments);
                CCATLogger.FOOTPRINT_LOGGER.info("Start preparing Footprint model for the following baseRequest --> {}", baseRequest);
                setMsisdn(methodArguments);
            }

            footprint = prepareFootprintForEnqueue(baseRequest, response, className, methodName, throwable);
            enqueueFootprintModel(footprint);
        }
        return response;
    }

    private FootprintModel prepareFootprintForEnqueue(BaseRequest request,
                                                      Object response, String controllerName,
                                                      String methodName, Throwable throwable) {
        try {
            if (request != null && request.getToken() != null)
                populateTokenData(request.getToken());
            else {
                CCATLogger.FOOTPRINT_LOGGER.info("No token found");
                profileName = Optional.ofNullable(request)
                        .map(BaseRequest::getFootprintModel)
                        .map(FootprintModel::getProfileName)
                        .orElse(null);
                machineName = Optional.ofNullable(request)
                        .map(BaseRequest::getFootprintModel)
                        .map(FootprintModel::getMachineName)
                        .orElse(null);
                sessionId = Optional.ofNullable(request)
                        .map(BaseRequest::getSessionId)
                        .orElse(null);
                username = Optional.ofNullable(request)
                        .map(BaseRequest::getUsername)
                        .orElse(null);
                String[] extractedDate = {profileName, machineName, sessionId, username};
                CCATLogger.FOOTPRINT_LOGGER.info("Data extracted from the token: {}", (Object) extractedDate);
            }
            populateRequestData(request);
            populateResponseData(response, throwable);
            populateCachedData(controllerName, methodName);
        } catch (Exception ex) {
            CCATLogger.FOOTPRINT_LOGGER.error("Exception while preparing footprint object ", ex);
            CCATLogger.DEBUG_LOGGER.error("Exception while preparing footprint object ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while preparing footprint object ", ex);
        }
        FootprintModel footprintModel = new FootprintModel(requestId, pageName, actionName, actionType, username, profileName, msisdn, status, errorMessage, errorCode, sessionId, machineName, sendSms);
        if (request != null && (request.getFootprintModel() != null))
            footprintModel.setFootPrintDetails(footprintModel.getFootPrintDetails());
        return footprintModel;
    }

    @Override
    public void populateRequestData(BaseRequest request) {
        requestId = Optional.ofNullable(request)
                .map(BaseRequest::getRequestId)
                .orElse(null);

        sendSms = Optional.ofNullable(request)
                .map(BaseRequest::getFootprintModel)
                .map(FootprintModel::getSendSms)
                .orElse(0);

        CCATLogger.FOOTPRINT_LOGGER.info("RequestID = {}, sendSMS = {}", requestId, sendSms);
    }

    @Override
    public void populateTokenData(String token) throws GatewayException {

        Map<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(token);
        profileName = Optional.ofNullable(tokenData.get(Defines.SecurityKeywords.PROFILE_NAME))
                .map(Object::toString)
                .orElse(null);
        machineName = Optional.ofNullable(tokenData.get(Defines.SecurityKeywords.MACHINE_NAME))
                .map(Object::toString)
                .orElse(null);
        sessionId = Optional.ofNullable(tokenData.get(Defines.SecurityKeywords.SESSION_ID))
                .map(Object::toString)
                .orElse(null);
        username = Optional.ofNullable(tokenData.get(Defines.SecurityKeywords.USERNAME))
                .map(Object::toString)
                .orElse(null);

        String[] extractedDate = {profileName, machineName, sessionId, username};
        CCATLogger.FOOTPRINT_LOGGER.info("Data extracted from the token: {}", (Object) extractedDate);
    }

    @Override
    public void populateCachedData(String controllerName, String methodName) {
        CCATLogger.FOOTPRINT_LOGGER.info("ControllerName = {} AND MethodName = {}", controllerName, methodName);
        controllerName = controllerName.substring(controllerName.lastIndexOf(".") + 1);
        pageName = this.lookupsCache.getFootPrintPages().get(controllerName).getPageName();
        pageName = Objects.isNull(pageName) || pageName.equals("") ?
                controllerName.replace("Controller", "") : pageName;
        actionName = this.lookupsCache.getFootPrintPages()
                .get(controllerName)
                .getFootprintPageInfoMap()
                .get(methodName).getActionName();
        actionName = Objects.isNull(actionName) || actionName.equals("") ? methodName : actionName;
        actionType = this.lookupsCache.getFootPrintPages()
                .get(controllerName)
                .getFootprintPageInfoMap()
                .get(methodName).getActionType();
        actionType = Objects.isNull(actionType) || actionType.equals("") ? methodName : actionType;
        CCATLogger.FOOTPRINT_LOGGER.info("PageName={}, ActionName={}, ActionType={}", pageName, actionName , actionType);
    }

    @Override
    public void populateResponseData(Object response, Throwable throwable) {
        try {
            if (Objects.nonNull(throwable)) {
                FootprintModel f = new FootprintModel();
                gatewayUtil.footprintExceptionHandler(throwable, f);
                status = f.getStatus();
                errorCode = f.getErrorCode();
                errorMessage = f.getErrorMessage();
                CCATLogger.FOOTPRINT_LOGGER.info("Throwable response: status={}, errorCode={}, errorMessage={}", status, errorCode, errorMessage);
                return;
            }

            if (response instanceof ResponseEntity) {
                status = Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS;
                errorMessage = Defines.FOOT_PRINT_STATUS.SUCCESSFUL;
                errorCode = Defines.FOOT_PRINT_STATUS.SUCCESSFUL;
                CCATLogger.FOOTPRINT_LOGGER.info("Success response: status={}, errorCode={}, errorMessage={}", status, errorCode, errorMessage);
            } else {
                BaseResponse result = (BaseResponse) response;
                CCATLogger.FOOTPRINT_LOGGER.info("Custom response {}", result);
                errorMessage = result.getStatusMessage();
                errorCode = String.valueOf(result.getStatusCode());
                status = result.getStatusCode().equals(0) ? Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS :
                        Defines.FOOT_PRINT_STATUS.FAILED_STATUS;
            }
        } catch (Exception ex) {
            CCATLogger.FOOTPRINT_LOGGER.error("Exception while prepare footprint response status ");
            CCATLogger.DEBUG_LOGGER.error("Exception while prepare footprint response status ");
            CCATLogger.ERROR_LOGGER.error("Exception while prepare footprint response status ", ex);
        }
    }


    private void enqueueFootprintModel(FootprintModel footprint) {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start enqueue footprint");
            footprintService.enqueueFootprint(footprint);
            CCATLogger.DEBUG_LOGGER.info("Footprint enqueue is done successfully!!");
        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.error("GatewayException while enqueue footprint object {}", ex.getMessage());
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

    private void setMsisdn(Object[] methodArgs) {
        for (Object methodArg : methodArgs)
            this.msisdn = extractMsisdnFromMethodArg(methodArg);
        CCATLogger.FOOTPRINT_LOGGER.debug("MSISDN = {}", msisdn);
    }

    private String extractMsisdnFromMethodArg(Object methodArg) {
        try {
            // Check if there's a getMsisdn() method
            Method getMsisdnMethod = methodArg.getClass().getMethod("getMsisdn");
            return (String) getMsisdnMethod.invoke(methodArg);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // If no getMsisdn() method exists, check if there's a msisdn field
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

    private boolean handleInvalidLoginExceptions(GatewayException ex) {
        int exErrorCode = ex.getErrorCode();
        return switch (exErrorCode) {
            case ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD, ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.INVALID_USERNAME_OR_PASSWORD, ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.LDAP_AUTH_FAILED ->
                    false; // No need to log for these cases
            default -> true;
        };
    }

}
