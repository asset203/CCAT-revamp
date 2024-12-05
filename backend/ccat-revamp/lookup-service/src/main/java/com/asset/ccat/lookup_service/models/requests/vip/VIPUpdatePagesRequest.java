package com.asset.ccat.lookup_service.models.requests.vip;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;
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
