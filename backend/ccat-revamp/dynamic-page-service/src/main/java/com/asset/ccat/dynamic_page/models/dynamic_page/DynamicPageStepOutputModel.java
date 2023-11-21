package com.asset.ccat.dynamic_page.models.dynamic_page;

public class DynamicPageStepOutputModel implements Comparable<DynamicPageStepOutputModel> {

    private String parameterName;
    private Integer parameterDataType;
    private Integer parameterOrder;
    private Integer parameterId;
    private Boolean isHidden;
    private Object parameterValue;

    public DynamicPageStepOutputModel() {
    }

    public DynamicPageStepOutputModel(String parameterName, Integer parameterDataType, Integer parameterOrder, Integer parameterId, Boolean isHidden, Object parameterValue) {
        this.parameterName = parameterName;
        this.parameterDataType = parameterDataType;
        this.parameterOrder = parameterOrder;
        this.parameterId = parameterId;
        this.isHidden = isHidden;
        this.parameterValue = parameterValue;
    }

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

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public String toString() {
        return "DynamicPageStepOutputModel{" +
                "parameterName='" + parameterName + '\'' +
                ", parameterDataType=" + parameterDataType +
                ", parameterOrder=" + parameterOrder +
                ", parameterId=" + parameterId +
                ", isHidden=" + isHidden +
                ", parameterValue=" + parameterValue +
                '}';
    }

    @Override
    public int compareTo(DynamicPageStepOutputModel o) {
        if (getParameterOrder() == null || o.getParameterOrder() == null) {
            return 0;
        }
        return getParameterOrder().compareTo(o.getParameterOrder());
    }
}

