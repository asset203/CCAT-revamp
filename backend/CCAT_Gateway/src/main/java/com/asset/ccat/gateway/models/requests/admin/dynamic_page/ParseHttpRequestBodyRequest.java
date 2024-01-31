package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class ParseHttpRequestBodyRequest extends BaseRequest {

    private String requestBody;

    public ParseHttpRequestBodyRequest() {
    }

    public ParseHttpRequestBodyRequest(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "ParseHttpRequestBodyRequest{" +
                "query='" + requestBody + '\'' +
                '}';
    }
}
