package com.asset.ccat.ods_service.defines;

public enum DSSReports {
    COMPLAINT_VIEW("Complaint View"),
    VODAFONE_ONE("Vodafone One"),
    BTIVR("BTIVR"),
    ETOPUP("Etopup Transactions/Points Transaction"),
    CONTRACT_BILL("Contract Bill"),
    OUTGOING_VIEW("Outgoing View"),
    TRAFFIC_BEHAVIOR("Traffic Behavior"),
    CONTRACT_BALANCE("Contract Balance"),
    VODAFONE_ONE_REDEEM("Vodafone One Redeem"),
    USSD_REPORT("USSD Report"),
    CONTRACT_BALANCE_TRANSFER("Contract Balance Transfer"),
    VISITED_URL("Visited URLs"),
    Outgoing_View("Outgoing View");

    final String pageName;

    DSSReports(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
