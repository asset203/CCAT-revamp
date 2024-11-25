package com.asset.ccat.user_management.configurations.constants;

/**
 *
 * @author marwa.elshawarby
 */
public enum LkMonetaryLimits {
    CREDIT_CARD_MAX(1),
    CREDIT_CARD_MIN(2),
    REBATE_MAX(3),
    REBATE_MIN(4),
    DEBIT_MAX(5),
    DEBIT_MIN(6),
    DAILY_TOTAL_CC_MAX(7),
    DAILY_TOTAL_REBATE_MAX(8),
    DAILY_TOTAL_DEBIT_MAX(9),
    DAILY_TOTAL_CC(10),
    DAILY_TOTAL_REBATE(11),
    DAILY_TOTAL_DEBIT(12);
    
    
    public final int id;
    private LkMonetaryLimits(int id){
        this.id = id;
    }  
}
