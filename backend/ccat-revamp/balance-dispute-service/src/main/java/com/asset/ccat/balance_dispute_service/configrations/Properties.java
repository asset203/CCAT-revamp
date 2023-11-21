package com.asset.ccat.balance_dispute_service.configrations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class Properties {

  private String accessTokenKey;
  private long accessTokenValidity;
  private String lookupsServiceUrls;
  private String slGetMocPreFn;
  private String slGetAdjFn;
  private String getMocPreFnRaNew4;
  private String getLastDateFn;
  private String balanceDisputeDatabaseURL;
  private String balanceDisputeDatabaseUsername;
  private String balanceDisputeDatabasePassword;
  private String bdMapperServiceUrls;
  private Integer responseTimeout;
  private Integer readTimeout;
  private Integer writeTimeout;
  private Integer connectionTimeout;
  private String bdTemplatesPath;
  private String dbCalculationSheetName;
  private Integer hikariMaximumPoolSize;

  public String getAccessTokenKey() {
    return accessTokenKey;
  }

  public void setAccessTokenKey(String accessTokenKey) {
    this.accessTokenKey = accessTokenKey;
  }

  public long getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(long accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public String getLookupsServiceUrls() {
    return lookupsServiceUrls;
  }

  public void setLookupsServiceUrls(String lookupsServiceUrls) {
    this.lookupsServiceUrls = lookupsServiceUrls;
  }

  public String getSlGetMocPreFn() {
    return slGetMocPreFn;
  }

  public void setSlGetMocPreFn(String slGetMocPreFn) {
    this.slGetMocPreFn = slGetMocPreFn;
  }

  public String getSlGetAdjFn() {
    return slGetAdjFn;
  }

  public void setSlGetAdjFn(String slGetAdjFn) {
    this.slGetAdjFn = slGetAdjFn;
  }

  public String getGetMocPreFnRaNew4() {
    return getMocPreFnRaNew4;
  }

  public void setGetMocPreFnRaNew4(String getMocPreFnRaNew4) {
    this.getMocPreFnRaNew4 = getMocPreFnRaNew4;
  }

  public String getGetLastDateFn() {
    return getLastDateFn;
  }

  public void setGetLastDateFn(String getLastDateFn) {
    this.getLastDateFn = getLastDateFn;
  }

  public String getBalanceDisputeDatabaseURL() {
    return balanceDisputeDatabaseURL;
  }

  public void setBalanceDisputeDatabaseURL(String balanceDisputeDatabaseURL) {
    this.balanceDisputeDatabaseURL = balanceDisputeDatabaseURL;
  }

  public String getBalanceDisputeDatabaseUsername() {
    return balanceDisputeDatabaseUsername;
  }

  public void setBalanceDisputeDatabaseUsername(String balanceDisputeDatabaseUsername) {
    this.balanceDisputeDatabaseUsername = balanceDisputeDatabaseUsername;
  }

  public String getBalanceDisputeDatabasePassword() {
    return balanceDisputeDatabasePassword;
  }

  public void setBalanceDisputeDatabasePassword(String balanceDisputeDatabasePassword) {
    this.balanceDisputeDatabasePassword = balanceDisputeDatabasePassword;
  }

  public String getBdMapperServiceUrls() {
    return bdMapperServiceUrls;
  }

  public void setBdMapperServiceUrls(String bdMapperServiceUrls) {
    this.bdMapperServiceUrls = bdMapperServiceUrls;
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

  public Integer getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public String getBdTemplatesPath() {
    return bdTemplatesPath;
  }

  public void setBdTemplatesPath(String bdTemplatesPath) {
    this.bdTemplatesPath = bdTemplatesPath;
  }

  public String getDbCalculationSheetName() {
    return dbCalculationSheetName;
  }

  public void setDbCalculationSheetName(String dbCalculationSheetName) {
    this.dbCalculationSheetName = dbCalculationSheetName;
  }

  public Integer getHikariMaximumPoolSize() {
    return hikariMaximumPoolSize;
  }

  public void setHikariMaximumPoolSize(Integer hikariMaximumPoolSize) {
    this.hikariMaximumPoolSize = hikariMaximumPoolSize;
  }
}
