package com.asset.ccat.simulators_service.models;

/**
 * @author mahmoud.shehab
 */
public class AccountDetailsModel {

  private String subscriber;
  private String languageId;
  private String serviceClassId;
  private String adjustmentAmount;

  public AccountDetailsModel() {
  }


  public AccountDetailsModel(String subscriber, String languageId, String serviceClassId, String adjustmentAmount) {
    this.subscriber = subscriber;
    this.languageId = languageId;
    this.serviceClassId = serviceClassId;
    this.adjustmentAmount = adjustmentAmount;
  }

  public String getSubscriber() {
    return subscriber;
  }

  public void setSubscriber(String msisdn) {
    this.subscriber = msisdn;
  }

  public String getLanguageId() {
    return languageId;
  }

  public void setLanguageId(String languageId) {
    this.languageId = languageId;
  }

  public String getServiceClassId() {
    return serviceClassId;
  }

  public void setServiceClassId(String serviceClassId) {
    this.serviceClassId = serviceClassId;
  }

  public String getAdjustmentAmount() {
    return adjustmentAmount;
  }

  public void setAdjustmentAmount(String adjustmentAmount) {
    this.adjustmentAmount = adjustmentAmount;
  }
}
