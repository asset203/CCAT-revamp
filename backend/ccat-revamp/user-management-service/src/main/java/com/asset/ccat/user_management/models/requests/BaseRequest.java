/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.models.requests;

import java.io.Serializable;

/**
 *
 * @author Mahmoud Shehab
 */
public abstract class BaseRequest implements Serializable {

    private String token;
    private String requestId;
    private String sessionId;

    private Integer userId;

    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                ", requestId='" + requestId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
