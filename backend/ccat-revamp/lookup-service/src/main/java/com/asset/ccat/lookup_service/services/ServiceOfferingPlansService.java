package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.database.dao.ServiceOfferingDao;
import com.asset.ccat.lookup_service.database.dao.ServiceOfferingPlansDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.requests.service_offering_plans.*;
import com.asset.ccat.lookup_service.models.responses.service_offering_description.GetAllServiceOfferingDescriptionResponse;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceOfferingPlansService {

    @Autowired
    ServiceOfferingPlansDao serviceOfferingPlansDao;
    @Autowired
    ServiceOfferingDao serviceOfferingDao;


    public List<ServiceOfferingPlan> getAllServiceOfferingPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all serviceOfferingPlans.");
        List<ServiceOfferingPlan> serviceOfferingPlans = serviceOfferingPlansDao.retrieveServiceOfferingPlans();

        if (serviceOfferingPlans == null || serviceOfferingPlans.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No serviceOfferingPlans were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "serviceOfferingPlans");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all serviceOfferingPlans with size[" + serviceOfferingPlans.size() + "].");
        return new ArrayList<ServiceOfferingPlan>(serviceOfferingPlans);
    }


    public ServiceOfferingPlan getAllServiceOfferingPlanBits(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all serviceOfferingPlan bits.");
        HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlanBits = serviceOfferingPlansDao.retrieveServiceOfferingPlanBits(planId);

        if (serviceOfferingPlanBits == null || serviceOfferingPlanBits.isEmpty()) {
            ServiceOfferingPlan plan = serviceOfferingPlansDao.getServiceOfferingPlan(planId).get(0);
            if (plan == null) {
                CCATLogger.DEBUG_LOGGER.error("No serviceOfferingPlanBits were found.");
                throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "serviceOfferingPlanBits");
            }
            return plan;
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all serviceOfferingPlanBits with size[" + serviceOfferingPlanBits.size() + "].");
        return new ArrayList<ServiceOfferingPlan>(serviceOfferingPlanBits.values()).get(0);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void addServiceOfferingPlan(AddServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding  ServiceOfferingPlan");

        CCATLogger.DEBUG_LOGGER.debug("Start add  ServiceOfferingPlan in ServiceOfferingPlan table");
        int isAdded = serviceOfferingPlansDao.addRecordToServiceOfferingPlan(request);
        CCATLogger.DEBUG_LOGGER.debug(" add  ServiceOfferingPlan in ServiceOfferingPlan table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start delete  ServiceOfferingPlanBits in ServiceOfferingPlanBits table");
        int isDeleted = serviceOfferingPlansDao.deleteBitsFromServiceOfferingPlanBits(request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" deleted  ServiceOfferingPlanBits in ServiceOfferingPlanBits table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start update  ServiceOfferingPlanBits in ServiceOfferingPlanBits table");
        int[] isUpdated = serviceOfferingPlansDao.addBitsToServiceOfferingPlanBits(request.getServiceOfferingPlanBits(), request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" updated  ServiceOfferingPlanBits in ServiceOfferingPlanBits table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Done adding all serviceOfferingPlan ");
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void updateServiceOfferingPlan(UpdateServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating  ServiceOfferingPlan");

        CCATLogger.DEBUG_LOGGER.debug("Start update  ServiceOfferingPlan in ServiceOfferingPlan table");
        int isUpdated = serviceOfferingPlansDao.updateRecordToServiceOfferingPlan(request);
        CCATLogger.DEBUG_LOGGER.debug(" update  ServiceOfferingPlan in ServiceOfferingPlan table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start delete  ServiceOfferingPlanBits in ServiceOfferingPlanBits table");
        int isDeleted = serviceOfferingPlansDao.deleteBitsFromServiceOfferingPlanBits(request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" deleted  ServiceOfferingPlanBits in ServiceOfferingPlanBits table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start update  ServiceOfferingPlanBits in ServiceOfferingPlanBits table");
        int[] isUpdated2 = serviceOfferingPlansDao.addBitsToServiceOfferingPlanBits(request.getServiceOfferingPlanBits(), request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" updated  ServiceOfferingPlanBits in ServiceOfferingPlanBits table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Done updating all serviceOfferingPlan ");
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void deleteServiceOfferingPlan(DeleteServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting  ServiceOfferingPlan");

        CCATLogger.DEBUG_LOGGER.debug("Start delete  ServiceOfferingPlan in ServiceOfferingPlan table");
        int isUpdated = serviceOfferingPlansDao.deleteServiceOfferingPlan(request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" delete  ServiceOfferingPlan in ServiceOfferingPlan table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start delete  ServiceOfferingPlanBits in ServiceOfferingPlanBits table");
        int isDeleted = serviceOfferingPlansDao.deleteBitsFromServiceOfferingPlanBits(request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" deleted  ServiceOfferingPlanBits in ServiceOfferingPlanBits table successfully.");


        CCATLogger.DEBUG_LOGGER.debug("Start delete  ServiceOffering Service class descriptions in SO-SC-DESC table");
        int isDeleted2 = serviceOfferingPlansDao.deleteServiceOfferingPlanFromSOSCTable(request.getPlanId());
        CCATLogger.DEBUG_LOGGER.debug(" deleted  ServiceOffering Service class descriptions in SO-SC-DESC table successfully.");

        CCATLogger.DEBUG_LOGGER.debug("Done deleting  serviceOfferingPlan ");
    }

//    public ServiceOfferingPlan getAllServiceOfferingServiceClassDescriptions(int planId) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Start retrieving All ServiceOfferingServiceClassDescriptions.");
//        HashMap<Integer, ServiceOfferingPlan> serviceOfferingServiceClassDescriptions = serviceOfferingPlansDao.retrieveServiceOfferingServiceClassDescriptions(planId);
//
//        if (serviceOfferingServiceClassDescriptions == null || serviceOfferingServiceClassDescriptions.isEmpty()) {
//            ServiceOfferingPlan plan = serviceOfferingPlansDao.getServiceOfferingPlan(planId).get(0);
//            if (plan == null) {
//                CCATLogger.DEBUG_LOGGER.error("No serviceOfferingPlans were found.");
//                throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "serviceOfferingPlans");
//            }
//            return plan;   }
//        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all serviceOfferingServiceClassDescriptions with size[" + serviceOfferingServiceClassDescriptions.size() + "].");
//        return new ArrayList<ServiceOfferingPlan>(serviceOfferingServiceClassDescriptions.values()).get(0);
//    }

//    public void addSOServiceClassDescription(AddServiceClassPlanDescriptionRequest request) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Start AddServiceClassPlanDescriptionRequest");
//
//        int isAdded = serviceOfferingPlansDao.addSOServiceClassDescription(request);
//        CCATLogger.DEBUG_LOGGER.debug(" add  ServiceOffering   ServiceClassDescription  successfully.");
//    }

//    public void updateSOServiceClassDescription(UpdateServiceClassPlanDescriptionRequest request) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Start UpdateServiceClassPlanDescriptionRequest");
//
//        int isAdded = serviceOfferingPlansDao.updateSOServiceClassDescription(request);
//        CCATLogger.DEBUG_LOGGER.debug(" update  ServiceOffering   ServiceClassDescription  successfully.");
//    }
//
//    public void deleteSOServiceClassDescription(DeleteServiceClassPlanDescriptionRequest request) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Start UpdateServiceClassPlanDescriptionRequest");
//
//        int isAdded = serviceOfferingPlansDao.deleteSOServiceClassDescription(request);
//        CCATLogger.DEBUG_LOGGER.debug(" delete  ServiceOffering   ServiceClassDescription  successfully.");
//    }


    public boolean isServiceClassExistWithPlanId(int planId, int serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start finding service class with planId: " + planId + " and serviceClassId: " + serviceClassId);
        return serviceOfferingPlansDao.findServiceClassDescFoeServiceOfferingPlan(planId, serviceClassId) > 0;

    }

    public ServiceOfferingPlan isServiceOfferingPlanExistWithPlanId(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start finding service offering plan with planId: " + planId);

        return serviceOfferingPlansDao.findServiceOfferingPlanWithId(planId);

    }

    public ServiceOfferingPlan isServiceOfferingPlanExistWithPlanName(String planName) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start finding service offering plan with planName: " + planName);

        return serviceOfferingPlansDao.findServiceOfferingPlanWithName(planName);

    }

    public HashMap<Integer, ServiceOfferingPlan> getAllServicePlansWithBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start getting sll service offering plan with bits ");

        return serviceOfferingPlansDao.retrieveServiceOfferingPlansWithBits();

    }

    public HashMap<Integer, ServiceOfferingPlanBitModel> getAllServicePlansBitsDetails() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start getAllServicePlansBitsDetails() ");
        HashMap<Integer, ServiceOfferingPlanBitModel> result = serviceOfferingDao.retrieveServiceOfferingPlanBitDetails();
        CCATLogger.DEBUG_LOGGER.debug("Ended getAllServicePlansBitsDetails Successfully ");

        return result;

    }

}
