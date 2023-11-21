package com.asset.ccat.lookup_service.models.requests.call_activity_admin;

import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

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
