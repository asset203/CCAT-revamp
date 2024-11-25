package com.asset.ccat.gateway.models.requests.admin.admin_faf_plan;

import com.asset.ccat.gateway.models.admin.AdminFafPlanModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class UpdateAdminFafPlanRequest extends BaseRequest {
    private AdminFafPlanModel updatedPlan;

    public UpdateAdminFafPlanRequest() {
    }

    public AdminFafPlanModel getUpdatedPlan() {
        return updatedPlan;
    }

    public void setUpdatedPlan(AdminFafPlanModel updatedPlan) {
        this.updatedPlan = updatedPlan;
    }

    @Override
    public String toString() {
        return "UpdateAdminFafPlanRequest{" +
                "updatedPlan=" + updatedPlan +
                '}';
    }
}
