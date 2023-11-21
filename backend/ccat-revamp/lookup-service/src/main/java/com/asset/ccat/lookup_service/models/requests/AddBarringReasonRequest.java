package com.asset.ccat.lookup_service.models.requests;

import com.asset.ccat.lookup_service.models.BarringReasonModel;

public class AddBarringReasonRequest extends BaseRequest{

    private BarringReasonModel reasonModel;

    public BarringReasonModel getReasonModel() {
        return reasonModel;
    }

    public void setReasonModel(BarringReasonModel reasonModel) {
        this.reasonModel = reasonModel;
    }
}
