package com.asset.ccat.balancedisputemapperservice.models;

import java.io.Serializable;
import java.util.Objects;

public class BdAdjustmentDedicatedModel implements Serializable {

  private Integer dedicatedAccountID;
  private Double dedicatedAmount;
  private String daName;

  public BdAdjustmentDedicatedModel() {
  }

  public BdAdjustmentDedicatedModel(Integer dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public BdAdjustmentDedicatedModel(Integer dedicatedAccountID, Double dedicatedAmount) {
    this.dedicatedAccountID = dedicatedAccountID;
    this.dedicatedAmount = dedicatedAmount;
  }

  public BdAdjustmentDedicatedModel(Integer dedicatedAccountID, Double dedicatedAmount,
      String daName) {
    this.dedicatedAccountID = dedicatedAccountID;
    this.daName = daName;
    this.dedicatedAmount = dedicatedAmount;
  }

  public Integer getDedicatedAccountID() {
    return dedicatedAccountID;
  }

  public void setDedicatedAccountID(Integer dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public Double getDedicatedAmount() {
    return dedicatedAmount;
  }

  public void setDedicatedAmount(Double dedicatedAmount) {
    this.dedicatedAmount = dedicatedAmount;
  }

  public String getDaName() {
    return daName;
  }

  public void setDaName(String daName) {
    this.daName = daName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BdAdjustmentDedicatedModel that = (BdAdjustmentDedicatedModel) o;
    return getDedicatedAccountID().equals(that.getDedicatedAccountID());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDedicatedAccountID());
  }
}
