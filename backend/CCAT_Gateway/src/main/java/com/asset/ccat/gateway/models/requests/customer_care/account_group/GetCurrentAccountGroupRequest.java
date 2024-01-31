package com.asset.ccat.gateway.models.requests.customer_care.account_group;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetCurrentAccountGroupRequest extends SubscriberRequest {
    private Integer serviceClassId;

    public GetCurrentAccountGroupRequest() {
    }

    public GetCurrentAccountGroupRequest(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }

    public Integer getServiceClassId() {
        return serviceClassId;
    }

    public void setServiceClassId(Integer serviceClassId) {
        this.serviceClassId = serviceClassId;
    }
}
