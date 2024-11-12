package com.asset.ccat.air_service.models.responses.customer_care.voucher;

import com.asset.ccat.air_service.models.VoucherModel;


public class GetVoucherDetailsResponse {

    private VoucherModel voucher;

    public GetVoucherDetailsResponse() {
    }

    public GetVoucherDetailsResponse(VoucherModel voucher) {
        this.voucher = voucher;
    }

    public VoucherModel getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherModel voucher) {
        this.voucher = voucher;
    }
}
