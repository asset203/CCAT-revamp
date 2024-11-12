package com.asset.ccat.air_service.models.requests.customer_care.voucher;

import com.asset.ccat.air_service.models.requests.SubscriberRequest;

import java.util.ArrayList;

public class CheckVoucherNumberRequest extends SubscriberRequest {
    private String voucherSerialNumber;
    private Integer serverId;
    private ArrayList<String> voucherNumber;
    private Boolean skipValidationFlag;

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

    public ArrayList<String> getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(ArrayList<String> voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Boolean getSkipValidationFlag() {
        return skipValidationFlag;
    }

    public void setSkipValidationFlag(Boolean skipValidationFlag) {
        this.skipValidationFlag = skipValidationFlag;
    }

    @Override
    public String toString() {
        return "CheckVoucherNumberRequest{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", serverId=" + serverId +
                ", voucherNumber=" + voucherNumber +
                ", skipValidationFlag=" + skipValidationFlag +
                '}';
    }
}
