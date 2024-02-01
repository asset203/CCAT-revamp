package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.GetAllServiceOfferingDescriptionRequest;
import com.asset.ccat.gateway.models.requests.admin.service_offering_description.UpdateServiceOfferingBitDescRequest;
import com.asset.ccat.gateway.models.responses.admin.service_offering_description.GetAllServiceOfferingDescriptionResponse;
import com.asset.ccat.gateway.proxy.admin.ServiceOfferingDescProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceOfferingDescService {


    @Autowired
    ServiceOfferingDescProxy serviceOfferingDescProxy;


    public GetAllServiceOfferingDescriptionResponse getAllServiceOffering(GetAllServiceOfferingDescriptionRequest getAllRequest) throws GatewayException {
        GetAllServiceOfferingDescriptionResponse response = serviceOfferingDescProxy.getAllServiceOfferingDesc(getAllRequest);
        return response;
    }

    public void updateServiceOfferingDesc(UpdateServiceOfferingBitDescRequest req) throws GatewayException {
        serviceOfferingDescProxy.updateServiceOfferingDesc(req);
    }
}
