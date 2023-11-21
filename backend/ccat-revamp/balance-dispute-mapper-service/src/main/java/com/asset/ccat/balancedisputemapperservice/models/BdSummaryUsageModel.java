package com.asset.ccat.balancedisputemapperservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;


public class BdSummaryUsageModel {

  private String type = "";
  private BigDecimal total = new BigDecimal(0);
  // private double total = 0d;
  @JsonIgnore
  private int intTotal = 0;

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
//    public double getTotal() {
//        return total;
//    }
//
//    public void setTotal(double total) {
//        this.total = total;
//    }

  public int getIntTotal() {
    return intTotal;
  }

  public void setIntTotal(int intTotal) {
    this.intTotal = intTotal;
  }

}
