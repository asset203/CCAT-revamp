package com.asset.ccat.lookup_service.models.requests.admin_faf_plan;


import com.asset.ccat.lookup_service.models.requests.BaseRequest;

/**
 * @author mohamed.metwaly
 */
public class DeleteAdminFafPlanRequest extends BaseRequest {
    private Integer fafPlanId;

    public DeleteAdminFafPlanRequest() {
    }

    public Integer getFafPlanId() {
        return fafPlanId;
    }

    public void setFafPlanId(Integer fafPlanId) {
        this.fafPlanId = fafPlanId;
    }
}
