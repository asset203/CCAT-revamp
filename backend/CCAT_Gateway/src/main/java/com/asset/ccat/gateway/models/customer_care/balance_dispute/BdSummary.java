/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care.balance_dispute;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * @author karma.elqadi
 */
public class BdSummary implements Serializable {

  private int daId;
  private String daName;
  private String reportName;

  private ArrayList<BdSubTypeModel> daAdjustments = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSubTypeModel> daBonusAdj = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSubTypeModel> daUsg = new ArrayList<BdSubTypeModel>();

  private ArrayList<BdSubTypeModel> mbRecharges = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSubTypeModel> mbPayments = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSubTypeModel> mbAdjustments = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSubTypeModel> mbUsg = new ArrayList<BdSubTypeModel>();
  private ArrayList<BdSummaryUsageModel> internetUsages = new ArrayList<BdSummaryUsageModel>();
  private ArrayList<BdSummaryUsageModel> mobileTelephony = new ArrayList<>();
  private ArrayList<BdSummaryUsageModel> shortMessages = new ArrayList<BdSummaryUsageModel>();
  private double daAdjustmentsTtlCredit = 0;
  private double daBonusAdjTtlCredit = 0;
  private double mbRechargesTtlCredit = 0;
  private double mbPaymentsTtlCredit = 0;
  private double mbAdjustmentsTtlCredit = 0;
  private double daAdjustmentsTtlDebit = 0;
  private double daUsgTtlDebit = 0;
  private double mbAdjustmentsTtlDebit = 0;
  private double mbUsgTtlDebit = 0;
  private int mobileTelephonyTotal = 0;


  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public int getDaId() {
    return daId;
  }

  public void setDaId(int daId) {
    this.daId = daId;
  }

  public String getDaName() {
    return daName;
  }

  public void setDaName(String daName) {
    this.daName = daName;
  }

  public ArrayList<BdSubTypeModel> getDaAdjustments() {
    return daAdjustments;
  }

  public void setDaAdjustments(ArrayList<BdSubTypeModel> daAdjustments) {
    this.daAdjustments = daAdjustments;
  }

  public double getDaAdjustmentsTtlCredit() {
    return daAdjustmentsTtlCredit;
  }

  public void setDaAdjustmentsTtlCredit(double daAdjustmentsTtlCredit) {
    this.daAdjustmentsTtlCredit = daAdjustmentsTtlCredit;
  }

  public double getDaAdjustmentsTtlDebit() {
    return daAdjustmentsTtlDebit;
  }

  public void setDaAdjustmentsTtlDebit(double daAdjustmentsTtlDebit) {
    this.daAdjustmentsTtlDebit = daAdjustmentsTtlDebit;
  }

  public ArrayList<BdSubTypeModel> getDaBonusAdj() {
    return daBonusAdj;
  }

  public void setDaBonusAdj(ArrayList<BdSubTypeModel> daBonusAdj) {
    this.daBonusAdj = daBonusAdj;
  }

  public double getDaBonusAdjTtlCredit() {
    return daBonusAdjTtlCredit;
  }

  public void setDaBonusAdjTtlCredit(double daBonusAdjTtlCredit) {
    this.daBonusAdjTtlCredit = daBonusAdjTtlCredit;
  }

  public ArrayList<BdSubTypeModel> getDaUsg() {
    return daUsg;
  }

  public void setDaUsg(ArrayList<BdSubTypeModel> daUsg) {
    this.daUsg = daUsg;
  }

  public double getDaUsgTtlDebit() {
    return daUsgTtlDebit;
  }

  public void setDaUsgTtlDebit(double daUsgTtlDebit) {
    this.daUsgTtlDebit = daUsgTtlDebit;
  }

  public ArrayList<BdSubTypeModel> getMbAdjustments() {
    return mbAdjustments;
  }

  public void setMbAdjustments(ArrayList<BdSubTypeModel> mbAdjustments) {
    this.mbAdjustments = mbAdjustments;
  }

  public double getMbAdjustmentsTtlCredit() {
    return mbAdjustmentsTtlCredit;
  }

  public void setMbAdjustmentsTtlCredit(double mbAdjustmentsTtlCredit) {
    this.mbAdjustmentsTtlCredit = mbAdjustmentsTtlCredit;
  }

  public double getMbAdjustmentsTtlDebit() {
    return mbAdjustmentsTtlDebit;
  }

  public void setMbAdjustmentsTtlDebit(double mbAdjustmentsTtlDebit) {
    this.mbAdjustmentsTtlDebit = mbAdjustmentsTtlDebit;
  }

  public ArrayList<BdSubTypeModel> getMbPayments() {
    return mbPayments;
  }

  public void setMbPayments(ArrayList<BdSubTypeModel> mbPayments) {
    this.mbPayments = mbPayments;
  }

  public double getMbPaymentsTtlCredit() {
    return mbPaymentsTtlCredit;
  }

  public void setMbPaymentsTtlCredit(double mbPaymentsTtlCredit) {
    this.mbPaymentsTtlCredit = mbPaymentsTtlCredit;
  }

  public ArrayList<BdSubTypeModel> getMbRecharges() {
    return mbRecharges;
  }

  public void setMbRecharges(ArrayList<BdSubTypeModel> mbRecharges) {
    this.mbRecharges = mbRecharges;
  }

  public double getMbRechargesTtlCredit() {
    return mbRechargesTtlCredit;
  }

  public void setMbRechargesTtlCredit(double mbRechargesTtlCredit) {
    this.mbRechargesTtlCredit = mbRechargesTtlCredit;
  }

  public ArrayList<BdSubTypeModel> getMbUsg() {
    return mbUsg;
  }

  public void setMbUsg(ArrayList<BdSubTypeModel> mbUsg) {
    this.mbUsg = mbUsg;
  }

  public double getMbUsgTtlDebit() {
    return mbUsgTtlDebit;
  }

  public void setMbUsgTtlDebit(double mbUsgTtlDebit) {
    this.mbUsgTtlDebit = mbUsgTtlDebit;
  }

  // Radwa Hamdi 7/2/2016 CCAT_CR1603_BalanceDispute

  public ArrayList<BdSummaryUsageModel> getInternetUsages() {
    return internetUsages;
  }

  public void setInternetUsages(ArrayList<BdSummaryUsageModel> internetUsages) {
    this.internetUsages = internetUsages;
  }

  public ArrayList<BdSummaryUsageModel> getMobileTelephony() {
    return mobileTelephony;
  }

  public void setMobileTelephony(ArrayList<BdSummaryUsageModel> mobileTelephony) {
    this.mobileTelephony = mobileTelephony;
  }

  public ArrayList<BdSummaryUsageModel> getShortMessages() {
    return shortMessages;
  }

  public void setShortMessages(ArrayList<BdSummaryUsageModel> shortMessages) {
    this.shortMessages = shortMessages;
  }

  public int getMobileTelephonyTotal() {
    return mobileTelephonyTotal;
  }

  public void setMobileTelephonyTotal(int mobileTelephonyTotal) {
    this.mobileTelephonyTotal = mobileTelephonyTotal;
  }
}
