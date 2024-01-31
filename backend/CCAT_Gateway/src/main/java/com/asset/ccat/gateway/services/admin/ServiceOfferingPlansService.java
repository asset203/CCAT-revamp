package com.asset.ccat.gateway.services.admin;


import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.mappers.ServiceOfferingPlanResponseMapper;
import com.asset.ccat.gateway.models.admin.ServiceOfferingPlan;
import com.asset.ccat.gateway.models.requests.admin.service_offering_plans.*;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlanBitsResponse;
import com.asset.ccat.gateway.models.responses.admin.service_offering_plans.GetAllServiceOfferingPlansResponse;
import com.asset.ccat.gateway.proxy.admin.ServiceOfferingPlansProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOfferingPlansService {

    @Autowired
    ServiceOfferingPlansProxy serviceOfferingPlansProxy;

    @Autowired
    ServiceOfferingPlanResponseMapper serviceOfferingPlanResponseMapper;


    public GetAllServiceOfferingPlansResponse getAllServiceOfferingPlans(GetAllServiceOfferingPlansRequest request) throws GatewayException {

        List<ServiceOfferingPlan> plans = serviceOfferingPlansProxy.getAllServiceOfferingPlans(request);

        GetAllServiceOfferingPlansResponse response = serviceOfferingPlanResponseMapper.mapGetAllServiceOfferingPlanNameResponse(plans);
        return response;
    }

    public GetAllServiceOfferingPlanBitsResponse getServiceOfferingPlanBits(GetServiceOfferingPlanBitsRequest request) throws GatewayException {

        ServiceOfferingPlan plans = serviceOfferingPlansProxy.getServiceOfferingPlanBits(request);
        GetAllServiceOfferingPlanBitsResponse response = serviceOfferingPlanResponseMapper.extractServiceOfferingPlanBits(plans);
        return response;
    }

    public void addServiceOfferingPlan(AddServiceOfferingPlanRequest request) throws GatewayException {
        serviceOfferingPlansProxy.addServiceOfferingPlan(request);
    }

    public void updateServiceOfferingPlan(UpdateServiceOfferingPlanRequest request) throws GatewayException {
        serviceOfferingPlansProxy.updateServiceOfferingPlan(request);
    }


    public void deleteServiceOfferingPlan(DeleteServiceOfferingPlanRequest request) throws GatewayException {
        serviceOfferingPlansProxy.deleteServiceOfferingPlan(request);
    }

//    public GetAllServiceClassPlanDescriptionResponse getAllServiceClassPlanDesc(GetServiceClassServiceOfferingPlanDescs request) throws GatewayException {
//
//        ServiceOfferingPlan soSCDs = serviceOfferingPlansProxy.getAllServiceClassPlanDesc(request);
//        GetAllServiceClassPlanDescriptionResponse response = serviceOfferingPlanResponseMapper.mapGetAllServiceOfferingServiceClassDescriptionResponse(soSCDs);
//        return response;
//    }

//    public void addServiceClassPlanDesc(AddServiceClassPlanDescriptionRequest request) throws GatewayException {
//        serviceOfferingPlansProxy.addServiceClassPlanDesc(request);
//    }
//
//    public void updateServiceClassPlanDesc(UpdateServiceClassPlanDescriptionRequest request) throws GatewayException {
//        serviceOfferingPlansProxy.updateServiceClassPlanDesc(request);
//    }
//
//    public void deleteServiceClassPlanDesc(DeleteServiceClassPlanDescriptionRequest request) throws GatewayException {
//        serviceOfferingPlansProxy.deleteServiceClassPlanDesc(request);
//    }
}
