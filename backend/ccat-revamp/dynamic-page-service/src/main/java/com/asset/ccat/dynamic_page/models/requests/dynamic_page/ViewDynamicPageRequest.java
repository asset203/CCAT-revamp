package com.asset.ccat.dynamic_page.models.requests.dynamic_page;

import com.asset.ccat.dynamic_page.models.requests.BaseRequest;

public class ViewDynamicPageRequest extends BaseRequest {

    private Integer privilegeId;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }
}
