package com.asset.ccat.procedureservice.dto.models.flex_share;

public class FlexShareInquirySPResponse {
    private Integer bundleOut;
    private String param2Out;
    private String statusOut;

    public FlexShareInquirySPResponse() {
    }

    public FlexShareInquirySPResponse(Integer bundleOut, String param2Out, String statusOut) {
        this.bundleOut = bundleOut;
        this.param2Out = param2Out;
        this.statusOut = statusOut;
    }

    public Integer getBundleOut() {
        return bundleOut;
    }

    public void setBundleOut(Integer bundleOut) {
        this.bundleOut = bundleOut;
    }

    public String getParam2Out() {
        return param2Out;
    }

    public void setParam2Out(String param2Out) {
        this.param2Out = param2Out;
    }

    public String getStatusOut() {
        return statusOut;
    }

    public void setStatusOut(String statusOut) {
        this.statusOut = statusOut;
    }

    @Override
    public String toString() {
        return "FlexShareInquirySPResponse{" +
                "bundleOut=" + bundleOut +
                ", param2Out='" + param2Out + '\'' +
                ", statusOut='" + statusOut + '\'' +
                '}';
    }
}
