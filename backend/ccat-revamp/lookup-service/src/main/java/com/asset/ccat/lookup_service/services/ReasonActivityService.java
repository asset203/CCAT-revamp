package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.database.dao.ReasonActivityDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ReasonActivityFlatModel;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import com.asset.ccat.lookup_service.models.requests.call_activity_admin.UploadActivitiesFileRequest;
import com.asset.ccat.lookup_service.validators.ReasonActivityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ReasonActivityService {


    @Autowired
    ReasonActivityDao reasonActivityDao;

//    @Autowired
//    ReasonActivityValidator reasonActivityValidator;

    @Autowired
    CAFileExportService caFileExportService;

    @Autowired
    CAFileUploadService caFileUploadService;


    public List<ReasonActivityModel> getAllReasonsWithType(String type, Integer parentId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all activities reasons.");
        List<ReasonActivityModel> activityReasons = new ArrayList<>(reasonActivityDao.retrieveActivityReasonsByType(type, parentId).values());

        if (activityReasons == null || activityReasons.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No call reason activities were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "callReasonActivities");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all serviceOfferingPlans with size[" + activityReasons.size() + "].");
        return activityReasons;
    }

    public void updateReasonActivity(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating reason activity");
        this.validateUpdateReasonActivityRequest(reason);
        int isUpdated = reasonActivityDao.updateReasonActivity(reason);

        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update  reason activity");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "activityId: " + reason.getActivityId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating reason activity ");

    }


    public void addReasonActivity(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding reason activity");
        this.validateAddReasonActivityRequest(reason);
//        int activityId = reasonActivityDao.getReasonActivityId();
//        reason.setActivityId(activityId);
        int isAdded = reasonActivityDao.addReasonActivity(reason);
        if (isAdded <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add  reason activity");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "activityName");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating reason activity ");

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void deleteReasonActivity(Integer activityId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting reason activity");
        List<ReasonActivityModel> activityReasons = new ArrayList<>(reasonActivityDao.retrieveActivityReasonsByType("none", null).values());
        List<Integer> deletedReasonIds = new ArrayList<>();
        deletedReasonIds.add(activityId);
        activityReasons.forEach((key) -> {
            CCATLogger.DEBUG_LOGGER.debug("all is: " + key + key.getParents().contains(activityId));
            if (key.getParents().contains(activityId))
                deletedReasonIds.add(key.getActivityId());
        });

        CCATLogger.DEBUG_LOGGER.debug("result is: " + deletedReasonIds);
        // call the delete dao
        reasonActivityDao.deleteReasonActivity(deletedReasonIds);
        CCATLogger.DEBUG_LOGGER.debug("Done deleting reason activity ");

    }

    public ReasonActivityModel findReason(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start finding ReasonActivity: " + reason.getActivityName() + " and parentId: " + reason.getParentId());
        ReasonActivityModel existedReason = reasonActivityDao.findReasonActivity(reason);
        return existedReason;

    }

    public List sortActivitiesDescByActivityType(HashMap<Integer, ReasonActivityModel> activities) {
        List<ReasonActivityModel> sorted = new ArrayList<>();
        for (int i = 4; i >= 1; i--) {
            for (ReasonActivityModel act : activities.values()) {
                if (act.getActivityType().id == i)
                    sorted.add(act);
            }
            //       CCATLogger.DEBUG_LOGGER.info(sorted);
        }
        return sorted;
    }


    public String addParentsNameToTheRow(String row, List<Integer> parents, HashMap<Integer, ReasonActivityModel> acts) {
        CCATLogger.DEBUG_LOGGER.info("start: addParentsNameToTheRow");
        for (Integer parentId : parents) {
            CCATLogger.DEBUG_LOGGER.info("start: concat" + parentId + "to row");

            if (Objects.nonNull(acts.get(parentId)) && parentId != 0)
                row = row.concat(", " + acts.get(parentId).getActivityName());

            CCATLogger.DEBUG_LOGGER.info("end: concat" + parentId + "to row");
        }
        return row;
    }

    public byte[] exportActivities() throws LookupException {
        ArrayList<String> headers = new ArrayList<String>(Arrays.asList("Direction", "Family", "Type", "Reason"));
        HashMap<Integer, ReasonActivityModel> activities = reasonActivityDao.retrieveActivityReasonsByType("none", null);
        if (activities == null || activities.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("No call reason activities found, export finished");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        List<ReasonActivityModel> sortedDescByActivityType = sortActivitiesDescByActivityType(activities);
        List<Integer> importedActivities = new ArrayList<>();
        List<String> rows = new ArrayList<>();

        CCATLogger.DEBUG_LOGGER.info("exportActivities: Start inserting rows");
        CCATLogger.DEBUG_LOGGER.info(sortedDescByActivityType);

        for (ReasonActivityModel value : sortedDescByActivityType) {
            CCATLogger.DEBUG_LOGGER.info("start:" + value);

            String row = "";
            CCATLogger.DEBUG_LOGGER.info("reason");
            if (value.getActivityType().id == 4) {
                row = row.concat(value.getActivityName());
                importedActivities.add(value.getActivityId());
                importedActivities.addAll(value.getParents());

            } else {
                if (!importedActivities.contains(value.getActivityId())) {
                    row = row.concat(value.getActivityName());
                    importedActivities.add(value.getActivityId());
                    importedActivities.addAll(value.getParents());
                }
            }
            CCATLogger.DEBUG_LOGGER.info("end:" + value);
            if (!row.equals("")) {
                row = addParentsNameToTheRow(row, value.getParents(), activities);
                rows.add(row);
            }
        }
        byte[] content = caFileExportService.exportAsExcel(headers, "activities", rows);
        return content;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void uploadActivities(UploadActivitiesFileRequest uploadActivitiesFileRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Started ReasonActivityService - uploadActivities()");
        CCATLogger.DEBUG_LOGGER.info("Start serving upload call activities request");

        CCATLogger.DEBUG_LOGGER.debug("Start parsing call activties file [" + uploadActivitiesFileRequest.getFileName() + "]");
        ArrayList<ReasonActivityFlatModel> activitiesFlatList = caFileUploadService.uploadCallActivityFile(uploadActivitiesFileRequest.getFile(), uploadActivitiesFileRequest.getFileExt());
        CCATLogger.DEBUG_LOGGER.debug("Finished parsing file with [" + (activitiesFlatList.size()) + "] number of rows");

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all activities map");
        HashMap<String, ReasonActivityModel> cachedActivities = reasonActivityDao.retrieveAllActivitiesAsMap();
        CCATLogger.DEBUG_LOGGER.debug("Finished retrieving all activities map");

        CCATLogger.DEBUG_LOGGER.debug("Start inserting activities in database");
        reasonActivityDao.insertActivityReasonsTree(activitiesFlatList, cachedActivities);
        CCATLogger.DEBUG_LOGGER.debug("Finished inserting activities in database");

        CCATLogger.DEBUG_LOGGER.info("Finished serving upload call activities request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended ReasonActivityService - uploadActivities()");
    }
    public void validateAddReasonActivityRequest(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating AddActivityReasonRequest");
        ReasonActivityModel existedReason = this.findReason(reason);
        if (existedReason != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating AddActivityReasonRequest failed , reason activity with ACTIVITY_NAME [" + reason.getActivityName());
            throw new LookupException(ErrorCodes.ERROR.ALREADY_EXIST, Defines.SEVERITY.VALIDATION, reason.getActivityType().name + " NAME");
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  AddActivityReasonRequest  successfully");
    }


    public void validateUpdateReasonActivityRequest(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating UpdateReasonActivityRequest");
        ReasonActivityModel existedReason = this.findReason(reason);
        if (existedReason != null) {
            CCATLogger.DEBUG_LOGGER.debug("Validating UpdateReasonActivityRequest failed , reason activity with name [" + reason.getActivityName());
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION, " reason activity with name " + reason.getActivityName());
        }

        CCATLogger.DEBUG_LOGGER.info("Finished validating  UpdateReasonActivityRequest  successfully");
    }
}
