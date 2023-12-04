package com.asset.ccat.user_management.models.requests;

import com.asset.ccat.user_management.models.requests.BaseRequest;

public class SubscriptionRequest extends BaseRequest {
    private String msisdn ;

    public SubscriptionRequest(String msisdn) {
        this.msisdn = msisdn;
    }

    public SubscriptionRequest() {
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "SubscriptionRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
