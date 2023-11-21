package com.asset.ccat.user_management.models.responses.lookup;

import com.asset.ccat.user_management.models.users.ServiceClassModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetAllServiceClassesResponse {

    List<ServiceClassModel> serviceClasses;

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
