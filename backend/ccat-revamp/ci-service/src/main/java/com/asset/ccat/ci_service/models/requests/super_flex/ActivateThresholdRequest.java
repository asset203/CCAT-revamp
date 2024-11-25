package com.asset.ccat.ci_service.models.requests.super_flex;

import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

public class ActivateThresholdRequest extends SubscriberRequest {

    private Integer thresholdAmount;

    public Integer getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(Integer thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }
}
