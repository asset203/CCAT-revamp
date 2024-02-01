package com.asset.ccat.gateway.constants;

public enum FootprintReportHeaders {
    ID("Id"),
    PAGE_NAME("PageName"),
    TAB_NAME("TabName"),
    ACTION_NAME("ActionName"),
    ACTION_TYPE("ActionType"),
    ACTION_TIME("ActionTime"),
    USER_NAME("UserName"),
    PROFILE_NAME("ProfileName"),
    MSISDN("Msisdn"),
    STATUS("Status"),
    ERROR_MESSAGE("ErrorMessage"),
    ERROR_CODE("ErrorCode"),
    SESSION_ID("SessionId"),
    MACHINE_NAME("MachineName"),
    REQUEST_ID("RequestId"),
    PARAM_NAME("ParamName"),
    OLD_VALUE("OldValue"),
    NEW_VALUE("NewValue");

    public final String header;

    FootprintReportHeaders(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
