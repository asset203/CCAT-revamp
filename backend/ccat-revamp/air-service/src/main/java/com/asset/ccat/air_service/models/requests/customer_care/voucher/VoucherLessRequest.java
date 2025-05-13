/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests.customer_care.voucher;

import com.asset.ccat.air_service.models.requests.BaseRequest;

import java.math.BigDecimal;

/**
 *
 * @author wael.mohamed
 */
public class VoucherLessRequest extends BaseRequest {

    private String msisdn;
    private BigDecimal amount;
    private String paymentProfileId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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
        return "VoucherLessRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", amount=" + amount +
                ", paymentProfileId='" + paymentProfileId + '\'' +
                '}';
    }
}
