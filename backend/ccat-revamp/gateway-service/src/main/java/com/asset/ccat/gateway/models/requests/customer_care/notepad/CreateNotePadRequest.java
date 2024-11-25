package com.asset.ccat.gateway.models.requests.customer_care.notepad;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class CreateNotePadRequest extends BaseRequest {

    private String msisdn;
    private String entry;
    private Integer userId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CreateNotePadRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", entry='" + entry + '\'' +
                ", userId=" + userId +
                '}';
    }
}
