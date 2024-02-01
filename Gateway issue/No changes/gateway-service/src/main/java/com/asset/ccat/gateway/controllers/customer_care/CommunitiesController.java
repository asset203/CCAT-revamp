package com.asset.ccat.gateway.controllers.customer_care;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.CommunitiesRequest;
import com.asset.ccat.gateway.models.requests.GetCommunitiesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.customer_care.GetCommunitiesResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.CommunitiesService;
import com.asset.ccat.gateway.services.FootprintService;
import com.asset.ccat.gateway.util.GatewayUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.COMMUNITIES)
public class CommunitiesController {

    @Autowired
    LookupsCache lookupsCache;
    @Autowired
    FootprintService footprintService;
    @Autowired
    GatewayUtil gatewayUtil;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CommunitiesService communitiesService;

    @LogFootprint
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateCommunities(@RequestBody CommunitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Communities Request [" + request + "]");
        communitiesService.updateCommunities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Communities Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "Success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetCommunitiesResponse> getCommunities(@RequestBody GetCommunitiesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Communities Request [" + request + "]");
        GetCommunitiesResponse response = communitiesService.getCommunities(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Communities Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }
}
