/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.configurations;

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
    private Integer lookupsRefreshTask;
    private String smsSrcEndcoding;
    private String smsDestEndcoding;

    private Integer subPartitons;
    private String encryptionKey;


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

    public Integer getLookupsRefreshTask() {
        return lookupsRefreshTask;
    }

    public void setLookupsRefreshTask(Integer lookupsRefreshTask) {
        this.lookupsRefreshTask = lookupsRefreshTask;
    }

    public Integer getSubPartitons() {
        return subPartitons;
    }

    public void setSubPartitons(Integer subPartitons) {
        this.subPartitons = subPartitons;
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

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
