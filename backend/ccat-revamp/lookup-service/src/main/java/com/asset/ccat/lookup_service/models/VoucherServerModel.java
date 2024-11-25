package com.asset.ccat.lookup_service.models;

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
}
