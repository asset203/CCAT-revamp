package com.asset.ccat.notification_service.models;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.Map;

public class CSRequestModel {
    int id;
    String url;

    Map<String, Object> variables;

    public CSRequestModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "CSRequestModel{" + "id=" + id + ", url='" + url + '\'' + ", variables=" + variables + '}';
    }
}
