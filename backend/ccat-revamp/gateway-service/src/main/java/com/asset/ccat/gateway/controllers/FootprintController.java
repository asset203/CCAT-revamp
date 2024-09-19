package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.shared.FootprintLogRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
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

    @Autowired
    private GatewayUtil gatewayUtil;


    @PostMapping(value = Defines.WEB_ACTIONS.LOG)
    @CrossOrigin(origins = "*")
    public BaseResponse logFootprint(HttpServletRequest req,
                                     @RequestBody FootprintLogRequest logRequest) throws GatewayException, UnknownHostException {
        String requestId = UUID.randomUUID().toString();
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(logRequest.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[{}], username = [{}]", sessionId, username );
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", requestId);

        CCATLogger.DEBUG_LOGGER.info("Received Footprint Log Request");

        if (Objects.nonNull(logRequest.getFootprintModel().getPageName()))
            logRequest.getFootprintModel().setActionName("View" + logRequest.getFootprintModel().getPageName() + "page");
        logRequest.getFootprintModel().setRequestId(requestId);
        logRequest.getFootprintModel().setSessionId(sessionId);
        logRequest.getFootprintModel().setUserName(username);

        logRequest.getFootprintModel().setActionType("Load");
        logRequest.getFootprintModel().setStatus(Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS);
        logRequest.getFootprintModel().setErrorCode(Integer.toString(ErrorCodes.SUCCESS.SUCCESS));
        logRequest.getFootprintModel().setErrorMessage("Successful");
        logRequest.getFootprintModel().setMachineName(gatewayUtil.getHostNameIfExist());

        footprintService.enqueueFootprint(logRequest.getFootprintModel());
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Footprint Log Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                logRequest.getRequestId(),
                null);
    }
}
