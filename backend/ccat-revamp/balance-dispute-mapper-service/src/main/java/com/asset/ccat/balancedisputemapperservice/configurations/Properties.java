package com.asset.ccat.balancedisputemapperservice.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
@ConfigurationProperties
public class Properties {
    private String accessTokenKey;
    private String lookupsServiceUrls;
    private String userManagementUrls;
    private String dateTimeFormat;
    private String dateFormat;
    private String timeFormat;
    private Long responseTimeout;
    private String destinationMaskedKeys;
    private String maskValue;
    private String zoneWhiteListKeys;
    private String smsType;
    private String internetType;
    private String internetPackages;
    private Integer maxRetrievalRecords;

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Long getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(Long responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public String getDestinationMaskedKeys() {
        return destinationMaskedKeys;
    }

    public void setDestinationMaskedKeys(String destinationMaskedKeys) {
        this.destinationMaskedKeys = destinationMaskedKeys;
    }

    public String getMaskValue() {
        return maskValue;
    }

    public void setMaskValue(String maskValue) {
        this.maskValue = maskValue;
    }

    public String getZoneWhiteListKeys() {
        return zoneWhiteListKeys;
    }

    public void setZoneWhiteListKeys(String zoneWhiteListKeys) {
        this.zoneWhiteListKeys = zoneWhiteListKeys;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getInternetType() {
        return internetType;
    }

    public void setInternetType(String internetType) {
        this.internetType = internetType;
    }

    public String getInternetPackages() {
        return internetPackages;
    }

    public void setInternetPackages(String internetPackages) {
        this.internetPackages = internetPackages;
    }

    public Integer getMaxRetrievalRecords() {
        return maxRetrievalRecords;
    }

    public void setMaxRetrievalRecords(Integer maxRetrievalRecords) {
        this.maxRetrievalRecords = maxRetrievalRecords;
    }

    public String getUserManagementUrls() {
        return userManagementUrls;
    }

    public void setUserManagementUrls(String userManagementUrls) {
        this.userManagementUrls = userManagementUrls;
    }
}
