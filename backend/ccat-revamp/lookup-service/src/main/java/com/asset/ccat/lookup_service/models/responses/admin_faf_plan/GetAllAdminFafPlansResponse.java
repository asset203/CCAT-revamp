package com.asset.ccat.lookup_service.models.responses.admin_faf_plan;


import com.asset.ccat.lookup_service.models.FafPlanModel;

import java.util.List;

/**
 * @author mohamed.metwaly
 */
public class GetAllAdminFafPlansResponse {
    private List<FafPlanModel> plans;

    public List<FafPlanModel> getPlans() {
        return plans;
    }

    public void setPlans(List<FafPlanModel> plans) {
        this.plans = plans;
    }

    public GetAllAdminFafPlansResponse(List<FafPlanModel> plans) {
        this.plans = plans;

    }
}
