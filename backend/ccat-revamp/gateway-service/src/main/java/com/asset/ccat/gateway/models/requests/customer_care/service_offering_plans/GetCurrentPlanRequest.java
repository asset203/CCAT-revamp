package com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetCurrentPlanRequest extends SubscriberRequest {
    private Integer serviceClassId;

    public GetCurrentPlanRequest() {
    }

    public GetCurrentPlanRequest(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }
}
