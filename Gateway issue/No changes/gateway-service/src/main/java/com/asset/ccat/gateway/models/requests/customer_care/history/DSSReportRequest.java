package com.asset.ccat.gateway.models.requests.customer_care.history;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

/**
 * @author wael.mohamed
 */
public class DSSReportRequest extends SubscriberRequest {

    private Long dateFrom;
    private Long dateTo;
    private Integer btivr;

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

    public Integer getBtivr() {
        return btivr;
    }

    public void setBtivr(Integer btivr) {
        this.btivr = btivr;
    }

    @Override
    public String toString() {
        return "DSSReportRequest{" +
                "dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", btivr=" + btivr +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
