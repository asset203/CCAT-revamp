/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.customer_care.history;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

/**
 *
 * @author wael.mohamed
 */
public class GetAllSubscriberActivityRequest extends SubscriberRequest {

    private Long dateFrom;
    private Long dateTo;
    private Integer fetchCount;

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

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
    }
}
