package com.asset.ccat.gateway.models.responses.admin.business_plan;

import com.asset.ccat.gateway.models.users.BusinessPlanModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllBusinessPlansResponse {

    private List<BusinessPlanModel> businessPlans;

    public GetAllBusinessPlansResponse() {
    }

    public GetAllBusinessPlansResponse(List<BusinessPlanModel> businessPlans) {
        this.businessPlans = businessPlans;
    }

    public List<BusinessPlanModel> getBusinessPlans() {
        return businessPlans;
    }

    public void setBusinessPlans(List<BusinessPlanModel> businessPlans) {
        this.businessPlans = businessPlans;
    }

}
