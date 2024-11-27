package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.VIPListRequest;
import com.asset.ccat.lookup_service.models.responses.VIPListsResponse;
import com.asset.ccat.lookup_service.services.VIPService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.VIP)
public class VIPController {
    private final VIPService vipService;

    public VIPController(VIPService vipService) {
        this.vipService = vipService;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<VIPListsResponse> getVIPLists(VIPListRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request started");
        VIPListsResponse response = vipService.getVIPLists();
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_MSISDN + Defines.WEB_ACTIONS.ADD)
    public BaseResponse<VIPListsResponse> addVIPMsisdn(VIPListRequest addVipListRequest) throws LookupException {
        ThreadContext.put("requestId", addVipListRequest.getRequestId());
        ThreadContext.put("sessionId", addVipListRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request started with request = {}", addVipListRequest);
        vipService.addVIPMsisdn(addVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, null);
    }
}
