package com.asset.ccat.gateway.models.shared;

public class DynamicPageStepOutputModel {

    private String parameterName;
    private Integer parameterDataType;
    private Integer parameterOrder;
    private Integer parameterId;
    private Object parameterValue;
    private Boolean isHidden;

    public String getParameterName() {
        return parameterName;
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

    public Integer getParameterOrder() {
        return parameterOrder;
    }

    public void setParameterOrder(Integer parameterOrder) {
        this.parameterOrder = parameterOrder;
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Object parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    @Override
    public String toString() {
        return "DynamicPageStepOutputModel{" +
                "parameterName='" + parameterName + '\'' +
                ", parameterDataType=" + parameterDataType +
                ", parameterOrder=" + parameterOrder +
                ", parameterId=" + parameterId +
                ", parameterValue=" + parameterValue +
                ", isHidden=" + isHidden +
                '}';
    }
}
