package com.asset.ccat.dynamic_page.models.requests;

public class ParseHttpParametersRequest extends BaseRequest {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
