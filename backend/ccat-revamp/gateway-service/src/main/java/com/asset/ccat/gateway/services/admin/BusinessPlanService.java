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
import com.asset.ccat.gateway.models.requests.admin.business_plan.AddBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.DeleteBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.GetAllBusinessPlansRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.GetBusinessPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.business_plan.UpdateBusinessPlanRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.gateway.models.responses.admin.business_plan.GetBusinessPlanResponse;
import com.asset.ccat.gateway.models.users.BusinessPlanModel;
import com.asset.ccat.gateway.proxy.admin.BusinessPlanProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BaseResponse addBusinessPlan(AddBusinessPlanRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("BusinessPlanService->addBusinessPlan() Started");
        CCATLogger.DEBUG_LOGGER.debug("BusinessPlanService->addBusinessPlan() Started with request "+request);
        GetAllBusinessPlansResponse getAllBusinessPlansResponse = businessPlanProxy.getAllBusinessPlans();
        List<BusinessPlanModel> businessPlans = getAllBusinessPlansResponse.getBusinessPlans();
        if (!businessPlans.isEmpty()){
            for (BusinessPlanModel planModel : businessPlans){
                String planName = planModel.getBusinessPlanName();
                Integer planCode =planModel.getBusinessPlanCode();
                if (planCode.equals(request.getBusinessPlan().getBusinessPlanCode())) {
                    CCATLogger.DEBUG_LOGGER.info("BusinessPlanService->addBusinessPlan() Business Plan Code is duplicated");
                    CCATLogger.DEBUG_LOGGER.debug("BusinessPlanService->addBusinessPlan() Business Plan Code is duplicated with code : "
                            +request.getBusinessPlan().getBusinessPlanCode());
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Business Plan Code");
                }else if (planName.equals(request.getBusinessPlan().getBusinessPlanName())) {
                    CCATLogger.DEBUG_LOGGER.info("BusinessPlanService->addBusinessPlan() Business Plan Name is duplicated");
                    CCATLogger.DEBUG_LOGGER.debug("BusinessPlanService->addBusinessPlan() Business Plan Name is duplicated with code : "
                            +request.getBusinessPlan().getBusinessPlanName());
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Business Plan Name");
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
