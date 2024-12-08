package com.asset.ccat.gateway.models.shared;

/**
 * @author mohamed.metwaly
 */
public class PaginationModel {
    private Integer offset;
    private Integer fetchCount;
    private String queryString;
    private String sortedBy;
    private Integer order; // 1>> asc , 2 > desc
    private Boolean isGetAll;

    public Integer getOffset() {
        return offset != null ? offset : 0;
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

    public Boolean getIsGetAll() {
        return isGetAll;
    }

    public void setIsGetAll(Boolean getAll) {
        isGetAll = getAll;
    }

    @Override
    public String toString() {
        return "PaginationModel{" +
                "offset=" + offset +
                ", fetchCount=" + fetchCount +
                ", queryString='" + queryString + '\'' +
                ", sortedBy='" + sortedBy + '\'' +
                ", order=" + order +
                ", isGetAll=" + isGetAll +
                '}';
    }
}
