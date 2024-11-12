package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.ServiceOfferingPlanModel;
import com.asset.ccat.air_service.models.requests.UpdateServiceOfferingRequest;
import com.asset.ccat.air_service.models.requests.customer_care.GetCurrentServiceOfferingPlan;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.services.ServiceOfferingService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping(Defines.ContextPaths.SERVICE_OFFERING)
public class ServiceOfferingController {
    private final ServiceOfferingService serviceOfferingService;

    public ServiceOfferingController(ServiceOfferingService serviceOfferingService) {
        this.serviceOfferingService = serviceOfferingService;
    }

    @PostMapping(Defines.WEB_ACTIONS.GET)
    public BaseResponse<ServiceOfferingPlanModel> getCurrentServiceOfferingPlan(@RequestBody GetCurrentServiceOfferingPlan request) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received Get Current Service Offering Plan Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        ServiceOfferingPlanModel currentPlan = serviceOfferingService.getCurrentServiceOffering(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Current Service Offering Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                currentPlan);
    }

    @PostMapping(Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceOfferingPlan(@RequestBody UpdateServiceOfferingRequest request) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("Received Update Current Service Offering Plan Request [" + request + "]");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        serviceOfferingService.updateServiceOffering(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Current Service Offering Plan Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }
}
