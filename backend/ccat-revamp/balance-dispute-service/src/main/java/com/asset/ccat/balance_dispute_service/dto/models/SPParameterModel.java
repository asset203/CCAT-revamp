package com.asset.ccat.balance_dispute_service.dto.models;

public class SPParameterModel {
    private String parameterName;
    private String parameterValue;

    public SPParameterModel() {
    }

    public SPParameterModel(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
