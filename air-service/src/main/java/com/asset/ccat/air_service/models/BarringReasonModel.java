package com.asset.ccat.air_service.models;

public class BarringReasonModel {

    private String msisdn;
    private int barringType;
    private String reason;
    private String requestedBy;
    private String userId;
    private int modMsisdn;

    public BarringReasonModel() {
    }

    public BarringReasonModel(String msisdn, int barringType, String reason, String requestedBy, String userId) {
        this.msisdn = msisdn;
        this.barringType = barringType;
        this.reason = reason;
        this.requestedBy = requestedBy;
        this.userId = userId;
    }

    public int getBarringType() {
        return barringType;
    }

    public void setBarringType(int barringType) {
        this.barringType = barringType;
    }

    public int getModMsisdn() {
        return modMsisdn;
    }

    public void setModMsisdn(int modMsisdn) {
        this.modMsisdn = modMsisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
