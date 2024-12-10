package com.asset.ccat.ods_service.models.requests;

public class ContractBillRequest extends SubscriberRequest{
    private Integer reportType;
    private Integer numOfBill;

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getNumOfBill() {
        return numOfBill;
    }

    public void setNumOfBill(Integer numOfBill) {
        this.numOfBill = numOfBill;
    }
}
