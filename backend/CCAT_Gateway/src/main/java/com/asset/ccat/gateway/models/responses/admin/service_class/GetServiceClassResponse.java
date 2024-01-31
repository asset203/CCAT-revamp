package com.asset.ccat.gateway.models.responses.admin.service_class;

import com.asset.ccat.gateway.models.admin.AdmServiceClassModel;
import com.asset.ccat.gateway.models.admin.ServiceClassModel;

/**
 * @author wael.mohamed
 */
public class GetServiceClassResponse {

    private AdmServiceClassModel serviceClass;

    public GetServiceClassResponse() {

    }

    public GetServiceClassResponse(AdmServiceClassModel serviceClass) {
        this.serviceClass = serviceClass;
    }

    public AdmServiceClassModel getServiceClass() {
        return serviceClass;
    }

    public void setServiceClasses(AdmServiceClassModel serviceClass) {
        this.serviceClass = serviceClass;
    }
}
