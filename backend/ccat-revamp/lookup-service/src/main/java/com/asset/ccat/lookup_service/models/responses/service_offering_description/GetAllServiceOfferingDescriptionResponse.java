package com.asset.ccat.lookup_service.models.responses.service_offering_description;



import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;

import java.util.List;

public class GetAllServiceOfferingDescriptionResponse  {
    private List<ServiceOfferingBitModel> serviceOfferingBitList;


    public GetAllServiceOfferingDescriptionResponse(List<ServiceOfferingBitModel> serviceOfferingBitList){this.serviceOfferingBitList=serviceOfferingBitList;}
    public GetAllServiceOfferingDescriptionResponse(){}


    public void setServiceOfferingBitList(List<ServiceOfferingBitModel> serviceOfferingBitList) {
        this.serviceOfferingBitList = serviceOfferingBitList;
    }

    public List<ServiceOfferingBitModel> getServiceOfferingBitList() {
        return serviceOfferingBitList;
    }



    @Override
    public String toString() {
        return "GetAllServiceOfferingDescriptionResponse{" +
                "serviceOfferingBitList=" + serviceOfferingBitList +
                '}';
    }
}
