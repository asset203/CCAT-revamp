package com.asset.ccat.gateway.models.customer_care;

public class CallReasonModel {
    private String username;
    private Integer userId ;
    private String msisdn ;
    private String msisdnLastDigit ;
    private String direction ;
    private String family ;
    private String type ;
    private String reason;
    private Integer callReasonId ;

    public CallReasonModel() {
    }

    public String getUsername() {
        return username;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getMsisdnLastDigit() {
        return msisdnLastDigit;
    }

    public String getDirection() {
        return direction;
    }

    public String getFamily() {
        return family;
    }

    public String getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public Integer getCallReasonId() {
        return callReasonId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setMsisdnLastDigit(String msisdnLastDigit) {
        this.msisdnLastDigit = msisdnLastDigit;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCallReasonId(Integer callReasonId) {
        this.callReasonId = callReasonId;
    }

    @Override
    public String toString() {
        return "CallReasonsResponse{" +
                "userName='" + username + '\'' +
                ", userId=" + userId +
                ", msisdn='" + msisdn + '\'' +
                ", msisdnLastDigit='" + msisdnLastDigit + '\'' +
                ", direction='" + direction + '\'' +
                ", family='" + family + '\'' +
                ", type='" + type + '\'' +
                ", reason='" + reason + '\'' +
                ", callReasonId=" + callReasonId +
                '}';
    }

}
