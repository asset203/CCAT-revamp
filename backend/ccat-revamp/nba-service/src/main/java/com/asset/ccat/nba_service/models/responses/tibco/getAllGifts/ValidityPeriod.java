package com.asset.ccat.nba_service.models.responses.tibco.getAllGifts;

public class ValidityPeriod {
    ToDate toDate;
    FromDate fromDate;

    public ToDate getToDate() {
        return toDate;
    }

    public void setToDate(ToDate toDate) {
        this.toDate = toDate;
    }

    public FromDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(FromDate fromDate) {
        this.fromDate = fromDate;
    }
}
