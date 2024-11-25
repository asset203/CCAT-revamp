/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.admin.user;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class CheckLimitRequest extends BaseRequest {

    private Integer userId;

    /*  
        UPDATE_BALANCE_ADD = 1;
        UPDATE_BALANCE_SUBTRACT = 2;
        UPDATE_BALANCE_SETAMT = 3;
     */
    private Integer actionType;
//    private List<Float> amounts;
    private Float amount;
    private Float balance;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Float getAmount() {
//        this.amount = amounts.stream().reduce(0f, Float::sum);
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
