package com.asset.ccat.dynamic_page.models.responses.dynamic_page;


import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;

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
