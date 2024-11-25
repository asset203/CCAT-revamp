package com.asset.ccat.procedureservice.dto.requests.flex_share;

import com.asset.ccat.procedureservice.dto.requests.BaseRequest;

public class FlexShareUpdateRequest extends BaseRequest {
    private String msisdn;
    private String inputValue;

    public FlexShareUpdateRequest() {
    }

    public FlexShareUpdateRequest(String msisdn, String inputValue) {
        this.msisdn = msisdn;
        this.inputValue = inputValue;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    @Override
    public String toString() {
        return "FlexShareUpdateRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", inputValue='" + inputValue + '\'' +
                '}';
    }
}
