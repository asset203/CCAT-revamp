/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.business_plan.AddBusinessPlanRequest;
import com.asset.ccat.lookup_service.models.requests.business_plan.DeleteBusinessPlanRequest;
import com.asset.ccat.lookup_service.models.requests.business_plan.UpdateBusinessPlanRequest;
import com.asset.ccat.lookup_service.models.responses.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.lookup_service.models.responses.business_plan.GetBusinessPlanResponse;
import com.asset.ccat.lookup_service.services.BusinessPlanService;
import com.asset.ccat.lookup_service.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.BUSINESS_PLANS)
public class BusinessPlanController {

    @Autowired
    private Utils utils;
    @Autowired
    private BusinessPlanService businessPlanService;

    @GetMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllBusinessPlansResponse> getAllBusinessPlans() throws LookupException {
        GetAllBusinessPlansResponse response = businessPlanService.getAllBusinessPlans();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @GetMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetBusinessPlanResponse> getBusinessPlanById(
            @RequestParam("businessPlanId") Integer businessPlanId) throws LookupException {
        utils.isFieldInteger(businessPlanId);
        GetBusinessPlanResponse businessPlan = businessPlanService.getBusinessPlanById(businessPlanId);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, businessPlan);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceClass(@RequestBody UpdateBusinessPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        businessPlanService.updateBusinessPlan(request.getBusinessPlan());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addBusinessPlan(@RequestBody AddBusinessPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        businessPlanService.addBusinessPlan(request.getBusinessPlan());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteBusinessPlanById(@RequestBody DeleteBusinessPlanRequest request) throws LookupException {
        utils.isFieldInteger(request.getBusinessPlanId());
        businessPlanService.deleteBusinessPlan(request.getBusinessPlanId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

}
