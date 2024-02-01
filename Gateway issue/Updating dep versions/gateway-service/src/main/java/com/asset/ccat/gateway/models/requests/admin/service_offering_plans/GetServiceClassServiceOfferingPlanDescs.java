package com.asset.ccat.gateway.models.requests.admin.service_offering_plans;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class GetServiceClassServiceOfferingPlanDescs extends BaseRequest {

    private Integer serviceClassId;

    public GetServiceClassServiceOfferingPlanDescs() {
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    @Override
    public String toString() {
        return "GetAllServiceClassPlanDescRequest{" +
                "serviceClassId=" + serviceClassId +
                '}';
    }
}
