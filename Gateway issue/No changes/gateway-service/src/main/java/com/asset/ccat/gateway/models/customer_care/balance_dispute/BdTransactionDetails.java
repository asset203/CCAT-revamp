package com.asset.ccat.gateway.models.customer_care.balance_dispute;


import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class BdTransactionDetails implements Serializable, Comparable<Object> {

  private ArrayList<BdAdjustmentDedicatedModel> adjustmentDedicatedList;
  private int typeId;   //Bonus Adjustment or Adjustment
  private double debit;
  private double credit;
  private String roundedVolume;
  private String zn;
  private String type;
  private String subType;
  private String destination;             //opnumber
  private String styleString;
  private String dateStr = null;
  private String callTime = null;
  private Date dateTime = null;
  private String dateTimeStr = "";
  private String dateTimeStrNew = "";
  private Date dateTimeNew;
  private String ratePlan = null; //Name of Rate Plan or Service Class to be added instead
  private String regionCellSite = null;  //to be added as (Qebly - Bahary) or (In Region - Out of Region)   [region name is retrieved from a lookup table]
  private String callDurationInMin = null;
  private String callEnd = null;
  //Radwa Hamdi
  //added columns after call End
  private String ratingGroup;
  private String cipipInCommunityID;
  //////////////////////////////
  private String otherPartyNum = null;
  private String totalActualSecs;
  private double cost;                    //XFile charge amount
  private String costAsStr;               //Amount as string
  private String freeSMS;                 //If subtype is MO/PP and XFile charge amount is 0  -- freeSMS will be 1
  private String internetUsage;           //internetUsage --  Rounded
  private String sobs;                    //Service Offering Bits
  private String accountGroupBitIds;      //Account Group Bits
  private String chargingSource;          //Amount deducted from MB and Ded. Accs  ,, ///Format will be -->>    MB=2.0,D1=2.0
  private String chargingAmount;          //CR1505: Amount deducted from MB and Ded. Accs  ,, ///Format will be -->>    2.0,2.0
  private String sobsSimple;
  private String accountGroupBitIdsSimple;
  private String chargingSourceSimple;
  private String rechargeCards;           //Recharge Cards
  private double inAccAft;
  private double inAccBef;
  private String inAccAftAsStr = null;
  private String inAccBefAsStr = null;
  private String inDedAft;
  private String inDedBef;
  private String inAccAftAsStrSimple = null;
  private String inAccBefAsStrSimple = null;
  private String inDedAftSimple;
  private String inDedBefSimple;
  private double adjustment;
  private String adjustmentAsStr = null;
  private String adjustmentType;
  private String adjustmentCode;
  private String accumulators;
  private String trxType;
  private String trxCode;
  private String accBefore;
  private String accAfter;
  private String accBeforeSimple;
  private String accAfterSimple;
  private double amount;
  private String amountStr;

  public BdTransactionDetails() {
    adjustmentDedicatedList = new ArrayList<BdAdjustmentDedicatedModel>();
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

  public String getAccAfterSimple() {
    return accAfterSimple;
  }

  public void setAccAfterSimple(String accAfterSimple) {
    this.accAfterSimple = accAfterSimple;
  }

  public String getAccBeforeSimple() {
    return accBeforeSimple;
  }

  public void setAccBeforeSimple(String accBeforeSimple) {
    this.accBeforeSimple = accBeforeSimple;
  }

  public String getAccountGroupBitIdsSimple() {
    return accountGroupBitIdsSimple;
  }

  public void setAccountGroupBitIdsSimple(String accountGroupBitIdsSimple) {
    this.accountGroupBitIdsSimple = accountGroupBitIdsSimple;
  }

  public String getChargingSourceSimple() {
    return chargingSourceSimple;
  }

  public void setChargingSourceSimple(String chargingSourceSimple) {
    this.chargingSourceSimple = chargingSourceSimple;
  }

  public String getSobsSimple() {
    return sobsSimple;
  }

  public void setSobsSimple(String sobsSimple) {
    this.sobsSimple = sobsSimple;
  }

  public String getDateTimeStr() {
    return dateTimeStr;
  }

  public void setDateTimeStr(String dateTimeStr) throws ParseException {
    this.dateTimeStr = dateTimeStr;
//    String dateTimeFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_DATE_TIME_FORMAT)).getItemValue();
//    String dateFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_DATE_FORMAT)).getItemValue();
//    String timeFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_TIME_FORMAT)).getItemValue();
//
//    this.dateTime = CCUtilities.parseDate(dateTimeStr, dateTimeFormat);
//    this.dateStr = CCUtilities.formatDate(dateTime, dateFormat);
//    this.callTime = CCUtilities.formatDate(dateTime, timeFormat);
  }

  public String getInAccAftAsStrSimple() {
    return inAccAftAsStrSimple;
  }

  public void setInAccAftAsStrSimple(String inAccAftAsStrSimple) {
    this.inAccAftAsStrSimple = inAccAftAsStrSimple;
  }

  public String getInAccBefAsStrSimple() {
    return inAccBefAsStrSimple;
  }

  public void setInAccBefAsStrSimple(String inAccBefAsStrSimple) {
    this.inAccBefAsStrSimple = inAccBefAsStrSimple;
  }

  public String getInDedAftSimple() {
    return inDedAftSimple;
  }

  public void setInDedAftSimple(String inDedAftSimple) {
    this.inDedAftSimple = inDedAftSimple;
  }

  public String getInDedBefSimple() {
    return inDedBefSimple;
  }

  public void setInDedBefSimple(String inDedBefSimple) {
    this.inDedBefSimple = inDedBefSimple;
  }

  public String getDateTimeStrNew() {
    return dateTimeStrNew;
  }

  public void setDateTimeStrNew(String dateTimeStrNew) {
    this.dateTimeStrNew = dateTimeStrNew;
  }

  public Date getDateTimeNew() {
    return dateTimeNew;
  }

  public void setDateTimeNew(Date dateTimeNew) {
    this.dateTimeNew = dateTimeNew;
    this.dateTime = dateTimeNew;
//    String dateTimeFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_DATE_TIME_FORMAT)).getItemValue();
//    String dateFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_DATE_FORMAT)).getItemValue();
//    String timeFormat = ((ConfigurationModel) CsatProperties.configurations.get(
//        CSATConstants.BALANCE_DISPUTE_TIME_FORMAT)).getItemValue();
//
//    this.dateTimeStrNew = CCUtilities.formatDate(dateTimeNew, dateTimeFormat);
//    this.dateStr = CCUtilities.formatDate(dateTimeNew, dateFormat);
//    this.callTime = CCUtilities.formatDate(dateTimeNew, timeFormat);

  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getDateStr() {
    return dateStr;
  }

  public void setDateStr(String dateStr) {
    this.dateStr = dateStr;
  }

  public String getCallTime() {
    return callTime;
  }

  public void setCallTime(String callTime) {
    this.callTime = callTime;
  }

  public String getCostAsStr() {
    return costAsStr;
  }

  public void setCostAsStr(String costAsStr) {
    this.costAsStr = costAsStr;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
    this.costAsStr = cost == 0 ? "" : Double.toString(cost);
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
    inAccAftAsStr = Double.toString(inAccAft);
  }

  public double getInAccBef() {
    return inAccBef;
  }

  public void setInAccBef(double inAccBef) {
    this.inAccBef = inAccBef;
    inAccBefAsStr = Double.toString(inAccBef);
  }

  public String getInAccAftAsStr() {
    return inAccAftAsStr;
  }

  public void setInAccAftAsStr(String inAccAftAsStr) {
    this.inAccAftAsStr = inAccAftAsStr;
  }

  public String getInAccBefAsStr() {
    return inAccBefAsStr;
  }

  public void setInAccBefAsStr(String inAccBefAsStr) {
    this.inAccBefAsStr = inAccBefAsStr;
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
//    if (CSATConstants.BD_STYLES.containsKey(type)) {
//      styleString = CSATConstants.BD_STYLES.get(type);
//    }
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public double getAdjustment() {
    return adjustment;
  }

  public void setAdjustment(double adjustment) {
    this.adjustment = adjustment;
    adjustmentAsStr = Double.toString(adjustment);
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

  public String getChargingSource() {
    return chargingSource;
  }

  public void setChargingSource(String chargingSource) {
    this.chargingSource = chargingSource;
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

  public String getRechargeCards() {
    return rechargeCards;
  }

  public void setRechargeCards(String rechargeCards) {
    this.rechargeCards = rechargeCards;
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

  public String getAccumulators() {
    return accumulators;
  }

  public void setAccumulators(String accumulators) {
    this.accumulators = accumulators;
  }

  public String getStyleString() {
    return styleString;
  }

  public void setStyleString(String styleString) {
    this.styleString = styleString;
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

  public String getAdjustmentAsStr() {
    return adjustmentAsStr;
  }

  public void setAdjustmentAsStr(String adjustmentAsStr) {
    this.adjustmentAsStr = adjustmentAsStr;
  }

  public String getRoundedVolume() {
    return roundedVolume;
  }

  public void setRoundedVolume(String roundedVolume) {
    if (this.subType != null) {
      if (!this.subType.equals("Mobile Telephony")) {
        this.roundedVolume = roundedVolume;
      } else {
        if (roundedVolume != null && !roundedVolume.trim().equals("")) {
          this.roundedVolume = String.valueOf(
              Math.ceil((double) Double.parseDouble(roundedVolume) / 60));
        } else {
          this.roundedVolume = "";
        }
      }
    } else {
      this.roundedVolume = roundedVolume;
    }
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

  public String getZn() {
    return zn;
  }

  public void setZn(String zn) {
    this.zn = zn;
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
    this.amountStr = amount == 0 ? "" : String.valueOf(amount);
  }

  public String getAmountStr() {
    return amountStr;
  }

  public void setAmountStr(String amountStr) {
    this.amountStr = amountStr;
  }

  public int compareTo(Object obj) {
    BdTransactionDetails model = (BdTransactionDetails) obj;
    int output = -1;
    try {
      output = model.getDateTime().compareTo(this.dateTime);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return output;
  }

}
