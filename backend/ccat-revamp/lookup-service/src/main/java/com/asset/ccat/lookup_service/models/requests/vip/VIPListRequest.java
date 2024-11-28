package com.asset.ccat.lookup_service.models.requests.vip;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class VIPListRequest extends BaseRequest {
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
