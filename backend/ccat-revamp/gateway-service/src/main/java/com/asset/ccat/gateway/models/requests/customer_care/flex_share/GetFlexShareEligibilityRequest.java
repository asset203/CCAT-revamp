package com.asset.ccat.gateway.models.requests.customer_care.flex_share;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetFlexShareEligibilityRequest extends SubscriberRequest {

    private String receiverMsisdn;
    private Integer flexAmount;

    public String getReceiverMsisdn() {
        return receiverMsisdn;
    }

    public void setReceiverMsisdn(String receiverMsisdn) {
        this.receiverMsisdn = receiverMsisdn;
    }

    public Integer getFlexAmount() {
        return flexAmount;
    }

    public void setFlexAmount(Integer flexAmount) {
        this.flexAmount = flexAmount;
    }

    @Override
    public String toString() {
        return "GetFlexShareEligibilityRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", receiverMsisdn='" + receiverMsisdn + '\'' +
                ", flexAmount=" + flexAmount +
                '}';
    }
}
