package com.asset.ccat.balance_dispute_service.dto.requests;

public class BaseRequest{
    public BaseRequest() {
    }

    private String requestId;
    private String sessionId;
    private String token;
    private String username;

    private Integer userId;

    public BaseRequest(String requestId, String sessionId, String token, String username, Integer userId) {
        this.requestId = requestId;
        this.sessionId = sessionId;
        this.token = token;
        this.username = username;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "requestId='" + requestId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                '}';
    }
}
