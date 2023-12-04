package com.asset.ccat.ci_service.models.requests;

/**
 *
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

}
