package com.asset.ccat.gateway.models.requests;

import com.asset.ccat.rabbitmq.models.FootprintModel;

import java.io.Serializable;

/**
 * @author Mahmoud Shehab
 */
public abstract class BaseRequest implements Serializable {

    private String token;
    private String username;
    private String requestId;
    private String sessionId;
    private FootprintModel footprintModel;
    private Integer userId;

    public FootprintModel getFootprintModel() {
        return footprintModel;
    }

    public void setFootprintModel(FootprintModel footprintModel) {
        this.footprintModel = footprintModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "BaseRequest{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", requestId='" + requestId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", footPrint=" + footprintModel +
                ", userId=" + userId +
                '}';
    }
}
