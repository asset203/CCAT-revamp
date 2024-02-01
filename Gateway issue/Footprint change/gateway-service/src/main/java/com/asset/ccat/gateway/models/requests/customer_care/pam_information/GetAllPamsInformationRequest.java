/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.requests.customer_care.pam_information;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 *
 * @author Asset
 */
public class GetAllPamsInformationRequest extends BaseRequest {

    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
