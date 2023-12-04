package com.asset.ccat.procedureservice.dto.requests.flex_share;

import com.asset.ccat.procedureservice.dto.requests.BaseRequest;

public class FlexShareInquiryRequest extends BaseRequest {
    private String msisdn;

    public FlexShareInquiryRequest() {
    }

    public FlexShareInquiryRequest(String msisdn) {
        this.msisdn = msisdn;
    }



    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "FlexShareInquiryRequest{" +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
