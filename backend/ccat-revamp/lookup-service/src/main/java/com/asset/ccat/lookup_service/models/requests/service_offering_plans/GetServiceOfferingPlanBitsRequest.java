package com.asset.ccat.lookup_service.models.requests.service_offering_plans;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class GetServiceOfferingPlanBitsRequest extends BaseRequest {

    private Integer planId;

    public GetServiceOfferingPlanBitsRequest() {
    }

    public GetServiceOfferingPlanBitsRequest(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }


    @Override
    public String toString() {
        return "GetServiceOfferingPlanBitsRequest{" +
                "planId=" + planId +
                '}';
    }
}
