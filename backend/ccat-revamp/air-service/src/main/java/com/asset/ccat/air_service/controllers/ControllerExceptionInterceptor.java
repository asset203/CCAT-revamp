/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.cache.MessagesCache;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.exceptions.AIRVoucherException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    private final MessagesCache messagesCache;

    @Autowired
    public ControllerExceptionInterceptor(MessagesCache messagesCache) {
        this.messagesCache = messagesCache;
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<String>> handleAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("Unknown error: {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("Unknown error: ", ex);
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AIRServiceException.class)
    public final ResponseEntity<BaseResponse<String>> handleAIRServiceException(AIRServiceException ex, WebRequest req) {
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null) {
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        }
        CCATLogger.DEBUG_LOGGER.error("AIRServiceException has occurred : {}", msg);
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AIRException.class)
    public final ResponseEntity<BaseResponse<String>> handleAIRException(AIRException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("AIRException has occurred ex : {}", ex.getMessage());
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getExternalSystemErrorMsg(ex.getErrorCode());
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AIRVoucherException.class)
    public final ResponseEntity<BaseResponse<String>> handleAIRVoucherException(AIRVoucherException ex, WebRequest req) {
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getVoucherErrorMsg(ex.getErrorCode());
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        CCATLogger.DEBUG_LOGGER.error("AIRVoucherException occurred ex : {}", msg);
        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
