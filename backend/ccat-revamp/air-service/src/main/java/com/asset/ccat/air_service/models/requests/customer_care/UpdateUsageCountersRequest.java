package com.asset.ccat.air_service.models.requests.customer_care;

import com.asset.ccat.air_service.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.air_service.models.requests.BaseRequest;

import java.util.List;

/**
 * @author assem.hassan
 */
public class UpdateUsageCountersRequest extends BaseRequest {

    private String msisdn;
    private Integer id;
    private String counterValue;
    private String monetaryValue1;
    private String monetaryValue2;
    private List<UsageThresholdInformationModel> usageThresholdInformation;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

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

    @Override
    public String toString() {
        return "UpdateUsageCountersRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", id=" + id +
                ", counterValue='" + counterValue + '\'' +
                ", monetaryValue1='" + monetaryValue1 + '\'' +
                ", monetaryValue2='" + monetaryValue2 + '\'' +
                ", usageThresholdInformation=" + usageThresholdInformation +
                '}';
    }
}
