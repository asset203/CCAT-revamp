package com.asset.ccat.balance_dispute_service.dto.models;

public class MainBalanceModel {

  private String type;
  private String debit;
  private String credit;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDebit() {
    return debit;
  }

  public void setDebit(String debit) {
    this.debit = debit;
  }

  public String getCredit() {
    return credit;
  }

  public void setCredit(String credit) {
    this.credit = credit;
  }
}
