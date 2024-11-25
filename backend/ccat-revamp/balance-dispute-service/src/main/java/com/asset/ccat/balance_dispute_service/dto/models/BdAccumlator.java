package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;


public class BdAccumlator implements Serializable {

  private Integer id;
  private Double accBefore;
  private Double deltaValue;
  private Double accAfter;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getAccBefore() {
    return accBefore;
  }

  public void setAccBefore(Double accBefore) {
    this.accBefore = accBefore;
  }

  public Double getDeltaValue() {
    return deltaValue;
  }

  public void setDeltaValue(Double deltaValue) {
    this.deltaValue = deltaValue;
  }

  public Double getAccAfter() {
    return accAfter;
  }

  public void setAccAfter(Double accAfter) {
    this.accAfter = accAfter;
  }
}
