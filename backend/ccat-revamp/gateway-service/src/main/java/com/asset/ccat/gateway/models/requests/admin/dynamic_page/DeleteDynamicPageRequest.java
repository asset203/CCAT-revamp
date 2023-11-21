package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author assem.hassan
 */
public class DeleteDynamicPageRequest extends BaseRequest {

    private Integer pageId;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "DeleteDynamicPageRequest{" +
                "pageId=" + pageId +
                '}';
    }
}
