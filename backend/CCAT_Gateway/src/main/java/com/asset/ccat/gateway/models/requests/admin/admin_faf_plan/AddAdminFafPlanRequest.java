package com.asset.ccat.gateway.models.requests.admin.admin_faf_plan;

import com.asset.ccat.gateway.models.admin.AdminFafPlanModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class AddAdminFafPlanRequest extends BaseRequest {
    private AdminFafPlanModel addedPlan;

    public AddAdminFafPlanRequest() {
    }

    public AdminFafPlanModel getAddedPlan() {
        return addedPlan;
    }

    public void setAddedPlan(AdminFafPlanModel addedPlan) {
        this.addedPlan = addedPlan;
    }

    @Override
    public String toString() {
        return "AddAdminFafPlanRequest{" +
                "addedPlan=" + addedPlan +
                '}';
    }
}
