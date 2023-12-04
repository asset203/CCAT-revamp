package com.asset.ccat.air_service.models.requests;

/**
 * @author Mahmoud Shehab
 */
public class SubscriberRequest extends BaseRequest {

    private String msisdn;

    public SubscriberRequest() {
    }

    public SubscriberRequest(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "SubscriberRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
