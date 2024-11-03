package com.asset.ccat.balance_dispute_service.constant;

/**
 * Author: Mayar Ezz el-Din
 * */
public enum AdjustmentType {

    ADJUSTMENT(1, "ADJUSTMENT"),
    RECHARGE(2, "RECHARGE"),
    PAYMENT(3, "PAYMENT"),
    BONUS_OR_DEDICATED_ADJUSTMENT(4, "BONUS_OR_DEDICATED_ADJUSTMENT"),
    USAGE_AND_ACCUMULATORS(0, "USAGE_AND_ACCUMULATORS");
    int type;
    String name;

    AdjustmentType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public static AdjustmentType getAdjustment(int typeId) {
        for (AdjustmentType type : AdjustmentType.values())
            if (type.getType() == typeId)
                return type;
        return null;
    }

    @Override
    public String toString() {
        return "AdjustmentType{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
