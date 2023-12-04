package com.asset.ccat.ods_service.models.requests;

/**
 *
 * @author Mahmoud Shehab
 */
public class SubscriberRequest extends BaseRequest {

    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
