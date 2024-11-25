/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.configurations;

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
  private String lookupsServiceUrls;
  private String chanCcat;
  private String getOffersSpName;
  private String nbaPlatform;
  private String getPromoListTriggerId;
  private String nbaDbPassword;
  private String nbaDbUrl;
  private String nbaDbUsername;
  private String nbaDbSchema;
  private String nbaOfferStatusNormal;
  private String nbaOfferStatusRejected;
  private String promoId;
  private String redeemOfferUrl;
  private String rejectOfferSpName;
  private String sendSmsUrl;
  private String triggerId;
  private String wlistId;
  private String tibcoGetOffersUrl;
  private String tibcoRedeemOfferUrl;
  private String tibcoGetOffersTriggerId;
  private String tibcoGetOffersAgentId;
  private String tibcoRedeemGiftStoreId;
  private String tibcoRedeemGiftChannelId;
  private String tibcoRedeemGiftCategory;

  private String tibcoSendSmsChannelId;
  private String tibcoGetOffersChannelId;
  private String tibcoSendSmsCategory;
  private String tibcoSendSmsShortCode;
  private String tibcoSendSmsUrl;
  private String tibcoAcceptLanguageHeader;
  private String tibcoApplicationUserHeader;
  private String tibcoApplicationPasswordHeader;


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

  public String getChanCcat() {
    return chanCcat;
  }

  public void setChanCcat(String chanCcat) {
    this.chanCcat = chanCcat;
  }

  public String getGetOffersSpName() {
    return getOffersSpName;
  }

  public void setGetOffersSpName(String getOffersSpName) {
    this.getOffersSpName = getOffersSpName;
  }

  public String getGetPromoListTriggerId() {
    return getPromoListTriggerId;
  }

  public void setGetPromoListTriggerId(String getPromoListTriggerId) {
    this.getPromoListTriggerId = getPromoListTriggerId;
  }

  public String getNbaDbPassword() {
    return nbaDbPassword;
  }

  public void setNbaDbPassword(String nbaDbPassword) {
    this.nbaDbPassword = nbaDbPassword;
  }

  public String getNbaDbUrl() {
    return nbaDbUrl;
  }

  public void setNbaDbUrl(String nbaDbUrl) {
    this.nbaDbUrl = nbaDbUrl;
  }

  public String getNbaDbUsername() {
    return nbaDbUsername;
  }

  public void setNbaDbUsername(String nbaDbUsername) {
    this.nbaDbUsername = nbaDbUsername;
  }

  public String getNbaDbSchema() {
    return nbaDbSchema;
  }

  public void setNbaDbSchema(String nbaDbSchema) {
    this.nbaDbSchema = nbaDbSchema;
  }

  public String getNbaOfferStatusNormal() {
    return nbaOfferStatusNormal;
  }

  public void setNbaOfferStatusNormal(String nbaOfferStatusNormal) {
    this.nbaOfferStatusNormal = nbaOfferStatusNormal;
  }

  public String getNbaOfferStatusRejected() {
    return nbaOfferStatusRejected;
  }

  public void setNbaOfferStatusRejected(String nbaOfferStatusRejected) {
    this.nbaOfferStatusRejected = nbaOfferStatusRejected;
  }

  public String getPromoId() {
    return promoId;
  }

  public void setPromoId(String promoId) {
    this.promoId = promoId;
  }

  public String getRedeemOfferUrl() {
    return redeemOfferUrl;
  }

  public void setRedeemOfferUrl(String redeemOfferUrl) {
    this.redeemOfferUrl = redeemOfferUrl;
  }

  public String getRejectOfferSpName() {
    return rejectOfferSpName;
  }

  public void setRejectOfferSpName(String rejectOfferSpName) {
    this.rejectOfferSpName = rejectOfferSpName;
  }

  public String getSendSmsUrl() {
    return sendSmsUrl;
  }

  public void setSendSmsUrl(String sendSmsUrl) {
    this.sendSmsUrl = sendSmsUrl;
  }

  public String getTriggerId() {
    return triggerId;
  }

  public void setTriggerId(String triggerId) {
    this.triggerId = triggerId;
  }

  public String getWlistId() {
    return wlistId;
  }

  public void setWlistId(String wlistId) {
    this.wlistId = wlistId;
  }

  public String getLookupsServiceUrls() {
    return lookupsServiceUrls;
  }

  public void setLookupsServiceUrls(String lookupsServiceUrls) {
    this.lookupsServiceUrls = lookupsServiceUrls;
  }

  public String getNbaPlatform() {
    return nbaPlatform;
  }

  public void setNbaPlatform(String nbaPlatform) {
    this.nbaPlatform = nbaPlatform;
  }

  public String getTibcoGetOffersUrl() {
    return tibcoGetOffersUrl;
  }

  public void setTibcoGetOffersUrl(String tibcoGetOffersUrl) {
    this.tibcoGetOffersUrl = tibcoGetOffersUrl;
  }

  public String getTibcoGetOffersTriggerId() {
    return tibcoGetOffersTriggerId;
  }

  public void setTibcoGetOffersTriggerId(String tibcoGetOffersTriggerId) {
    this.tibcoGetOffersTriggerId = tibcoGetOffersTriggerId;
  }

  public String getTibcoGetOffersAgentId() {
    return tibcoGetOffersAgentId;
  }

  public void setTibcoGetOffersAgentId(String tibcoGetOffersAgentId) {
    this.tibcoGetOffersAgentId = tibcoGetOffersAgentId;
  }

  public String getTibcoRedeemOfferUrl() {
    return tibcoRedeemOfferUrl;
  }

  public void setTibcoRedeemOfferUrl(String tibcoRedeemOfferUrl) {
    this.tibcoRedeemOfferUrl = tibcoRedeemOfferUrl;
  }

  public String getTibcoRedeemGiftStoreId() {
    return tibcoRedeemGiftStoreId;
  }

  public void setTibcoRedeemGiftStoreId(String tibcoRedeemGiftStoreId) {
    this.tibcoRedeemGiftStoreId = tibcoRedeemGiftStoreId;
  }

  public String getTibcoRedeemGiftChannelId() {
    return tibcoRedeemGiftChannelId;
  }

  public void setTibcoRedeemGiftChannelId(String tibcoRedeemGiftChannelId) {
    this.tibcoRedeemGiftChannelId = tibcoRedeemGiftChannelId;
  }

  public String getTibcoRedeemGiftCategory() {
    return tibcoRedeemGiftCategory;
  }

  public void setTibcoRedeemGiftCategory(String tibcoRedeemGiftCategory) {
    this.tibcoRedeemGiftCategory = tibcoRedeemGiftCategory;
  }

  public String getTibcoSendSmsChannelId() {
    return tibcoSendSmsChannelId;
  }

  public void setTibcoSendSmsChannelId(String tibcoSendSmsChannelId) {
    this.tibcoSendSmsChannelId = tibcoSendSmsChannelId;
  }

  public String getTibcoSendSmsCategory() {
    return tibcoSendSmsCategory;
  }

  public void setTibcoSendSmsCategory(String tibcoSendSmsCategory) {
    this.tibcoSendSmsCategory = tibcoSendSmsCategory;
  }

  public String getTibcoSendSmsShortCode() {
    return tibcoSendSmsShortCode;
  }

  public void setTibcoSendSmsShortCode(String tibcoSendSmsShortCode) {
    this.tibcoSendSmsShortCode = tibcoSendSmsShortCode;
  }

  public String getTibcoSendSmsUrl() {
    return tibcoSendSmsUrl;
  }

  public void setTibcoSendSmsUrl(String tibcoSendSmsUrl) {
    this.tibcoSendSmsUrl = tibcoSendSmsUrl;
  }

  public String getTibcoAcceptLanguageHeader() {
    return tibcoAcceptLanguageHeader;
  }

  public void setTibcoAcceptLanguageHeader(String tibcoAcceptLanguageHeader) {
    this.tibcoAcceptLanguageHeader = tibcoAcceptLanguageHeader;
  }

  public String getTibcoApplicationUserHeader() {
    return tibcoApplicationUserHeader;
  }

  public void setTibcoApplicationUserHeader(String tibcoApplicationUserHeader) {
    this.tibcoApplicationUserHeader = tibcoApplicationUserHeader;
  }

  public String getTibcoApplicationPasswordHeader() {
    return tibcoApplicationPasswordHeader;
  }

  public void setTibcoApplicationPasswordHeader(String tibcoApplicationPasswordHeader) {
    this.tibcoApplicationPasswordHeader = tibcoApplicationPasswordHeader;
  }

  public String getTibcoGetOffersChannelId() {
    return tibcoGetOffersChannelId;
  }

  public void setTibcoGetOffersChannelId(String tibcoGetOffersChannelId) {
    this.tibcoGetOffersChannelId = tibcoGetOffersChannelId;
  }
}
