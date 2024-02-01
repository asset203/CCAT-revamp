package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.SmsActionModel;

import java.util.List;

public class GetSmsActionsResponse {

    private List<SmsActionModel> smsActions;

    public GetSmsActionsResponse() {
    }

    public GetSmsActionsResponse(List<SmsActionModel> smsActions) {
        this.smsActions = smsActions;
    }

    public List<SmsActionModel> getSmsActions() {
        return smsActions;
    }

    public void setSmsActions(List<SmsActionModel> smsActions) {
        this.smsActions = smsActions;
    }

    @Override
    public String toString() {
        return "GetSmsActionsResponse{" +
                "smsActions=" + smsActions +
                '}';
    }
}
