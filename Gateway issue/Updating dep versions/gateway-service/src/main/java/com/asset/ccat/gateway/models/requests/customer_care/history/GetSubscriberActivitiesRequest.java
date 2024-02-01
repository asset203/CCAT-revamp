package com.asset.ccat.gateway.models.requests.customer_care.history;

import com.asset.ccat.gateway.constants.FilterType;
import com.asset.ccat.gateway.models.requests.SubscriberRequest;
import com.asset.ccat.gateway.models.shared.Filter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class GetSubscriberActivitiesRequest extends SubscriberRequest {

    private Integer offset;
    private Integer fetchCount;
    private String queryString;
    private Long dateFrom;
    private Long dateTo;
    private String sortedBy;
    private Integer order; // 1>> asc , 2 > desc
    private Boolean isGetAll;


    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Boolean getIsGetAll() {
        return isGetAll;
    }

    public void setIsGetAll(Boolean isGetAll) {
        this.isGetAll = isGetAll;
    }

    @JsonIgnore
    public List<Filter> getFilters() {
        List<Filter> filters = new ArrayList<>();
        String[] filterArray = this.queryString.split("&");

        for (String filter : filterArray) {
            filters.add(
                    new Filter(
                            filter.substring(0, filter.indexOf("=")),
                            filter.substring(filter.indexOf("=") + 1, filter.indexOf(",")),
                            FilterType.get(filter.substring(filter.indexOf(",") + 1))
                    )
            );
        }
        return filters;
    }

    @Override
    public String toString() {
        return "GetSubscriberActivitiesRequest{" +
                "offset=" + offset +
                ", fetchCount=" + fetchCount +
                ", queryString='" + queryString + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", sortedBy='" + sortedBy + '\'' +
                ", order=" + order +
                ", isGetAll=" + isGetAll +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
