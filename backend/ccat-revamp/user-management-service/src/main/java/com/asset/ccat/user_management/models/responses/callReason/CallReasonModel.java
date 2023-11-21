package com.asset.ccat.user_management.models.responses.callReason;

public class CallReasonModel {
    private String username ;
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

    public CallReasonModel(String username, Integer userId, String msisdn, String msisdnLastDigit, String direction, String family, String type, String reason, Integer callReasonId) {
        this.username = username;
        this.userId = userId;
        this.msisdn = msisdn;
        this.msisdnLastDigit = msisdnLastDigit;
        this.direction = direction;
        this.family = family;
        this.type = type;
        this.reason = reason;
        this.callReasonId = callReasonId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdnLastDigit() {
        return msisdnLastDigit;
    }

    public void setMsisdnLastDigit(String msisdnLastDigit) {
        this.msisdnLastDigit = msisdnLastDigit;
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

    public Integer getCallReasonId() {
        return callReasonId;
    }

    public void setCallReasonId(Integer callReasonId) {
        this.callReasonId = callReasonId;
    }

    @Override
    public String toString() {
        return "CallReasonModel{" +
                "username='" + username + '\'' +
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
