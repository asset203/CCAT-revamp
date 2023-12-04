package com.asset.ccat.gateway.models.requests.admin.business_plan;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.users.BusinessPlanModel;

/**
 * @author nour.ihab
 */
public class AddBusinessPlanRequest extends BaseRequest {

    private BusinessPlanModel businessPlan;

    public BusinessPlanModel getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlanModel businessPlan) {
        this.businessPlan = businessPlan;
    }

    @Override
    public String toString() {
        return "AddBusinessPlanRequest{" +
                "businessPlan=" + businessPlan +
                '}';
    }
}
