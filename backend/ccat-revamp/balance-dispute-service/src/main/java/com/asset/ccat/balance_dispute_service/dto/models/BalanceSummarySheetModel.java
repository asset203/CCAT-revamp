package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Assem.Hassan
 */
public class BalanceSummarySheetModel implements Serializable{

  private static final long serialVersionUID = 446053213892113948L;

  private ArrayList<BdSubTypeModel> mbRecharges;
  private ArrayList<BdSubTypeModel> mbPayment;
  private ArrayList<BdSubTypeModel> mbAdjustment;
  private ArrayList<BdSubTypeModel> mbUsage;
  private ArrayList<BdSubTypeModel> daAdjustments;
  private ArrayList<BdSubTypeModel> daBonusAdj;
  private ArrayList<BdSubTypeModel> daUsg;

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

  public BalanceSummarySheetModel() {
    this.mbRecharges = new ArrayList<>();
    this.mbPayment = new ArrayList<>();
    this.mbAdjustment = new ArrayList<>();
    this.mbUsage = new ArrayList<>();
    this.daAdjustments = new ArrayList<>();
    this.daBonusAdj = new ArrayList<>();
    this.daUsg = new ArrayList<>();
  }


  public ArrayList<BdSubTypeModel> getMbRecharges() {
    return mbRecharges;
  }

  public void setMbRecharges(
      ArrayList<BdSubTypeModel> mbRecharges) {
    this.mbRecharges = mbRecharges;
  }


  public ArrayList<BdSubTypeModel> getMbPayment() {
    return mbPayment;
  }

  public void setMbPayment(
      ArrayList<BdSubTypeModel> mbPayment) {
    this.mbPayment = mbPayment;
  }


  public ArrayList<BdSubTypeModel> getMbAdjustment() {
    return mbAdjustment;
  }

  public void setMbAdjustment(
      ArrayList<BdSubTypeModel> mbAdjustment) {
    this.mbAdjustment = mbAdjustment;
  }


  public ArrayList<BdSubTypeModel> getMbUsage() {
    return mbUsage;
  }

  public void setMbUsage(
      ArrayList<BdSubTypeModel> mbUsage) {
    this.mbUsage = mbUsage;
  }


  public ArrayList<BdSubTypeModel> getDaAdjustments() {
    return daAdjustments;
  }

  public void setDaAdjustments(
      ArrayList<BdSubTypeModel> daAdjustments) {
    this.daAdjustments = daAdjustments;
  }

  public ArrayList<BdSubTypeModel> getDaBonusAdj() {
    return daBonusAdj;
  }

  public void setDaBonusAdj(
      ArrayList<BdSubTypeModel> daBonusAdj) {
    this.daBonusAdj = daBonusAdj;
  }

  public ArrayList<BdSubTypeModel> getDaUsg() {
    return daUsg;
  }

  public void setDaUsg(
      ArrayList<BdSubTypeModel> daUsg) {
    this.daUsg = daUsg;
  }

  public double getDaAdjustmentsTtlCredit() {
    return daAdjustmentsTtlCredit;
  }

  public void setDaAdjustmentsTtlCredit(double daAdjustmentsTtlCredit) {
    this.daAdjustmentsTtlCredit = daAdjustmentsTtlCredit;
  }

  public double getDaBonusAdjTtlCredit() {
    return daBonusAdjTtlCredit;
  }

  public void setDaBonusAdjTtlCredit(double daBonusAdjTtlCredit) {
    this.daBonusAdjTtlCredit = daBonusAdjTtlCredit;
  }

  public double getMbRechargesTtlCredit() {
    return mbRechargesTtlCredit;
  }

  public void setMbRechargesTtlCredit(double mbRechargesTtlCredit) {
    this.mbRechargesTtlCredit = mbRechargesTtlCredit;
  }

  public double getMbPaymentsTtlCredit() {
    return mbPaymentsTtlCredit;
  }

  public void setMbPaymentsTtlCredit(double mbPaymentsTtlCredit) {
    this.mbPaymentsTtlCredit = mbPaymentsTtlCredit;
  }

  public double getMbAdjustmentsTtlCredit() {
    return mbAdjustmentsTtlCredit;
  }

  public void setMbAdjustmentsTtlCredit(double mbAdjustmentsTtlCredit) {
    this.mbAdjustmentsTtlCredit = mbAdjustmentsTtlCredit;
  }

  public double getDaAdjustmentsTtlDebit() {
    return daAdjustmentsTtlDebit;
  }

  public void setDaAdjustmentsTtlDebit(double daAdjustmentsTtlDebit) {
    this.daAdjustmentsTtlDebit = daAdjustmentsTtlDebit;
  }

  public double getDaUsgTtlDebit() {
    return daUsgTtlDebit;
  }

  public void setDaUsgTtlDebit(double daUsgTtlDebit) {
    this.daUsgTtlDebit = daUsgTtlDebit;
  }

  public double getMbAdjustmentsTtlDebit() {
    return mbAdjustmentsTtlDebit;
  }

  public void setMbAdjustmentsTtlDebit(double mbAdjustmentsTtlDebit) {
    this.mbAdjustmentsTtlDebit = mbAdjustmentsTtlDebit;
  }

  public double getMbUsgTtlDebit() {
    return mbUsgTtlDebit;
  }

  public void setMbUsgTtlDebit(double mbUsgTtlDebit) {
    this.mbUsgTtlDebit = mbUsgTtlDebit;
  }

  public int getMobileTelephonyTotal() {
    return mobileTelephonyTotal;
  }

  public void setMobileTelephonyTotal(int mobileTelephonyTotal) {
    this.mobileTelephonyTotal = mobileTelephonyTotal;
  }
}
