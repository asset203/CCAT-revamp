package com.asset.ccat.lookup_service.models.requests.vip;

import com.asset.ccat.lookup_service.models.requests.BaseRequest;

import java.util.List;

public class VIPUpdatePagesRequest extends BaseRequest {
    private List<String> newVipPages;

    public List<String> getNewVipPages() {
        return newVipPages;
    }

    public void setNewVipPages(List<String> newVipPages) {
        this.newVipPages = newVipPages;
    }
}
