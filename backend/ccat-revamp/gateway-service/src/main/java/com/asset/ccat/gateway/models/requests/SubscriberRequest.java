package com.asset.ccat.gateway.models.requests;

/**
 * @author Mahmoud Shehab
 */
public class SubscriberRequest extends BaseRequest {

    protected String msisdn;

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
