/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.services;

import java.util.List;

import com.asset.ccat.lookup_service.models.responses.business_plan.GetDeletedBusinessPlansResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.database.dao.BusinessPlanDao;
import com.asset.ccat.lookup_service.database.dao.SequenceTableDao;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.Defines.SEVERITY;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import com.asset.ccat.lookup_service.models.responses.business_plan.GetAllBusinessPlansResponse;
import com.asset.ccat.lookup_service.models.responses.business_plan.GetBusinessPlanResponse;

/**
 *
 * @author wael.mohamed
 */
@Service
public class BusinessPlanService {

    @Autowired
    private BusinessPlanDao businessPlanDao;

    @Autowired
    private SequenceTableDao sequenceTableDao;

    public GetAllBusinessPlansResponse getAllBusinessPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all businessPlans.");
        List<BusinessPlanModel> plans = businessPlanDao.retrieveBusinessPlans();
        if (plans == null || plans.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No businessPlans were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, SEVERITY.ERROR, "businessPlan");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all businessPlans with size[" + plans.size() + "].");
        return new GetAllBusinessPlansResponse(plans);
    }

    public GetBusinessPlanResponse getBusinessPlanById(int businessPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving businessPlans with ID[" + businessPlanId + "].");
        BusinessPlanModel businessPlan = businessPlanDao.retrieveBusinessPlanById(businessPlanId);
        if (businessPlan == null) {
            CCATLogger.DEBUG_LOGGER.error("BusinessPlan with id [" + businessPlanId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, Defines.SEVERITY.ERROR, "businessPlanId " + businessPlanId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving businessPlan with ID[" + businessPlanId + "].");
        return new GetBusinessPlanResponse(businessPlan);
    }

    public void addBusinessPlan(BusinessPlanModel businessPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding businessPlans with Code[" + businessPlan.getBusinessPlanCode() + "].");
        Integer businessPlanId = sequenceTableDao.getNextId(DatabaseStructs.SEQUENCE.S_ADM_BUSINESS_PLANS);
        int isAdded = businessPlanDao.addBusinessPlan(businessPlan, businessPlanId);
        if (isAdded <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add businessPlan.");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "businessPlanId " + businessPlanId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding businessPlan with ID[" + businessPlanId + "].");
    }

    public void deleteBusinessPlan(int businessPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting businessPlans with ID[" + businessPlanId + "].");
        boolean isDeleted = businessPlanDao.deleteBusinessPlan(businessPlanId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete businessPlan.");
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "businessPlanId " + businessPlanId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting businessPlan with ID[" + businessPlanId + "].");
    }

    public void updateBusinessPlan(BusinessPlanModel businessPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating businessPlans with ID[" + businessPlan.getBusinessPlanId() + "].");
        int isUpdated = businessPlanDao.updateBusinessPlan(businessPlan);
        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add businessPlan.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "businessPlanId " + businessPlan.getBusinessPlanId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding businessPlan with ID[" + businessPlan.getBusinessPlanId() + "].");
    }

    public GetDeletedBusinessPlansResponse getDeletedBusinessPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all deleted businessPlans.");
        List<BusinessPlanModel> plans = businessPlanDao.retrieveDeletedBusinessPlans();
        if (plans == null || plans.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("No deleted businessPlans were found.");

        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all deleted businessPlans with size[" + plans.size() + "].");
        return new GetDeletedBusinessPlansResponse(plans);
    }
}
