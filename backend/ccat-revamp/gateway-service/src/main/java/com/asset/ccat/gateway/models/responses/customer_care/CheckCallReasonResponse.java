package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.CallReasonModel;

public class CheckCallReasonResponse {
    private CallReasonModel callReason;

    public CheckCallReasonResponse() {
    }

    public CheckCallReasonResponse(CallReasonModel callReason) {
        this.callReason = callReason;
    }

    public CallReasonModel getCallReason() {
        return callReason;
    }

    public void setCallReason(CallReasonModel callReason) {
        this.callReason = callReason;
    }

    @Override
    public String toString() {
        return "CheckCallReasonResponse{" +
                "callReason=" + callReason +
                '}';
    }
}
