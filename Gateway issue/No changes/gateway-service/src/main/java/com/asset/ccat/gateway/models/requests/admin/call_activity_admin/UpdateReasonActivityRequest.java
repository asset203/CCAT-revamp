package com.asset.ccat.gateway.models.requests.admin.call_activity_admin;

import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class UpdateReasonActivityRequest extends BaseRequest {

    private ReasonActivityModel reasonActivity;


    @Override
    public String toString() {
        return "UpdateReasonActivityRequest{" +
                "reasonActivity=" + reasonActivity +
                '}';
    }

    public ReasonActivityModel getReasonActivity() {
        return reasonActivity;
    }

    public void setReasonActivity(ReasonActivityModel reasonActivity) {
        this.reasonActivity = reasonActivity;
    }
}
