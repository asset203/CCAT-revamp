package com.asset.ccat.lookup_service.models.migration;

import java.util.HashMap;
import java.util.List;

/**
 * @author marwa.elshawarby
 */
public class MigrationModel {

    private String tableName;
    private String keyIdentifier;
    private List<String> headers;
    private List<HashMap<String, String>> data;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }
}
