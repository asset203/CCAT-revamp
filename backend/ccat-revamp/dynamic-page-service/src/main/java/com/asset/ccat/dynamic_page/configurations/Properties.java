/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.configurations;

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
    private String encryptionKey;
    private Integer connectionTimeout;
    private Integer responseTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;


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

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
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
}
