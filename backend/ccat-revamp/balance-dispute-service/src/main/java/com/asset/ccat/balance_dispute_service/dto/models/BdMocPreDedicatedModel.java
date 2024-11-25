package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;
import java.util.Objects;


public class BdMocPreDedicatedModel implements Serializable {

  private Integer dedicatedAccountID;
  private Double multiDedicatedAccountBefore;
  private Double multiDedicatedAccountAfter;

  public BdMocPreDedicatedModel() {
  }

  public BdMocPreDedicatedModel(Integer dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public BdMocPreDedicatedModel(Integer dedicatedAccountID, Double multiDedicatedAccountBefore,
      double multiDedicatedAccountAfter) {
    this.dedicatedAccountID = dedicatedAccountID;
    this.multiDedicatedAccountBefore = multiDedicatedAccountBefore;
    this.multiDedicatedAccountAfter = multiDedicatedAccountAfter;
  }

  public Integer getDedicatedAccountID() {
    return dedicatedAccountID;
  }

  public void setDedicatedAccountID(Integer dedicatedAccountID) {
    this.dedicatedAccountID = dedicatedAccountID;
  }

  public Double getMultiDedicatedAccountBefore() {
    return multiDedicatedAccountBefore;
  }

  public void setMultiDedicatedAccountBefore(Double multiDedicatedAccountBefore) {
    this.multiDedicatedAccountBefore = multiDedicatedAccountBefore;
  }

  public Double getMultiDedicatedAccountAfter() {
    return multiDedicatedAccountAfter;
  }

  public void setMultiDedicatedAccountAfter(Double multiDedicatedAccountAfter) {
    this.multiDedicatedAccountAfter = multiDedicatedAccountAfter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BdMocPreDedicatedModel that = (BdMocPreDedicatedModel) o;
    return getDedicatedAccountID().equals(that.getDedicatedAccountID());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDedicatedAccountID());
  }
}
