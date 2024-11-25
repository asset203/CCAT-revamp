package com.asset.ccat.lookup_service.controllers.admin;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.requests.admin_faf_plan.AddAdminFafPlanRequest;
import com.asset.ccat.lookup_service.models.requests.admin_faf_plan.DeleteAdminFafPlanRequest;
import com.asset.ccat.lookup_service.models.requests.admin_faf_plan.GetAllAdminFafPlansRequest;
import com.asset.ccat.lookup_service.models.requests.admin_faf_plan.UpdateAdminFafPlanRequest;
import com.asset.ccat.lookup_service.services.FafPlansService;
import com.asset.ccat.lookup_service.validators.FafPlanValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.FAF_PLANS)
public class AdminFafPlansController
{
    @Autowired
    FafPlansService fafPlansService;

    @Autowired
    FafPlanValidator fafPlanValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<FafPlanModel>> getAllCommunities(GetAllAdminFafPlansRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        List<FafPlanModel> response = fafPlansService.getAllAdminFafPlans();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR,
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateFafPlan(@RequestBody UpdateAdminFafPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        fafPlanValidator.isFafPlanUpdateValid(request.getUpdatedPlan());
        fafPlansService.updateFafPlan(request.getUpdatedPlan());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addFafPlan(@RequestBody AddAdminFafPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        fafPlanValidator.isFafPlanAdminAddValid(request.getAddedPlan());
        fafPlansService.addFafPlan(request.getAddedPlan());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteFafPlan(@RequestBody DeleteAdminFafPlanRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        fafPlansService.deleteFafPlan(request.getFafPlanId());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }
}
