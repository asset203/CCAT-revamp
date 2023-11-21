/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoud.shehab
 */
@Component
@ConfigurationProperties
public class Properties {

    private String accessTokenKey;
    private long accessTokenValidity;
    private String dateFormat;
    private Integer usersRefreshTask;
    private Boolean ldapAuthenticationFlag;
    private String ldapDomainName;
    private String ldapServerUrl;
    private String ldapUsersSearchBase;

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

    public Integer getUsersRefreshTask() {
        return usersRefreshTask;
    }

    public void setUsersRefreshTask(Integer usersRefreshTask) {
        this.usersRefreshTask = usersRefreshTask;
    }

    public Boolean getLdapAuthenticationFlag() {
        return ldapAuthenticationFlag;
    }

    public void setLdapAuthenticationFlag(Boolean ldapAuthenticationFlag) {
        this.ldapAuthenticationFlag = ldapAuthenticationFlag;
    }

    public String getLdapDomainName() {
        return ldapDomainName;
    }

    public void setLdapDomainName(String ldapDomainName) {
        this.ldapDomainName = ldapDomainName;
    }

    public String getLdapServerUrl() {
        return ldapServerUrl;
    }

    public void setLdapServerUrl(String ldapServerUrl) {
        this.ldapServerUrl = ldapServerUrl;
    }

    public String getLdapUsersSearchBase() {
        return ldapUsersSearchBase;
    }

    public void setLdapUsersSearchBase(String ldapUsersSearchBase) {
        this.ldapUsersSearchBase = ldapUsersSearchBase;
    }

}
