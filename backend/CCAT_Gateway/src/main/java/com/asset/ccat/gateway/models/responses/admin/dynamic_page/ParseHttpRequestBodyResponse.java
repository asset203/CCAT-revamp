package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.HttpParameterModel;

import java.util.List;

public class ParseHttpRequestBodyResponse {

    private List<HttpParameterModel> parameters;

    public ParseHttpRequestBodyResponse() {
    }

    public ParseHttpRequestBodyResponse(List<HttpParameterModel> parameters) {
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
        return "ParseHttpRequestBodyResponse{" +
                "parameters=" + parameters +
                '}';
    }
}
