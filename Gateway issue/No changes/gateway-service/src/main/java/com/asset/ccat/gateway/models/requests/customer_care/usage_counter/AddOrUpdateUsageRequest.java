package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;

import com.asset.ccat.gateway.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

public class AddOrUpdateUsageRequest extends BaseRequest {

    private String msisdn;
    private Integer id;
    private String counterValue;
    private String monetaryValue1;
    private String monetaryValue2;
    private List<UsageThresholdInformationModel> usageThresholdInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(String counterValue) {
        this.counterValue = counterValue;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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
