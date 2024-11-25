/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.users;

/**
 * @author wael.mohamed
 */
public class LiteLoginModel {

  private String ssoVfUrl;
  private Boolean ssoIntegration;
  private String msisdnPattern;
  private String passwordPattern;
  private Integer voucherSerialNumberLength;
  private Integer voucherNumberLength;
  private Integer reportDefaultSearchPeriod;
  private Integer reportMaxSearchPeriod;
  private Integer accountHistorySearchPeriod;
private  Integer accountHistoryMaxSearchPeriod;
  private Boolean nbaInterfaceSelector;

  public LiteLoginModel() {
  }

  public LiteLoginModel(String ssoVfUrl, Boolean ssoIntegration,
      String msisdnPattern, String passwordPattern,
      Integer voucherSerialNumberLength, Integer voucherNumberLength,
      Integer reportDefaultSearchPeriod, Integer reportMaxSearchPeriod,
                        Integer accountHistorySearchPeriod,
                        Integer accountHistoryMaxSearchPeriod,
                        Boolean nbaInterfaceSelector) {
    this.ssoVfUrl = ssoVfUrl;
    this.ssoIntegration = ssoIntegration;
    this.msisdnPattern = msisdnPattern;
    this.passwordPattern = passwordPattern;
    this.voucherSerialNumberLength = voucherSerialNumberLength;
    this.voucherNumberLength = voucherNumberLength;
    this.reportDefaultSearchPeriod = reportDefaultSearchPeriod;
    this.reportMaxSearchPeriod = reportMaxSearchPeriod;
    this.accountHistorySearchPeriod = accountHistorySearchPeriod;
    this.accountHistoryMaxSearchPeriod = accountHistoryMaxSearchPeriod;

    this.nbaInterfaceSelector = nbaInterfaceSelector;
  }

  public String getSsoVfUrl() {
    return ssoVfUrl;
  }

  public void setSsoVfUrl(String ssoVfUrl) {
    this.ssoVfUrl = ssoVfUrl;
  }

  public Boolean getSsoIntegration() {
    return ssoIntegration;
  }

  public void setSsoIntegration(Boolean ssoIntegration) {
    this.ssoIntegration = ssoIntegration;
  }

  public String getMsisdnPattern() {
    return msisdnPattern;
  }

  public void setMsisdnPattern(String msisdnPattern) {
    this.msisdnPattern = msisdnPattern;
  }

  public String getPasswordPattern() {
    return passwordPattern;
  }

  public void setPasswordPattern(String passwordPattern) {
    this.passwordPattern = passwordPattern;
  }

  public Integer getVoucherSerialNumberLength() {
    return voucherSerialNumberLength;
  }

  public void setVoucherSerialNumberLength(Integer voucherSerialNumberLength) {
    this.voucherSerialNumberLength = voucherSerialNumberLength;
  }

  public Integer getVoucherNumberLength() {
    return voucherNumberLength;
  }

  public void setVoucherNumberLength(Integer voucherNumberLength) {
    this.voucherNumberLength = voucherNumberLength;
  }

  public Integer getReportDefaultSearchPeriod() {
    return reportDefaultSearchPeriod;
  }

  public void setReportDefaultSearchPeriod(Integer reportDefaultSearchPeriod) {
    this.reportDefaultSearchPeriod = reportDefaultSearchPeriod;
  }

  public Integer getReportMaxSearchPeriod() {
    return reportMaxSearchPeriod;
  }

  public void setReportMaxSearchPeriod(Integer reportMaxSearchPeriod) {
    this.reportMaxSearchPeriod = reportMaxSearchPeriod;
  }

  public Boolean getNbaInterfaceSelector() {
    return nbaInterfaceSelector;
  }

  public void setNbaInterfaceSelector(Boolean nbaInterfaceSelector) {
    this.nbaInterfaceSelector = nbaInterfaceSelector;
  }

  public Integer getAccountHistorySearchPeriod() {
    return accountHistorySearchPeriod;
  }

  public void setAccountHistorySearchPeriod(Integer accountHistorySearchPeriod) {
    this.accountHistorySearchPeriod = accountHistorySearchPeriod;
  }

  public Integer getAccountHistoryMaxSearchPeriod() {
    return accountHistoryMaxSearchPeriod;
  }

  public void setAccountHistoryMaxSearchPeriod(Integer accountHistoryMaxSearchPeriod) {
    this.accountHistoryMaxSearchPeriod = accountHistoryMaxSearchPeriod;
  }

  @Override
  public String toString() {
    return "LiteLoginModel{" +
            "ssoVfUrl='" + ssoVfUrl + '\'' +
            ", ssoIntegration=" + ssoIntegration +
            ", msisdnPattern='" + msisdnPattern + '\'' +
            ", passwordPattern='" + passwordPattern + '\'' +
            ", voucherSerialNumberLength=" + voucherSerialNumberLength +
            ", voucherNumberLength=" + voucherNumberLength +
            ", reportDefaultSearchPeriod=" + reportDefaultSearchPeriod +
            ", reportMaxSearchPeriod=" + reportMaxSearchPeriod +
            ", accountHistorySearchPeriod=" + accountHistorySearchPeriod +
            ", accountHistoryMaxSearchPeriod=" + accountHistoryMaxSearchPeriod +
            ", nbaInterfaceSelector=" + nbaInterfaceSelector +
            '}';
  }
}
