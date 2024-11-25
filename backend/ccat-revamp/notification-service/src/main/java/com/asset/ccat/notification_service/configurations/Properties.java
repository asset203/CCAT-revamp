package com.asset.ccat.notification_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
@ConfigurationProperties
public class Properties {

    private String accessTokenKey;
    private long accessTokenValidity;
    private String smsSrcEndcoding;
    private String smsDestEndcoding;
    private String sendSmsProcedureName;
    private Integer csSmsIntegration;
    private Integer connTimeOut;
    private Integer messageType;
    private Integer moduleId;
    private Integer originatorType;
    private String csUrl;
    private String doNotApply;
    private String contentType;
    private String serviceName;
    private String servicePassword;
    private String msgPriority;
    private String smsOriginator;
    private String lookupsServiceUrls;
    private Integer readTimeOut;
    private Integer csArabicLanguageId;
    private Integer csEnglishLanguageId;
    private Integer defaultLanguage;

    public String getLookupsServiceUrls() {
        return lookupsServiceUrls;
    }

    public void setLookupsServiceUrls(String lookupsServiceUrls) {
        this.lookupsServiceUrls = lookupsServiceUrls;
    }

    public String getSmsOriginator() {
        return smsOriginator;
    }

    public void setSmsOriginator(String smsOriginator) {
        this.smsOriginator = smsOriginator;
    }

    public String getMsgPriority() {
        return msgPriority;
    }

    public void setMsgPriority(String msgPriority) {
        this.msgPriority = msgPriority;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDoNotApply() {
        return doNotApply;
    }

    public void setDoNotApply(String doNotApply) {
        this.doNotApply = doNotApply;
    }

    public String getCsUrl() {
        return csUrl;
    }

    public void setCsUrl(String csUrl) {
        this.csUrl = csUrl;
    }

    public Integer getOriginatorType() {
        return originatorType;
    }

    public void setOriginatorType(Integer originatorType) {
        this.originatorType = originatorType;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(Integer readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public Integer getConnTimeOut() {
        return connTimeOut;
    }

    public void setConnTimeOut(Integer connTimeOut) {
        this.connTimeOut = connTimeOut;
    }

    public Integer getCsSmsIntegration() {
        return csSmsIntegration;
    }

    public void setCsSmsIntegration(Integer csSmsIntegration) {
        this.csSmsIntegration = csSmsIntegration;
    }

    public String getSendSmsProcedureName() {
        return sendSmsProcedureName;
    }

    public void setSendSmsProcedureName(String sendSmsProcedureName) {
        this.sendSmsProcedureName = sendSmsProcedureName;
    }


    public Integer getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(Integer defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
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

    public String getSmsSrcEndcoding() {
        return smsSrcEndcoding;
    }

    public void setSmsSrcEndcoding(String smsSrcEndcoding) {
        this.smsSrcEndcoding = smsSrcEndcoding;
    }

    public String getSmsDestEndcoding() {
        return smsDestEndcoding;
    }

    public void setSmsDestEndcoding(String smsDestEndcoding) {
        this.smsDestEndcoding = smsDestEndcoding;
    }

    public String getServicePassword() {
        return servicePassword;
    }

    public void setServicePassword(String servicePassword) {
        this.servicePassword = servicePassword;
    }

    public Integer getCsArabicLanguageId() {
        return csArabicLanguageId;
    }

    public void setCsArabicLanguageId(Integer csArabicLanguageId) {
        this.csArabicLanguageId = csArabicLanguageId;
    }

    public Integer getCsEnglishLanguageId() {
        return csEnglishLanguageId;
    }

    public void setCsEnglishLanguageId(Integer csEnglishLanguageId) {
        this.csEnglishLanguageId = csEnglishLanguageId;
    }
}

