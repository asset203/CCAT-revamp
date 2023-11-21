package com.asset.ccat.lookup_service.models.requests;

public class GetBarringReasonRequest extends BaseRequest {

    private Boolean isTempBlocked;
    private String msisdn;

    public Boolean getTempBlocked() {
        return isTempBlocked;
    }

    public void setTempBlocked(Boolean tempBlocked) {
        isTempBlocked = tempBlocked;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
