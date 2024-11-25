package com.asset.ccat.procedureservice.dto.responses.payment_gateway_voucher;

public class GetPaymentGatewayVoucherResponse {
    private Integer billingNumber;
    private String msisdn;

    public GetPaymentGatewayVoucherResponse() {
    }

    public GetPaymentGatewayVoucherResponse(Integer billingNumber, String msisdn) {
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
