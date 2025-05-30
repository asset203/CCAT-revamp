package com.asset.ccat.gateway.controllers.dss_reports;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.DSSReportRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.DSSReportsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*")
@RequestMapping(value = Defines.ContextPaths.DSS_REPORT)
public class OutgoingViewReportController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DSSReportsService dSSReportsService;

    @SubscriberOwnership
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.OUTGOING_VIEW+ Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getOutgoingViewReport(@RequestBody DSSReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Outgoing View Report Request [" + request + "]");
        DSSReportModel response = dSSReportsService.getOutgoingViewReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Outgoing View Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);

    }
}
