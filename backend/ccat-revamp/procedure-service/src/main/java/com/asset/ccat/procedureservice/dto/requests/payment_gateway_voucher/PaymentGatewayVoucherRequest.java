package com.asset.ccat.procedureservice.dto.requests.payment_gateway_voucher;

import com.asset.ccat.procedureservice.dto.requests.BaseRequest;

public class PaymentGatewayVoucherRequest extends BaseRequest {
    private String voucherSerialNumber;
    private String msisdn;

    public PaymentGatewayVoucherRequest() {
    }

    public PaymentGatewayVoucherRequest(String voucherSerialNumber, String msisdn) {
        this.voucherSerialNumber = voucherSerialNumber;
        this.msisdn = msisdn;
    }

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return "PaymentGatewayVoucherRequest{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
