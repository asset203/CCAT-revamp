package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.requests.service_offering_plans.*;
import com.asset.ccat.lookup_service.services.ServiceOfferingPlansService;
import com.asset.ccat.lookup_service.validators.ServiceOfferingPlansValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.SERVICE_OFFERING_PLANS)
public class ServiceOfferingPlansController {

    @Autowired
    ServiceOfferingPlansService serviceOfferingPlansService;

    @Autowired
    ServiceOfferingPlansValidator serviceOfferingPlansValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<ServiceOfferingPlan>> getAllServiceOfferingPlans(@RequestBody GetAllServiceOfferingPlansRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        List<ServiceOfferingPlan> response = serviceOfferingPlansService.getAllServiceOfferingPlans();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<ServiceOfferingPlan> getAllServiceOfferingPlanBits(@RequestBody GetServiceOfferingPlanBitsRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        ServiceOfferingPlan response = serviceOfferingPlansService.getAllServiceOfferingPlanBits(request.getPlanId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addServiceOfferingPlan(@RequestBody AddServiceOfferingPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        serviceOfferingPlansValidator.isAddServiceOfferingPlanValid(request);
        serviceOfferingPlansService.addServiceOfferingPlan(request);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceOfferingPlan(@RequestBody UpdateServiceOfferingPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        serviceOfferingPlansValidator.isUpdateServiceOfferingPlanValid(request);
        serviceOfferingPlansService.updateServiceOfferingPlan(request);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteServiceOfferingPlan(@RequestBody DeleteServiceOfferingPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        serviceOfferingPlansService.deleteServiceOfferingPlan(request);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                null);
    }


//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.ADD)
//    public BaseResponse addServiceOfferingServiceClassDescription(@RequestBody AddServiceClassPlanDescriptionRequest request) throws LookupException {
//        ThreadContext.put("requestId", request.getRequestId());
//        ThreadContext.put("sessionId", request.getSessionId());
//        serviceOfferingPlansValidator.validateAddServiceClassPlanDescriptionRequest(request);
////        serviceOfferingPlansService.addSOServiceClassDescription(request);
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success", Defines.SEVERITY.CLEAR,
//                null);
//    }

//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.UPDATE)
//    public BaseResponse updateServiceOfferingServiceClassDescription(@RequestBody UpdateServiceClassPlanDescriptionRequest request) throws LookupException {
//        ThreadContext.put("requestId", request.getRequestId());
//        ThreadContext.put("sessionId", request.getSessionId());
//        serviceOfferingPlansValidator.isUpdateSOSCDescriptionValid(request);
//        serviceOfferingPlansService.updateSOServiceClassDescription(request);
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success", Defines.SEVERITY.CLEAR,
//                null);
//    }
//
//    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.DELETE)
//    public BaseResponse deleteServiceOfferingServiceClassDescription(@RequestBody DeleteServiceClassPlanDescriptionRequest request) throws LookupException {
//        ThreadContext.put("requestId", request.getRequestId());
//        ThreadContext.put("sessionId", request.getSessionId());
//        serviceOfferingPlansValidator.isDeleteSOSCDescriptionValid(request);
//        serviceOfferingPlansService.deleteSOServiceClassDescription(request);
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success", Defines.SEVERITY.CLEAR,
//                null);
//    }

    //    @PostMapping(value = Defines.ContextPaths.SO_SC_DESC + Defines.WEB_ACTIONS.GET)
//    public BaseResponse<ServiceOfferingPlan> getAllServiceOfferingServiceClassDescriptions(@RequestBody GetAllServiceClassPlanDescRequest request) throws LookupException {
//        ThreadContext.put("requestId", request.getRequestId());
//        ThreadContext.put("sessionId", request.getSessionId());
//        ServiceOfferingPlan response = serviceOfferingPlansService.getAllServiceOfferingServiceClassDescriptions(request.getPlanId());
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success", Defines.SEVERITY.CLEAR,
//                response);
//    }

}


