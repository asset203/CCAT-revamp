package com.asset.ccat.balance_dispute_service.dto.models;

import java.math.BigDecimal;

public class UsageModel {

  private String type;
  private BigDecimal total;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
