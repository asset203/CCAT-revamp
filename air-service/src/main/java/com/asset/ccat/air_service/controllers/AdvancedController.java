package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.air_service.models.requests.advanced.InstallSubscriberRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.security.JwtTokenUtil;
import com.asset.ccat.air_service.services.AdvancedService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author marwa.elshawarby
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.ADVANCED)
public class AdvancedController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdvancedService advancedService;

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> install(HttpServletRequest req,
                                @RequestBody InstallSubscriberRequest installSubscriberRequest) throws AIRServiceException, AIRException {
        ThreadContext.put("sessionId", installSubscriberRequest.getSessionId());
        ThreadContext.put("requestId", installSubscriberRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Install Subscriber Request | subscriber [" + installSubscriberRequest.getSubscriberMsisdn() + "]");
        advancedService.installSubscriber(installSubscriberRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished Install Subscriber Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse disconnect(HttpServletRequest req,
                                   @RequestBody DisconnectSubscriberRequest disconnectSubscriberRequest) throws AIRServiceException, AIRException, ParserConfigurationException {
        ThreadContext.put("sessionId", disconnectSubscriberRequest.getSessionId());
        ThreadContext.put("requestId", disconnectSubscriberRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Install Subscriber Request | subscriber [" + disconnectSubscriberRequest.getSubscriberMsisdn() + "]");
        Integer profileId = Integer.valueOf((String) jwtTokenUtil.extractDataFromToken(disconnectSubscriberRequest.getToken())
                .get(Defines.SecurityKeywords.PROFILE_ID));
        advancedService.disconnectSubscriber(disconnectSubscriberRequest, profileId);
        CCATLogger.DEBUG_LOGGER.info("Finished Disconnect Subscriber Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0, null);
    }

}
