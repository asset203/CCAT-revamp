package com.asset.ccat.balance_dispute_service.constant;

public enum ActionType {
    DATE_RANGE(0),
    LAST_RECHARGE(1),
    LAST_BALANCE_TRANSFER(2);

    public Integer id;
    ActionType(int id) {
        this.id = id;
    }
}
