package com.asset.ccat.gateway.models.responses.customer_care;

public class PrepaidVBPCheckResponse {

    private Boolean isSubscribed;

    public PrepaidVBPCheckResponse() {
    }

    public PrepaidVBPCheckResponse(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }
}
