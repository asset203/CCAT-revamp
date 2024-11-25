/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.redis.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author wael.mohamed
 */

public class SubscriberActivityModel implements Serializable {

    private static final long serialVersionUID = 4814365201984826511L;

    private Integer identifier;
    private String subscriber;
    private Integer activityId;
    private String activityType;
    private Long date;
    private String subType;
    private String amount;
    private String balance;
    private String accountStatus;
    private String transactionCode;
    private String transactionType;
    private LinkedHashMap details;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LinkedHashMap getDetails() {
        return details;
    }

    public void setDetails(LinkedHashMap details) {
        this.details = details;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getField(String fieldName) {
        switch (fieldName) {
            case "id":
                return identifier.toString();
            case "date":
                return date.toString();
            case "type":
                return activityType;
            case "amount":
                return amount;
            case "balance":
                return balance;
            case "subType":
                return subType;
            case "accountStatus":
                return accountStatus;
            case "trxType":
                return transactionType;
            case "trxCode":
                return transactionCode;
            default:
                return null;
        }
    }
}
