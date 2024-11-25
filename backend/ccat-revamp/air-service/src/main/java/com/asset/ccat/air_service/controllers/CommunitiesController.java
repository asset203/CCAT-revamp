package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.CommunitiesRequest;
import com.asset.ccat.air_service.models.requests.GetSelectedCommunitiesRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.customer_care.GetCommunitiesResponse;
import com.asset.ccat.air_service.services.CommunitiesService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Defines.ContextPaths.COMMUNITIES)
public class CommunitiesController {

    @Autowired
    CommunitiesService communitiesService;

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateCommunities(@RequestBody CommunitiesRequest request) throws AIRException, AIRServiceException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received update Communities Request [" + request + "]");
        communitiesService.updateCommunities(request.getMsisdn(), request.getCurrentList(), request.getNewList(), request.getUsername());
        CCATLogger.DEBUG_LOGGER.info("Finished update Communities request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetCommunitiesResponse> getCommunities(@RequestBody GetSelectedCommunitiesRequest request) throws AIRServiceException {
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Selected Communities Request [" + request + "]");
        GetCommunitiesResponse response = communitiesService.getCommunities(request.getMsisdn(), request.getUsername());
        CCATLogger.DEBUG_LOGGER.info("Finished Get Selected Communities Request Successfully");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "Success", Defines.SEVERITY.CLEAR, response);
    }
}
