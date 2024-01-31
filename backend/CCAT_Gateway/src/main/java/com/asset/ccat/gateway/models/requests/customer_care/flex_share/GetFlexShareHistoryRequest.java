package com.asset.ccat.gateway.models.requests.customer_care.flex_share;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetFlexShareHistoryRequest extends SubscriberRequest {

    private Long dateFrom;
    private Long dateTo;
    private String flag;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "GetFlexShareHistoryRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", flag='" + flag + '\'' +
                '}';
    }
}
