package com.asset.ccat.gateway.models.responses.admin.service_class;

import java.util.List;

/**
 * @author wael.mohamed
 */
public class GetAllServiceClassesResponse {

    private List<ServiceClassResponse> serviceClasses;

    public GetAllServiceClassesResponse() {

    }

    public GetAllServiceClassesResponse(List<ServiceClassResponse> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }

    public List<ServiceClassResponse> getServiceClasses() {
        return serviceClasses;
    }

    public void setServiceClasses(List<ServiceClassResponse> serviceClasses) {
        this.serviceClasses = serviceClasses;
    }
}
