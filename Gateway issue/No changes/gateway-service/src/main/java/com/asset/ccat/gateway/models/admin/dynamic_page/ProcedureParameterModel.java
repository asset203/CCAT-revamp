package com.asset.ccat.gateway.models.admin.dynamic_page;

import java.util.List;

public class ProcedureParameterModel extends ParameterModel {

    private List<ProcedureCursorMappingModel> cursorParameterMappings;

    public List<ProcedureCursorMappingModel> getCursorParameterMappings() {
        return cursorParameterMappings;
    }

    public void setCursorParameterMappings(List<ProcedureCursorMappingModel> cursorParameterMappings) {
        this.cursorParameterMappings = cursorParameterMappings;
    }

    @Override
    public String toString() {
        return "ProcedureParameterModel{" +
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
                ", cursorParameterMappings=" + cursorParameterMappings +
                '}';
    }
}
