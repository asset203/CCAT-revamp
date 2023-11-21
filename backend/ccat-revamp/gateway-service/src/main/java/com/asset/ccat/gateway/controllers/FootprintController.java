package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.shared.FootprintLogRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FootprintService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author marwa.elshawarby
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.FOOTPRINT)
@CrossOrigin(origins = "*")
public class FootprintController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private FootprintService footprintService;


    @PostMapping(value = Defines.WEB_ACTIONS.LOG)
    @CrossOrigin(origins = "*")
    public BaseResponse logFootprint(HttpServletRequest req,
                                     @RequestBody FootprintLogRequest logRequest) throws AuthenticationException, GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Receive log footprint request");
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(logRequest.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        logRequest.setRequestId(UUID.randomUUID().toString());
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", logRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Footprint Log Request [" + logRequest + "]");
        logRequest.getFootprint().setRequestId(logRequest.getRequestId());
        logRequest.getFootprint().setActionType("Load");
        if (Objects.nonNull(logRequest.getFootprint().getPageName())) {
            logRequest.getFootprint().setActionName("View" + logRequest.getFootprint().
                    getPageName() + "page");
        }
        logRequest.getFootprint().setUserName(username);
        logRequest.getFootprint().setStatus(Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS);
        logRequest.getFootprint().setSessionId(sessionId);
        logRequest.getFootprint().setErrorCode(Integer.toString(ErrorCodes.SUCCESS.SUCCESS));
        logRequest.getFootprint().setErrorMessage("Successful");
        footprintService.enqueueFootprint(logRequest.getFootprint());
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Footprint Log Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                logRequest.getRequestId(),
                null);
    }
}
