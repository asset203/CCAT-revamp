package com.asset.ccat.ci_service.models.requests.prepaidVBP;

import com.asset.ccat.ci_service.models.requests.SubscriberRequest;

public class PrepaidVBPSubscriptionRequest extends SubscriberRequest {
    private String transactionType;
    private Integer transactionCode;
    private Float transactionAmount;

    private String transactionCodeName;
    public PrepaidVBPSubscriptionRequest() {
    }

    public PrepaidVBPSubscriptionRequest(String transactionType, Integer transactionCode, Float transactionAmount) {
        this.transactionType = transactionType;
        this.transactionCode = transactionCode;
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Integer transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCodeName() {
        return transactionCodeName;
    }

    public void setTransactionCodeName(String transactionCodeName) {
        this.transactionCodeName = transactionCodeName;
    }

    @Override
    public String toString() {
        return "PrepaidVBPSubscriptionRequest{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionCode=" + transactionCode +
                ", transactionAmount=" + transactionAmount +
                ", transactionName='" + transactionCodeName + '\'' +
                '}';
    }
}
