package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.HttpParameterModel;

import java.util.List;

public class ParseResponseParametersResponse {

    private List<HttpParameterModel> parameters;
    private List<String> paths;

    public ParseResponseParametersResponse() {
    }

    public ParseResponseParametersResponse(List<HttpParameterModel> parameters) {
        this.parameters = parameters;
    }

    public ParseResponseParametersResponse(List<HttpParameterModel> parameters, List<String> paths) {
        this.parameters = parameters;
        this.paths = paths;
    }

    public List<HttpParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<HttpParameterModel> parameters) {
        this.parameters = parameters;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "ParseHttpParametersResponse{" +
                "parameters=" + parameters +
                '}';
    }
}
