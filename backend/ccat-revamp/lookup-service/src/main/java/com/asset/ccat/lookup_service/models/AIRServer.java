package com.asset.ccat.lookup_service.models;

import java.util.Date;

/**
 *
 * @author Mahmoud Shehab
 */
public class AIRServer {

    private Integer id;
    private String url;
    private String authorization;
    private String host;
    private String agentName;
    private String capabilityValue;
    private Date firstFailureDate;
    private Integer failuresCount;
    private Integer isDown;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCapabilityValue() {
        return capabilityValue;
    }

    public void setCapabilityValue(String capabilityValue) {
        this.capabilityValue = capabilityValue;
    }

    public Date getFirstFailureDate() {
        return firstFailureDate;
    }

    public void setFirstFailureDate(Date firstFailureDate) {
        this.firstFailureDate = firstFailureDate;
    }

    public Integer getFailuresCount() {
        return failuresCount;
    }

    public void setFailuresCount(Integer failuresCount) {
        this.failuresCount = failuresCount;
    }

    public Integer getIsDown() {
        return isDown;
    }

    public void setIsDown(Integer isDown) {
        this.isDown = isDown;
    }

}
