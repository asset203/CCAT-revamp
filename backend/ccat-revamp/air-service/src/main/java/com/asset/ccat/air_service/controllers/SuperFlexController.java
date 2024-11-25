package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.models.responses.customer_care.GetOptInAddonsResponse;
import com.asset.ccat.air_service.services.SuperFlexService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.SUPER_FLEX)
public class SuperFlexController {

    @Autowired
    private SuperFlexService superFlexService;

    @PostMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDONS + Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetOptInAddonsResponse> getOptinAddons(@RequestBody SubscriberRequest request) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received Get Addons Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        GetOptInAddonsResponse response = superFlexService.getOptInAddons(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Addons Request Successfully");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }
}
