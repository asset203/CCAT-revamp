/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.exceptions;

import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.cache.MessagesCache;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.models.responses.BaseResponse;
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
    public final ResponseEntity<BaseResponse<String>> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("An exception has been occurred: {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("An exception has been occurred: ", ex);

        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);

        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(ODSException.class)
    public final ResponseEntity<BaseResponse<String>> handelODSException(ODSException ex, WebRequest req) {
        String msg = messagesCache.getErrorMsg(ex.getErrorCode());
        if (ex.getArgs() != null)
            msg = messagesCache.replaceArgument(msg, ex.getArgs());

        CCATLogger.DEBUG_LOGGER.error("ODS exception: [code={}] [message:{}]", ex.getErrorCode(), msg);
        CCATLogger.ERROR_LOGGER.error("ODS exception: [code={}] [message:{}]", ex.getErrorCode(), msg);

        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ex.getErrorCode());
        response.setStatusMessage(msg);
        response.setSeverity(Defines.SEVERITY.ERROR);
        ThreadContext.remove("requestId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
