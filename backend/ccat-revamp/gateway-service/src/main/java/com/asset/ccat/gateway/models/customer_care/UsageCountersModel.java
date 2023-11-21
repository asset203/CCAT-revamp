package com.asset.ccat.gateway.models.customer_care;

import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class UsageCountersModel {

    private Integer id;
    private String description;
    private String value;
    private String monetaryValue1;
    private String monetaryValue2;
    private List<UsageThresholdInformationModel> usageThresholdInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMonetaryValue1() {
        return monetaryValue1;
    }

    public void setMonetaryValue1(String monetaryValue1) {
        this.monetaryValue1 = monetaryValue1;
    }

    public String getMonetaryValue2() {
        return monetaryValue2;
    }

    public void setMonetaryValue2(String monetaryValue2) {
        this.monetaryValue2 = monetaryValue2;
    }

    public List<UsageThresholdInformationModel> getUsageThresholdInformation() {
        return usageThresholdInformation;
    }

    public void setUsageThresholdInformation(List<UsageThresholdInformationModel> usageThresholdInformation) {
        this.usageThresholdInformation = usageThresholdInformation;
    }
}
