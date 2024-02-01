package com.asset.ccat.gateway.models.requests.customer_care.balance_dispute;

import com.asset.ccat.gateway.models.requests.SubscriberRequest;

public class GetBalanceDisputeReportRequest extends SubscriberRequest {

  private Long dateFrom;
  private Long dateTo;
  private Integer offset;
  private Integer fetchCount;
  private String queryString;
  private String sortedBy;
  private Integer order; // 1>> asc , 2 > desc
  private Boolean isGetAll;

  public GetBalanceDisputeReportRequest(Long dateFrom, Long dateTo,
      Integer offset,
      Integer fetchCount, String queryString, String sortedBy,
      Integer order, Boolean isGetAll) {
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.offset = offset;
    this.fetchCount = fetchCount;
    this.queryString = queryString;
    this.sortedBy = sortedBy;
    this.order = order;
    this.isGetAll = isGetAll;
  }

  public GetBalanceDisputeReportRequest() {
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

  public Boolean getIsGetAll() {
    return isGetAll;
  }

  public void setIsGetAll(Boolean isGetAll) {
    this.isGetAll = isGetAll;
  }

  @Override
  public String toString() {
    return "GetBalanceDisputeReportRequest{" +
        ", dateFrom=" + dateFrom +
        ", dateTo=" + dateTo +
        ", offset=" + offset +
        ", fetchCount=" + fetchCount +
        ", queryString='" + queryString + '\'' +
        ", sortedBy='" + sortedBy + '\'' +
        ", order=" + order +
        ", isGetAll=" + isGetAll +
        '}';
  }
}
