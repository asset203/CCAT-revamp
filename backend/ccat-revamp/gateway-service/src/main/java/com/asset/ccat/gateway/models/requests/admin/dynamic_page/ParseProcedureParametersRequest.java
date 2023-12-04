package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class ParseProcedureParametersRequest extends BaseRequest {

    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "ParseProcedureParametersRequest{" +
                "query='" + query + '\'' +
                '}';
    }
}
