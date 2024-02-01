package com.asset.ccat.gateway.models.requests.admin.dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author assem.hassan
 */
public class GetDynamicPageRequest extends BaseRequest {

    private Long pageId;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "GetDynamicPageRequest{" +
                "pageId=" + pageId +
                '}';
    }
}
