package com.asset.ccat.gateway.defines;

public enum DSSReports {
    COMPLAINT_VIEW("Complain View"),
    VODAFONE_ONE("Vodafone One"),
    BTIVR("BTVIR"),
    ETOPUP("Etopup Transactions/Points Transaction"),
    CONTRACT_BILL("Contract Bill"),
    OUTGOING_VIEW("Outgoing View"),
    TRAFFIC_BEHAVIOR("Traffic Behavior"),
    CONTRACT_BALANCE("Contract Balance"),
    VODAFONE_ONE_REDEEM("Vodafone One Redeem"),
    USSD_REPORT("USSD Report"),
    CONTRACT_BALANCE_TRANSFER("Contract Balance Transfer"),
    VISITED_URL("Visisted URLs");

    final String pageName;

    DSSReports(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}
