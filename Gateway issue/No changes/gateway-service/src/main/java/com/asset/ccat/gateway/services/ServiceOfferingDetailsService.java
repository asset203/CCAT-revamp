package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.mappers.ServiceOfferingPlanResponseMapper;
import com.asset.ccat.gateway.models.customer_care.ServiceOfferingPlanModel;
import com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models.ServiceOfferingPlanBitDetailsModel;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.GetServiceOfferingsRequest;
import com.asset.ccat.gateway.models.requests.customer_care.service_offering_plans.UpdateServiceOfferingRequest;
import com.asset.ccat.gateway.proxy.ServiceOfferingProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceOfferingDetailsService {
    private final LookupsService lookupsService;
    private final ServiceOfferingPlanResponseMapper serviceOfferingMapper;
    private final ServiceOfferingProxy serviceOfferingProxy;

    public ServiceOfferingDetailsService(LookupsService lookupsService, ServiceOfferingPlanResponseMapper serviceOfferingMapper, ServiceOfferingProxy serviceOfferingProxy) {
        this.lookupsService = lookupsService;
        this.serviceOfferingMapper = serviceOfferingMapper;
        this.serviceOfferingProxy = serviceOfferingProxy;
    }

    public List<ServiceOfferingPlanModel> getAllServiceOfferingPlansWithBitDetails(GetServiceOfferingsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDetailsService -> getAllServiceOfferingPlansWithBitDetails() : Started");
        CCATLogger.DEBUG_LOGGER.info("Start serving get all service offerings request");

        CCATLogger.DEBUG_LOGGER.debug("Getting current service offering model for subscriber [" + request.getMsisdn() + "]");
        ServiceOfferingPlanModel currentPlan = serviceOfferingProxy.getCurrentServiceOffering(request);

        CCATLogger.DEBUG_LOGGER.debug("Getting all service offering lookup");
        HashMap<Integer, ServiceOfferingPlanBitDetailsModel> lookup = lookupsService.getAllServiceOfferingPlansWithBitDetails();
        CCATLogger.DEBUG_LOGGER.debug("Mapping all service offering lookup response");
        List<ServiceOfferingPlanModel> mappedLookup = serviceOfferingMapper.serviceOfferingDetailsMapToServiceOfferingPlanList(lookup, request);

        //current offering must be at index zero
        List<ServiceOfferingPlanModel> responseList = new ArrayList<>();
        responseList.add(currentPlan);
        responseList.addAll(mappedLookup);

        CCATLogger.DEBUG_LOGGER.info("Finished serving get all service offerings request");
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDetailsService -> getAllServiceOfferingPlansWithBitDetails() : Ended Successfully");
        return responseList;
    }

    public void updateServiceOffering(UpdateServiceOfferingRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDetailsService -> updateServiceOffering() : Started");
        CCATLogger.DEBUG_LOGGER.info("Start serving update service offerings request");
        serviceOfferingProxy.updateServiceOffering(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update service offerings request");
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDetailsService -> updateServiceOffering() : Ended Successfully");
    }
}
