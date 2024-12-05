package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.vip.VIPListRequest;
import com.asset.ccat.lookup_service.models.requests.vip.VIPUpdatePagesRequest;
import com.asset.ccat.lookup_service.models.responses.vip.VIPListsResponse;
import com.asset.ccat.lookup_service.services.VIPService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public BaseResponse<VIPListsResponse> getVIPLists(@RequestBody VIPListRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request started");
        VIPListsResponse response = vipService.getVIPLists();
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, response);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_MSISDN + Defines.WEB_ACTIONS.ADD)
    public BaseResponse<String> addVIPMsisdn(@RequestBody VIPListRequest addVipListRequest) throws LookupException {
        ThreadContext.put("requestId", addVipListRequest.getRequestId());
        ThreadContext.put("sessionId", addVipListRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request started with request = {}", addVipListRequest);
        vipService.addVIPMsisdn(addVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_MSISDN + Defines.WEB_ACTIONS.DELETE)
    public BaseResponse<String> deleteVIPMsisdn(@RequestBody VIPListRequest deleteVipListRequest) throws LookupException {
        ThreadContext.put("requestId", deleteVipListRequest.getRequestId());
        ThreadContext.put("sessionId", deleteVipListRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Delete VIP MSISDN request started with request = {}", deleteVipListRequest);
        vipService.deleteVIPMsisdn(deleteVipListRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("Delete VIP MSISDN request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_PAGE + Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<String> syncVIPPages(@RequestBody VIPUpdatePagesRequest vipUpdatePagesRequest) throws LookupException {
        ThreadContext.put("requestId", vipUpdatePagesRequest.getRequestId());
        ThreadContext.put("sessionId", vipUpdatePagesRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("Update VIP Pages List request started with body = {}", vipUpdatePagesRequest);
        vipService.syncVIPPages(vipUpdatePagesRequest);
        CCATLogger.DEBUG_LOGGER.debug("Update VIP Pages List request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, null);
    }
}
