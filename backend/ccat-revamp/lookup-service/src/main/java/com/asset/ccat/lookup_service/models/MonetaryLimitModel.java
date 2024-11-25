package com.asset.ccat.lookup_service.models;

/**
 *
 * @author marwa.elshawarby
 */
public class MonetaryLimitModel {

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
}
