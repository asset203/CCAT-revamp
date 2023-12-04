package com.asset.ccat.dynamic_page.models.responses.dynamic_page;

import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;

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
