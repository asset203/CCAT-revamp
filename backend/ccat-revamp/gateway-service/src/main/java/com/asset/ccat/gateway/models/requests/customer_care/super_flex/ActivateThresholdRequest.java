package com.asset.ccat.gateway.models.requests.customer_care.super_flex;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class ActivateThresholdRequest extends SubscriberRequest {

    private Integer thresholdAmount;

    public Integer getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Integer thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    @Override
    public String toString() {
        return "ActivateThresholdRequest{" +
                "thresholdAmount=" + thresholdAmount +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
