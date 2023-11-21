package com.asset.ccat.gateway.models.requests.customer_care.account_group;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetAccountGroupsRequest extends SubscriberRequest {
    private Integer serviceClassId;

    public GetAccountGroupsRequest() {
    }

    public GetAccountGroupsRequest(Integer serviceClassId) {
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
        return "GetAccountGroupsRequest{" +
                "serviceClassId=" + serviceClassId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
