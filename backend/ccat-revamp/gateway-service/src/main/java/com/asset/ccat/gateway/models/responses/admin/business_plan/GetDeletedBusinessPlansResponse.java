package com.asset.ccat.gateway.models.responses.admin.business_plan;

import com.asset.ccat.gateway.models.users.BusinessPlanModel;

import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetDeletedBusinessPlansResponse {

    private List<BusinessPlanModel> businessPlans;

    public GetDeletedBusinessPlansResponse() {
    }

    public GetDeletedBusinessPlansResponse(List<BusinessPlanModel> businessPlans) {
        this.businessPlans = businessPlans;
    }

    public List<BusinessPlanModel> getBusinessPlans() {
        return businessPlans;
    }

    public void setBusinessPlans(List<BusinessPlanModel> businessPlans) {
        this.businessPlans = businessPlans;
    }

}
