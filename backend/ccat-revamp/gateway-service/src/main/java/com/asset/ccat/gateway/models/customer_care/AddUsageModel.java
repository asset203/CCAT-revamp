package com.asset.ccat.gateway.models.customer_care;

/**
 *
 * @author nour.ihab
 */
public class AddUsageModel {

    private Integer id;
    private String value;
    private Integer usageTypeId;
    private String msisdn;
//    private List<UsageThresholds> thresholds;
    

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
}
