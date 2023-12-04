package com.asset.ccat.air_service.models.requests;

public class DeleteBarringReasonRequest extends BaseRequest {

    private String msisdn;
    private int barringType;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getBarringType() {
        return barringType;
    }

    public void setBarringType(int barringType) {
        this.barringType = barringType;
    }
}
