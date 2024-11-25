package com.asset.ccat.gateway.models.requests.report;

/**
 * @author mohamed.metwaly
 */
public class GetOutgoingViewReportRequest extends GetDssReportRequest {
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "GetOutgoingViewReportRequest{" +
                "flag=" + flag +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }
}
