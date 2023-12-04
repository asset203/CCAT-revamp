package com.asset.ccat.gateway.models.customer_care.balance_dispute;

import java.util.ArrayList;

/**
 * @author Assem.Hassan
 */
public class UsageSummarySheetModel {

  private ArrayList<BdSummaryUsageModel> internetUsage;
  private ArrayList<BdSummaryUsageModel> mobileTelephony;
  private ArrayList<BdSummaryUsageModel> shortMessages;

  public UsageSummarySheetModel() {
    this.internetUsage = new ArrayList<>();
    this.mobileTelephony = new ArrayList<>();
    this.shortMessages = new ArrayList<>();
  }


  public ArrayList<BdSummaryUsageModel> getInternetUsage() {
    return internetUsage;
  }

  public void setInternetUsage(
      ArrayList<BdSummaryUsageModel> internetUsage) {
    this.internetUsage = internetUsage;
  }


  public ArrayList<BdSummaryUsageModel> getMobileTelephony() {
    return mobileTelephony;
  }

  public void setMobileTelephony(
      ArrayList<BdSummaryUsageModel> mobileTelephony) {
    this.mobileTelephony = mobileTelephony;
  }


  public ArrayList<BdSummaryUsageModel> getShortMessages() {
    return shortMessages;
  }

  public void setShortMessages(
      ArrayList<BdSummaryUsageModel> shortMessages) {
    this.shortMessages = shortMessages;
  }
}
