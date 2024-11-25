package com.asset.ccat.procedureservice.dto.models;

public class PaymentGatewayVoucherModel {
    private Integer billingNumber;
    private String msisdn;

    public PaymentGatewayVoucherModel() {
    }

    public PaymentGatewayVoucherModel(Integer billingNumber, String msisdn) {
        this.billingNumber = billingNumber;
        this.msisdn = msisdn;
    }

    public Integer getBillingNumber() {
        return billingNumber;
    }

    public void setBillingNumber(Integer billingNumber) {
        this.billingNumber = billingNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
