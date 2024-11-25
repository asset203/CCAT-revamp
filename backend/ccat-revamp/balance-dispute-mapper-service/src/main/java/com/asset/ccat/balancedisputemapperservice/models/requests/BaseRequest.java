package com.asset.ccat.balancedisputemapperservice.models.requests;

import java.io.Serializable;

/**
 * @author Mahmoud Shehab
 */
public abstract class BaseRequest implements Serializable {

  private String token;
  private String username;
  private String requestId;
  private String sessionId;

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

}
