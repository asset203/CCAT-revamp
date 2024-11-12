package com.asset.ccat.air_service.models.customer_care;

/**
 *
 * @author nour.ihab
 */
public class AddUsageModel {

    private Integer id;
    private String value;
    private Integer usageTypeId;
    private String msisdn;
    private String monetaryValue1;
    private String monetaryValue2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(Integer usageTypeId) {
        this.usageTypeId = usageTypeId;
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

}
