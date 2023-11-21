package com.asset.ccat.procedureservice.configrations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties
public class Properties {

    private String accessTokenKey;
    private long accessTokenValidity;
    private String lookupsServiceUrls;

    private String voucherPgDbUrl;

    private String voucherPgDbUsername;

    private String voucherPgDbPassword;

    private String voucherPgDbProcedureName;

    private String flexShareDbUrl;
    private String flexShareDbUsername;
    private String flexShareDbPassword;

    private String flexShareInquiryStoredProcedureName;
    private String flexShareUpdateStoredProcedureName;
    private Integer flexSharePromoIdIn;
    private Integer flexShareChannelIdIn;
    private Integer flexShareTransTypeIn;

    private String flexShareMsisdnPrefix;

    private Long responseTimeout;

    public Long getResponseTimeout() {
        return responseTimeout;
    }

    public void setResponseTimeout(Long responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public String getVoucherPgDbUrl() {
        return voucherPgDbUrl;
    }

    public void setVoucherPgDbUrl(String voucherPgDbUrl) {
        this.voucherPgDbUrl = voucherPgDbUrl;
    }

    public String getVoucherPgDbUsername() {
        return voucherPgDbUsername;
    }

    public void setVoucherPgDbUsername(String voucherPgDbUsername) {
        this.voucherPgDbUsername = voucherPgDbUsername;
    }

    public String getVoucherPgDbPassword() {
        return voucherPgDbPassword;
    }

    public void setVoucherPgDbPassword(String voucherPgDbPassword) {
        this.voucherPgDbPassword = voucherPgDbPassword;
    }

    public String getVoucherPgDbProcedureName() {
        return voucherPgDbProcedureName;
    }

    public void setVoucherPgDbProcedureName(String voucherPgDbProcedureName) {
        this.voucherPgDbProcedureName = voucherPgDbProcedureName;
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


    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public String getFlexShareDbUrl() {
        return flexShareDbUrl;
    }

    public void setFlexShareDbUrl(String flexShareDbUrl) {
        this.flexShareDbUrl = flexShareDbUrl;
    }

    public String getFlexShareDbUsername() {
        return flexShareDbUsername;
    }

    public void setFlexShareDbUsername(String flexShareDbUsername) {
        this.flexShareDbUsername = flexShareDbUsername;
    }

    public String getFlexShareDbPassword() {
        return flexShareDbPassword;
    }

    public void setFlexShareDbPassword(String flexShareDbPassword) {
        this.flexShareDbPassword = flexShareDbPassword;
    }

    public String getFlexShareInquiryStoredProcedureName() {
        return flexShareInquiryStoredProcedureName;
    }

    public void setFlexShareInquiryStoredProcedureName(String flexShareInquiryStoredProcedureName) {
        this.flexShareInquiryStoredProcedureName = flexShareInquiryStoredProcedureName;
    }

    public String getFlexShareUpdateStoredProcedureName() {
        return flexShareUpdateStoredProcedureName;
    }

    public void setFlexShareUpdateStoredProcedureName(String flexShareUpdateStoredProcedureName) {
        this.flexShareUpdateStoredProcedureName = flexShareUpdateStoredProcedureName;
    }

    public Integer getFlexSharePromoIdIn() {
        return flexSharePromoIdIn;
    }

    public void setFlexSharePromoIdIn(Integer flexSharePromoIdIn) {
        this.flexSharePromoIdIn = flexSharePromoIdIn;
    }

    public Integer getFlexShareChannelIdIn() {
        return flexShareChannelIdIn;
    }

    public void setFlexShareChannelIdIn(Integer flexShareChannelIdIn) {
        this.flexShareChannelIdIn = flexShareChannelIdIn;
    }

    public Integer getFlexShareTransTypeIn() {
        return flexShareTransTypeIn;
    }

    public void setFlexShareTransTypeIn(Integer flexShareTransTypeIn) {
        this.flexShareTransTypeIn = flexShareTransTypeIn;
    }

    public String getFlexShareMsisdnPrefix() {
        return flexShareMsisdnPrefix;
    }

    public void setFlexShareMsisdnPrefix(String flexShareMsisdnPrefix) {
        this.flexShareMsisdnPrefix = flexShareMsisdnPrefix;
    }
}
