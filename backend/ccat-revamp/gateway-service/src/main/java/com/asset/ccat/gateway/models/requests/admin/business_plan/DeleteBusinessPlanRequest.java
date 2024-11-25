package com.asset.ccat.gateway.models.requests.admin.business_plan;

import com.asset.ccat.gateway.models.requests.BaseRequest;

import jakarta.validation.constraints.NotNull;

/**
 * @author nour.ihab
 */
public class DeleteBusinessPlanRequest extends BaseRequest {

    @NotNull
    private Integer businessPlanId;

    public Integer getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(Integer businessPlanId) {
        this.businessPlanId = businessPlanId;
    }

    @Override
    public String toString() {
        return "DeleteBusinessPlanRequest{" +
                "businessPlanId=" + businessPlanId +
                '}';
    }
}
