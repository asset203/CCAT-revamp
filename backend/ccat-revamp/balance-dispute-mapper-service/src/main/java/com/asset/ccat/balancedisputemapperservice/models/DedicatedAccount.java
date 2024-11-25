package com.asset.ccat.balancedisputemapperservice.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
public class DedicatedAccount implements Serializable {

  private Integer daID;
  private String description;
  private Float ratingFactor;

  public DedicatedAccount() {
  }

  public DedicatedAccount(Integer daID, String description, Float ratingFactor) {
    this.daID = daID;
    this.description = description;
    this.ratingFactor = ratingFactor;
  }

  public Integer getDaID() {
    return daID;
  }

  public void setDaID(Integer daID) {
    this.daID = daID;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getRatingFactor() {
    return ratingFactor;
  }

  public void setRatingFactor(Float ratingFactor) {
    this.ratingFactor = ratingFactor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.daID);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final DedicatedAccount other = (DedicatedAccount) obj;
    return Objects.equals(this.daID, other.daID);
  }
}
