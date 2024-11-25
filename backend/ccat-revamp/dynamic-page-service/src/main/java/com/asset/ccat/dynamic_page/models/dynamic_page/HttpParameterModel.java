package com.asset.ccat.dynamic_page.models.dynamic_page;

import java.util.List;

public class HttpParameterModel extends ParameterModel {

    private List<HttpResponseMappingModel> httpResponseMappingModels;
    private String xPath;
    private String jsonPath;
    public HttpParameterModel() {
    }

    public HttpParameterModel(List<HttpResponseMappingModel> httpResponseMappingModels) {
        this.httpResponseMappingModels = httpResponseMappingModels;
    }

    public List<HttpResponseMappingModel> getHttpResponseMappingModels() {
        return httpResponseMappingModels;
    }

    public void setHttpResponseMappingModels(List<HttpResponseMappingModel> httpResponseMappingModels) {
        this.httpResponseMappingModels = httpResponseMappingModels;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }
}
