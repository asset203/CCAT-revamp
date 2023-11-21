package com.asset.ccat.user_management.models.requests.callReason;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.requests.SubscriptionRequest;

public class UpdateCallReasonRequest extends BaseRequest {
    private Integer callReasonId ;
    private String direction ;
    private String family ;
    private String type ;
    private String reason;

    public UpdateCallReasonRequest() {
    }

    public UpdateCallReasonRequest(Integer callReasonId, String direction, String family, String type, String reason) {
        this.callReasonId = callReasonId;
        this.direction = direction;
        this.family = family;
        this.type = type;
        this.reason = reason;
    }

    public Integer getCallReasonId() {
        return callReasonId;
    }

    public void setCallReasonId(Integer callReasonId) {
        this.callReasonId = callReasonId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "UpdateCallReasonRequest{" +
                "callReasonId=" + callReasonId +
                ", direction='" + direction + '\'' +
                ", family='" + family + '\'' +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
