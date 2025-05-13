package com.asset.ccat.gateway.controllers.dss_reports;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.annotation.SubscriberOwnership;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.report.GetContractBillReportRequest;
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
public class ContractBillReportController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DSSReportsService dSSReportsService;

    @SubscriberOwnership
    @LogFootprint
    @PostMapping(value = Defines.ContextPaths.CONTRACT_BILL + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<DSSReportModel> getContractBillReport(@RequestBody GetContractBillReportRequest request) throws GatewayException, JsonProcessingException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Contract Bill Report Request [" + request + "]");
        // this.reportsValidator.getContractBillReportValidator(request);
        DSSReportModel response = dSSReportsService.getContractBillReport(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Contract Bill Report Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }
}
