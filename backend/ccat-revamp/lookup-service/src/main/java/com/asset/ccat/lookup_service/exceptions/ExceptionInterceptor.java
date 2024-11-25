/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.exceptions;

import com.asset.ccat.lookup_service.cache.MessagesCache;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessagesCache messagesCache;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error(" An error has occured ex : " + ex.getMessage());
        CCATLogger.ERROR_LOGGER.error(" An error has occured  errorcode message : ", ex);
        BaseResponse<String> response = new BaseResponse();
        response.setStatusCode(ErrorCodes.ERROR.GENERAL_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.GENERAL_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        CCATLogger.DEBUG_LOGGER.debug("Api Response is " + response);
        ThreadContext.remove("transactionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(LookupException.class)
    public final ResponseEntity<BaseResponse> handelAIRException(LookupException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error(" An error has occured ex : " + ex.getMessage());
        CCATLogger.ERROR_LOGGER.error(" An error has occured  errorcode message : ", ex);
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
