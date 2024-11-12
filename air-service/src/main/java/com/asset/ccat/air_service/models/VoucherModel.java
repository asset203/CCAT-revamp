/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
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

    public String getVoucherSerialNumber() {
        return voucherSerialNumber;
    }

    public void setVoucherSerialNumber(String voucherSerialNumber) {
        this.voucherSerialNumber = voucherSerialNumber;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
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

    public String getRechargeSource() {
        return rechargeSource;
    }

    public void setRechargeSource(String rechargeSource) {
        this.rechargeSource = rechargeSource;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVoucherGroup() {
        return voucherGroup;
    }

    public void setVoucherGroup(String voucherGroup) {
        this.voucherGroup = voucherGroup;
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

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

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

    @Override
    public String toString() {
        return "VoucherModel{" +
                "voucherSerialNumber='" + voucherSerialNumber + '\'' +
                ", batchId='" + batchId + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", value='" + value + '\'' +
                ", rechargeSource='" + rechargeSource + '\'' +
                ", agent='" + agent + '\'' +
                ", currency='" + currency + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", state='" + state + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", voucherDigit='" + voucherDigit + '\'' +
                ", voucherStatus=" + voucherStatus +
                ", voucherStyle='" + voucherStyle + '\'' +
                '}';
    }
}
