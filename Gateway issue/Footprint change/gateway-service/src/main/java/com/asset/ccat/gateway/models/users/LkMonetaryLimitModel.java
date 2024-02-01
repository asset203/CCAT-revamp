package com.asset.ccat.gateway.models.users;

/**
 * @author marwa.elshawarby
 */
public class LkMonetaryLimitModel {

    private Integer limitId;
    private String limitName;
    private Long value;
    private Long defaultValue;

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Long defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "Limit ID: " + limitId +
                ", Limit name: " + limitName +
                ", Limit value: " + value +
                ", Limit default value: " + defaultValue;
    }
}
