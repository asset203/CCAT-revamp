package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BarringRequest;
import com.asset.ccat.gateway.models.requests.GetBarringReasonRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetBarringReasonResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.BarringService;
import com.asset.ccat.gateway.validators.customer_care.BarringValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.BARRING)
public class BarringController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BarringService barringService;
    @Autowired
    private BarringValidator barringValidator;


    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.BAR)
    public BaseResponse barTemporaryBlocked(@RequestBody BarringRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Barring Request [" + request + "]");
        barringValidator.validateBarringRequest(request);
        barringService.barTemporaryBlocked(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Barring Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.UNBAR)
    public BaseResponse unbarTemporaryBlocked(@RequestBody BarringRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Unbar Temporary Blocked Request [" + request + "]");
        barringValidator.validateBarringRequest(request);
        barringService.unbarTemporaryBlocked(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Unbar Temporary Blocked Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.UNBAR_REFILL_BARRING)
    public BaseResponse unbarRefillBarring(@RequestBody BarringRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Unbar Refill Barring Request [" + request + "]");
        barringValidator.validateBarringRequest(request);
        barringService.unbarRefillBarring(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Unbar Refill Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.REASON + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetBarringReasonResponse> getBarringReason(@RequestBody GetBarringReasonRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setRequestId(UUID.randomUUID().toString());
        request.setUsername(username);
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Barring Reason Request [" + request + "]");
        GetBarringReasonResponse res = barringService.getBarringReason(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Barring Reason Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                res);

    }
}

