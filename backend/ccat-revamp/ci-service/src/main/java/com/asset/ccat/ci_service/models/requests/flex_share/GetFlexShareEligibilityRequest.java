package com.asset.ccat.ci_service.models.requests.flex_share;


import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

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
                "msisdn='" + getMsisdn() + '\'' +
                ", receiverMsisdn='" + receiverMsisdn + '\'' +
                ", flexAmount=" + flexAmount +
                '}';
    }
}
