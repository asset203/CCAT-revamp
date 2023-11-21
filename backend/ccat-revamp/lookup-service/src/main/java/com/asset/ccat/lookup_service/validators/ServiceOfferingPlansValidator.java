package com.asset.ccat.lookup_service.validators;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.requests.service_offering_plans.*;
import com.asset.ccat.lookup_service.services.ServiceOfferingPlansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ServiceOfferingPlansValidator {
    @Autowired
    ServiceOfferingPlansService serviceOfferingPlansService;


    public void validateAddServiceClassPlanDescriptionRequest(AddServiceClassPlanDescriptionRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating AddServiceClassPlanDescriptionRequest");
        if (serviceOfferingPlansService.isServiceClassExistWithPlanId(request.getPlanId(), request.getServiceClassId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddServiceClassPlanDescriptionRequest failed , service offering plan with PLAN_ID [" + request.getPlanId() + "] has description for the same classId: " + request.getServiceClassId());
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "service offering plan with planId: " + request.getPlanId() + " and classId: " + request.getServiceClassId());
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  AddServiceClassPlanDescriptionRequest  successfully");
    }

    public void isUpdateSOSCDescriptionValid(UpdateServiceClassPlanDescriptionRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating UpdateServiceClassPlanDescriptionRequest");
        if (!serviceOfferingPlansService.isServiceClassExistWithPlanId(request.getPlanId(), request.getServiceClassId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating UpdateServiceClassPlanDescriptionRequest failed , service offering plan with PLAN_ID [" + request.getPlanId() + "] does not have description for service class with classId: " + request.getServiceClassId());
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, Defines.SEVERITY.VALIDATION, "service offering plan with planId:" + request.getPlanId() + "does not have description for service class with  classId: " + request.getServiceClassId());
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  UpdateServiceClassPlanDescriptionRequest  successfully");
    }

    public void isDeleteSOSCDescriptionValid(DeleteServiceClassPlanDescriptionRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating DeleteServiceClassPlanDescriptionRequest");
        if (!serviceOfferingPlansService.isServiceClassExistWithPlanId(request.getPlanId(), request.getServiceClassId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating DeleteServiceClassPlanDescriptionRequest failed , service offering plan with PLAN_ID [" + request.getPlanId() + "] does not have description for service class with classId: " + request.getServiceClassId());
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.VALIDATION, "service offering plan with planId:" + request.getPlanId() + "does not have description for service class with  classId: " + request.getServiceClassId());
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  UpdateServiceClassPlanDescriptionRequest  successfully");
    }


    public void isAddServiceOfferingPlanValid(AddServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating AddServiceOfferingPlanRequest");
        if (serviceOfferingPlansService.isServiceOfferingPlanExistWithPlanId(request.getPlanId()) != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddServiceOfferingPlanRequest failed , service offering plan with PLAN_ID [" + request.getPlanId() + "] exists");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.VALIDATION, " (service offering plan with planId: " + request.getPlanId() + " exists) ");
        }

        if (serviceOfferingPlansService.isServiceOfferingPlanExistWithPlanName(request.getPlanName()) != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddServiceOfferingPlanRequest failed , service offering plan with PLAN_NAME [" + request.getPlanName() + "] exists");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.VALIDATION, " (service offering plan with planName: " + request.getPlanName() + " exists) ");
        }

        if (checkEnabledPlanBits(request.getServiceOfferingPlanBits(), request.getPlanId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddServiceOfferingPlanRequest failed , service offering plan with same plan bits exists");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.VALIDATION, " (service offering plan with same plan bits exists) ");
        }


        CCATLogger.DEBUG_LOGGER.info("Finished validating  AddServiceOfferingPlanRequest  successfully");
    }


    public void isUpdateServiceOfferingPlanValid(UpdateServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating UpdateServiceOfferingPlanRequest");
        if (serviceOfferingPlansService.isServiceOfferingPlanExistWithPlanId(request.getPlanId()) == null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating UpdateServiceOfferingPlanRequest failed , service offering plan with PLAN_ID [" + request.getPlanId() + "] is not found");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, Defines.SEVERITY.VALIDATION, " (service offering plan with planId: " + request.getPlanId() + ")");
        }
        if (serviceOfferingPlansService.isServiceOfferingPlanExistWithPlanName(request.getPlanName()) != null)
            if (serviceOfferingPlansService.isServiceOfferingPlanExistWithPlanName(request.getPlanName()).getPlanId() != request.getPlanId()) {
                CCATLogger.DEBUG_LOGGER.debug("Validating UpdateServiceOfferingPlanRequest failed , service offering plan with PLAN_NAME [" + request.getPlanName() + "] exists for other plan id");
                throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "Service Offering plan with planName:" + request.getPlanName());
            }
        if (checkEnabledPlanBits(request.getServiceOfferingPlanBits(), request.getPlanId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating UpdateServiceOfferingPlanRequest failed , service offering plan with same plan bits exists");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, "Service Offering Plan bits combination");
        }


        CCATLogger.DEBUG_LOGGER.info("Finished validating  UpdateServiceOfferingPlanRequest  successfully");
    }

    public boolean checkEnabledPlanBits(List<Integer> EnabledBits, Integer planId) throws LookupException {
        int decimalValue = 0;

        for (Integer bitPosition : EnabledBits) {
            decimalValue += Math.pow(2, bitPosition);

        }

        HashMap<Integer, ServiceOfferingPlan> allServiceOfferingPlans = this.serviceOfferingPlansService.getAllServicePlansWithBits();


        for (Integer id : allServiceOfferingPlans.keySet()) {

            if (allServiceOfferingPlans.get(id).getDecimalValue().equals(decimalValue) && !id.equals(planId)) {

                return true;
            }
        }
        return false;

    }

}