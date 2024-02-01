package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.CallReasonModel;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.AddCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.CheckCallReasonRequest;
import com.asset.ccat.gateway.models.requests.customer_care.call_reasons.UpdateCallReasonRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.AddCallReasonResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.CallReasonService;
import com.asset.ccat.gateway.validators.customer_care.CallReasonValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.CALL_REASON)
public class CallReasonController {

    private final JwtTokenUtil jwtTokenUtil;
    private final CallReasonService callReasonService;
    private final CallReasonValidator callReasonValidator;

    public CallReasonController(JwtTokenUtil jwtTokenUtil, CallReasonService callReasonService, Properties properties, CallReasonValidator callReasonValidator) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.callReasonService = callReasonService;
        this.callReasonValidator = callReasonValidator;
    }

    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<AddCallReasonResponse> addCallReason(@RequestBody AddCallReasonRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Call Reason Request [" + request + "]");
        callReasonValidator.addCallReason(request);
        AddCallReasonResponse response = callReasonService.addCallReason(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Call Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<Object> updateCallReason(@RequestBody UpdateCallReasonRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Call Reason Request [" + request + "]");
        callReasonValidator.updateCallReason(request);
        callReasonService.updateCallReason(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Call Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<CallReasonModel> checkCallReason(@RequestBody CheckCallReasonRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Check Call Reason Request [" + request + "]");
        CallReasonModel callReasonModel = callReasonService.checkCallReason(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Check Call Reason Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                callReasonModel);
    }
}

