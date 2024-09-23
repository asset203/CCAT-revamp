/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.business_plan.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetBusinessPlanResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetDeletedBusinessPlansResponse;
import com.asset.ccat.gateway.models.users.BusinessPlanModel;
import com.asset.ccat.gateway.proxy.admin.BusinessPlanProxy;
import com.graphbuilder.curve.NURBSpline;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;

import static java.sql.Types.NULL;

/**
 *
 * @author wael.mohamed
 */
@Service
public class BusinessPlanService {

    @Autowired
    private BusinessPlanProxy businessPlanProxy;

    public GetAllBusinessPlansResponse getAllBusinessPlans(GetAllBusinessPlansRequest request) throws GatewayException {
        return businessPlanProxy.getAllBusinessPlans();
    }


    public GetBusinessPlanResponse getBusinessPlansById(GetBusinessPlanRequest request) throws GatewayException {
        return businessPlanProxy.getBusinessPlansById(request.getBusinessPlanId());
    }

    public GetDeletedBusinessPlansResponse getDeletedBusinessPlansResponse (GetDeletedBusinessPlanRequest request ) throws GatewayException {
        return  businessPlanProxy.getDeletedBusinessPlans();
    }

    public BaseResponse addBusinessPlan(AddBusinessPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("addBusinessPlan() Started");
        CCATLogger.DEBUG_LOGGER.debug(" Started with request "+request);
        GetDeletedBusinessPlansResponse getDeletedBusinessPlansResponse = businessPlanProxy.getDeletedBusinessPlans();
        List<BusinessPlanModel> businessPlans = getDeletedBusinessPlansResponse.getBusinessPlans();
        if (!businessPlans.isEmpty()){
            for (BusinessPlanModel planModel : businessPlans){
                String planName = planModel.getBusinessPlanName();
                Integer planCode =planModel.getBusinessPlanCode();
                Integer isDeleted = planModel.getIsDeleted();
                if (planCode.equals(request.getBusinessPlan().getBusinessPlanCode())  && isDeleted == 0) {
                    CCATLogger.DEBUG_LOGGER.debug("Business Plan Code is duplicated with code : " +request.getBusinessPlan().getBusinessPlanCode());
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Business Plan Code");
                }else if (planName.equals(request.getBusinessPlan().getBusinessPlanName())   && isDeleted == 0) {
                    CCATLogger.DEBUG_LOGGER.debug("Business Plan Name is duplicated with code : " +request.getBusinessPlan().getBusinessPlanName());
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Business Plan Name");
                } else if (planName.equals(request.getBusinessPlan().getBusinessPlanName()) &&
                        planCode.equals(request.getBusinessPlan().getBusinessPlanCode())  && isDeleted == 1 ) {
                    CCATLogger.DEBUG_LOGGER.debug("Business Plan already exist but isDeleted we will return it"+request);
                    request.getBusinessPlan().setIsDeleted(0);
                    request.getBusinessPlan().setBusinessPlanId(planModel.getBusinessPlanId());
                    UpdateBusinessPlanRequest updateRequest = new UpdateBusinessPlanRequest();
                    updateRequest.setBusinessPlan(request.getBusinessPlan());
                    CCATLogger.DEBUG_LOGGER.debug("Deleted Business plan returned  successfully");
                    return  businessPlanProxy.updateBusinessPlan(updateRequest);
                }
            }
        }
        CCATLogger.DEBUG_LOGGER.info("BusinessPlanService->addBusinessPlan() Ended Successfully");
        CCATLogger.DEBUG_LOGGER.debug("BusinessPlanService->addBusinessPlan() Ended Successfully");
        return businessPlanProxy.addBusinessPlan(request);
    }

    public BaseResponse updateBusinessPlan(UpdateBusinessPlanRequest request) throws GatewayException {
        return businessPlanProxy.updateBusinessPlan(request);
    }

    public BaseResponse deleteBusinessPlanById(DeleteBusinessPlanRequest request) throws GatewayException {
        return businessPlanProxy.deleteBusinessPlanById(request);
    }
}
