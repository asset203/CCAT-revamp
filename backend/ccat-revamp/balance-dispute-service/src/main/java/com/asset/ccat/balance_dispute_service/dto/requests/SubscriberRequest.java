package com.asset.ccat.balance_dispute_service.dto.requests;

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
