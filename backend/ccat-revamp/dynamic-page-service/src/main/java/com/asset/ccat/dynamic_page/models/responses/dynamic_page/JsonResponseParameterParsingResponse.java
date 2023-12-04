package com.asset.ccat.dynamic_page.models.responses.dynamic_page;

import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;

import java.util.List;

public class JsonResponseParameterParsingResponse {
    private List<HttpParameterModel> parameters;
    private List<String> paths;

    public JsonResponseParameterParsingResponse() {
    }

    public JsonResponseParameterParsingResponse(List<HttpParameterModel> parameters, List<String> paths) {
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
        return "XMLResponseParameterParsingResponse{" +
                "parameters=" + parameters +
                ", paths=" + paths +
                '}';
    }
}
