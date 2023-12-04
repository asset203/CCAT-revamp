/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.row_mapper.BusinessPlanRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import com.asset.ccat.lookup_service.util.Utils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class BusinessPlanDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Utils utils;
    private String retrieveBusinessPlans;
    private String addBusinessPlanSeqQuery;
    private String deleteBusinessPlanByIdQuery;
    private String retrieveBusinessPlanByIdQuery;
    private String updateBusinessPlanQuery;

    @LogExecutionTime
    public List<BusinessPlanModel> retrieveBusinessPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting BusinessPlanDAO - retrieveBusinessPlans");

        List<BusinessPlanModel> businessPlans;

        try {
            if (retrieveBusinessPlans == null) {
                retrieveBusinessPlans
                        = "Select  "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.ID
                        + ","
                        + DatabaseStructs.ADM_BUSINESS_PLANS.NAME
                        + ","
                        + DatabaseStructs.ADM_BUSINESS_PLANS.CODE
                        + ","
                        + DatabaseStructs.ADM_BUSINESS_PLANS.SERVICE_CLASS_ID
                        + ","
                        + DatabaseStructs.ADM_BUSINESS_PLANS.HLR_ID
                        + " From " + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + " Where " + DatabaseStructs.ADM_BUSINESS_PLANS.IS_DELETED + " = 0"
                        + " Order By Lower(" + DatabaseStructs.ADM_BUSINESS_PLANS.NAME + ")";
            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveBusinessPlans);

            businessPlans = jdbcTemplate.query(retrieveBusinessPlans, new BusinessPlanRowMapper());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending BusinessPlanDAO - retrieveBusinessPlans");
        return businessPlans;
    }

    @LogExecutionTime
    public int addBusinessPlan(BusinessPlanModel businessPlan, Integer businessPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting BusinessPlanDAO - addBusinessPlan");

//        String name = utils.checkString(businessPlan.getBusinessPlanName());

        try {
            if (addBusinessPlanSeqQuery == null) {
                addBusinessPlanSeqQuery
                        = "INSERT INTO "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + "(" + DatabaseStructs.ADM_BUSINESS_PLANS.ID
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.NAME
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.CODE
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.HLR_ID
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.SERVICE_CLASS_ID
                        + ") "
                        + "values ( ?, ? , ? , ? , ? )";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addBusinessPlanSeqQuery);

            CCATLogger.DEBUG_LOGGER.debug("Ending BusinessPlanDAO - addBusinessPlan");

            return jdbcTemplate.update(addBusinessPlanSeqQuery,
                    businessPlanId,
                    businessPlan.getBusinessPlanName(),
                    businessPlan.getBusinessPlanCode(),
                    businessPlan.getHlrProfileId(),
                    businessPlan.getServiceClassId());

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

    @LogExecutionTime
    public boolean deleteBusinessPlan(int businessPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting BusinessPlanDAO - deleteBusinessPlan");

        try {
            if (deleteBusinessPlanByIdQuery == null) {
                deleteBusinessPlanByIdQuery
                        = "update "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + " set "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.IS_DELETED + " = 1 "
                        + " where "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.ID + " = ? ";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteBusinessPlanByIdQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending BusinessPlanDAO - deleteBusinessPlan");
            return jdbcTemplate.update(deleteBusinessPlanByIdQuery, businessPlanId) != 0;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public BusinessPlanModel retrieveBusinessPlanById(int businessPlanId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting BusinessPlanDAO - retrieveBusinessPlan");

        BusinessPlanModel businessPlanModel;
        try {
            if (retrieveBusinessPlanByIdQuery == null) {
                retrieveBusinessPlanByIdQuery = "Select  "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.ID
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.NAME
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.CODE
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.HLR_ID
                        + "," + DatabaseStructs.ADM_BUSINESS_PLANS.SERVICE_CLASS_ID
                        + " From "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + " Where " + DatabaseStructs.ADM_BUSINESS_PLANS.ID
                        + " = ? "
                        + " AND "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.IS_DELETED
                        + " = 0 ";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + retrieveBusinessPlanByIdQuery);

            businessPlanModel = jdbcTemplate.queryForObject(retrieveBusinessPlanByIdQuery, new BusinessPlanRowMapper(), businessPlanId);
        } catch (EmptyResultDataAccessException e) {
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

        CCATLogger.DEBUG_LOGGER.debug("Ending BusinessPlanDAO - retrieveBusinessPlan");

        return businessPlanModel;
    }

    @LogExecutionTime
    public int updateBusinessPlan(BusinessPlanModel businessPlan) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting BusinessPlanDAO - updateBusinessPlan");

        try {
            if (updateBusinessPlanQuery == null) {
                updateBusinessPlanQuery
                        = "update " + DatabaseStructs.ADM_BUSINESS_PLANS.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.NAME + " = ? ,"
                        + DatabaseStructs.ADM_BUSINESS_PLANS.HLR_ID + " = ? ,"
                        + DatabaseStructs.ADM_BUSINESS_PLANS.SERVICE_CLASS_ID + " = ? "
                        + " where "
                        + DatabaseStructs.ADM_BUSINESS_PLANS.ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateBusinessPlanQuery);

            CCATLogger.DEBUG_LOGGER.debug("Ending BusinessPlanDAO - updateServiceClass");
            return jdbcTemplate.update(updateBusinessPlanQuery,
//                    utils.checkString(businessPlan.getBusinessPlanName()),
                    businessPlan.getBusinessPlanName(),
                    businessPlan.getHlrProfileId(),
                    businessPlan.getServiceClassId(),
                    businessPlan.getBusinessPlanId());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

}
