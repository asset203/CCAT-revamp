package com.asset.ccat.dynamic_page.models.dynamic_page;


public class HttpResponseMappingModel {

    private Integer id;
    private Integer parameterId;
    private String headerName;
    private String headerDisplayName;


    public HttpResponseMappingModel() {
    }

    public HttpResponseMappingModel(Integer id, Integer parameterId, String headerName, String headerDisplayName) {
        this.id = id;
        this.parameterId = parameterId;
        this.headerName = headerName;
        this.headerDisplayName = headerDisplayName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderDisplayName() {
        return headerDisplayName;
    }

    public void setHeaderDisplayName(String headerDisplayName) {
        this.headerDisplayName = headerDisplayName;
    }

    @Override
    public String toString() {
        return "HttpRequestMappingModel{" +
                "id=" + id +
                ", parameterId=" + parameterId +
                ", headerName='" + headerName + '\'' +
                ", headerDisplayName='" + headerDisplayName + '\'' +
                '}';
    }
}
