package com.asset.ccat.user_management.controllers;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.callReason.AddCallReasonRequest;
import com.asset.ccat.user_management.models.requests.callReason.CheckCallReasonRequest;
import com.asset.ccat.user_management.models.requests.callReason.UpdateCallReasonRequest;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.responses.callReason.CallReasonModel;
import com.asset.ccat.user_management.services.CallReasonService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.CALL_REASON)
public class CallReasonController {

    private final CallReasonService callReasonService;

    public CallReasonController(CallReasonService callReasonService) {
        this.callReasonService = callReasonService;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<CallReasonModel> addCallReason(@RequestBody AddCallReasonRequest callReasonRequest) throws UserManagementException {
        ThreadContext.put("requestId", callReasonRequest.getRequestId());
        ThreadContext.put("sessionId", callReasonRequest.getSessionId());
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> addCallReason() : controller Started With Request : "+callReasonRequest);
        CallReasonModel callReason  = callReasonService.addCallReason(callReasonRequest);
        BaseResponse<CallReasonModel> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,"Success",Defines.SEVERITY.CLEAR,callReason);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> addCallReason() : controller Ended Successfully ");
        return response;
    }
    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<Integer> updateCallReason(@RequestBody UpdateCallReasonRequest updateCallReasonRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> updateCallReason() : controller Started With Request : "+updateCallReasonRequest);
        ThreadContext.put("requestId", updateCallReasonRequest.getRequestId());
        ThreadContext.put("sessionId", updateCallReasonRequest.getSessionId());        callReasonService.updateCallReason(updateCallReasonRequest);
        BaseResponse<Integer> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,"Success",Defines.SEVERITY.CLEAR,null);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> updateCallReason() : controller Ended Successfully ");
        return response;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.CHECK)
    public BaseResponse<CallReasonModel> checkCallReason(@RequestBody CheckCallReasonRequest checkCallReasonRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> checkCallReason() : controller Started With Request : "+checkCallReasonRequest);
        ThreadContext.put("requestId", checkCallReasonRequest.getRequestId());
        ThreadContext.put("sessionId", checkCallReasonRequest.getSessionId());
        CallReasonModel callReasonModel = callReasonService.checkCallReason(checkCallReasonRequest);
        BaseResponse<CallReasonModel> response = new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,"Success",Defines.SEVERITY.CLEAR,callReasonModel);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonController -> checkCallReason() : controller Ended Successfully ");
        return response;
    }
}
