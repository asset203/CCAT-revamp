package com.asset.ccat.air_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author mahmoud.shehab
 */
@Component
@ConfigurationProperties
public class Properties {

    //general
    private String accessTokenKey;
    private long accessTokenValidity;
    private String lookupsServiceUrls;

    //AIR properties
    private String airCommandsPath;
    private String airDateFormatNew;
    private String airDateFormat;
    private String airDateFormatGmt;
    private Long airTimeout;
    private Boolean applyPamFlag;
    private String ccatDateFormat;
    private String ccatExpiryDate;
    private String ccatExpiryTimeStamp;
    private Boolean isCs5;
    private String originHostName;
    private String originNodeType;
    private Integer pamClassId;
    private Integer pamServiceId;
    private Integer pamScheduleId;
    private Integer sdDisconnectScCode; // 10
    private String midnightCounter;
    //Added By Ihab
    private String userManagementUrls;
    private Long responseTimeout;

    //TODO
    //private Boolean isHistoryEnabled;
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

    public String getAirCommandsPath() {
        return airCommandsPath;
    }

    public void setAirCommandsPath(String airCommandsPath) {
        this.airCommandsPath = airCommandsPath;
    }

    public String getOriginHostName() {
        return originHostName;
    }

    public void setOriginHostName(String originHostName) {
        this.originHostName = originHostName;
    }

    public String getOriginNodeType() {
        return originNodeType;
    }

    public void setOriginNodeType(String originNodeType) {
        this.originNodeType = originNodeType;
    }

    public String getAirDateFormatNew() {
        return airDateFormatNew;
    }

    public void setAirDateFormatNew(String airDateFormatNew) {
        this.airDateFormatNew = airDateFormatNew;
    }

    public String getCcatDateFormat() {
        return ccatDateFormat;
    }

    public void setCcatDateFormat(String ccatDateFormat) {
        this.ccatDateFormat = ccatDateFormat;
    }

    public String getCcatExpiryDate() {
        return ccatExpiryDate;
    }

    public void setCcatExpiryDate(String ccatExpiryDate) {
        this.ccatExpiryDate = ccatExpiryDate;
    }

    public String getCcatExpiryTimeStamp() {
        return ccatExpiryTimeStamp;
    }

    public void setCcatExpiryTimeStamp(String ccatExpiryTimeStamp) {
        this.ccatExpiryTimeStamp = ccatExpiryTimeStamp;
    }

    public String getAirDateFormat() {
        return airDateFormat;
    }

    public void setAirDateFormat(String airDateFormat) {
        this.airDateFormat = airDateFormat;
    }

    public String getAirDateFormatGmt() {
        return airDateFormatGmt;
    }

    public void setAirDateFormatGmt(String airDateFormatGmt) {
        this.airDateFormatGmt = airDateFormatGmt;
    }

    public Boolean getIsCs5() {
        return isCs5;
    }

    public void setIsCs5(Boolean isCs5) {
        this.isCs5 = isCs5;
    }

    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public Boolean getApplyPamFlag() {
        return applyPamFlag;
    }

    public void setApplyPamFlag(Boolean applyPamFlag) {
        this.applyPamFlag = applyPamFlag;
    }

    public Integer getPamClassId() {
        return pamClassId;
    }

    public void setPamClassId(Integer pamClassId) {
        this.pamClassId = pamClassId;
    }

    public Integer getPamServiceId() {
        return pamServiceId;
    }

    public void setPamServiceId(Integer pamServiceId) {
        this.pamServiceId = pamServiceId;
    }

    public Integer getPamScheduleId() {
        return pamScheduleId;
    }

    public void setPamScheduleId(Integer pamScheduleId) {
        this.pamScheduleId = pamScheduleId;
    }

    public Long getAirTimeout() {
        return airTimeout;
    }

    public void setAirTimeout(Long airTimeout) {
        this.airTimeout = airTimeout;
    }

    public Integer getSdDisconnectScCode() {
        return sdDisconnectScCode;
    }

    public void setSdDisconnectScCode(Integer sdDisconnectScCode) {
        this.sdDisconnectScCode = sdDisconnectScCode;
    }

    public String getMidnightCounter() {
        return midnightCounter;
    }

    public void setMidnightCounter(String midnightCounter) {
        this.midnightCounter = midnightCounter;
    }

    public String getUserManagementUrls() {
        return userManagementUrls;
    }

    public void setUserManagementUrls(String userManagementUrls) {
        this.userManagementUrls = userManagementUrls;
    }

    public Long getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(Long responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

}
