package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

import com.asset.ccat.gateway.models.admin.dynamic_page.DynamicPageModel;

import java.util.List;



public class GetAllDynamicPagesResponse {

    private List<DynamicPageModel> pagesList;

    public GetAllDynamicPagesResponse() {
    }

    public GetAllDynamicPagesResponse(List<DynamicPageModel> pagesList) {
        this.pagesList = pagesList;
    }

    public List<DynamicPageModel> getPagesList() {
        return pagesList;
    }

    public void setPagesList(List<DynamicPageModel> pagesList) {
        this.pagesList = pagesList;
    }
}
