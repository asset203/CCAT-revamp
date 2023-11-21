/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessagesCache messagesCache;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getMessage());
        CCATLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        CCATLogger.DEBUG_LOGGER.debug("Api Response is " + response);
        ThreadContext.remove("transactionId");
        ThreadContext.remove("sessionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(UserManagementException.class)
    public final ResponseEntity<BaseResponse> handelUserManagementException(UserManagementException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error(" An error has occurred exc: " + ex.getArgs());
        CCATLogger.ERROR_LOGGER.error(" An error has occurred and the error code message: ", ex);
        CCATLogger.DEBUG_LOGGER.debug("create Api Response");
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ex.getErrorCode());
        String msg = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null) {
            msg = messagesCache.replaceArgument(msg, ex.getArgs());
        }
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("transactionId");
        ThreadContext.remove("sessionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
