package com.asset.ccat.lookup_service.models.requests.admin_faf_plan;


import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class AddAdminFafPlanRequest extends BaseRequest {
    private FafPlanModel addedPlan;

    public AddAdminFafPlanRequest() {
    }

    public FafPlanModel getAddedPlan() {
        return addedPlan;
    }

    public void setAddedPlan(FafPlanModel addedPlan) {
        this.addedPlan = addedPlan;
    }
}
