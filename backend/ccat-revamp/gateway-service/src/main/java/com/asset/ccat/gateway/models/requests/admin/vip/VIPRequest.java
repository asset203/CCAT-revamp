package com.asset.ccat.gateway.models.requests.admin.vip;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class VIPRequest extends BaseRequest {
    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "VIPListRequest{" +
                "msisdn='" + msisdn + '\'' +
                '}';
    }
}
