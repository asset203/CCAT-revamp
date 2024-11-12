package com.asset.ccat.air_service.models.requests.customer_care;

import com.asset.ccat.air_service.models.requests.SubscriberRequest;

public class BarringRequest extends SubscriberRequest {

    private String barringReason;
    private String requestedBy;

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getBarringReason() {
        return barringReason;
    }

    public void setBarringReason(String barringReason) {
        this.barringReason = barringReason;
    }

    @Override
    public String toString() {
        return "BarringRequest{" +
                "barringReason='" + barringReason + '\'' +
                ", requestedBy='" + requestedBy + '\'' +
                '}';
    }
}
