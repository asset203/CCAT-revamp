package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.database.dao.FafPlansDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FafPlansService {

 @Autowired
 FafPlansDao fafPlansDao;

 public List<FafPlanModel> getAllAdminFafPlans() throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Start retrieving all Admin FafPlans");
  List<FafPlanModel> fafPlans = fafPlansDao.getAllFafPlans();
  if (fafPlans == null || fafPlans.isEmpty()) {
   CCATLogger.DEBUG_LOGGER.error("No fafPlans were found.");
   throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "fafPlans");
  }
  CCATLogger.DEBUG_LOGGER.debug("Done retrieving all admin fafPlans with size[" + fafPlans.size() + "].");
  return fafPlans;
 }


 public void updateFafPlan(FafPlanModel fafPlan) throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Start updating fafPlan with PLAN_ID[" + fafPlan.getPlanId() + "].");
  int isUpdated = fafPlansDao.updateFafPlan(fafPlan);
  if (isUpdated <= 0) {
   CCATLogger.DEBUG_LOGGER.error("Failed to update fafPlan.");
   throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "planId " + fafPlan.getPlanId());
  }
  CCATLogger.DEBUG_LOGGER.debug("Done updating fafPlan with PLAN_ID[" + fafPlan.getPlanId() + "].");
 }



 public void deleteFafPlan(int fafPlanId) throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Start deleting fafPlan with PLAN_ID[" + fafPlanId + "].");
  int isDeleted = fafPlansDao.deleteFafPlan(fafPlanId);
  if (isDeleted <= 0) {
   CCATLogger.DEBUG_LOGGER.error("Failed to delete fafPlan.");
   throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "planId " + fafPlanId);
  }
  CCATLogger.DEBUG_LOGGER.debug("Done deleting fafPlan with planId[" + fafPlanId + "].");
 }

 public void addFafPlan(FafPlanModel fafPlan) throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Start adding fafPlan ]");
  int isAdded = fafPlansDao.addFafPlan(fafPlan);
  if (isAdded <= 0) {
   CCATLogger.DEBUG_LOGGER.error("Failed to add fafPlan.");
   throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "planId " + fafPlan.getPlanId());
  }
  CCATLogger.DEBUG_LOGGER.debug("Done adding fafPlan with PLAN_ID[" + fafPlan.getPlanId() + "].");
 }


 public Boolean isFafPlanIdExists(int fafPlanId) throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Checking if faf plan  with ID[" + fafPlanId+ "] exists in DB");
  return fafPlansDao.findFafPlanById(fafPlanId) > 0;
 }

 public Boolean isFafPlanDescExists(FafPlanModel fafPlan) throws LookupException {
  CCATLogger.DEBUG_LOGGER.debug("Checking if faf plan with Description[" + fafPlan.getName() +"] exists in DB");
  List<FafPlanModel> res =fafPlansDao.findFafPlan(fafPlan);
  if(res.size() == 0) return false;
   return (!res.get(0).getPlanId().equals(fafPlan.getPlanId()));
 }
}
