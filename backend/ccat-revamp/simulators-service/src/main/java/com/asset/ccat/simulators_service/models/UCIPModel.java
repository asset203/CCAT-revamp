package com.asset.ccat.simulators_service.models;

/**
 * @author mahmoud.shehab
 */
public class UCIPModel {

  private String msisdn;
  private String languageId;
  private String serviceClassId;
  private String adjustmentAmount;
  private String method;
  private String path;

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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
