package com.asset.ccat.gateway.models.requests.customer_care.voucher;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class UpdateVoucherStateRequest extends SubscriberRequest {

    private String voucherSerialNumber;
    private Integer currentState;
    private Integer newState;
    private Integer serverId;

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

    public Integer getNewState() {
        return newState;
    }

    public void setNewState(Integer newState) {
        this.newState = newState;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "UpdateVoucherStateRequest{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", currentState=" + currentState +
                ", newState=" + newState +
                ", serverId=" + serverId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
