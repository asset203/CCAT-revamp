package com.asset.ccat.gateway.models.responses.admin.dynamic_page;

public class AddDynamicPageResponse {

    private Integer pageId;
    private Integer privilegeId;

    public AddDynamicPageResponse() {
    }

    public AddDynamicPageResponse(Integer pageId, Integer privilegeId) {
        this.pageId = pageId;
        this.privilegeId = privilegeId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "AddDynamicPageResponse{" +
                "pageId=" + pageId +
                ", privilegeId=" + privilegeId +
                '}';
    }
}
