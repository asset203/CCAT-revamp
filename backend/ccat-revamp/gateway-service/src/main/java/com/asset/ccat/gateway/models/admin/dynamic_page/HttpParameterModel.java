package com.asset.ccat.gateway.models.admin.dynamic_page;

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


    public HttpParameterModel(List<HttpResponseMappingModel> httpResponseMappingModels, String xPath) {
        this.httpResponseMappingModels = httpResponseMappingModels;
        this.xPath = xPath;
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

    @Override
    public String toString() {
        return "HttpParameterModel{" +
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
                ", httpRequestMappingModels=" + httpResponseMappingModels +
                ", xPath=" + xPath +
                ", jsonPath=" + jsonPath +
                '}';
    }
}
