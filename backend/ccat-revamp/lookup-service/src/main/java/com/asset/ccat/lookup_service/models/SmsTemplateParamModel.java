package com.asset.ccat.lookup_service.models;

public class SmsTemplateParamModel {

    private Integer parameterId;
    private String parameterName;
    private Integer sequenceId;

    public SmsTemplateParamModel() {
    }

    public SmsTemplateParamModel(Integer parameterId, String parameterName) {
        this.parameterId = parameterId;
        this.parameterName = parameterName;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }
}
