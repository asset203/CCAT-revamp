package com.asset.ccat.dynamic_page.models.dynamic_page;

import java.util.List;


public class HttpConfigurationModel extends StepConfigurationModel {

    private Integer id;
    private Integer stepId;
    private String httpURL;
    private Integer httpMethod; //GET(1) --->  ...query parameters
                                //POST(2) ---> requestBody{ ...requestParameters }
    private String headers;//Stringify Array Of Objects
    private Integer requestContentType; // JSON , XML , TEXT in case en feh body
    private Integer responseContentType; // JSON , XML , TEXT
    private Integer responseForm; // MAP , CSV , TEXT
    private String requestBody; //Stringify Object POST
    private String keyValueDelimiter;
    private String mainDelimiter;
    private Integer maxRecords;
    private String successCode;
    private List<HttpParameterModel> parameters;

    public HttpConfigurationModel() {
    }

    public HttpConfigurationModel(Integer id, Integer stepId, String httpURL, Integer httpMethod, String headers, Integer requestContentType, Integer responseContentType, Integer responseForm, String requestBody, String keyValueDelimiter, String mainDelimiter, Integer maxRecords, String successCode, List<HttpParameterModel> parameters) {
        this.id = id;
        this.stepId = stepId;
        this.httpURL = httpURL;
        this.httpMethod = httpMethod;
        this.headers = headers;
        this.requestContentType = requestContentType;
        this.responseContentType = responseContentType;
        this.responseForm = responseForm;
        this.requestBody = requestBody;
        this.keyValueDelimiter = keyValueDelimiter;
        this.mainDelimiter = mainDelimiter;
        this.maxRecords = maxRecords;
        this.successCode = successCode;
        this.parameters = parameters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public String getHttpURL() {
        return httpURL;
    }

    public void setHttpURL(String httpURL) {
        this.httpURL = httpURL;
    }

    public Integer getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(Integer httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Integer getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(Integer requestContentType) {
        this.requestContentType = requestContentType;
    }

    public Integer getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(Integer responseContentType) {
        this.responseContentType = responseContentType;
    }

    public Integer getResponseForm() {
        return responseForm;
    }

    public void setResponseForm(Integer responseForm) {
        this.responseForm = responseForm;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getKeyValueDelimiter() {
        return keyValueDelimiter;
    }

    public void setKeyValueDelimiter(String keyValueDelimiter) {
        this.keyValueDelimiter = keyValueDelimiter;
    }

    public String getMainDelimiter() {
        return mainDelimiter;
    }

    public void setMainDelimiter(String mainDelimiter) {
        this.mainDelimiter = mainDelimiter;
    }

    public Integer getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(Integer maxRecords) {
        this.maxRecords = maxRecords;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public List<HttpParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(List<HttpParameterModel> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "HttpConfigurationModel{" +
                "id=" + id +
                ", stepId=" + stepId +
                ", httpURL='" + httpURL + '\'' +
                ", httpMethod=" + httpMethod +
                ", headers='" + headers + '\'' +
                ", requestContentType=" + requestContentType +
                ", responseContentType=" + responseContentType +
                ", responseForm=" + responseForm +
                ", requestBody='" + requestBody + '\'' +
                ", dataDelimiter='" + keyValueDelimiter + '\'' +
                ", lineDelimiter='" + mainDelimiter + '\'' +
                ", maxRecords=" + maxRecords +
                ", successCode=" + successCode +
                ", parameters=" + parameters +
                '}';
    }
}
