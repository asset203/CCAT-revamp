/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mahmoud.shehab
 */
@Component
@ConfigurationProperties
public class Properties {

  private String accessTokenKey;
  private long accessTokenValidity;
  private String dateFormat;
  private String userManagementUrls;
  private String dynamicPageServiceUrls;
  private String airServiceUrls;
  private String nbaServiceUrls;
  private String ciServiceUrls;
  private String historyServiceUrls;
  private String lookupsServiceUrls;
  private String notificationServiceUrls;
  private String odsServiceUrls;
  private String cloudConfigUrls;
  private Integer subInstallBatchSize;
  private Integer subDisconnectBatchSize;
  private Integer maxFileUploadSize;
  private Integer connectionTimeout;
  private Integer responseTimeout;
  private Integer readTimeout;
  private Integer writeTimeout;

  private Boolean bidTempBlock;
  private String bidOrganizationId;

  private String ssoVfUrl;
  private Boolean ssoIntegration;

  private String msisdnRegex;

  private String procedureServiceUrls;
  private String msisdnPattern;
  private String passwordPattern;
  private Integer voucherSerialNumberLength;
  private Integer voucherNumberLength;
  private String balanceDisputeServiceUrls;

  private Integer reportsDefaultSearchPeriod;
  private Integer reportsMaxSearchPeriod;
  private Integer accountHistorySearchPeriod;
  private Integer accountHistoryMaxSearchPeriod;

  private Boolean nbaInterfaceSelector;

  private String filesDateTimeFormat;

  public String getFilesDateTimeFormat() {
    return filesDateTimeFormat;
  }

  public void setFilesDateTimeFormat(String filesDateTimeFormat) {
    this.filesDateTimeFormat = filesDateTimeFormat;
  }

  public String getProcedureServiceUrls() {
    return procedureServiceUrls;
  }

  public void setProcedureServiceUrls(String procedureServiceUrls) {
    this.procedureServiceUrls = procedureServiceUrls;
  }

  public String getDynamicPageServiceUrls() {
    return dynamicPageServiceUrls;
  }

  public void setDynamicPageServiceUrls(String dynamicPageServiceUrls) {
    this.dynamicPageServiceUrls = dynamicPageServiceUrls;
  }

  public String getAccessTokenKey() {
    return accessTokenKey;
  }

  public void setAccessTokenKey(String accessTokenKey) {
    this.accessTokenKey = accessTokenKey;
  }

  public long getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(long accessTokenValidityHours) {
    this.accessTokenValidity = accessTokenValidityHours;
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

  public String getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  public String getUserManagementUrls() {
    return userManagementUrls;
  }

  public void setUserManagementUrls(String userManagementUrls) {
    this.userManagementUrls = userManagementUrls;
  }

  public String getAirServiceUrls() {
    return airServiceUrls;
  }

  public void setAirServiceUrls(String airServiceUrls) {
    this.airServiceUrls = airServiceUrls;
  }

  public String getNbaServiceUrls() {
    return nbaServiceUrls;
  }

  public void setNbaServiceUrls(String nbaServiceUrls) {
    this.nbaServiceUrls = nbaServiceUrls;
  }

  public String getCiServiceUrls() {
    return ciServiceUrls;
  }

  public void setCiServiceUrls(String ciServiceUrls) {
    this.ciServiceUrls = ciServiceUrls;
  }

  public String getHistoryServiceUrls() {
    return historyServiceUrls;
  }

  public void setHistoryServiceUrls(String historyServiceUrls) {
    this.historyServiceUrls = historyServiceUrls;
  }

  public String getLookupsServiceUrls() {
    return lookupsServiceUrls;
  }

  public void setLookupsServiceUrls(String lookupsServiceUrls) {
    this.lookupsServiceUrls = lookupsServiceUrls;
  }

  public String getNotificationServiceUrls() {
    return notificationServiceUrls;
  }

  public void setNotificationServiceUrls(String notificationServiceUrls) {
    this.notificationServiceUrls = notificationServiceUrls;
  }

  public Integer getSubInstallBatchSize() {
    return subInstallBatchSize;
  }

  public void setSubInstallBatchSize(Integer subInstallBatchSize) {
    this.subInstallBatchSize = subInstallBatchSize;
  }

  public Integer getSubDisconnectBatchSize() {
    return subDisconnectBatchSize;
  }

  public void setSubDisconnectBatchSize(Integer subDisconnectBatchSize) {
    this.subDisconnectBatchSize = subDisconnectBatchSize;
  }

  public Boolean getBidTempBlock() {
    return bidTempBlock;
  }

  public void setBidTempBlock(Boolean bidTempBlock) {
    this.bidTempBlock = bidTempBlock;
  }

  public String getBidOrganizationId() {
    return bidOrganizationId;
  }

  public void setBidOrganizationId(String bidOrganizationId) {
    this.bidOrganizationId = bidOrganizationId;
  }

  public Integer getMaxFileUploadSize() {
    return maxFileUploadSize;
  }

  public void setMaxFileUploadSize(Integer maxFileUploadSize) {
    this.maxFileUploadSize = maxFileUploadSize;
  }

  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public Integer getResponseTimeout() {
    return responseTimeout;
  }

  public void setResponseTimeout(Integer responseTimeout) {
    this.responseTimeout = responseTimeout;
  }

  public Integer getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  public Integer getWriteTimeout() {
    return writeTimeout;
  }

  public void setWriteTimeout(Integer writeTimeout) {
    this.writeTimeout = writeTimeout;
  }

  public String getOdsServiceUrls() {
    return odsServiceUrls;
  }

  public void setOdsServiceUrls(String odsServiceUrls) {
    this.odsServiceUrls = odsServiceUrls;
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

  public String getMsisdnRegex() {
    return msisdnRegex;
  }

  public void setMsisdnRegex(String msisdnRegex) {
    this.msisdnRegex = msisdnRegex;
  }

  public String getCloudConfigUrls() {
    return cloudConfigUrls;
  }

  public void setCloudConfigUrls(String cloudConfigUrls) {
    this.cloudConfigUrls = cloudConfigUrls;
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

  public String getBalanceDisputeServiceUrls() {
    return balanceDisputeServiceUrls;
  }

  public void setBalanceDisputeServiceUrls(String balanceDisputeServiceUrls) {
    this.balanceDisputeServiceUrls = balanceDisputeServiceUrls;
  }

  public Integer getReportsDefaultSearchPeriod() {
    return reportsDefaultSearchPeriod;
  }

  public void setReportsDefaultSearchPeriod(Integer reportsDefaultSearchPeriod) {
    this.reportsDefaultSearchPeriod = reportsDefaultSearchPeriod;
  }

  public Integer getReportsMaxSearchPeriod() {
    return reportsMaxSearchPeriod;
  }

  public void setReportsMaxSearchPeriod(Integer reportsMaxSearchPeriod) {
    this.reportsMaxSearchPeriod = reportsMaxSearchPeriod;
  }

  public Boolean getNbaInterfaceSelector() {
    return nbaInterfaceSelector;
  }

  public void setNbaInterfaceSelector(Boolean nbaInterfaceSelector) {
    this.nbaInterfaceSelector = nbaInterfaceSelector;
  }
}
