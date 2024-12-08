package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.FafPlansExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.FafPlanRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.FAFIndicatorModel;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class FafPlansDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FafPlansExtractor fafPlansExtractor;

    private String retrieveFafPlansQuery;

    private String getAllFafPlansQuery;


    private String updateFafPlanQuery;

    private String deleteFafPlanQuery;

    private String addFafPlanQuery;

    private String findFafPlanByIdQuery;

    private String findFafPlanByDescQuery;
    private String findFafPIndicatorQuery;

    public List<FafPlanModel> getAllFafPlans() throws LookupException {

        CCATLogger.DEBUG_LOGGER.info("Starting FafPlanDao - getAllFafPlans");

        List<FafPlanModel> fafPlans;

        try {
            if (getAllFafPlansQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("Select * From ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME).
                        append(" Where ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED).append("= ?")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.NAME)
                        .append(" ASC ");
                getAllFafPlansQuery = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + getAllFafPlansQuery);

            fafPlans = jdbcTemplate.query(getAllFafPlansQuery.toString(), new FafPlanRowMapper(), false);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.info("result is " + fafPlans);
        CCATLogger.DEBUG_LOGGER.debug("Ending FafPlansDao - getAllFafPlans");
        return fafPlans;

    }

    public HashMap<Integer, FafPlanModel> retrieveAllFafPlans() {
        HashMap<Integer, FafPlanModel> plans = null;
        try {
            if (retrieveFafPlansQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT plans.").append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID)
                        .append(" , plans.").append(DatabaseStructs.ADM_FAF_PLANS.NAME)
                        .append(" , plans.").append(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID)
                        .append(" , indicators.").append(DatabaseStructs.ADM_FAF_INDICATORS.MAPPED_INDICATOR_ID)
                        .append(" FROM ").append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME).append(" plans ")
                        .append(" LEFT JOIN ").append(DatabaseStructs.ADM_FAF_INDICATORS.TABLE_NAME).append(" indicators ")
                        .append(" ON ")
                        .append(" plans.").append(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID)
                        .append(" = ")
                        .append(" indicators.").append(DatabaseStructs.ADM_FAF_INDICATORS.INDICATOR_ID)
                        .append(" WHERE ")
                        .append(" plans.").append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED).append(" = 0 ")
                        .append(" ORDER BY ").append("plans.").append(DatabaseStructs.ADM_FAF_PLANS.NAME);
                retrieveFafPlansQuery = queryBuilder.toString();
            }
            plans = jdbcTemplate.query(retrieveFafPlansQuery, fafPlansExtractor);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving FAF plans " + retrieveFafPlansQuery);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FAF plans " + retrieveFafPlansQuery, ex);
        }
        return plans;
    }


    @LogExecutionTime
    public int updateFafPlan(FafPlanModel fafPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FafPlansDao - updateFafPlan");

        try {
            if (updateFafPlanQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_FAF_PLANS.NAME).append(" = ? ,")
                        .append(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID).append(" = ? ")
                        .append(" WHERE ").append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED).append("= 0");
                updateFafPlanQuery = query.toString();
            }
            int res = jdbcTemplate.update(updateFafPlanQuery.toString(),
                    fafPlan.getName(),
                    fafPlan.getFafIndicatorId(),
                    fafPlan.getPlanId()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateFafPlanQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending FafPlansDao - updateFafPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteFafPlan(int fafPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FafPlansDao - deleteFafPlan");

        try {
            if (deleteFafPlanQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ").append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED)
                        .append(" = ? ").append(" WHERE ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID).append(" = ?");
                deleteFafPlanQuery = query.toString();
            }
            int res = jdbcTemplate.update(deleteFafPlanQuery.toString(),
                    1,
                    fafPlanId
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteFafPlanQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending FafPlansDao - deleteFafPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int addFafPlan(FafPlanModel fafPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting FafPlansDao - addFafPlan");

        try {
            if (addFafPlanQuery == null) {
                StringBuilder query = new StringBuilder();
                query
                        .append("INSERT INTO ").append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID)
                        .append(",").append(DatabaseStructs.ADM_FAF_PLANS.INDICATOR_ID)
                        .append(",").append(DatabaseStructs.ADM_FAF_PLANS.NAME)
                        .append(") ")
                        .append("values ( ?,?,?)");
                addFafPlanQuery = query.toString();
            }
            int res = jdbcTemplate.update(addFafPlanQuery.toString(),
                    fafPlan.getPlanId(),
                    fafPlan.getFafIndicatorId(),
                    fafPlan.getName()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addFafPlanQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending FafPlansDao - addFafPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public Integer findFafPlanById(int fafPlanId) throws LookupException {
        try {
            if (findFafPlanByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(1) FROM ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED).append("= 0");
                findFafPlanByIdQuery = query.toString();

            }
            return jdbcTemplate.queryForObject(findFafPlanByIdQuery.toString(), Integer.class,
                    fafPlanId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findFafPlanByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findFafPlanByIdQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<FafPlanModel> findFafPlan(FafPlanModel fafPlanModel) throws LookupException {
        try {
            if (findFafPlanByDescQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.NAME).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.IS_DELETED).append("= 0");
                findFafPlanByDescQuery = query.toString();

            }
            return jdbcTemplate.query(findFafPlanByDescQuery.toString(), new FafPlanRowMapper(),
                    fafPlanModel.getName());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findFafPlanByDescQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findFafPlanByDescQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public List<FAFIndicatorModel> findFAFIndicators() throws LookupException {
        try {
            if(findFafPIndicatorQuery == null)
                findFafPIndicatorQuery = "SELECT * FROM " +
                        DatabaseStructs.ADM_FAF_INDICATORS.TABLE_NAME +
                        " WHERE " +
                        DatabaseStructs.ADM_FAF_INDICATORS.IS_DELETED + " = 0" +
                        " ORDER BY " +
                        DatabaseStructs.ADM_FAF_INDICATORS.INDICATOR_NAME;
            return jdbcTemplate.query(findFafPIndicatorQuery, new BeanPropertyRowMapper<>(FAFIndicatorModel.class));
        } catch (DataAccessException ex){
            return new ArrayList<>();
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while finding faf indicators. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while finding faf indicators. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

}
