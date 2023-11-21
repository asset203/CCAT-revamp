package com.asset.ccat.ci_service.controllers;

import com.asset.ccat.ci_service.defines.Defines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIException;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.SubscriberRequest;
import com.asset.ccat.ci_service.models.requests.super_flex.ActivateAddonRequest;
import com.asset.ccat.ci_service.models.requests.super_flex.ActivateThresholdRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.models.responses.GetMIThresholdResponse;
import com.asset.ccat.ci_service.services.SuperFlexService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.SUPER_FLEX)
public class SuperFlexController {

    @Autowired
    private SuperFlexService superFlexService;

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.ACTIVATE)
    public BaseResponse activateAddon(@RequestBody ActivateAddonRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - activateAddon()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received activate addon request");
        superFlexService.activateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished activate addon request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - activateAddon()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateAddon(@RequestBody SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - deactivateAddon()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received deactivate addon request");
        superFlexService.deactivateAddon(request);
        CCATLogger.DEBUG_LOGGER.info("Finished deactivate addon request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - deactivateAddon()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }


    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetMIThresholdResponse> getMiThreshold(@RequestBody SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - getMiThreshold()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received get threshold request");
        GetMIThresholdResponse response = superFlexService.getMiThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished get threshold request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - getMiThreshold()");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.ACTIVATE)
    public BaseResponse activateThreshold(@RequestBody ActivateThresholdRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - activateThreshold()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received activate threshold request");
        superFlexService.activateThreshold(request);

        CCATLogger.DEBUG_LOGGER.info("Finished activate threshold request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - activateThreshold()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLDS + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateThreshold(@RequestBody SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - deactivateThreshold()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received deactivate threshold request");
        superFlexService.deactivateThreshold(request);
        CCATLogger.DEBUG_LOGGER.info("Finished deactivate threshold request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - deactivateThreshold()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_STOP_MI)
    public BaseResponse stopMIThreshold(@RequestBody SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - stopMIThreshold()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received stop MI threshold request");
        superFlexService.stopMIThreshold(request);

        CCATLogger.DEBUG_LOGGER.info("Finished stop MI threshold request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - stopMIThreshold()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_STOP_MI + Defines.WEB_ACTIONS.DEACTIVATE)
    public BaseResponse deactivateStopMI(@RequestBody SubscriberRequest request) throws CIServiceException, CIException {
        CCATLogger.DEBUG_LOGGER.debug("Started SuperFlexController - deactivateStopMI()");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());

        CCATLogger.DEBUG_LOGGER.info("Received stop MI threshold request");
        superFlexService.deactivateStopMI(request);

        CCATLogger.DEBUG_LOGGER.info("Finished stop MI threshold request Successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending SuperFlexController - stopMIThreshold()");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                null);
    }
}
