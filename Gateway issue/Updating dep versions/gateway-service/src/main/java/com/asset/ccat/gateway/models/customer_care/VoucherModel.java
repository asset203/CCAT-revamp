package com.asset.ccat.gateway.models.customer_care;

import java.util.Date;

public class VoucherModel {

    private String voucherSerialNumber;
    private String batchId;
    private String expiryDate;
    private String responseCode;
    private String timeStamp;
    private String value;
    private String rechargeSource;
    private String agent;
    private String currency;
    private String operatorId;
    private String subscriberId;
    private String state;
    private String voucherGroup;
    private String transactionId;
    private String newState;
    private Date timeStampDate;
    private String activationCode;
    private String voucherDigit;
    private boolean voucherStatus;
    private String voucherStyle;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getRechargeSource() {
        return rechargeSource;
    }

    public void setRechargeSource(String rechargeSource) {
        this.rechargeSource = rechargeSource;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVoucherGroup() {
        return voucherGroup;
    }

    public void setVoucherGroup(String voucherGroup) {
        this.voucherGroup = voucherGroup;
    }

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    /*Ahmed Magdy */
    public String getVoucherDigit() {
        return voucherDigit;
    }

    public void setVoucherDigit(String voucherDigit) {
        this.voucherDigit = voucherDigit;
    }

    public boolean isVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(boolean voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getVoucherStyle() {
        return voucherStyle;
    }

    public void setVoucherStyle(String voucherStyle) {
        this.voucherStyle = voucherStyle;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }

    public Date getTimeStampDate() {
        return timeStampDate;
    }

    public void setTimeStampDate(Date timeStampDate) {
        this.timeStampDate = timeStampDate;
    }

}
