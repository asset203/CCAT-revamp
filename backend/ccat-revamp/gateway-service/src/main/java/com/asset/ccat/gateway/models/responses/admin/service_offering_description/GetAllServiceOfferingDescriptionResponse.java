package com.asset.ccat.gateway.models.responses.admin.service_offering_description;


import com.asset.ccat.gateway.models.admin.ServiceOfferingBitDescModel;

import java.util.List;

public class GetAllServiceOfferingDescriptionResponse  {
    private List<ServiceOfferingBitDescModel> serviceOfferingBitList;


    public GetAllServiceOfferingDescriptionResponse(List<ServiceOfferingBitDescModel> serviceOfferingBitList){this.serviceOfferingBitList=serviceOfferingBitList;}
    public GetAllServiceOfferingDescriptionResponse(){}

    public List<ServiceOfferingBitDescModel> getServiceOfferingBitList() {
        return serviceOfferingBitList;
    }

    public void setServiceOfferingBitList(List<ServiceOfferingBitDescModel> serviceOfferingBitList) {
        this.serviceOfferingBitList = serviceOfferingBitList;
    }

    @Override
    public String toString() {
        return "GetAllServiceOfferingDescriptionResponse{" +
                "serviceOfferingBitList=" + serviceOfferingBitList +
                '}';
    }
}
