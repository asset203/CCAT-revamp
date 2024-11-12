package com.asset.ccat.air_service.models.requests.pam_info;

import com.asset.ccat.air_service.models.requests.BaseRequest;

/**
 *
 * @author wael.mohamed
 */
public class EvaluatePamInformationRequest extends BaseRequest {

    private String msisdn;
    private Integer pamId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getPamId() {
        return pamId;
    }

    public void setPamId(Integer pamId) {
        this.pamId = pamId;
    }

    @Override
    public String toString() {
        return "EvaluatePamInformationRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", pamId=" + pamId +
                '}';
    }
}
