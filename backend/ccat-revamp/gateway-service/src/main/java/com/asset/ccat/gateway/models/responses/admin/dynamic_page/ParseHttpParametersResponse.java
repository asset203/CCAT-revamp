package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.HttpParameterModel;


import java.util.List;

public class ParseHttpParametersResponse {

    private List<HttpParameterModel> parameters;

    public ParseHttpParametersResponse() {
    }

    public ParseHttpParametersResponse(List<HttpParameterModel> parameters) {
        this.parameters = parameters;
    }

    public List<HttpParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<HttpParameterModel> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ParseHttpParametersResponse{" +
                "parameters=" + parameters +
                '}';
    }
}
