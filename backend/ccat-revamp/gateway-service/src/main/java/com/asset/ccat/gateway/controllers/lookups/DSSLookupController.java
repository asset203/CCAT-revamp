package com.asset.ccat.gateway.controllers.lookups;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.dss.DssFlagModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.shared.GetBaseRequest;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.LookupsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Defines.LOOKUP_SERVICE.DSS_FLAGS)
public class DSSLookupController {
    private final LookupsService lookupsService;
    private final JwtTokenUtil jwtTokenUtil;

    public DSSLookupController(LookupsService lookupsService, JwtTokenUtil jwtTokenUtil) {
        this.lookupsService = lookupsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<Map<String, List<DssFlagModel>>> getDssFlags(@RequestBody GetBaseRequest request) throws GatewayException {
        prepareRequest(request);
        CCATLogger.DEBUG_LOGGER.debug("Get-All DSS Flags request started.");
        Map<String, List<DssFlagModel>> response = lookupsService.getDssFlags();
        CCATLogger.DEBUG_LOGGER.debug("Get-All DSS Flags request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                response);
    }

    private void prepareRequest(BaseRequest baseRequest) throws GatewayException {
        String requestId = UUID.randomUUID().toString();
        String sessionId = jwtTokenUtil.extractDataFromToken(baseRequest.getToken())
                .get(Defines.SecurityKeywords.SESSION_ID).toString();
        ThreadContext.put("requestId", requestId);
        ThreadContext.put("sessionId", sessionId);
        baseRequest.setRequestId(requestId);
        baseRequest.setSessionId(sessionId);
    }
}
