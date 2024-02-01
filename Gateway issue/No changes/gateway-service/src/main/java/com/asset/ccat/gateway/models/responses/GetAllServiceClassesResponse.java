package com.asset.ccat.gateway.models.responses;

import com.asset.ccat.gateway.models.customer_care.ServiceClassModel;

import java.util.List;

/**
 * @author wael.mohamed
 */
public class GetAllServiceClassesResponse {
    private List<ServiceClassModel> serviceClasses;

    public GetAllServiceClassesResponse() {

    }

    public GetAllServiceClassesResponse(List<ServiceClassModel> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public List<ServiceClassModel> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(List<ServiceClassModel> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }
}
