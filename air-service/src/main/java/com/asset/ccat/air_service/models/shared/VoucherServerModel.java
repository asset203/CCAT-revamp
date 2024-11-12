package com.asset.ccat.air_service.models.shared;

/**
 * @author assem.hassan
 */
public class VoucherServerModel extends AIRServer {

    private Integer serverIndex;
    private Integer voucherSerialLength;

    public Integer getServerIndex() {
        return serverIndex;
    }

    public void setServerIndex(Integer serverIndex) {
        this.serverIndex = serverIndex;
    }

    public Integer getVoucherSerialLength() {
        return voucherSerialLength;
    }

    public void setVoucherSerialLength(Integer voucherSerialLength) {
        this.voucherSerialLength = voucherSerialLength;
    }

    @Override
    public String toString() {
        return "VoucherServerModel{" +
                "serverIndex=" + serverIndex +
                ", voucherSerialLength=" + voucherSerialLength +
                '}';
    }
}
