package com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

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

    public String getTransactionCodeName() {
        return transactionCodeName;
    }

    public void setTransactionCodeName(String transactionCodeName) {
        this.transactionCodeName = transactionCodeName;
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

    @Override
    public String toString() {
        return "SubscriptionRequest{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
