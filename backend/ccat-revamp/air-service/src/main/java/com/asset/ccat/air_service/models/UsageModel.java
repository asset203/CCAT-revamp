/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models;

import com.asset.ccat.air_service.models.customer_care.UsageThresholdInformationModel;
import java.util.List;

/**
 *
 * @author Mahmoud Shehab
 */
public class UsageModel {

    private Integer usageCounterID;
    private String usageCounterDesc;
    private Long usageCounterValue;
    private String usageCounterMonetaryValue1;
    private String usageCounterMonetaryValue2;
    private List<UsageThresholdInformationModel> usageCounterThresholdInformation;

    public Integer getUsageCounterID() {
        return usageCounterID;
    }

    public void setUsageCounterID(Integer usageCounterID) {
        this.usageCounterID = usageCounterID;
    }

    public String getUsageCounterDesc() {
        return usageCounterDesc;
    }

    public void setUsageCounterDesc(String usageCounterDesc) {
        this.usageCounterDesc = usageCounterDesc;
    }

    public Long getUsageCounterValue() {
        return usageCounterValue;
    }

    public void setUsageCounterValue(Long usageCounterValue) {
        this.usageCounterValue = usageCounterValue;
    }

    public String getUsageCounterMonetaryValue1() {
        return usageCounterMonetaryValue1;
    }

    public void setUsageCounterMonetaryValue1(String usageCounterMonetaryValue1) {
        this.usageCounterMonetaryValue1 = usageCounterMonetaryValue1;
    }

    public String getUsageCounterMonetaryValue2() {
        return usageCounterMonetaryValue2;
    }

    public void setUsageCounterMonetaryValue2(String usageCounterMonetaryValue2) {
        this.usageCounterMonetaryValue2 = usageCounterMonetaryValue2;
    }

    public List<UsageThresholdInformationModel> getUsageCounterThresholdInformation() {
        return usageCounterThresholdInformation;
    }

    public void setUsageCounterThresholdInformation(List<UsageThresholdInformationModel> usageCounterThresholdInformation) {
        this.usageCounterThresholdInformation = usageCounterThresholdInformation;
    }

}
