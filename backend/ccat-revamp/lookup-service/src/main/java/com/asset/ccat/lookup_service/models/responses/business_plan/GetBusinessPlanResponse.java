package com.asset.ccat.lookup_service.models.responses.business_plan;

import com.asset.ccat.lookup_service.models.BusinessPlanModel;

/**
 *
 * @author nour.ihab
 */
public class GetBusinessPlanResponse {

    private BusinessPlanModel businessPlan;

    public GetBusinessPlanResponse() {

    }

    public GetBusinessPlanResponse(BusinessPlanModel businessPlan) {
        this.businessPlan = businessPlan;
    }

    public BusinessPlanModel getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlanModel businessPlan) {
        this.businessPlan = businessPlan;
    }

}
