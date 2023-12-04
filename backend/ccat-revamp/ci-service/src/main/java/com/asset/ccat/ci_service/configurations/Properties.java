package com.asset.ccat.ci_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nour.ihab
 */
@Component
@ConfigurationProperties
public class Properties {

    private String ccatDateFormat;
    private String ccatDateTimeFormat;
    private String ciDateFormat;
    private String getPreProfileUrl;
    private String accessTokenKey;
    private long accessTokenValidity;
    private String lookupsServiceUrls;
    private Long responseTimeout;
    //Add by Wael
    private String scConversionUrl;

    private String mainProductsFetchUrl;
    //Added By Ihab
    private String pvbpCheckSubscriptionUrl;
    private String pvbpUnsubscribeUrl;
    private String pvbpSubscribeUrl;
    private String userManagementUrls;

    private String mainProductsXApiKey;

    private String prepaidVBP;
    private String flexShareUrl;
    private String channelIdFlex;
    private String fsSuccessCode;

    private String superFlexCiSuccessCodes;
    private String superFlexAddonOptin;
    private String superFlexAddonOptout;
    private String superFlexThresholdOptin;
    private String superFlexThresholdOptout;
    private String superFlexThresholdStop;
    private String superFlexDeactivateStopMi;

    //    private Integer scFriendsScCode;
//    private Integer sc7AkawyExtraScCode;
//    private Integer sc7AkawyBokraScCode;
//    private Integer scInvalidValue;
//    private Integer sc7AkawyExtraMbFactor;
//    private String scDa129ClearanceServiceId;
//    private String scDa1ClearanceServiceId;
//    private String scDaClearanceUrl;
//    private String scAs7AbPlusOptoutUrl;
    public String getCcatDateFormat() {
        return ccatDateFormat;
    }

    public void setCcatDateFormat(String ccatDateFormat) {
        this.ccatDateFormat = ccatDateFormat;
    }

    public String getCcatDateTimeFormat() {
        return ccatDateTimeFormat;
    }

    public void setCcatDateTimeFormat(String ccatDateTimeFormat) {
        this.ccatDateTimeFormat = ccatDateTimeFormat;
    }

    public String getCiDateFormat() {
        return ciDateFormat;
    }

    public void setCiDateFormat(String ciDateFormat) {
        this.ciDateFormat = ciDateFormat;
    }

    public String getGetPreProfileUrl() {
        return getPreProfileUrl;
    }

    public void setGetPreProfileUrl(String getPreProfileUrl) {
        this.getPreProfileUrl = getPreProfileUrl;
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

    public String getScConversionUrl() {
        return scConversionUrl;
    }

    public void setScConversionUrl(String scConversionUrl) {
        this.scConversionUrl = scConversionUrl;
    }

    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public String getMainProductsFetchUrl() {
        return mainProductsFetchUrl;
    }

    public void setMainProductsFetchUrl(String mainProductsFetchUrl) {
        this.mainProductsFetchUrl = mainProductsFetchUrl;
    }

    public Long getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(Long responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public String getPvbpCheckSubscriptionUrl() {
        return pvbpCheckSubscriptionUrl;
    }

    public void setPvbpCheckSubscriptionUrl(String pvbpCheckSubscriptionUrl) {
        this.pvbpCheckSubscriptionUrl = pvbpCheckSubscriptionUrl;
    }

    public String getPvbpUnsubscribeUrl() {
        return pvbpUnsubscribeUrl;
    }

    public void setPvbpUnsubscribeUrl(String pvbpUnsubscribeUrl) {
        this.pvbpUnsubscribeUrl = pvbpUnsubscribeUrl;
    }

    public String getPvbpSubscribeUrl() {
        return pvbpSubscribeUrl;
    }

    public void setPvbpSubscribeUrl(String pvbpSubscribeUrl) {
        this.pvbpSubscribeUrl = pvbpSubscribeUrl;
    }

    public String getUserManagementUrls() {
        return userManagementUrls;
    }

    public void setUserManagementUrls(String userManagementUrls) {
        this.userManagementUrls = userManagementUrls;
    }

    public String getMainProductsXApiKey() {
        return mainProductsXApiKey;
    }

    public void setMainProductsXApiKey(String mainProductsXApiKey) {
        this.mainProductsXApiKey = mainProductsXApiKey;
    }

    public String getFlexShareUrl() {
        return flexShareUrl;
    }

    public void setFlexShareUrl(String flexShareUrl) {
        this.flexShareUrl = flexShareUrl;
    }

    public String getChannelIdFlex() {
        return channelIdFlex;
    }

    public void setChannelIdFlex(String channelIdFlex) {
        this.channelIdFlex = channelIdFlex;
    }

    public String getFsSuccessCode() {
        return fsSuccessCode;
    }

    public void setFsSuccessCode(String fsSuccessCode) {
        this.fsSuccessCode = fsSuccessCode;
    }

    public String getPrepaidVBP() {
        return prepaidVBP;
    }

    public void setPrepaidVBP(String prepaidVBP) {
        this.prepaidVBP = prepaidVBP;
    }

    public String getSuperFlexCiSuccessCodes() {
        return superFlexCiSuccessCodes;
    }

    public void setSuperFlexCiSuccessCodes(String superFlexCiSuccessCodes) {
        this.superFlexCiSuccessCodes = superFlexCiSuccessCodes;
    }

    public String getSuperFlexAddonOptin() {
        return superFlexAddonOptin;
    }

    public void setSuperFlexAddonOptin(String superFlexAddonOptin) {
        this.superFlexAddonOptin = superFlexAddonOptin;
    }

    public String getSuperFlexAddonOptout() {
        return superFlexAddonOptout;
    }

    public void setSuperFlexAddonOptout(String superFlexAddonOptout) {
        this.superFlexAddonOptout = superFlexAddonOptout;
    }

    public String getSuperFlexThresholdOptin() {
        return superFlexThresholdOptin;
    }

    public void setSuperFlexThresholdOptin(String superFlexThresholdOptin) {
        this.superFlexThresholdOptin = superFlexThresholdOptin;
    }

    public String getSuperFlexThresholdOptout() {
        return superFlexThresholdOptout;
    }

    public void setSuperFlexThresholdOptout(String superFlexThresholdOptout) {
        this.superFlexThresholdOptout = superFlexThresholdOptout;
    }

    public String getSuperFlexThresholdStop() {
        return superFlexThresholdStop;
    }

    public void setSuperFlexThresholdStop(String superFlexThresholdStop) {
        this.superFlexThresholdStop = superFlexThresholdStop;
    }

    public String getSuperFlexDeactivateStopMi() {
        return superFlexDeactivateStopMi;
    }

    public void setSuperFlexDeactivateStopMi(String superFlexDeactivateStopMi) {
        this.superFlexDeactivateStopMi = superFlexDeactivateStopMi;
    }
}
