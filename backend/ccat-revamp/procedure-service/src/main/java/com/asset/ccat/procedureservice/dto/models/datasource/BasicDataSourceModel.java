package com.asset.ccat.procedureservice.dto.models.datasource;

public class BasicDataSourceModel {
    private String URL;
    private String username;
    private String password;
    private String schema;

    public BasicDataSourceModel() {
    }

    public BasicDataSourceModel(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public BasicDataSourceModel(String URL, String username, String password, String schema) {
        this.URL = URL;
        this.username = username;
        this.password = password;
        this.schema = schema;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
