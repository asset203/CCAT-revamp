package com.asset.ccat.gateway.models.requests;

public class GetBarringReasonRequest extends SubscriberRequest {

    private Boolean isTempBlocked;

    public Boolean getTempBlocked() {
        return isTempBlocked;
    }

    public void setTempBlocked(Boolean tempBlocked) {
        isTempBlocked = tempBlocked;
    }

    @Override
    public String toString() {
        return "GetBarringReasonRequest{" +
                "isTempBlocked=" + isTempBlocked +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
