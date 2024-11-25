package com.asset.ccat.lookup_service.models.requests.admin_faf_plan;


import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class UpdateAdminFafPlanRequest extends BaseRequest {
    private FafPlanModel updatedPlan;

    public UpdateAdminFafPlanRequest() {
    }

    public FafPlanModel getUpdatedPlan() {
        return updatedPlan;
    }

    public void setUpdatedPlan(FafPlanModel updatedPlan) {
        this.updatedPlan = updatedPlan;
    }
}
