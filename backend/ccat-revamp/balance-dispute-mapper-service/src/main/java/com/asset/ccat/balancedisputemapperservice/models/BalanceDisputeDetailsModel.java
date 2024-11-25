package com.asset.ccat.balancedisputemapperservice.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BalanceDisputeDetailsModel {

  private ArrayList<String> columnNames;
  private ArrayList<HashMap<String,String>> transactionDetailsList;
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

  public BalanceDisputeDetailsModel() {
    this.columnNames = new ArrayList<>();
    this.transactionDetailsList = new ArrayList<>();
  }

  public ArrayList<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(
          ArrayList<String> columnNames) {
    this.columnNames = columnNames;
  }

  public ArrayList<HashMap<String,String>> getTransactionDetailsList() {
    return transactionDetailsList;
  }

  public void setTransactionDetailsList(
          ArrayList<HashMap<String,String>> transactionDetailsList) {
    this.transactionDetailsList = transactionDetailsList;
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
