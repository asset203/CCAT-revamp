/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.FilesException;
import com.asset.ccat.user_management.exceptions.LoginException;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.StandardCharsets;

@ControllerAdvice
@RestController
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MessagesCache messagesCache;

    @ExceptionHandler(UserManagementException.class)
    public final ResponseEntity<BaseResponse<String>> handelUserManagementException(UserManagementException ex, WebRequest req) {
        if(ex instanceof LoginException)
            CCATLogger.DEBUG_LOGGER.debug("Invalid Login occurred --> exception: {}", ex.getArgs());
        else{
            CCATLogger.DEBUG_LOGGER.error("UserManagementException occurred. {}", ex.getArgs());
            CCATLogger.ERROR_LOGGER.error("UserManagementException occurred {}", ex.getArgs());
        }

        BaseResponse<String> response = new BaseResponse<>();
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

    @ExceptionHandler(FilesException.class)
    public final ResponseEntity<Resource> handelUserManagementException(FilesException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("FilesException has been thrown");
        CCATLogger.ERROR_LOGGER.error("FilesException has been thrown");

        byte[] emptyBytes = "".getBytes(StandardCharsets.UTF_8);

        ByteArrayResource resource = new ByteArrayResource(emptyBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=error.csv")
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<String>> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("An exception has occurred exc: ", ex);
        CCATLogger.ERROR_LOGGER.error(" An exception has occurred and the error code message: ", ex);
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        CCATLogger.DEBUG_LOGGER.debug("Api Response is {}", response);
        ThreadContext.remove("transactionId");
        ThreadContext.remove("sessionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
