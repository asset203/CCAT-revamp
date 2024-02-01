package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author marwa.elshawarby
 */
public class AddUsageCountersRequest extends BaseRequest {

    private Integer id;
    private String value;
    private Integer usageTypeId;
    private String msisdn;

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

    @Override
    public String toString() {
        return "AddUsageCountersRequest{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", usageTypeId=" + usageTypeId +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
