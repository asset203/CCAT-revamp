package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareHistoryRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareStatesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.UpdateBWStateRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareEligibilityResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareHistoryResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareStatesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FlexShareService;
import com.asset.ccat.gateway.validators.customer_care.FlexShareValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.FLEX_SHARE)
public class FlexShareController {

    private final JwtTokenUtil jwtTokenUtil;
    private final FlexShareService flexShareService;
    private final FlexShareValidator flexShareValidator;

    public FlexShareController(JwtTokenUtil jwtTokenUtil, FlexShareService flexShareService, FlexShareValidator flexShareValidator) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.flexShareService = flexShareService;
        this.flexShareValidator = flexShareValidator;
    }


    @PostMapping(value = Defines.ContextPaths.FLEX_SHARE_STATES + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareStatesResponse> getFlexShareStates
            (@RequestBody GetFlexShareStatesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Flex Share States Request [" + request + "]");
        flexShareValidator.getFlexShareStatesValidator(request);
        GetFlexShareStatesResponse response = flexShareService.flexShareInquiry(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Flex Share States Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.FLEX_SHARE_STATES + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateFlexShareState
            (@RequestBody UpdateBWStateRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update BW State Request [" + request + "]");
        flexShareValidator.updateFlexShareStateValidator(request);
        flexShareService.updateFlexShareState(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update BW State Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.ContextPaths.FLEX_SHARE_ELIGIBILITY + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareEligibilityResponse> getFlexShareEligibility
            (@RequestBody GetFlexShareEligibilityRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Flex Share Eligibility Request [" + request + "]");
        flexShareValidator.getFlexShareEligibilityValidator(request);
        GetFlexShareEligibilityResponse response = flexShareService.getFlexShareEligibility(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Flex Share Eligibility Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.FLEX_SHARE_HISTORY + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetFlexShareHistoryResponse> getFlexShareHistory
            (@RequestBody GetFlexShareHistoryRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Flex Share History Request [" + request + "]");
        flexShareValidator.validateGetFlexShareHistoryRequest(request);
        GetFlexShareHistoryResponse response = flexShareService.getFlexShareHistory(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Flex Share History Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }
}
