package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.CallReasonModel;

public class AddCallReasonResponse {
    private CallReasonModel callReason;

    public AddCallReasonResponse() {
    }

    public AddCallReasonResponse(CallReasonModel callReason) {
        this.callReason = callReason;
    }

    public CallReasonModel getCallReason() {
        return callReason;
    }

    public void setCallReason(CallReasonModel callReason) {
        this.callReason = callReason;
    }
}
