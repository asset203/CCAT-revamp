
package com.asset.ccat.balance_dispute_service.exceptions;


import com.asset.ccat.balance_dispute_service.cache.MessagesCache;
import com.asset.ccat.balance_dispute_service.dto.responses.BaseResponse;
import com.asset.ccat.balance_dispute_service.defines.Defines;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.StandardCharsets;


@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
    private final MessagesCache messagesCache;

    public ExceptionInterceptor(MessagesCache messagesCache) {
        this.messagesCache = messagesCache;
    }


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<String>> handelAllExceptions(Exception ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("Exception has occurred ex : {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("Exception has occurred  error code message : ", ex);
        BaseResponse<String> response = new BaseResponse<>();
        response.setStatusCode(ErrorCodes.ERROR.UNKNOWN_ERROR);
        response.setStatusMessage(messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR));
        response.setSeverity(Defines.SEVERITY.FATAL);
        CCATLogger.DEBUG_LOGGER.debug("Api Response is {}", response);
        ThreadContext.remove("transactionId");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BalanceDisputeException.class)
    public final ResponseEntity<BaseResponse<String>> handelProcedureException(BalanceDisputeException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("BalanceDisputeException has occurred ex : {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("BalanceDisputeException has occurred error code message : ", ex);
        BaseResponse<String> response = new BaseResponse<>();
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

    @ExceptionHandler(BalanceDisputeFileException.class)
    public final ResponseEntity<Resource> handleFilesExceptions(BalanceDisputeFileException ex, WebRequest req) {
        CCATLogger.DEBUG_LOGGER.error("BalanceDisputeFileException: {}", ex.getMessage());
        CCATLogger.ERROR_LOGGER.error("BalanceDisputeFileException code message : ", ex);

        byte[] emptyBytes = "".getBytes(StandardCharsets.UTF_8);

        ByteArrayResource resource = new ByteArrayResource(emptyBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + Defines.BALANCE_DISPUTE.BALANCE_DISPUTE_CSV_FILE_NAME)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
