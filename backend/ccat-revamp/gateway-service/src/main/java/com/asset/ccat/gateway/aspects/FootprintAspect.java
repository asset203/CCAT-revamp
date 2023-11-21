package com.asset.ccat.gateway.aspects;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.gateway.util.GatewayUtil;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
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
            CCATLogger.DEBUG_LOGGER.info("Arguments of Method" + methodName + " In class:" + className + "are:" + Arrays.toString(methodArguments));
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info(methodName + "In class: " + className + " executed in " + executionTime + "ms");
        } catch (Throwable th) {
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.info("Exception while enqueue footprint object" + " executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.error("Exception while enqueue footprint object" + " Failed ", th);
            throwable = th;
            throw th;
        } finally {
            if (Objects.nonNull(methodArguments)) {
                baseRequest = getRequestFromArgs(methodArguments);
            }
            footprint = prepareFootprintForEnqueue(baseRequest, response, className, methodName, throwable);
            enqueueFootprintModel(footprint);
        }
        return response;
    }


    private FootprintModel prepareFootprintForEnqueue(BaseRequest request,
                                                      Object response, String controllerName, String methodName, Throwable throwable) {
        FootprintModel footprint = null;
        String pageName;
        String actionName;
        String actionType;
        try {
            if (Objects.isNull(request.getFootprint())) {
                footprint = new FootprintModel();
            } else {
                footprint = request.getFootprint();
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
            CCATLogger.DEBUG_LOGGER.info("Exception while prepare footprint object " + footprint);
            CCATLogger.DEBUG_LOGGER.error("Exception while prepare footprint object " + footprint, ex);
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
}
