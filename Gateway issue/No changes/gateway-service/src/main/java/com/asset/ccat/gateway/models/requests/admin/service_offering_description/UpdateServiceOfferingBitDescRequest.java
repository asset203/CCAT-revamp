package com.asset.ccat.gateway.models.requests.admin.service_offering_description;

import com.asset.ccat.gateway.models.admin.ServiceOfferingBitDescModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

public class UpdateServiceOfferingBitDescRequest extends BaseRequest {

   private ServiceOfferingBitDescModel serviceOffering;

    public ServiceOfferingBitDescModel getServiceOffering() {
        return serviceOffering;
    }

    @Override
    public String toString() {
        return "UpdateServiceOfferingDescRequest{" +
                "serviceOffering=" + serviceOffering +
                '}';
    }
}
