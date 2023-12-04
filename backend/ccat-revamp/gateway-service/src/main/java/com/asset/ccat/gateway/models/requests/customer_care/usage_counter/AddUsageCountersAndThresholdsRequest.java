package com.asset.ccat.gateway.models.requests.customer_care.usage_counter;

import com.asset.ccat.gateway.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

/**
 * @author nour.ihab
 */
public class AddUsageCountersAndThresholdsRequest extends BaseRequest {

    private Integer id;
    private String value;
    private Integer usageTypeId;
    private String msisdn;
    private List<UsageThresholdInformationModel> thresholds;

    @Override
    public String getUsername() {
        return super.getUsername();
    }

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

    public List<UsageThresholdInformationModel> getThresholds() {
        return thresholds;
    }

    public void setThresholds(List<UsageThresholdInformationModel> thresholds) {
        this.thresholds = thresholds;
    }

    @Override
    public String toString() {
        return "AddUsageCountersAndThresholdsRequest{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", usageTypeId=" + usageTypeId +
                ", msisdn='" + msisdn + '\'' +
                ", thresholds=" + thresholds +
                '}';
    }
}
