/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.LookupModel;
import com.asset.ccat.gateway.models.shared.ServiceOfferingPlanModel;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllServiceOfferingResponse {

    List<ServiceOfferingPlanModel> serviceOffering;

    public GetAllServiceOfferingResponse() {
    }

    public GetAllServiceOfferingResponse(List<ServiceOfferingPlanModel> serviceOffering) {
        this.serviceOffering = serviceOffering;
    }

    public List<ServiceOfferingPlanModel> getServiceOffering() {
        return serviceOffering;
    }

    public void setServiceOffering(List<ServiceOfferingPlanModel> serviceOffering) {
        this.serviceOffering = serviceOffering;
    }

}
