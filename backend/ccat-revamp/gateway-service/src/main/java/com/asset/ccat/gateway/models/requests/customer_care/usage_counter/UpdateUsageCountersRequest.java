package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class UpdateUsageCountersRequest extends BaseRequest {

    private Integer id;
    private String counterValue;
    private String msisdn;
    private String monetaryValue1;
    private String monetaryValue2;

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

    @Override
    public String toString() {
        return "UpdateUsageCountersRequest{" +
                "id=" + id +
                ", counterValue='" + counterValue + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", monetaryValue1='" + monetaryValue1 + '\'' +
                ", monetaryValue2='" + monetaryValue2 + '\'' +
                '}';
    }
}
