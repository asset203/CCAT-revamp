package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.VoucherModel;

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
