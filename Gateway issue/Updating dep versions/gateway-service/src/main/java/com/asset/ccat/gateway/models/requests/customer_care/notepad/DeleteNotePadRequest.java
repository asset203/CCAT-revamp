package com.asset.ccat.gateway.models.requests.customer_care.notepad;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class DeleteNotePadRequest extends BaseRequest {

    private Integer msisdn;

    public Integer getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Integer msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "DeleteNotePadRequest{" +
                "msisdn=" + msisdn +
                '}';
    }
}
