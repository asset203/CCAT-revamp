package com.asset.ccat.gateway.models.requests.admin.admin_faf_plan;

import com.asset.ccat.gateway.models.requests.BaseRequest;

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

    @Override
    public String toString() {
        return "DeleteAdminFafPlanRequest{" +
                "fafPlanId=" + fafPlanId +
                '}';
    }
}
