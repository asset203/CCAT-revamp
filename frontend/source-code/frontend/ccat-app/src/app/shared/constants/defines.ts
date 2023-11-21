export class Defines{
    public static LK_FEATURES_TYPES={
        CC_FEATURES : 1,
        ADM_FEATURES : 2,
        REPORT_FEATURES : 3
    }
    public static FILTERS_TYPES={
        START_WITH : 1,
        CONTAINS : 2,
        EQUALS : 3
    }
    public static CONTRACT_BALANCE_TRANSFER_FLAGS = [
        {label : "Sender" , value : 1},
        {label : "Receiver" , value : 2}
    ]
    public static OUTGOING_VIEW_FLAGS = [
        {label : "Voice" , value : 1},
        {label : "GPRS" , value : 2},
        {label : "SMS" , value : 3},
        {label : "All" , value : 4},
    ]
    public static COMPLAINT_VIEW_FLAGS = [
        {label : "CMrecharge_ETopup" , value : 1},
        {label : "CMajustments" , value : 2},
        {label : "CMusage" , value : 3},
        {label : "Bonus Adjustments" , value : 4},
    ]
    public static CONTRACT_BILL_NUM_OF_BILLS = [
        {label : "1" , value : 1},
        {label : "2" , value : 2},
        {label : "3" , value : 3},
        {label : "4" , value : 4},
    ]
    public static CONTRACT_BILL_REPORT_TYPES = [
        {label : "all_bills" , value : 1},
        {label : "top_bills" , value : 2},
    ]
    public static ACTIVITY_TYPES = {
        DIRECTION : 0,
        FAMILY : 1,
        REASON_TYPE : 2,
        REASON : 3

    }
    public static callReasonActions={
        CALL_ACTIVITY_HAS_REASON: 1,
        CALL_ACTIVITY_HAS_NO_REASON : 3,
        CALL_ACTIVITY_HAS_NO_PERMISSION : 2,
        CALL_ACTIVITY_CHECKED_HAS_REASON : 5,
        CALL_ACTIVITY_CHECKED_HAS_NO_REASON : 6,
        CALL_ACTIVITY_HAS_NO_LOCAL_REASON : 4
    }
    public static balanceDisputeSummaryLables = {
        mbRecharges  :  "Main Balance Recharges",
        mbPayment : "Main Balance Payment",
        mbAdjustment : "Main Balance Adjustment",
        mbUsage : "Main Balance Usage",
        daAdjustments : "DA Adjustments",
        daBonusAdj : "DA Bonus Adjustments",
        daUsg : "DA Usage"
    }
    

}