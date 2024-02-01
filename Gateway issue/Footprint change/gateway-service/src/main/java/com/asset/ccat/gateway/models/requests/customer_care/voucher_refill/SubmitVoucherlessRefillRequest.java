package com.asset.ccat.gateway.models.requests.customer_care.voucher_refill;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class SubmitVoucherlessRefillRequest extends BaseRequest {

    private String msisdn;
    private Float amount;
    private String paymentProfileId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPaymentProfileId() {
        return paymentProfileId;
    }

    public void setPaymentProfileId(String paymentProfileId) {
        this.paymentProfileId = paymentProfileId;
    }

    @Override
    public String toString() {
        return "SubmitVoucherlessRefillRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", amount=" + amount +
                ", paymentProfileId='" + paymentProfileId + '\'' +
                '}';
    }
}
