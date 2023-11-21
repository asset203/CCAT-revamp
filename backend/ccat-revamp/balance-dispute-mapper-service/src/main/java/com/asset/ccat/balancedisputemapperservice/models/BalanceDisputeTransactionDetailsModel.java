package com.asset.ccat.balancedisputemapperservice.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.*;

public class BalanceDisputeTransactionDetailsModel implements Serializable, Comparable<Object> {


  private String type;
  private String subType;
  private String date = null;//dateStr
  private String callTime = null;
  private String regionCellSite = null;  //to be added as (Qebly - Bahary) or (In Region - Out of Region)   [region name is retrieved from a lookup table]
  private String totalActualSecs;
  private String callDurationInMin = null;
  private String internetUsage;           //internetUsage --  Rounded
  private String otherPartyNum = null;
  private String destination;             //opnumber
  private String callEnd = null;
  private String ratingGroup;
  private String cipipInCommunityID;
  private double amount; //amountStr
  private double inAccAft;
  private double inAccBef;
  private String adjustmentCode;
  private String adjustmentType;

  private String inDedAft;//inDedAftSimple
  private String inDedBef;//inDedBefSimple

  private String chargingSource; ////Amount deducted from MB and Ded. Accs  ,, ///Format will be -->>    MB=2.0,D1=2.0
  private String chargingAmount;
  private String freeSMS;                 //If subtype is MO/PP and XFile charge amount is 0  -- freeSMS will be 1
  private String accBefore;//accBeforeSimple
  private String accAfter;//accAfterSimple
  private String sobs;//sobsSimple //Service Offering Bits
  private String accountGroupBitIds;//accountGroupBitIdsSimple
  private String ratePlan = null; //Name of Rate Plan or Service Class to be added instead
  private int typeId;   //Bonus Adjustment or Adjustment
  private double debit;
  private double credit;


  @JsonIgnore
  private ArrayList<BdAdjustmentDedicatedModel> adjustmentDedicatedList;
  private Date dateTime = null;
  private double cost;                    //XFile charge amount


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


  public String getChargingSource() {
    return chargingSource;
  }

