package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.ProcedureParameterModel;

import java.util.List;

public class ParseProcedureParametersResponse {

    private List<ProcedureParameterModel> parameters;

    public ParseProcedureParametersResponse() {
    }

    public ParseProcedureParametersResponse(List<ProcedureParameterModel> parameters) {
        this.parameters = parameters;
    }

    public List<ProcedureParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<ProcedureParameterModel> parameters) {
        this.parameters = parameters;
    }
}
