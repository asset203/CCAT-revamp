package com.asset.ccat.gateway.models.requests.admin.service_offering_plans;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class DeleteServiceOfferingPlanRequest extends BaseRequest {

    private Integer planId;

    public DeleteServiceOfferingPlanRequest(Integer planId) {
        this.planId = planId;
    }

    public DeleteServiceOfferingPlanRequest() {
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "DeleteServiceOfferingPlan{" +
                "planId=" + planId +
                '}';
    }
}
