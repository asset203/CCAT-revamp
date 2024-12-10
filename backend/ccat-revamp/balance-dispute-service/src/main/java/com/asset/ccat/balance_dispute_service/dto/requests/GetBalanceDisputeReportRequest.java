package com.asset.ccat.balance_dispute_service.dto.requests;

import java.util.Date;

public class GetBalanceDisputeReportRequest extends BaseRequest {

  private String msisdn;
  private Long dateFrom;
  private Long dateTo;
  private Integer offset;
  private Integer fetchCount;
  private String queryString;
  private String sortedBy;
  private Integer order; // 1>> asc , 2 > desc
  private Boolean isGetAll;

  public GetBalanceDisputeReportRequest() {
  }

  public GetBalanceDisputeReportRequest(String requestId, String sessionId, String token,
      String username, Integer userId, String msisdn, Long dateFrom, Long dateTo,
      Integer offset, Integer fetchCount, String queryString, String sortedBy, Integer order,
      Boolean isGetAll) {
    super(requestId, sessionId, token, username, userId);
    this.msisdn = msisdn;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.offset = offset;
    this.fetchCount = fetchCount;
    this.queryString = queryString;
    this.sortedBy = sortedBy;
    this.order = order;
    this.isGetAll = isGetAll;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public Long getDateFrom() {
    return dateFrom; //+ 24 * 60 * 60 * 1000;
  }

  public void setDateFrom(Long dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Long getDateTo() {
    return dateTo; // + 24 * 60 * 60 * 1000;
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
        "msisdn='" + msisdn + '\'' +
        ", dateFrom=" + dateFrom + " = " + new Date(getDateFrom()) +
        ", dateTo=" + dateTo + " = " + new Date(getDateTo()) +
            ", offset=" + offset +
        ", fetchCount=" + fetchCount +
        ", queryString='" + queryString + '\'' +
        ", sortedBy='" + sortedBy + '\'' +
        ", order=" + order +
        ", isGetAll=" + isGetAll +
        '}';
  }
}
