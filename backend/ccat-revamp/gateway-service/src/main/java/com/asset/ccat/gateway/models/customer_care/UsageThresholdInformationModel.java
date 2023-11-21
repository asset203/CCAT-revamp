/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.customer_care;

/**
 *
 * @author assem.hassan
 */
public class UsageThresholdInformationModel {

    private Integer usageThresholdID;
    private Integer usageThresholdSource;
    private String usageThresholdValue;

    public Integer getUsageThresholdID() {
        return usageThresholdID;
    }

    public void setUsageThresholdID(Integer usageThresholdID) {
        this.usageThresholdID = usageThresholdID;
    }

    public Integer getUsageThresholdSource() {
        return usageThresholdSource;
    }

    public void setUsageThresholdSource(Integer usageThresholdSource) {
        this.usageThresholdSource = usageThresholdSource;
    }

    public String getUsageThresholdValue() {
        return usageThresholdValue;
    }

    public void setUsageThresholdValue(String usageThresholdValue) {
        this.usageThresholdValue = usageThresholdValue;
    }
}
