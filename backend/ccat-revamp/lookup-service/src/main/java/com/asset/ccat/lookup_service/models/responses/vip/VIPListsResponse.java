package com.asset.ccat.lookup_service.models.responses.vip;

import com.asset.ccat.lookup_service.models.PageModel;

import java.util.List;

public class VIPListsResponse {
    private List<String> vipMsisdn;
//    private Map<Integer, List<VIPPageModel>> vipPages;
    private List<PageModel> appPages;

    private List<PageModel> vipPages;


    public VIPListsResponse(List<String> vipMsisdn, List<PageModel> appPages, List<PageModel> vipPages) {
        this.vipMsisdn = vipMsisdn;
        this.appPages = appPages;
        this.vipPages = vipPages;
    }

    public List<PageModel> getVipPages() {
        return vipPages;
    }

    public void setVipPages(List<PageModel> vipPages) {
        this.vipPages = vipPages;
    }

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

    @Override
    public String toString() {
        return "VIPListsResponse{" +
                "vipMsisdn=" + vipMsisdn +
                ", vipPages=" + vipPages +
                '}';
    }
}
