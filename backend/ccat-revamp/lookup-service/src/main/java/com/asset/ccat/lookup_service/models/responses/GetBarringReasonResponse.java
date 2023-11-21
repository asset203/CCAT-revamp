package com.asset.ccat.lookup_service.models.responses;

public class GetBarringReasonResponse {

    private String reason;

    public GetBarringReasonResponse() {
    }

    public GetBarringReasonResponse(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
