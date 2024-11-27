package com.asset.ccat.lookup_service.models.responses;

import java.util.List;

public class VIPListsResponse {
    private List<String> vipMsisdn;
    private List<String> vipPages;

    public VIPListsResponse() {
    }

    public VIPListsResponse(List<String> vipMsisdn, List<String> vipPages) {
        this.vipMsisdn = vipMsisdn;
        this.vipPages = vipPages;
    }

    public List<String> getVipMsisdn() {
        return vipMsisdn;
    }

    public void setVipMsisdn(List<String> vipMsisdn) {
        this.vipMsisdn = vipMsisdn;
    }

    public List<String> getVipPages() {
        return vipPages;
    }

    public void setVipPages(List<String> vipPages) {
        this.vipPages = vipPages;
    }

    @Override
    public String toString() {
        return "VIPListsResponse{" +
                "vipMsisdn=" + vipMsisdn +
                ", vipPages=" + vipPages +
                '}';
    }
}
