package com.asset.ccat.gateway.redis.model;

import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("DSSRedisModel")
public class DSSRedisModel {
    @Id
    private String msisdn;
    private DSSReportModel dssReportModel;
    private String jsonData;
    private String dssPageName;

    public DSSRedisModel(String msisdn, DSSReportModel dssReportModel) {
        this.msisdn = msisdn;
        this.dssReportModel = dssReportModel;
    }

    public String getDssPageName() {
        return dssPageName;
    }

    public void setDssPageName(String dssPageName) {
        this.dssPageName = dssPageName;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public DSSReportModel getDssReportModel() {
        return dssReportModel;
    }

    public void setDssReportModel(DSSReportModel dssReportModel) {
        this.dssReportModel = dssReportModel;
    }
}
