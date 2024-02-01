package com.asset.ccat.gateway.models.customer_care.balance_dispute;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Assem.Hassan
 */
public class BalanceTransactionsDetailsModel {

  HashMap<String, ArrayList<HashMap<String, String>>> detailsMap;

  private double totalMBCredit = 0;
  private double totalMBDebit = 0;
  private double totalDACredit = 0;
  private double totalDADebit = 0;
  private double totalAmountCredit = 0;
  private double totalAmountDebit = 0;
  private double totalDuration = 0;
  private double totalCost = 0;
  private double totalActualSeconds = 0;
  private double totalFreeSms = 0;
  private double totalInternetUsage = 0;

  public BalanceTransactionsDetailsModel() {
    this.detailsMap = new HashMap<>();
  }

  public HashMap<String, ArrayList<HashMap<String, String>>> getDetailsMap() {
    return detailsMap;
  }

  public void setDetailsMap(
      HashMap<String, ArrayList<HashMap<String, String>>> detailsMap) {
    this.detailsMap = detailsMap;
  }

  public double getTotalMBCredit() {
    return totalMBCredit;
  }

  public void setTotalMBCredit(double totalMBCredit) {
    this.totalMBCredit = totalMBCredit;
  }

  public double getTotalMBDebit() {
    return totalMBDebit;
  }

  public void setTotalMBDebit(double totalMBDebit) {
    this.totalMBDebit = totalMBDebit;
  }

  public double getTotalDACredit() {
    return totalDACredit;
  }

  public void setTotalDACredit(double totalDACredit) {
    this.totalDACredit = totalDACredit;
  }

  public double getTotalDADebit() {
    return totalDADebit;
  }

  public void setTotalDADebit(double totalDADebit) {
    this.totalDADebit = totalDADebit;
  }

  public double getTotalAmountCredit() {
    return totalAmountCredit;
  }

  public void setTotalAmountCredit(double totalAmountCredit) {
    this.totalAmountCredit = totalAmountCredit;
  }

  public double getTotalAmountDebit() {
    return totalAmountDebit;
  }

  public void setTotalAmountDebit(double totalAmountDebit) {
    this.totalAmountDebit = totalAmountDebit;
  }

  public double getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(double totalDuration) {
    this.totalDuration = totalDuration;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public double getTotalActualSeconds() {
    return totalActualSeconds;
  }

  public void setTotalActualSeconds(double totalActualSeconds) {
    this.totalActualSeconds = totalActualSeconds;
  }

  public double getTotalFreeSms() {
    return totalFreeSms;
  }

  public void setTotalFreeSms(double totalFreeSms) {
    this.totalFreeSms = totalFreeSms;
  }

  public double getTotalInternetUsage() {
    return totalInternetUsage;
  }

  public void setTotalInternetUsage(double totalInternetUsage) {
    this.totalInternetUsage = totalInternetUsage;
  }
}
