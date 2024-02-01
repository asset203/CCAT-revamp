package com.asset.ccat.gateway.models.requests.customer_care.notepad;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class GetAllNotePadRequest extends BaseRequest {

    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "GetAllNotePadRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
