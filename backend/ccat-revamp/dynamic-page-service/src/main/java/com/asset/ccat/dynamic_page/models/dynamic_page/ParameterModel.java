package com.asset.ccat.dynamic_page.models.dynamic_page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author assem.hassan
 */
public class ParameterModel {

    private Integer id;
    private Integer configId;
    private String parameterName;
    private Integer parameterDataType;
    private Integer parameterType;
    private Integer parameterOrder;
    private Integer displayOrder;
    private Integer inputMethod;
    private String defaultValue;
    private List<DropdownItem> dropdownList;
    private Boolean isResponseCode;
    private Boolean isResponseDescription;
    private String displayName;
    private Boolean isHidden;
    private Integer sourceStepParameterId;
    private String sourceStepParameterName;
    private String dateFormat;
    private String StaticData;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getParameterName() {
        return parameterName;
    }

    @JsonIgnore
    public String getParameterNameUpperCase() {
        return parameterName.toUpperCase();
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Integer getParameterDataType() {
        return parameterDataType;
    }

    public void setParameterDataType(Integer parameterDataType) {
        this.parameterDataType = parameterDataType;
    }

    public Integer getParameterType() {
        return parameterType;
    }

    public void setParameterType(Integer parameterType) {
        this.parameterType = parameterType;
    }

    public Integer getParameterOrder() {
        return parameterOrder;
    }

    public void setParameterOrder(Integer parameterOrder) {
        this.parameterOrder = parameterOrder;
    }

    public Integer getInputMethod() {
        return inputMethod;
    }

    public void setInputMethod(Integer inputMethod) {
        this.inputMethod = inputMethod;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getResponseCode() {
        return isResponseCode;
    }

    public void setResponseCode(Boolean responseCode) {
        isResponseCode = responseCode;
    }

    public Boolean getResponseDescription() {
        return isResponseDescription;
    }

    public void setResponseDescription(Boolean responseDescription) {
        isResponseDescription = responseDescription;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Integer getSourceStepParameterId() {
        return sourceStepParameterId;
    }

    public void setSourceStepParameterId(Integer sourceStepParameterId) {
        this.sourceStepParameterId = sourceStepParameterId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<DropdownItem> getDropdownList() {
        return dropdownList;
    }

    public void setDropdownList(List<DropdownItem> dropdownList) {
        this.dropdownList = dropdownList;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getStaticData() {
        return StaticData;
    }

    public void setStaticData(String staticData) {
        StaticData = staticData;
    }

    public String getSourceStepParameterName() {
        return sourceStepParameterName;
    }

    public void setSourceStepParameterName(String sourceStepParameterName) {
        this.sourceStepParameterName = sourceStepParameterName;
    }

    @Override
    public String toString() {
        return "ParameterModel{" +
                "id=" + id +
                ", configId=" + configId +
                ", parameterName='" + parameterName + '\'' +
                ", parameterDataType=" + parameterDataType +
                ", parameterType=" + parameterType +
                ", parameterOrder=" + parameterOrder +
                ", displayOrder=" + displayOrder +
                ", inputMethod=" + inputMethod +
                ", defaultValue='" + defaultValue + '\'' +
                ", dropdownList=" + dropdownList +
                ", isResponseCode=" + isResponseCode +
                ", isResponseDescription=" + isResponseDescription +
                ", displayName='" + displayName + '\'' +
                ", isHidden=" + isHidden +
                ", sourceStepParameterId=" + sourceStepParameterId +
                ", sourceStepParameterName='" + sourceStepParameterName + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                ", StaticData='" + StaticData + '\'' +
                '}';
    }
}
