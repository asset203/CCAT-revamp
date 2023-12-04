/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses;

import com.asset.ccat.gateway.models.users.UserProfileModel;
import com.asset.ccat.gateway.models.users.UserModel;

import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class LoginWrapperModel {

    private String token;
    private UserModel user;
    private UserProfileModel userProfile;
    private String sessionId;
    private Long tokenExpiryEpoch;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getTokenExpiryEpoch() {
        return tokenExpiryEpoch;
    }

    public void setTokenExpiryEpoch(Long tokenExpiryEpoch) {
        this.tokenExpiryEpoch = tokenExpiryEpoch;
    }
}
