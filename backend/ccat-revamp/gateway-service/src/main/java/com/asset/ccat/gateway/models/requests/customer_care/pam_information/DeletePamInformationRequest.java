package com.asset.ccat.gateway.models.requests.customer_care.pam_information;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class DeletePamInformationRequest extends BaseRequest {

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
        return "DeletePamInformationRequest{" +
                "msisdn='" + msisdn + '\'' +
                ", pamId=" + pamId +
                '}';
    }
}
