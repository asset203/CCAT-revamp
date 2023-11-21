package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.business_plan.UpdateBusinessPlanRequest;
import com.asset.ccat.lookup_service.models.requests.service_offering_description.GetAllServiceOfferingDescriptionRequest;
import com.asset.ccat.lookup_service.models.requests.service_offering_description.UpdateServiceOfferingDescRequest;
import com.asset.ccat.lookup_service.models.responses.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.lookup_service.models.responses.service_offering_description.GetAllServiceOfferingDescriptionResponse;
import com.asset.ccat.lookup_service.services.ServiceOfferingBitsService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Defines.ContextPaths.SERVICE_OFFERING_DESCRIPTION)
public class ServiceOfferingBitsController {

    @Autowired
    private ServiceOfferingBitsService serviceOfferingBitsService;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceOfferingDescriptionResponse> getAllServiceOfferingBits(GetAllServiceOfferingDescriptionRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        GetAllServiceOfferingDescriptionResponse response = serviceOfferingBitsService.getAllServiceOfferingBits();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceOfferingBit(@RequestBody UpdateServiceOfferingDescRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        serviceOfferingBitsService.updateServiceOfferingBit(request.getServiceOffering());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
