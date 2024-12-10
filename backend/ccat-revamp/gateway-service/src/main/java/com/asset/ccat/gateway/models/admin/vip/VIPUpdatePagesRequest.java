package com.asset.ccat.gateway.models.admin.vip;

import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;


public class VIPUpdatePagesRequest extends BaseRequest {
    private List<Integer> menuIds;

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "VIPUpdatePagesRequest{" +
                "menuIds=" + menuIds +
                '}';
    }
}
