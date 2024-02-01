package com.asset.ccat.gateway.models.requests.admin.business_plan;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class GetBusinessPlanRequest extends BaseRequest {

    private Integer businessPlanId;

    public Integer getBusinessPlanId() {
        return businessPlanId;
    }

    public void setBusinessPlanId(Integer businessPlanId) {
        this.businessPlanId = businessPlanId;
    }

    @Override
    public String toString() {
        return "GetBusinessPlanRequest{" +
                "businessPlanId=" + businessPlanId +
                '}';
    }
}
