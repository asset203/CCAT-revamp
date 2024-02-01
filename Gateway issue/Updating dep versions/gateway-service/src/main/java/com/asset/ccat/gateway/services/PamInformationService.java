/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.AddPamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.DeletePamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.EvaluatePamInformationRequest;
import com.asset.ccat.gateway.proxy.PamInformationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class PamInformationService {

    @Autowired
    PamInformationProxy pamInformationProxy;

    public void addPamInfo(AddPamInformationRequest pamRequest) throws GatewayException {
        pamInformationProxy.addPamInfo(pamRequest);
    }

    public void evaluatePamInfo(EvaluatePamInformationRequest pamRequest) throws GatewayException {
        pamInformationProxy.evaluatePamInfo(pamRequest);
    }

    public void deletePamInfo(DeletePamInformationRequest pamRequest) throws GatewayException {
        pamInformationProxy.deletePamInfo(pamRequest);
    }

}
