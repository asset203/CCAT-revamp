package com.asset.ccat.gateway.models.shared;

public class VoucherDigitModel {

    private String digit;
    private Boolean status;

    public VoucherDigitModel() {
    }

    public VoucherDigitModel(String digit, Boolean status) {
        this.digit = digit;
        this.status = status;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VoucherDigitModel{" +
                "digit='" + digit + '\'' +
                ", status=" + status +
                '}';
    }
}
