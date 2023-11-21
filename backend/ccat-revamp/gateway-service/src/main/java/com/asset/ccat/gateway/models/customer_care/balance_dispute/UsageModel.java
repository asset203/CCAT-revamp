package com.asset.ccat.gateway.models.customer_care.balance_dispute;

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
