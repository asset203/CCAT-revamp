package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessagesCache messagesCache;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<String>> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("Exception occurred exc: {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("Exception has occurred  ", ex);
        String requestId = ThreadContext.get("requestId");
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        response.setRequestId(requestId);
        CCATLogger.DEBUG_LOGGER.debug("Api Response is {}", response);
        ThreadContext.remove("transactionId");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(GatewayValidationException.class)
    public final ResponseEntity<BaseResponse<String>> handelGatewayValidationException(GatewayValidationException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.debug("GatewayValidationException occurred : {}", ex.getMessage());
        String requestId = ThreadContext.get("requestId");
        BaseResponse<String> response = new BaseResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getWarningMsg(ex.getErrorCode());
        if (Objects.nonNull(ex.getArgs())) {
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        }
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.VALIDATION);
        ThreadContext.remove("transactionId");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(GatewayException.class)
    public final ResponseEntity<BaseResponse<String>> handelGatewayException(GatewayException ex, WebRequest req) {
        if(ex.getErrorCode() != ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.INVALID_USERNAME_OR_PASSWORD &&
                ex.getErrorCode() != ErrorCodes.USER_MANAGEMENT_SERVICE_CODES.LDAP_AUTH_FAILED){
            CCATLogger.DEBUG_LOGGER.error("GatewayException has been thrown: {}", ex.getMessage());
            CCATLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        }
        else
            CCATLogger.DEBUG_LOGGER.debug(" Authentication failed + {}", ex.getMessage());

        String requestId = ThreadContext.get("requestId");
        BaseResponse<String> response = new BaseResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ex.getErrorCode());
        String msg = "";
        if (Objects.isNull(ex.getMessage()) && Objects.nonNull(ex.getArgs()) && ex.getArgs().length > 0) {
            msg = messagesCache.getErrorMsg(ex.getErrorCode());
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        } else if (Objects.nonNull(ex.getMessage())) {
            msg = ex.getMessage();
        } else {
            msg = messagesCache.getErrorMsg(ex.getErrorCode());
        }
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("transactionId");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
