package com.asset.ccat.gateway.models.requests.report;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.shared.PaginationModel;

/**
 * @author mohamed.metwaly
 */
public class GetDssReportRequest extends SubscriberRequest {
    private PaginationModel pagination;
    private Long dateFrom;
    private Long dateTo;

    public PaginationModel getPagination() {
        return pagination;
    }

    public void setPagination(PaginationModel pagination) {
        this.pagination = pagination;
    }

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "GetDssReportRequest{" +
                "pagination=" + pagination +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
