package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.vip.VIPListsResponse;
import com.asset.ccat.gateway.models.admin.vip.VIPUpdatePagesRequest;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.requests.admin.vip.VIPRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.VIPService;
import com.asset.ccat.gateway.validators.admins.VIPValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = Defines.ContextPaths.VIP)
public class VIPController {
    private final VIPService vipService;

    private final VIPValidator vipValidator;

    private final JwtTokenUtil jwtTokenUtil;

    public VIPController(VIPService vipService, VIPValidator vipValidator, JwtTokenUtil jwtTokenUtil) {
        this.vipService = vipService;
        this.vipValidator = vipValidator;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<VIPListsResponse> getVIPLists(@RequestBody VIPRequest request) throws GatewayException {
        prepareRequest(request);
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request started");
        BaseResponse<VIPListsResponse> response = vipService.getAllVIPs(request);
        response.setRequestId(request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Get All VIP Lists request ended");
        return response;
    }

    @PostMapping(value = Defines.ContextPaths.VIP_MSISDN + Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public BaseResponse<String> addVIPMsisdn(@RequestBody VIPRequest addVipListRequest) throws GatewayException {
        prepareRequest(addVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request started with request = {}", addVipListRequest);
        vipValidator.validateMSISDN(addVipListRequest.getMsisdn());
        addVipListRequest.setMsisdn(vipValidator.formatMSISDN(addVipListRequest.getMsisdn()));
        vipService.addVIPMsisdn(addVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Add VIP MSISDN request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, addVipListRequest.getRequestId(), null);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_MSISDN + Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public BaseResponse<String> deleteVIPMsisdn(@RequestBody VIPRequest deleteVipListRequest) throws GatewayException {
        prepareRequest(deleteVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Delete VIP MSISDN request started with request = {}", deleteVipListRequest);
        vipService.deleteVIPMsisdn(deleteVipListRequest);
        CCATLogger.DEBUG_LOGGER.debug("Delete VIP MSISDN request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, deleteVipListRequest.getRequestId(), null);
    }

    @PostMapping(value = Defines.ContextPaths.VIP_PAGE + Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public BaseResponse<String> syncVIPPages(@RequestBody VIPUpdatePagesRequest vipUpdatePagesRequest) throws GatewayException {
        prepareRequest(vipUpdatePagesRequest);
        CCATLogger.DEBUG_LOGGER.debug("Update VIP Pages List request started with body = {}", vipUpdatePagesRequest);
        vipService.syncVIPPages(vipUpdatePagesRequest);
        CCATLogger.DEBUG_LOGGER.debug("Update VIP Pages List request ended.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, vipUpdatePagesRequest.getRequestId(), null);
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
