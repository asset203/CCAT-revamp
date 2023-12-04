package com.asset.ccat.air_service.models.requests.customer_care.voucher;

import com.asset.ccat.air_service.models.requests.SubscriberRequest;

public class VoucherBasedRefillRequest extends SubscriberRequest {

    private String voucherNumber;
    private Boolean isMaredCard;
    private String selectedMaredCard;

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Boolean getIsMaredCard() {
        return isMaredCard;
    }

    public void setIsMaredCard(Boolean maredCard) {
        isMaredCard = maredCard;
    }

    public String getSelectedMaredCard() {
        return selectedMaredCard;
    }

    public void setSelectedMaredCard(String selectedMaredCard) {
        this.selectedMaredCard = selectedMaredCard;
    }

    @Override
    public String toString() {
        return "VoucherBasedRefillRequest{" +
                "voucherNumber='" + voucherNumber + '\'' +
                ", isMaredCard=" + isMaredCard +
                ", selectedMaredCard='" + selectedMaredCard + '\'' +
                '}';
    }
}
