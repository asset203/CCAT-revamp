package com.asset.ccat.gateway.models.requests.admin.call_activity_admin;

import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class AddActivityReasonRequest extends BaseRequest {

    private ReasonActivityModel reasonActivity;


    public ReasonActivityModel getReasonActivity() {
        return reasonActivity;
    }

    public void setReasonActivity(ReasonActivityModel reasonActivity) {
        this.reasonActivity = reasonActivity;
    }

    @Override
    public String toString() {
        return "AddReasonActivityRequest{" +
                "reasonActivityModel=" + reasonActivity +
                '}';
    }
}
