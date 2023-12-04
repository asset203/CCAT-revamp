package com.asset.ccat.lookup_service.models.requests.service_offering_description;


import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;

public class UpdateServiceOfferingDescRequest extends BaseRequest {

   private ServiceOfferingBitModel serviceOffering;

    public ServiceOfferingBitModel getServiceOffering() {
        return serviceOffering;
    }

    @Override
    public String toString() {
        return "UpdateServiceOfferingDescRequest{" +
                "serviceOffering=" + serviceOffering +
                '}';
    }
}
