package com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page;

import com.asset.ccat.gateway.models.requests.BaseRequest;

public class ViewDynamicPageRequest extends BaseRequest {

    private Integer privilegeId;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "ViewDynamicPageRequest{" +
                "privilegeId=" + privilegeId +
                '}';
    }
}
