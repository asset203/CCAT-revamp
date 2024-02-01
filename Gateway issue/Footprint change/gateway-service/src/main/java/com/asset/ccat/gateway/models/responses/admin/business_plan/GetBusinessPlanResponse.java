package com.asset.ccat.gateway.models.responses.admin.business_plan;

import com.asset.ccat.gateway.models.users.BusinessPlanModel;

/**
 *
 * @author nour.ihab
 */
public class GetBusinessPlanResponse {

    private BusinessPlanModel businessPlan;

    public BusinessPlanModel getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlanModel businessPlan) {
        this.businessPlan = businessPlan;
    }

}
