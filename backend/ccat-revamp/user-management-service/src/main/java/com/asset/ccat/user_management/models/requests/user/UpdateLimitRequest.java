
package com.asset.ccat.user_management.models.requests.user;

import com.asset.ccat.user_management.models.requests.BaseRequest;

public class UpdateLimitRequest extends BaseRequest {

    private Integer userId;

    /*  
        UPDATE_BALANCE_ADD = 1;
        UPDATE_BALANCE_SUBTRACT = 2;
        UPDATE_BALANCE_SETAMT = 3;
     */
    private Integer actionType;
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
