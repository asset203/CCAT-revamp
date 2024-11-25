package com.asset.ccat.dynamic_page.models.requests.dynamic_page;

import com.asset.ccat.dynamic_page.models.requests.BaseRequest;

public class AddDynamicPageRequest extends BaseRequest {

    private String pageName;
    private String privilegeName;
    private Boolean requireSubscriber;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public Boolean getRequireSubscriber() {
        return requireSubscriber;
    }

    public void setRequireSubscriber(Boolean requireSubscriber) {
        this.requireSubscriber = requireSubscriber;
    }

    @Override
    public String toString() {
        return "AddDynamicPageRequest{" +
                "pageName='" + pageName + '\'' +
                ", privilegeName='" + privilegeName + '\'' +
                ", requireSubscriber=" + requireSubscriber +
                '}';
    }
}
