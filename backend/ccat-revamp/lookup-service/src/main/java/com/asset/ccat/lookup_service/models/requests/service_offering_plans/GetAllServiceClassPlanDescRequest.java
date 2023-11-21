package com.asset.ccat.lookup_service.models.requests.service_offering_plans;


import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class GetAllServiceClassPlanDescRequest extends BaseRequest {


    private Integer planId;

    public GetAllServiceClassPlanDescRequest() {
    }

    public GetAllServiceClassPlanDescRequest(Integer planId) {
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
        return "GetAllServiceClassPlanDescRequest{" +
                "planId=" + planId +
                '}';
    }
}