  public void setChargingSource(String chargingSource) {
    this.chargingSource = chargingSource;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getCallTime() {
    return callTime;
  }

  public void setCallTime(String callTime) {
    this.callTime = callTime;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public String getCallDurationInMin() {
    return callDurationInMin;
  }

  public void setCallDurationInMin(String callDurationInMin) {
    this.callDurationInMin = callDurationInMin;
  }

  public String getFreeSMS() {
    return freeSMS;
  }

  public void setFreeSMS(String freeSMS) {
    this.freeSMS = freeSMS;
  }

  public String getInDedAft() {
    return inDedAft;
  }

  public void setInDedAft(String inDedAft) {
    this.inDedAft = inDedAft;
  }

  public String getInDedBef() {
    return inDedBef;
  }

  public void setInDedBef(String inDedBef) {
    this.inDedBef = inDedBef;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public double getInAccAft() {
    return inAccAft;
  }

  public void setInAccAft(double inAccAft) {
    this.inAccAft = inAccAft;
    /*inAccAftAsStr = Double.toString(inAccAft);*/
  }

  public double getInAccBef() {
    return inAccBef;
  }

  public void setInAccBef(double inAccBef) {
    this.inAccBef = inAccBef;
    /*inAccBefAsStr = Double.toString(inAccBef);*/
  }

  public String getSubType() {
    return subType;
  }

  public void setSubType(String subType) {
    this.subType = subType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public String getAccountGroupBitIds() {
    return accountGroupBitIds;
  }

  public void setAccountGroupBitIds(String accountGroupBitIds) {
    this.accountGroupBitIds = accountGroupBitIds;
  }

  public String getSobs() {
    return sobs;
  }

  public void setSobs(String sobs) {
    this.sobs = sobs;
  }

  public String getCallEnd() {
    return callEnd;
  }

  public void setCallEnd(String callEnd) {
    this.callEnd = callEnd;
  }

  public String getChargingAmount() {
    return chargingAmount;
  }

  public void setChargingAmount(String chargingAmount) {
    this.chargingAmount = chargingAmount;
  }

  public String getInternetUsage() {
    return internetUsage;
  }

  public void setInternetUsage(String internetUsage) {
    this.internetUsage = internetUsage;
  }

  public String getOtherPartyNum() {
    return otherPartyNum;
  }

  public void setOtherPartyNum(String otherPartyNum) {
    this.otherPartyNum = otherPartyNum;
  }

  public String getRatePlan() {
    return ratePlan;
  }

  public void setRatePlan(String ratePlan) {
    this.ratePlan = ratePlan;
  }


  public String getRegionCellSite() {
    return regionCellSite;
  }

  public void setRegionCellSite(String regionCellSite) {
    this.regionCellSite = regionCellSite;
  }

  public String getTotalActualSecs() {
    return totalActualSecs;
  }

  public void setTotalActualSecs(String totalActualSecs) {
    this.totalActualSecs = totalActualSecs;
  }

  public String getAdjustmentCode() {
    return adjustmentCode;
  }

  public void setAdjustmentCode(String adjustmentCode) {
    this.adjustmentCode = adjustmentCode;
  }

  public String getAdjustmentType() {
    return adjustmentType;
  }

  public void setAdjustmentType(String adjustmentType) {
    this.adjustmentType = adjustmentType;
  }

  public ArrayList<BdAdjustmentDedicatedModel> getAdjustmentDedicatedList() {
    return adjustmentDedicatedList;
  }

  public void setAdjustmentDedicatedList(
      ArrayList<BdAdjustmentDedicatedModel> adjustmentDedicatedList) {
    this.adjustmentDedicatedList = adjustmentDedicatedList;
  }

  public double getCredit() {
    return credit;
  }

  public void setCredit(double credit) {
    this.credit = credit;
  }

  public double getDebit() {
    return debit;
  }

  public void setDebit(double debit) {
    this.debit = debit;
  }

  public String getAccAfter() {
    return accAfter;
  }

  public void setAccAfter(String accAfter) {
    this.accAfter = accAfter;
  }

  public String getAccBefore() {
    return accBefore;
  }

  public void setAccBefore(String accBefore) {
    this.accBefore = accBefore;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
//    this.amountStr = amount == 0 ? "" : String.valueOf(amount);
  }

  public HashMap<String, String> toMap(LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> map) {
    HashMap<String, String> model = new HashMap();
    if (Objects.nonNull(map.get("type"))) {
      model.put(map.get("type").getDisplayName(), this.type);
    }
    if (Objects.nonNull(map.get("subType"))) {
      model.put(map.get("subType").getDisplayName(), this.subType);
    }
    if (Objects.nonNull(map.get("date"))) {
      model.put(map.get("date").getDisplayName(), this.date);
    }
    if (Objects.nonNull(map.get("callTime"))) {
      model.put(map.get("callTime").getDisplayName(), this.callTime);
    }
    if (Objects.nonNull(map.get("regionCellSite"))) {
      model.put(map.get("regionCellSite").getDisplayName(), this.regionCellSite);
    }
    if (Objects.nonNull(map.get("totalActualSecs"))) {
      model.put(map.get("totalActualSecs").getDisplayName(), this.totalActualSecs);
    }
    if (Objects.nonNull(map.get("callDurationInMin"))) {
      model.put(map.get("callDurationInMin").getDisplayName(), this.callDurationInMin);
    }
    if (Objects.nonNull(map.get("internetUsage"))) {
      model.put(map.get("internetUsage").getDisplayName(), this.internetUsage);
    }
    if (Objects.nonNull(map.get("otherPartyNum"))) {
      model.put(map.get("otherPartyNum").getDisplayName(), this.otherPartyNum);
    }
    if (Objects.nonNull(map.get("destination"))) {
      model.put(map.get("destination").getDisplayName(), this.destination);
    }
    if (Objects.nonNull(map.get("cost"))) {
      model.put(map.get("cost").getDisplayName(), String.valueOf(this.cost));
    }
    if (Objects.nonNull(map.get("callEnd"))) {
      model.put(map.get("callEnd").getDisplayName(), this.callEnd);
    }
    if (Objects.nonNull(map.get("ratingGroup"))) {
      model.put(map.get("ratingGroup").getDisplayName(), this.ratingGroup);
    }
    if (Objects.nonNull(map.get("cipipInCommunityID"))) {
      model.put(map.get("cipipInCommunityID").getDisplayName(), this.cipipInCommunityID);
    }
    if (Objects.nonNull(map.get("amount"))) {
      model.put(map.get("amount").getDisplayName(), String.valueOf(this.amount));
    }
    if (Objects.nonNull(map.get("adjustmentCode"))) {
      model.put(map.get("adjustmentCode").getDisplayName(), this.adjustmentCode);
    }
    if (Objects.nonNull(map.get("adjustmentType"))) {
      model.put(map.get("adjustmentType").getDisplayName(), this.adjustmentType);
    }
    if (Objects.nonNull(map.get("inAccBef"))) {
      model.put(map.get("inAccBef").getDisplayName(), String.valueOf(this.inAccBef));
    }
    if (Objects.nonNull(map.get("inAccAft"))) {
      model.put(map.get("inAccAft").getDisplayName(), String.valueOf(this.inAccAft));
    }
    if (Objects.nonNull(map.get("inDedBef"))) {
      model.put(map.get("inDedBef").getDisplayName(), this.inDedBef);
    }
    if (Objects.nonNull(map.get("inDedAft"))) {
      model.put(map.get("inDedAft").getDisplayName(), this.inDedAft);
    }
    if (Objects.nonNull(map.get("chargingSource"))) {
      model.put(map.get("chargingSource").getDisplayName(), this.chargingSource);
    }
    if (Objects.nonNull(map.get("chargingAmount"))) {
      model.put(map.get("chargingAmount").getDisplayName(), this.chargingAmount);
    }
    if (Objects.nonNull(map.get("freeSMS"))) {
      model.put(map.get("freeSMS").getDisplayName(), this.freeSMS);
    }
    if (Objects.nonNull(map.get("accBefore"))) {
      model.put(map.get("accBefore").getDisplayName(), this.accBefore);
    }
    if (Objects.nonNull(map.get("accAfter"))) {
      model.put(map.get("accAfter").getDisplayName(), this.accAfter);
    }
    if (Objects.nonNull(map.get("sobs"))) {
      model.put(map.get("sobs").getDisplayName(), this.sobs);
    }
    if (Objects.nonNull(map.get("accountGroupBitIds"))) {
      model.put(map.get("accountGroupBitIds").getDisplayName(), this.accountGroupBitIds);
    }
    if (Objects.nonNull(map.get("ratePlan"))) {
      model.put(map.get("ratePlan").getDisplayName(), this.ratePlan);
    }

    return model;
  }

  public int compareTo(Object obj) {
    BalanceDisputeTransactionDetailsModel model = (BalanceDisputeTransactionDetailsModel) obj;
    int output = -1;
    try {
      output = model.getDateTime().compareTo(this.dateTime);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return output;
  }
}
