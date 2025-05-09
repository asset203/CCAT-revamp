package com.asset.ccat.balancedisputemapperservice.controllers;

import com.asset.ccat.balancedisputemapperservice.cache.MessagesCache;
import com.asset.ccat.balancedisputemapperservice.defines.Defines;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.response.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Assem.Hassan
 */
@ControllerAdvice
public class ControllerExceptionInterceptor extends ResponseEntityExceptionHandler {
  private final MessagesCache messagesCache;

  @Autowired
  public ControllerExceptionInterceptor(MessagesCache messagesCache) {
    this.messagesCache = messagesCache;
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<BaseResponse<String>> handelAllExceptions(Exception ex, WebRequest req) {
    CCATLogger.DEBUG_LOGGER.error(" An error has occurred.  ", ex);
    CCATLogger.ERROR_LOGGER.error(" An error has occurred.  ", ex);
    String requestId = ThreadContext.get("requestId");
    BaseResponse<String> response = new BaseResponse<>();
    response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
    response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
    response.setSeverity(Defines.SEVERITY.FATAL);
    response.setRequestId(requestId);
    ThreadContext.remove("transactionId");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @ExceptionHandler(BalanceDisputeException.class)
  public final ResponseEntity<BaseResponse<String>> handleConfigServerException(BalanceDisputeException ex,
      WebRequest req) {
    CCATLogger.DEBUG_LOGGER.error(" A BalanceDisputeException occurred with Message: {} | Code = {}", ex.getMessage(), ex.getErrorCode());
    String requestId = ThreadContext.get("requestId");
    BaseResponse<String> response = new BaseResponse<>();
    response.setRequestId(requestId);
    response.setStatusCode(ex.getErrorCode());
    String msg;
    if (ex.getMessage() == null && ex.getArgs() != null && ex.getArgs().length > 0) {
      msg = messagesCache.getErrorMsg(ex.getErrorCode());
      msg = messagesCache.replaceArgument(msg, ex.getArgs());
    } else if (ex.getMessage() != null) {
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
