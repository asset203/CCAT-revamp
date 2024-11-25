package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.transaction.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionCodeResponse;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionTypeResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AdmTransactionService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.TRANSACTION_ADMIN)
public class AdmTransactionController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdmTransactionService transactionService;

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionTypeResponse> getAllTransactionTypes(HttpServletRequest req,
                                                                              @RequestBody GetAllTransactionTypeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Transaction Type Request [" + request + "]");
        GetAllTransactionTypeResponse pamAdministrationResponse = transactionService.getAllTransactionTypes(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Get All Transaction Type Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                pamAdministrationResponse);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionCodeResponse> getAllTransactionCodes(HttpServletRequest req,
                                                                              @RequestBody GetAllTransactionCodeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Transaction Code Request [" + request + "]");
        GetAllTransactionCodeResponse pamAdministrationResponse = transactionService.getAllTransactionCodes(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Get All Transaction Code Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                pamAdministrationResponse);
    }

    @PostMapping(value = Defines.ContextPaths.LINK + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllTransactionCodeResponse> getAllTransactionLinks(HttpServletRequest req,
                                                                              @RequestBody GetAllTransactionLinkRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Transaction Link Request [" + request + "]");
        GetAllTransactionCodeResponse pamAdministrationResponse = transactionService.getAllTransactionLinks(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Transaction Link Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                pamAdministrationResponse);
    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionType(HttpServletRequest req,
                                              @RequestBody UpdateTransactionTypeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Transaction Type Request [" + request + "]");
//        pamAdministrationValidator.validateUpdatePam(updatePamRequest);
        transactionService.updateTransactionType(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Transaction Type Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionCode(HttpServletRequest req,
                                              @RequestBody UpdateTransactionCodeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Transaction Code Request [" + request + "]");
//        pamAdministrationValidator.validateUpdatePam(updatePamRequest);
        transactionService.updateTransactionCode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Transaction Code Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.ADD)
    public BaseResponse addTransactionCode(HttpServletRequest req,
                                           @RequestBody AddTransactionCodeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        request.setUsername(username);
        request.setSessionId(sessionId);
        CCATLogger.DEBUG_LOGGER.info("Received Add Transaction Code Request [" + request + "]");
//        pamAdministrationValidator.validateAddPam(addPamRequest);
        transactionService.addTransactionCode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Transaction Code Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.ADD)
    public BaseResponse addTransactionType(HttpServletRequest req,
                                           @RequestBody AddTransactionTypeRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        request.setUsername(username);
        request.setSessionId(sessionId);
        CCATLogger.DEBUG_LOGGER.info("Received Add Transaction Type Request [" + request + "]");
//        pamAdministrationValidator.validateAddPam(addPamRequest);
        transactionService.addTransactionType(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Transaction Type Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);

    }

    @PostMapping(value = Defines.ContextPaths.CODE + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteTransactionCode(HttpServletRequest req,
                                              @RequestBody DeleteTransactionRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Transaction Request [" + request + "]");
        transactionService.deleteTransactionCode(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Transaction Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.TYPE + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteTransactionType(HttpServletRequest req,
                                              @RequestBody DeleteTransactionRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Transaction Request [" + request + "]");
        transactionService.deleteTransactionType(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Transaction Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.LINK + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateTransactionLink(HttpServletRequest req,
                                              @RequestBody UpdateTransactionLinkRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Transaction Link Request [" + request + "]");
        transactionService.updateTransactionLink(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Update Transaction Link Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }
}
