package com.asset.ccat.gateway.models.responses.admin.admin_faf_plan;

import com.asset.ccat.gateway.models.admin.AdminFafPlanModel;
import com.asset.ccat.gateway.models.shared.FafPlanModel;

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
