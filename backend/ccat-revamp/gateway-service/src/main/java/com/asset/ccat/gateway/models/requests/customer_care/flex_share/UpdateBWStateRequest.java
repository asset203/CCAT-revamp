package com.asset.ccat.gateway.models.requests.customer_care.flex_share;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class UpdateBWStateRequest extends SubscriberRequest {

    private Integer updatedValue;

    public Integer getUpdatedValue() {
        return updatedValue;
    }

    public void setUpdatedValue(Integer updatedValue) {
        this.updatedValue = updatedValue;
    }

    @Override
    public String toString() {
        return "UpdateBWStateRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", updatedValue=" + updatedValue +
                '}';
    }
}
