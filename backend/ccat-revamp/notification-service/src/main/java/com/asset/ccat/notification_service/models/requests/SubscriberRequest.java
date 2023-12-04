package com.asset.ccat.notification_service.models.requests;

public class SubscriberRequest extends BaseRequest {

    protected String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}