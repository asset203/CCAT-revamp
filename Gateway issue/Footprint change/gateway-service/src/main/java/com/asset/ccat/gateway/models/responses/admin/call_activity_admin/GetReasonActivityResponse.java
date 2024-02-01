package com.asset.ccat.gateway.models.responses.admin.call_activity_admin;

import com.asset.ccat.gateway.models.admin.ReasonActivityModel;

public class GetReasonActivityResponse {

    private ReasonActivityModel reasonActivity;


    public GetReasonActivityResponse() {
    }

    public GetReasonActivityResponse(ReasonActivityModel reasonActivity) {
        this.reasonActivity = reasonActivity;
    }

    public ReasonActivityModel getReasonActivity() {
        return reasonActivity;
    }

    public void setReasonActivity(ReasonActivityModel reasonActivity) {
        this.reasonActivity = reasonActivity;
    }
}
