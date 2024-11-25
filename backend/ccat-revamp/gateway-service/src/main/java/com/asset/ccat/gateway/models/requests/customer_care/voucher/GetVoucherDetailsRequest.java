package com.asset.ccat.gateway.models.requests.customer_care.voucher;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetVoucherDetailsRequest extends SubscriberRequest {

    private String voucherSerialNumber;
    private Integer serverId;

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "GetVoucherDetailsRequest{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", serverId=" + serverId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
