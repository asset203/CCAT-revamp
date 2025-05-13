package com.asset.ccat.air_service.models.requests;

import java.math.BigDecimal;

public class UpdateLimitRequest extends BaseRequest {
    private Integer userId;

    /*
        UPDATE_BALANCE_ADD = 1;
        UPDATE_BALANCE_SUBTRACT = 2;
        UPDATE_BALANCE_SETAMT = 3;
     */
    private Integer actionType;
    private BigDecimal amount;
    private BigDecimal balance;


    public UpdateLimitRequest(Integer userId, Integer actionType, BigDecimal amount, BigDecimal balance) {
        this.userId = userId;
        this.actionType = actionType;
        this.amount = amount;
        this.balance = balance;
    }

    public UpdateLimitRequest() {
    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UpdateLimitRequest{" +
                "userId=" + userId +
                ", actionType=" + actionType +
                ", amount=" + amount +
                '}';
    }
}
