package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.SendSMSRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.SendSmsService;
import com.asset.ccat.gateway.validators.SendSMSValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.SEND_SMS)
public class SendSMSController {

    private final JwtTokenUtil jwtTokenUtil;
    private final SendSMSValidator sendSMSValidator;
    private final SendSmsService sendSmsService;

    public SendSMSController(JwtTokenUtil jwtTokenUtil, SendSMSValidator sendSMSValidator, SendSmsService sendSmsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.sendSMSValidator = sendSMSValidator;
        this.sendSmsService = sendSmsService;
    }


    @PostMapping(value = Defines.WEB_ACTIONS.SEND)
    public BaseResponse sendSMS(@RequestBody SendSMSRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId +
                "] username=[" + username + "] userId=[" + userId + "]");
        request.setUsername(username);
        request.setSessionId(sessionId);
        request.setRequestId(UUID.randomUUID().toString());
        request.setUserId(userId);
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received Send SMS Request [" + request + "]");
        sendSMSValidator.validateSendSMS(request);
        sendSmsService.sendSMS(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Send SMS Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                1,
                request.getRequestId()
                , null);
    }
}
