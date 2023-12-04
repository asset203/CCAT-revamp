package com.asset.ccat.balance_dispute_service.dto.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class BdMocPre implements Serializable {

  private Double inAccountAfterCallAmount;
  private Double inAccountBeforeCallAmount;
  private Double ratedFlatAmount;
  private Double roundedVolume;
  private String zone;
  private String destination;
  private String otherPartyNumber;
  private Double xFileChargeAmount;
  private String opNumberAddress;             //Destination
  private Integer[] daIds;
  private Double[] multiDABefore;
  private Double[] multiDAAfter;
  private ArrayList<BdAccumlator> accumulators;
  private ArrayList<BdMocPreDedicatedModel> bdMocPreDedicatedList;
  private String serviceClass;
  private String missingInFlag;
  private String region;
  private String callDateTimeStr;
  private Double callDuration;
  private String callTotalActualSeconds;
  private String callEnd;
  private String ratingGroup;
  private String cipipInCommunityID;
  private Double ratedVolume;
  private Date dateTimeNew;
  private String adjustmentDescription;

  private String activeSOBs;
  private String accountGroups;
  private String trxCode;
  private String trxType;

  public BdMocPre() {
    multiDABefore = new Double[10];
    multiDAAfter = new Double[10];
    daIds = new Integer[10];
    bdMocPreDedicatedList = new ArrayList<>();
  }

  public String getRatingGroup() {
    return ratingGroup;
  }

  public void setRatingGroup(String ratingGroup) {
    this.ratingGroup = ratingGroup;
  }

  public String getCipipInCommunityID() {
    return cipipInCommunityID;
  }

  public void setCipipInCommunityID(String cipipInCommunityID) {
    this.cipipInCommunityID = cipipInCommunityID;
  }

  public String getCallDateTimeStr() {
    return callDateTimeStr;
  }

  public void setCallDateTimeStr(String callDateTimeStr) {
    this.callDateTimeStr = callDateTimeStr;
  }

  public String getServiceClass() {
    return serviceClass;
  }

  public void setServiceClass(String serviceClass) {
    this.serviceClass = serviceClass;
  }

  public String getMissingInFlag() {
    return missingInFlag;
  }

  public void setMissingInFlag(String missingInFlag) {
    this.missingInFlag = missingInFlag;
  }

  public Double getInAccountAfterCallAmount() {
    return inAccountAfterCallAmount;
  }

  public void setInAccountAfterCallAmount(Double inAccountAfterCallAmount) {
    this.inAccountAfterCallAmount = inAccountAfterCallAmount;
  }

  public double getInAccountBeforeCallAmount() {
    return inAccountBeforeCallAmount;
  }

  public void setInAccountBeforeCallAmount(Double inAccountBeforeCallAmount) {
    this.inAccountBeforeCallAmount = inAccountBeforeCallAmount;
  }

  public double getRatedFlatAmount() {
    return ratedFlatAmount;
  }

  public void setRatedFlatAmount(double ratedFlatAmount) {
    this.ratedFlatAmount = ratedFlatAmount;
  }

  public void setRatedFlatAmount(Double ratedFlatAmount) {
    this.ratedFlatAmount = ratedFlatAmount;
  }

  public Double getRoundedVolume() {
    return roundedVolume;
  }

  public void setRoundedVolume(Double roundedVolume) {
    this.roundedVolume = roundedVolume;
  }

  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getOpNumberAddress() {
    return opNumberAddress;
  }

  public void setOpNumberAddress(String opNumberAddress) {
    this.opNumberAddress = opNumberAddress;
  }

  public Double getxFileChargeAmount() {
    return xFileChargeAmount;
  }

  public void setxFileChargeAmount(Double xFileChargeAmount) {
    this.xFileChargeAmount = xFileChargeAmount;
  }

  public Integer[] getDaIds() {
    return daIds;
  }

  public void setDaIds(Integer[] daIds) {
    this.daIds = daIds;
  }

  public Double[] getMultiDABefore() {
    return multiDABefore;
  }

  public void setMultiDABefore(Double[] multiDABefore) {
    this.multiDABefore = multiDABefore;
  }

  public Double[] getMultiDAAfter() {
    return multiDAAfter;
  }

  public void setMultiDAAfter(Double[] multiDAAfter) {
    this.multiDAAfter = multiDAAfter;
  }

  public ArrayList<BdMocPreDedicatedModel> getBdMocPreDedicatedList() {
    return bdMocPreDedicatedList;
  }

  public String getCallEnd() {
    return callEnd;
  }

  public void setCallEnd(String callEnd) {
    this.callEnd = callEnd;
  }

  public Double getCallDuration() {
    return callDuration;
  }

  public void setCallDuration(Double callDuration) {
    this.callDuration = callDuration;
  }

  public String getCallTotalActualSeconds() {
    return callTotalActualSeconds;
  }

  public void setCallTotalActualSeconds(String callTotalActualSeconds) {
    this.callTotalActualSeconds = callTotalActualSeconds;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getActiveSOBs() {
    return activeSOBs;
  }

  public void setActiveSOBs(String activeSOBs) {
    this.activeSOBs = activeSOBs;
  }

  public String getAccountGroups() {
    return accountGroups;
  }

  public void setAccountGroups(String accountGroups) {
    this.accountGroups = accountGroups;
  }

  public String getOtherPartyNumber() {
    return otherPartyNumber;
  }

  public void setOtherPartyNumber(String otherPartyNumber) {
    this.otherPartyNumber = otherPartyNumber;
  }

  public String getTrxCode() {
    return trxCode;
  }

  public void setTrxCode(String trxCode) {
    this.trxCode = trxCode;
  }

  public String getTrxType() {
    return trxType;
  }

  public void setTrxType(String trxType) {
    this.trxType = trxType;
  }

  public ArrayList<BdAccumlator> getAccumulators() {
    return accumulators;
  }

  public void setAccumulators(ArrayList<BdAccumlator> accumulators) {
    this.accumulators = accumulators;
  }

  public void setBdMocPreDedicatedList(Integer[] dedIDs, Double[] dediAfter, Double[] dediBefore) {
    BdMocPreDedicatedModel bdMocPreDedicatedModel = null;
    for (int i = 0; i < dedIDs.length; i++) {
      bdMocPreDedicatedModel = new BdMocPreDedicatedModel();
      if (dedIDs[i] == 0 && (dediBefore[i] != 0 || dediAfter[i] != 0)) {
        dedIDs[i] = i + 1;
      }
      bdMocPreDedicatedModel.setDedicatedAccountID(dedIDs[i]);
      bdMocPreDedicatedModel.setMultiDedicatedAccountBefore(dediBefore[i]);
      bdMocPreDedicatedModel.setMultiDedicatedAccountAfter(dediAfter[i]);
      bdMocPreDedicatedList.add(bdMocPreDedicatedModel);
    }
  }

  public double getRatedVolume() {
    return ratedVolume;
  }

  public void setRatedVolume(Double ratedVolume) {
    this.ratedVolume = ratedVolume;
  }

  public void setRatedVolume(double ratedVolume) {
    this.ratedVolume = ratedVolume;
  }

  public Date getDateTimeNew() {
    return dateTimeNew;
  }

  public void setDateTimeNew(Date dateTimeNew) {
    this.dateTimeNew = dateTimeNew;
  }

  public String getAdjustmentDescription() {
    return adjustmentDescription;
  }

  public void setAdjustmentDescription(String adjustmentDescription) {
    this.adjustmentDescription = adjustmentDescription;
  }
}
