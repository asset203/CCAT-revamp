package com.asset.ccat.gateway.models.responses.admin.dynamic_page;


import com.asset.ccat.gateway.models.admin.dynamic_page.DynamicPageModel;

public class GetDynamicPageResponse {

    private DynamicPageModel dynamicPage;

    public GetDynamicPageResponse() {
    }

    public GetDynamicPageResponse(DynamicPageModel dynamicPage) {
        this.dynamicPage = dynamicPage;
    }

    public DynamicPageModel getDynamicPage() {
        return dynamicPage;
    }

    public void setDynamicPage(DynamicPageModel dynamicPage) {
        this.dynamicPage = dynamicPage;
    }
}
