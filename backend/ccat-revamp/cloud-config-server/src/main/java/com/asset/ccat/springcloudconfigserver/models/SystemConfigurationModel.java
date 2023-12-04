package com.asset.ccat.springcloudconfigserver.models;

public class SystemConfigurationModel {
    private String key;
    private String value;
    private Integer valueType;

    public SystemConfigurationModel() {
    }

    public SystemConfigurationModel(String key, String value, Integer valueType) {
        this.key = key;
        this.value = value;
        this.valueType = valueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }
}
