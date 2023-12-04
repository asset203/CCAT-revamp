package com.asset.ccat.gateway.models.requests.admin.service_class;

import com.asset.ccat.gateway.models.admin.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author wael.mohamed
 */
public class UpdateServiceClassRequest extends BaseRequest {

    private ServiceClassModel serviceClass;

    public ServiceClassModel getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClassModel serviceClass) {
        this.serviceClass = serviceClass;
    }

    @Override
    public String toString() {
        return "UpdateServiceClassRequest{" +
                "serviceClass=" + serviceClass +
                '}';
    }
}
