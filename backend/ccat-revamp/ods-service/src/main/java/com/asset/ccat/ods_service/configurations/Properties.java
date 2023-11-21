package com.asset.ccat.ods_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nour.ihab
 */
@Component
@ConfigurationProperties
public class Properties {

    private String dssDateFormat;
    private String accessTokenKey;
    private long accessTokenValidity;
    private String encryptionKey;
    private String btivrProcedure;
    private String trafficBehaviorProcedure;
    private String ussdProcedure;
    private String lookupsServiceUrls;
    private String accountHistoryProcedure;
    private String accountHistoryArrayName;
    private String accountHistoryArrayType;
    private String accountHistoryErrorCode;
    private String accountHistoryErrorMessage;
    private String accountHistoryMaxRecords;

    //flex share history
    private Integer flexHistoryMaxRecords;
    private String retrieveFlexHistorySp;
    private Integer flexHistorySuccessCode;
    private String flexShareHistoryDateFormat;
    private Integer flexDefaultSearchPeriod;


    public String getDssDateFormat() {
        return dssDateFormat;
    }

    public void setDssDateFormat(String dssDateFormat) {
        this.dssDateFormat = dssDateFormat;
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

    public void setAccessTokenValidity(long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getBtivrProcedure() {
        return btivrProcedure;
    }

    public void setBtivrProcedure(String btivrProcedure) {
        this.btivrProcedure = btivrProcedure;
    }

    public String getTrafficBehaviorProcedure() {
        return trafficBehaviorProcedure;
    }

    public void setTrafficBehaviorProcedure(String trafficBehaviorProcedure) {
        this.trafficBehaviorProcedure = trafficBehaviorProcedure;
    }

    public String getUssdProcedure() {
        return ussdProcedure;
    }

    public void setUssdProcedure(String ussdProcedure) {
        this.ussdProcedure = ussdProcedure;
    }

    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public String getAccountHistoryProcedure() {
        return accountHistoryProcedure;
    }

    public void setAccountHistoryProcedure(String accountHistoryProcedure) {
        this.accountHistoryProcedure = accountHistoryProcedure;
    }

    public String getAccountHistoryArrayName() {
        return accountHistoryArrayName;
    }

    public void setAccountHistoryArrayName(String accountHistoryArrayName) {
        this.accountHistoryArrayName = accountHistoryArrayName;
    }

    public String getAccountHistoryArrayType() {
        return accountHistoryArrayType;
    }

    public void setAccountHistoryArrayType(String accountHistoryArrayType) {
        this.accountHistoryArrayType = accountHistoryArrayType;
    }

    public String getAccountHistoryErrorCode() {
        return accountHistoryErrorCode;
    }

    public void setAccountHistoryErrorCode(String accountHistoryErrorCode) {
        this.accountHistoryErrorCode = accountHistoryErrorCode;
    }

    public String getAccountHistoryErrorMessage() {
        return accountHistoryErrorMessage;
    }

    public void setAccountHistoryErrorMessage(String accountHistoryErrorMessage) {
        this.accountHistoryErrorMessage = accountHistoryErrorMessage;
    }

    public String getAccountHistoryMaxRecords() {
        return accountHistoryMaxRecords;
    }

    public void setAccountHistoryMaxRecords(String accountHistoryMaxRecords) {
        this.accountHistoryMaxRecords = accountHistoryMaxRecords;
    }

    public Integer getFlexHistoryMaxRecords() {
        return flexHistoryMaxRecords;
    }

    public void setFlexHistoryMaxRecords(Integer flexHistoryMaxRecords) {
        this.flexHistoryMaxRecords = flexHistoryMaxRecords;
    }

    public String getRetrieveFlexHistorySp() {
        return retrieveFlexHistorySp;
    }

    public void setRetrieveFlexHistorySp(String retrieveFlexHistorySp) {
        this.retrieveFlexHistorySp = retrieveFlexHistorySp;
    }

    public Integer getFlexHistorySuccessCode() {
        return flexHistorySuccessCode;
    }

    public void setFlexHistorySuccessCode(Integer flexHistorySuccessCode) {
        this.flexHistorySuccessCode = flexHistorySuccessCode;
    }

    public String getFlexShareHistoryDateFormat() {
        return flexShareHistoryDateFormat;
    }

    public void setFlexShareHistoryDateFormat(String flexShareHistoryDateFormat) {
        this.flexShareHistoryDateFormat = flexShareHistoryDateFormat;
    }

    public Integer getFlexDefaultSearchPeriod() {
        return flexDefaultSearchPeriod;
    }

    public void setFlexDefaultSearchPeriod(Integer flexDefaultSearchPeriod) {
        this.flexDefaultSearchPeriod = flexDefaultSearchPeriod;
    }
}
