package com.asset.ccat.balancedisputemapperservice.models;

import java.util.ArrayList;

public class BdGetTodayDataUsageDetailsModel {

  private ArrayList<BdTransactionDetails> balanceDisputeTransactionDetailsList;


  public BdGetTodayDataUsageDetailsModel() {
    this.balanceDisputeTransactionDetailsList = new ArrayList<>();
  }

  public ArrayList<BdTransactionDetails> getBalanceDisputeTransactionDetailsList() {
    return balanceDisputeTransactionDetailsList;
  }

  public void setBalanceDisputeTransactionDetailsList(
      ArrayList<BdTransactionDetails> balanceDisputeTransactionDetailsList) {
    this.balanceDisputeTransactionDetailsList = balanceDisputeTransactionDetailsList;
  }
}
