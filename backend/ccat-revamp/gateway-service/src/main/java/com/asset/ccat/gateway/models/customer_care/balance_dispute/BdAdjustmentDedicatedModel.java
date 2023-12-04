package com.asset.ccat.gateway.models.customer_care.balance_dispute;

import java.io.Serializable;

public class BdAdjustmentDedicatedModel implements Serializable {

  private int dedicatedAccountID;
  private double dedicatedAmount;
  private String daName;

  public BdAdjustmentDedicatedModel() {
  }

  public BdAdjustmentDedicatedModel(int dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public BdAdjustmentDedicatedModel(int dedicatedAccountID, double dedicatedAmount) {
    this.dedicatedAccountID = dedicatedAccountID;
    this.dedicatedAmount = dedicatedAmount;
  }

  public BdAdjustmentDedicatedModel(int dedicatedAccountID, double dedicatedAmount, String daName) {
    this.dedicatedAccountID = dedicatedAccountID;
    this.daName = daName;
    this.dedicatedAmount = dedicatedAmount;
  }

  public int getDedicatedAccountID() {
    return dedicatedAccountID;
  }

  public void setDedicatedAccountID(int dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public double getDedicatedAmount() {
    return dedicatedAmount;
  }

  public void setDedicatedAmount(double dedicatedAmount) {
    this.dedicatedAmount = dedicatedAmount;
  }

  public String getDaName() {
    return daName;
  }

  public void setDaName(String daName) {
    this.daName = daName;
  }

  @Override
  public boolean equals(Object obj) {
    return this.getDedicatedAccountID()
        == ((BdAdjustmentDedicatedModel) obj).getDedicatedAccountID();
  }
}
