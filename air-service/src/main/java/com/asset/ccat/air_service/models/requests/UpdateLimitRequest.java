package com.asset.ccat.air_service.models.requests;

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


    public UpdateLimitRequest(Integer userId, Integer actionType, Float amount, Float balance) {
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

    @Override
    public String toString() {
        return "UpdateLimitRequest{" +
                "userId=" + userId +
                ", actionType=" + actionType +
                ", amount=" + amount +
                '}';
    }
}
