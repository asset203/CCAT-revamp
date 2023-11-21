package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.customer_care.BarringRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.security.JwtTokenUtil;
import com.asset.ccat.air_service.services.BarringService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Defines.ContextPaths.BARRING)
public class BarringController {

    @Autowired
    private BarringService barringService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = Defines.ContextPaths.BAR)
    public BaseResponse barTemporaryBlocked(@RequestBody BarringRequest request) throws AIRServiceException, AIRException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received barTemporaryBlocked Request [" + request + "]");
        Integer userId = Integer.valueOf(jwtTokenUtil.extractDataFromToken(request.getToken())
                .get(Defines.SecurityKeywords.USER_ID).toString());
        request.setUserId(userId);
        barringService.barTemporaryBlocked(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving barTemporaryBlocked request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.ContextPaths.UNBAR)
    public BaseResponse unbarTemporaryBlocked(@RequestBody BarringRequest request) throws AIRServiceException, AIRException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received unbarTemporaryBlocked Request [" + request + "]");
        Integer userId = Integer.valueOf(jwtTokenUtil.extractDataFromToken(request.getToken())
                .get(Defines.SecurityKeywords.USER_ID).toString());
        request.setUserId(userId);
        barringService.unbarTemporaryBlock(request, true);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving unbarTemporaryBlocked request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.ContextPaths.UNBAR_REFILL_BARRING)
    public BaseResponse unbarRefillBarring(@RequestBody BarringRequest request) throws AIRServiceException, AIRException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received unbarRefillBarring Request [" + request + "]");
        Integer userId = Integer.valueOf(jwtTokenUtil.extractDataFromToken(request.getToken())
                .get(Defines.SecurityKeywords.USER_ID).toString());
        request.setUserId(userId);
        barringService.unbarRefillBarring(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving unbarRefillBarring request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }
}
