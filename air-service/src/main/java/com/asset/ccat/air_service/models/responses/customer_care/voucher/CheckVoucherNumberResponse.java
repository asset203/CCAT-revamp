package com.asset.ccat.air_service.models.responses.customer_care.voucher;

import com.asset.ccat.air_service.models.shared.VoucherDigitModel;

import java.util.ArrayList;

public class CheckVoucherNumberResponse {

    private ArrayList<VoucherDigitModel> airVoucherNumberDigits;
    private String airVoucherNumber;

    public CheckVoucherNumberResponse() {

    }

    public CheckVoucherNumberResponse(ArrayList<VoucherDigitModel> airVoucherNumberDigits, String airVoucherNumber) {
        this.airVoucherNumberDigits = airVoucherNumberDigits;
        this.airVoucherNumber = airVoucherNumber;
    }

    public ArrayList<VoucherDigitModel> getAirVoucherNumberDigits() {
        return airVoucherNumberDigits;
    }

    public void setAirVoucherNumberDigits(ArrayList<VoucherDigitModel> airVoucherNumberDigits) {
        this.airVoucherNumberDigits = airVoucherNumberDigits;
    }

    public String getAirVoucherNumber() {
        return airVoucherNumber;
    }

    public void setAirVoucherNumber(String airVoucherNumber) {
        this.airVoucherNumber = airVoucherNumber;
    }
}
