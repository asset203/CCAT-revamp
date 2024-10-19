package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.requests.shared.FootprintLogRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.FootprintPopulationService;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author marwa.elshawarby
 */
@RestController
@RequestMapping(path = Defines.ContextPaths.FOOTPRINT)
@CrossOrigin(origins = "*")
public class FootprintController implements FootprintPopulationService {
    private final JwtTokenUtil jwtTokenUtil;
    private final FootprintService footprintService;
    private FootprintModel requestFootprint;

    @Autowired
    public FootprintController(JwtTokenUtil jwtTokenUtil, FootprintService footprintService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.footprintService = footprintService;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.LOG)
    @CrossOrigin(origins = "*")
    public BaseResponse<String> logFootprint(@RequestBody FootprintLogRequest logRequest) throws GatewayException {
        String requestId = UUID.randomUUID().toString();
        logRequest.setRequestId(requestId);
        ThreadContext.put("requestId", requestId);
        CCATLogger.DEBUG_LOGGER.info("Log Footprint request started");

        requestFootprint = logRequest.getFootprintModel();
        populateTokenData(logRequest.getToken());
        populateRequestData(logRequest);
        populateResponseData(null, null);
        populateCachedData("log", "log");
        CCATLogger.FOOTPRINT_LOGGER.info("Start footprint enqueue");
        footprintService.enqueueFootprint(requestFootprint);
        CCATLogger.FOOTPRINT_LOGGER.info("Footprint enqueued successfully");
        CCATLogger.DEBUG_LOGGER.info("Log Footprint request ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                logRequest.getRequestId(),
                null);
    }

    @Override
    public void populateRequestData(BaseRequest request) {
        //  MSISDN and SendSMS are already set in the request.
        requestFootprint.setRequestId(request.getRequestId());
        CCATLogger.FOOTPRINT_LOGGER.debug("Request Data =[MSISDN={}, SendSMS={}]", requestFootprint.getMsisdn(), requestFootprint.getSendSms());
    }

    @Override
    public void populateResponseData(Object response, Throwable throwable) {
        requestFootprint.populateResponseData(Integer.toString(ErrorCodes.SUCCESS.SUCCESS),
                "Success", Defines.FOOT_PRINT_STATUS.SUCCESS_STATUS);
        CCATLogger.FOOTPRINT_LOGGER.debug("Success response added.");
    }

    @Override
    public void populateTokenData(String token) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(token);
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        String profileName = tokenData.get(Defines.SecurityKeywords.PROFILE_NAME).toString();
        String machineName = tokenData.get(Defines.SecurityKeywords.MACHINE_NAME).toString();
        ThreadContext.put("sessionId", sessionId);
        CCATLogger.FOOTPRINT_LOGGER.debug("The extracted data from token are: [sessionID={}, username={}, profileName={}, machineName={}]",
                sessionId, username, profileName, machineName);
        requestFootprint.populateTokenData(sessionId, username, profileName, machineName);
    }

    @Override
    public void populateCachedData(String controllerName, String methodName) {
        if (Objects.nonNull(requestFootprint.getPageName()))
            requestFootprint.setActionName("View" + requestFootprint.getPageName() + "page");
        requestFootprint.setActionType("Load");
    }
}
