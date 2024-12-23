/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.shared;

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
    private Date firstFailureDate;
    private Integer failuresCount;
    private Integer isDown;
    private String capabilityValue;

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

    public String getCapabilityValue() {
        return capabilityValue;
    }

    public void setCapabilityValue(String capabilityValue) {
        this.capabilityValue = capabilityValue;
    }

    @Override
    public String toString() {
        return "AIRServer{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", authorization='" + authorization + '\'' +
                ", host='" + host + '\'' +
                ", agentName='" + agentName + '\'' +
                ", firstFailureDate=" + firstFailureDate +
                ", failuresCount=" + failuresCount +
                ", isDown=" + isDown +
                ", capabilityValue=" + capabilityValue +
                '}';
    }
}
