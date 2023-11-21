package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AdmServiceClassModel;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.AddBarringReasonRequest;
import com.asset.ccat.lookup_service.models.requests.DeleteBarringReasonRequest;
import com.asset.ccat.lookup_service.models.requests.GetBarringReasonRequest;
import com.asset.ccat.lookup_service.models.requests.service_class.UpdateServiceClassRequest;
import com.asset.ccat.lookup_service.models.responses.GetBarringReasonResponse;
import com.asset.ccat.lookup_service.services.BarringReasonService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Defines.ContextPaths.BARRING_REASON)
public class BarringReasonController {

    @Autowired
    private BarringReasonService barringReasonService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetBarringReasonResponse> getBarringReason(@RequestBody GetBarringReasonRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonController - getBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Received get barring reason request");
        GetBarringReasonResponse res = barringReasonService.getBarringReason(request);
        CCATLogger.DEBUG_LOGGER.debug("Finished get barring reason request successfully");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonController - getBarringReason()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, res);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addBarringReason(@RequestBody AddBarringReasonRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonController - addBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Received add barring reason request");
        barringReasonService.addBarringReason(request);
        CCATLogger.DEBUG_LOGGER.debug("Finished add barring reason request successfully");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonController - addBarringReason()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteBarringReason(@RequestBody DeleteBarringReasonRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Started BarringReasonController - deleteBarringReason()");
        CCATLogger.DEBUG_LOGGER.debug("Received delete barring reason request");
        barringReasonService.deleteBarringReason(request);
        CCATLogger.DEBUG_LOGGER.debug("Finished delete barring reason request successfully");
        CCATLogger.DEBUG_LOGGER.info("Ended BarringReasonController - deleteBarringReason()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
