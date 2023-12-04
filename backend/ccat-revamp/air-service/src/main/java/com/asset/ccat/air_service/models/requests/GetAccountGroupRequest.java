package com.asset.ccat.air_service.models.requests;

public class GetAccountGroupRequest extends SubscriberRequest {
    private Integer serviceClassId;

    public GetAccountGroupRequest() {
    }

    public GetAccountGroupRequest(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    @Override
    public String toString() {
        return "GetAccountGroupRequest{" +
                "serviceClassId=" + serviceClassId +
                '}';
    }
}
