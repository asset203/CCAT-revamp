package com.asset.ccat.lookup_service.models.responses.business_plan;

import com.asset.ccat.lookup_service.models.BusinessPlanModel;

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
