package com.asset.ccat.balance_dispute_service.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;


public class BdSubTypeModel implements Serializable {

  private static final long serialVersionUID = 30088721647973692L;

  private String subType = "";
  @JsonIgnore
  private Double credit = 0d;
  @JsonIgnore
  private Double debit = 0d;
  private String creditStr = "";
  private String debitStr = "";

  @JsonProperty("type")
  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public Double getCredit() {
    return credit;
  }

  public void setCredit(Double credit) {
    if (credit != null && !credit.equals(0d)) {
      this.creditStr = String.valueOf(credit);
    } else {
      this.creditStr = "";
    }
    this.credit = credit;
  }

  public Double getDebit() {
    return debit;
  }

  public void setDebit(Double debit) {
    if (debit != null && !debit.equals(0d)) {
      this.debitStr = String.valueOf(debit);
    } else {
      this.debitStr = "";
    }
    this.debit = debit;
  }

  @JsonProperty("Credit")
  public String getCreditStr() {
    return creditStr;
  }

  @JsonProperty("Debit")
  public String getDebitStr() {
    return debitStr;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final BdSubTypeModel other = (BdSubTypeModel) obj;
    return Objects.equals(this.subType, other.subType);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash;
  }

}
