package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;
import java.util.ArrayList;

public class BdModel implements Serializable {

  private String msisdn;
  private String mobNo;
  private String date1;
  private String date2;
  private String scId;
  private String actionType;
  private String sheetType;
  private String mainBalance;
  private double mainBalanceDbl;
  private int reportTypeInt = 0;
  private double currentBalance = 0;
  private boolean balanceDateHeaderRendered = false;
  private boolean moreRecordsThanAllowed;
  private boolean otherPartPrivilege;
  private String bdType = "";

  private ArrayList<BdMocPre> mocPre = new ArrayList<>();

  private BdTransactions bdTransactions = new BdTransactions();

  public BdTransactions getBdTransactions() {
    return bdTransactions;
  }

  public void setBdTransactions(BdTransactions bdTransactions) {
    this.bdTransactions = bdTransactions;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getDate1() {
    return date1;
  }

  public void setDate1(String date1) {
    this.date1 = date1;
  }

  public String getMobNo() {
    return mobNo;
  }

  public void setMobNo(String mobNo) {
    this.mobNo = mobNo;
  }

  public String getDate2() {
    return date2;
  }

  public void setDate2(String date2) {
    this.date2 = date2;
  }

  public ArrayList<BdMocPre> getMocPre() {
    return mocPre;
  }

  public void setMocPre(ArrayList<BdMocPre> mocPre) {
    this.mocPre = mocPre;
  }

  public void clearMocPre() {
    mocPre.clear();
  }

  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public String getMainBalance() {
    return mainBalance;
  }

  public void setMainBalance(String mainBalance) {
    this.mainBalance = mainBalance;
  }

  public String getScId() {
    return scId;
  }

  public void setScId(String scId) {
    this.scId = scId;
  }

  public String getSheetType() {
    return sheetType;
  }

  public void setSheetType(String sheetType) {
    this.sheetType = sheetType;
  }

  public int getReportTypeInt() {
    return reportTypeInt;
  }

  public void setReportTypeInt(int reportTypeInt) {
    this.reportTypeInt = reportTypeInt;
  }

  public double getMainBalanceDbl() {
    return mainBalanceDbl;
  }

  public void setMainBalanceDbl(double mainBalanceDbl) {
    this.mainBalanceDbl = mainBalanceDbl;
  }

  public double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(double currentBalance) {
    this.currentBalance = currentBalance;
  }

  public boolean isBalanceDateHeaderRendered() {
    return balanceDateHeaderRendered;
  }

  public void setBalanceDateHeaderRendered(boolean balanceDateHeaderRendered) {
    this.balanceDateHeaderRendered = balanceDateHeaderRendered;
  }

  public boolean isMoreRecordsThanAllowed() {
    return moreRecordsThanAllowed;
  }

  public void setMoreRecordsThanAllowed(boolean moreRecordsThanAllowed) {
    this.moreRecordsThanAllowed = moreRecordsThanAllowed;
  }

  public String getBdType() {
    return bdType;
  }

  public void setBdType(String bdType) {
    this.bdType = bdType;
  }

  public boolean isOtherPartPrivilege() {
    return otherPartPrivilege;
  }

  public void setOtherPartPrivilege(boolean otherPartPrivilege) {
    this.otherPartPrivilege = otherPartPrivilege;
  }
}
