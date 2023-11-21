package com.asset.ccat.dynamic_page.models.dynamic_page;

import com.asset.ccat.dynamic_page.constants.HTTPMethodType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPRequestWrapperModel {

    private HTTPMethodType httpMethod;
    private String url;
    private List<Map> headers;
    private String requestBody;

    public HTTPMethodType getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HTTPMethodType httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Map> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Map> headers) {
        this.headers = headers;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "HTTPRequestWrapperModel{" +
                "httpMethod=" + httpMethod +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
