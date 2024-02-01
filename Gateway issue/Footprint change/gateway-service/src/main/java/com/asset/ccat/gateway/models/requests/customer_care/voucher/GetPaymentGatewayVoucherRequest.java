package com.asset.ccat.gateway.models.requests.customer_care.voucher;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetPaymentGatewayVoucherRequest extends SubscriberRequest {
    private String voucherSerialNumber;

    public GetPaymentGatewayVoucherRequest() {
    }

    public GetPaymentGatewayVoucherRequest(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    @Override
    public String toString() {
        return "GetPaymentGatewayVoucherRequest{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
