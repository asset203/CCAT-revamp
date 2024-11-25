package com.asset.ccat.dynamic_page.models.requests.dynamic_page;

import com.asset.ccat.dynamic_page.models.requests.BaseRequest;

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
}
