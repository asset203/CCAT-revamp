package com.asset.ccat.lookup_service.database.dao;


import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.constants.ActivityType;
import com.asset.ccat.lookup_service.database.extractors.call_activties.CallActivitiesParentsMapExtractor;
import com.asset.ccat.lookup_service.database.extractors.call_activties.CallActivtiesMapExtractor;
import com.asset.ccat.lookup_service.database.extractors.call_activties.ReasonActivityIdMapExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.ReasonActivityMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ReasonActivityFlatModel;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReasonActivityDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CallActivitiesParentsMapExtractor callActivitiesParentsMapExtractor;

    @Autowired
    private CallActivtiesMapExtractor callActivtiesMapExtractor;

    @Autowired
    private ReasonActivityIdMapExtractor reasonActivityIdMapExtractor;

    private String retrieveActivityReasonsWithType;

    private String retrieveAllCallActivities;

    private String addReasonActivityQuery;

    private String updateReasonActivityQuery;

    private String getNextActivityId;

    private String deleteReasonActivity;

    private String findReasonActivity;


    @LogExecutionTime
    public HashMap<Integer, ReasonActivityModel> retrieveActivityReasonsByType(String type, Integer parentId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ReasonActivityDao - retrieveActivityReasons");

        HashMap<Integer, ReasonActivityModel> reasons;

        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("Select * From ")
                    .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                    .append(" WHERE ")
                    .append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE)
                    .append(" =1 ");
            if (parentId != null) {
                queryBuilder.append(" AND ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID)
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_TYPE)
                        .append(" = ?");
            }
            queryBuilder.append(" Order By ")
                    .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME)
                    .append(" ASC ");


            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + queryBuilder.toString());

            if (parentId != null) {
                reasons = jdbcTemplate.query(queryBuilder.toString(), reasonActivityIdMapExtractor, parentId, type);
            } else {
                reasons = jdbcTemplate.query(queryBuilder.toString(), reasonActivityIdMapExtractor);
            }

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.info("result is " + reasons);
        CCATLogger.DEBUG_LOGGER.debug("Ending ReasonActivityDao - retrieveActivityReasons");
        return reasons;

    }

    public HashMap<String, List<ReasonActivityModel>> retrieveActivitiesParentsMap() {
        HashMap<String, List<ReasonActivityModel>> result = null;
        try {
            if (retrieveAllCallActivities == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE).append("= 1");
                retrieveAllCallActivities = query.toString();
            }
            result = jdbcTemplate.query(retrieveAllCallActivities, callActivitiesParentsMapExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllCallActivities);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllCallActivities, ex);
        }
        return result;
    }

    public HashMap<String, ReasonActivityModel> retrieveAllActivitiesAsMap() {
        HashMap<String, ReasonActivityModel> result = null;
        try {
            if (retrieveAllCallActivities == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE).append("= 1");
                retrieveAllCallActivities = query.toString();
            }
            result = jdbcTemplate.query(retrieveAllCallActivities, callActivtiesMapExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveAllCallActivities);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveAllCallActivities, ex);
        }
        return result;
    }

    @LogExecutionTime
    public int addReasonActivity(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ReasonActivityDao - addReasonActivity()");
        Integer activityId;
        try {
            if (addReasonActivityQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append("(")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID)
                        .append(", ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_TYPE)
                        .append(", ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID)
                        .append(", ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME)
                        .append(", ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE)
                        .append(") ")
                        .append(" values(").append(DatabaseStructs.ADM_REASON_ACTIVITY.S_ADM_REASON_ACTIVITY)
                        .append(".NEXTVAL ,?,?,?,1)");
                addReasonActivityQuery = query.toString();

            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addReasonActivityQuery);
//            int res = jdbcTemplate.update(addReasonActivityQuery, reason.getActivityId(), reason.getActivityType().name(), reason.getParentId(), reason.getActivityName());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int res = jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        addReasonActivityQuery, new String[]{DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID});
                ps.setString(1, reason.getActivityType().name());
                ps.setInt(2, reason.getParentId());
                ps.setString(3, reason.getActivityName());
                return ps;
            }, keyHolder);

            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            activityId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            reason.setActivityId(activityId);
            CCATLogger.DEBUG_LOGGER.debug("Ending ReasonActivityDao - addReasonActivity()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int updateReasonActivity(ReasonActivityModel reason) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ReasonActivityDao - updateReasonActivity()");

        try {
            if (updateReasonActivityQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME)
                        .append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID)
                        .append(" = ? ");

                updateReasonActivityQuery = query.toString();

            }
            int res = jdbcTemplate.update(updateReasonActivityQuery, reason.getActivityName(), reason.getActivityId());
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateReasonActivityQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ReasonActivityDao - updateReasonActivity()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

//    @LogExecutionTime
//    public Integer getReasonActivityId() throws LookupException {
//
//        int activityId;
//        try {
//
//            if (getNextActivityId == null) {
//                StringBuilder query = new StringBuilder();
//
//                query.append("SELECT ")
//                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.S_ADM_REASON_ACTIVITY)
//                        .append(".NEXTVAL FROM DUAL");
//                getNextActivityId = query.toString();
//
//            }
//            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + getNextActivityId);
//
//            activityId = jdbcTemplate.queryForObject(getNextActivityId, Integer.class);
//        } catch (Exception e) {
//            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
//            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
//            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
//
//        }
//        CCATLogger.DEBUG_LOGGER.info("result is " + activityId);
//        CCATLogger.DEBUG_LOGGER.debug("Ending ReasonActivityDao - getReasonActivityId()");
//        return activityId;
//    }


    @LogExecutionTime
    public int deleteReasonActivity(List<Integer> deletedReasonsIds) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ReasonActivityDao - deleteReasonActivity()");

        try {
            if (deleteReasonActivity == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE)
                        .append(" = 0 ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_ID)
                        .append(" IN  ")
                        .append("(%s)");

                deleteReasonActivity = query.toString();

            }
            String inParams = String.join(",", deletedReasonsIds.stream().map(id -> "?").collect(Collectors.toList()));
            int res = jdbcTemplate.update(String.format(deleteReasonActivity, inParams), deletedReasonsIds.toArray());
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteReasonActivity);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ReasonActivityDao - deleteReasonActivity()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public ReasonActivityModel findReasonActivity(ReasonActivityModel reason) throws LookupException {

        try {
            if (findReasonActivity == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.ACTIVITY_NAME).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.PARENT_ID).append("= ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_REASON_ACTIVITY.IS_ACTIVE).append("= 1");
                findReasonActivity = query.toString();

            }
            return jdbcTemplate.queryForObject(findReasonActivity, new ReasonActivityMapper(),
                    reason.getActivityName(), reason.getParentId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findReasonActivity);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findReasonActivity, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    public void insertActivityReasonsTree(ArrayList<ReasonActivityFlatModel> reasonActivityFlatList, HashMap<String, ReasonActivityModel> cachedActivities) throws LookupException {
        String directionActivityKey;
        String familyActivityKey;
        String typeActivityKey;
        String reasonActivityKey;

        ReasonActivityModel directionActivity;
        ReasonActivityModel familyActivity;
        ReasonActivityModel typeActivity;
        ReasonActivityModel reasonActivity;

        int directionId;
        int familyId = -1;
        int typeId = -1;

        for (ReasonActivityFlatModel flatActivity : reasonActivityFlatList) {

            // Adding direction if not exist else get from cache
            directionActivityKey = flatActivity.getDirectionName() + "-" + ActivityType.DIRECTION.name() + "-" + "0";
            if (cachedActivities.get(directionActivityKey) == null) {
                CCATLogger.DEBUG_LOGGER.debug("Adding new direction activity [" + directionActivityKey + "]");
                directionActivity = new ReasonActivityModel(ActivityType.DIRECTION, 0, flatActivity.getDirectionName());
                addReasonActivity(directionActivity);
                directionId = directionActivity.getActivityId();
                cachedActivities.put(directionActivityKey, directionActivity);

            } else {
                CCATLogger.DEBUG_LOGGER.debug("Direction activity [" + directionActivityKey + "] already exists");
                directionActivity = cachedActivities.get(directionActivityKey);
                directionId = directionActivity.getActivityId();
            }

            // Adding family if not exist else get from cache
            if (flatActivity.getFamilyName() != null && !flatActivity.getFamilyName().isEmpty()) {
                familyActivityKey = flatActivity.getFamilyName() + "-" + ActivityType.FAMILY.name() + "-" + directionId;

                if (cachedActivities.get(familyActivityKey) == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Adding new family activity [" + familyActivityKey + "]");
                    familyActivity = new ReasonActivityModel(ActivityType.FAMILY, directionId, flatActivity.getFamilyName());
                    addReasonActivity(familyActivity);
                    familyId = familyActivity.getActivityId();
                    cachedActivities.put(familyActivityKey, familyActivity);

                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Family activity [" + familyActivityKey + "] already exists");
                    familyActivity = cachedActivities.get(familyActivityKey);
                    familyId = familyActivity.getActivityId();
                }
            }

            // Adding type if not exist else get from cache
            if (flatActivity.getTypeName() != null && !flatActivity.getTypeName().isEmpty()) {
                typeActivityKey = flatActivity.getTypeName() + "-" + ActivityType.TYPE.name() + "-" + familyId;

                if (cachedActivities.get(typeActivityKey) == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Adding new type activity [" + typeActivityKey + "]");
                    typeActivity = new ReasonActivityModel(ActivityType.TYPE, familyId, flatActivity.getTypeName());
                    addReasonActivity(typeActivity);
                    typeId = typeActivity.getActivityId();
                    cachedActivities.put(typeActivityKey, typeActivity);

                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Type activity [" + typeActivityKey + "] already exists");
                    typeActivity = cachedActivities.get(typeActivityKey);
                    typeId = typeActivity.getActivityId();
                }
            }

            // Adding reason if not exist else get from cache
            if (flatActivity.getReasonName() != null && !flatActivity.getReasonName().isEmpty()) {
                reasonActivityKey = flatActivity.getReasonName() + "-" + ActivityType.REASON.name() + "-" + typeId;

                if (cachedActivities.get(reasonActivityKey) == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Adding new reason [" + reasonActivityKey + "]");
                    reasonActivity = new ReasonActivityModel(ActivityType.REASON, typeId, flatActivity.getReasonName());
                    addReasonActivity(reasonActivity);
                    cachedActivities.put(reasonActivityKey, reasonActivity);
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Reason [" + reasonActivityKey + "] already exists");
                }
            }
        }
    }
}
