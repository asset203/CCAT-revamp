package com.asset.ccat.gateway.models.admin.vip;

import java.util.List;

public class VIPListsResponse {
    private List<String> vipMsisdn;
    private List<PageModel> appPages;

    private List<PageModel> vipPages;

    public List<String> getVipMsisdn() {
        return vipMsisdn;
    }

    public void setVipMsisdn(List<String> vipMsisdn) {
        this.vipMsisdn = vipMsisdn;
    }

    public List<PageModel> getAppPages() {
        return appPages;
    }

    public void setAppPages(List<PageModel> appPages) {
        this.appPages = appPages;
    }

    public List<PageModel> getVipPages() {
        return vipPages;
    }

    public void setVipPages(List<PageModel> vipPages) {
        this.vipPages = vipPages;
    }

    @Override
    public String toString() {
        return "VIPListsResponse{" +
                "vipMsisdn=" + vipMsisdn +
                ", appPages=" + appPages +
                ", vipPages=" + vipPages +
                '}';
    }
}
