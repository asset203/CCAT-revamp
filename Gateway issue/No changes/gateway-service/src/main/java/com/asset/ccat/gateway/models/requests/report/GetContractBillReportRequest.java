package com.asset.ccat.gateway.models.requests.report;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.shared.PaginationModel;

/**
 * @author mohamed.metwaly
 */
public class GetContractBillReportRequest extends SubscriberRequest {
    private PaginationModel pagination;
    private Integer numOfBill;
    private Integer reportType;

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }

    public Integer getNumOfBill() {
        return numOfBill;
    }

    public void setNumOfBill(Integer numOfBill) {
        this.numOfBill = numOfBill;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "GetContractBillReportRequest{" +
                "pagination=" + pagination +
                ", numOfBill=" + numOfBill +
                ", reportType=" + reportType +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
