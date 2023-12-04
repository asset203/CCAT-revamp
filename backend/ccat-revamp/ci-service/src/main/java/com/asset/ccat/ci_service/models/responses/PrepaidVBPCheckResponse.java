package com.asset.ccat.ci_service.models.responses;

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
