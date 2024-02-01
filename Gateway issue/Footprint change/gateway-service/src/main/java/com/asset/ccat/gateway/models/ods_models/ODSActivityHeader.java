/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.ods_models;

/**
 * @author Mahmoud Shehab
 */
public class ODSActivityHeader {

  private Integer headerId;
  private String headerName;
  private String headerType;
  private String displayName;
  private Integer order;

  public Integer getHeaderId() {
    return headerId;
  }

  public void setHeaderId(Integer headerId) {
    this.headerId = headerId;
  }

  public String getHeaderName() {
    return headerName;
  }

  public void setHeaderName(String headerName) {
    this.headerName = headerName;
  }

  public String getHeaderType() {
    return headerType;
  }

  public void setHeaderType(String headerType) {
    this.headerType = headerType;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }
}
