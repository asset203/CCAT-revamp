package com.asset.ccat.lookup_service.database.dao;


import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.ServiceOfferingPlansExtractor;
import com.asset.ccat.lookup_service.database.extractors.ServicePlanClassDescriptionsExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.ServiceOfferingPlanRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlanDescModel;
import com.asset.ccat.lookup_service.models.requests.service_offering_plans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class ServiceOfferingPlansDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retrieveServiceOfferingPlans;

    private String retrieveServiceOfferingPlanBits;

    private String retrieveServiceOfferingPlansWithBits;

    private String retrieveServiceOfferingServiceClassDescriptions;

    private String addRecordToServiceOfferingPlan;


    private String deleteServiceOfferingPlan;

    private String deleteBitsToServiceOfferingPlanBits;


    private String addBitsToServiceOfferingPlanBitsQuery;

    private String updateRecordToServiceOfferingPlan;

    private String deleteServiceOfferingPlanFromSOSCTable;

    private String findServiceClass;

    private String findServicePlanWithId;

    private String findServicePlanWithName;

    private String getServiceOfferingPlan;


    @LogExecutionTime
    public List<ServiceOfferingPlan> retrieveServiceOfferingPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingPlansDao - retrieveServiceOfferingPlans");

        List<ServiceOfferingPlan> serviceOfferingPlans;

        try {
            if (retrieveServiceOfferingPlans == null) {

                StringBuilder query = new StringBuilder();
                query.append("Select * From ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED)
                        .append(" = 0")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" ASC ");
                retrieveServiceOfferingPlans = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveServiceOfferingPlans);

            serviceOfferingPlans = jdbcTemplate.query(retrieveServiceOfferingPlans, new ServiceOfferingPlanRowMapper());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - retrieveServiceOfferingPlans");
        return serviceOfferingPlans;
    }


    @LogExecutionTime
    public List<ServiceOfferingPlan> getServiceOfferingPlan(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingPlansDao - retrieveServiceOfferingPlans");

        List<ServiceOfferingPlan> serviceOfferingPlans;

        try {
            if (getServiceOfferingPlan == null) {

                StringBuilder query = new StringBuilder();
                query.append("Select * From ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED)
                        .append(" = 0")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ")
                        .append(planId);

                getServiceOfferingPlan = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + getServiceOfferingPlan);

            serviceOfferingPlans = jdbcTemplate.query(getServiceOfferingPlan, new ServiceOfferingPlanRowMapper());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - retrieveServiceOfferingPlans");
        return serviceOfferingPlans;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlan> retrieveServiceOfferingPlanBits(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingPlansDao - retrieveServiceOfferingPlanBits");

        HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlanBits;

        try {
            if (retrieveServiceOfferingPlanBits == null) {

                StringBuilder query = new StringBuilder();
                query.append("SELECT  * FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.IS_ENABLED)
                        .append(" = 1 ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ?")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" ASC ");

                retrieveServiceOfferingPlanBits = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveServiceOfferingPlanBits);

            serviceOfferingPlanBits = jdbcTemplate.query(retrieveServiceOfferingPlanBits, new ServiceOfferingPlansExtractor(), planId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - retrieveServiceOfferingPlanBits");
        return serviceOfferingPlanBits;
    }


    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlan> retrieveServiceOfferingPlansWithBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingPlansDao - retrieveServiceOfferingPlansWithBits");

        HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlanBits;

        try {
            if (retrieveServiceOfferingPlansWithBits == null) {

                StringBuilder query = new StringBuilder();
                query.append("SELECT  * FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.IS_ENABLED)
                        .append(" = 1 ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED)
                        .append(" = 0")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" ASC ");

                retrieveServiceOfferingPlansWithBits = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveServiceOfferingPlansWithBits);

            serviceOfferingPlanBits = jdbcTemplate.query(retrieveServiceOfferingPlansWithBits, new ServiceOfferingPlansExtractor());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - retrieveServiceOfferingPlansWithBits");
        return serviceOfferingPlanBits;
    }

    @LogExecutionTime
    public HashMap<Integer, ServiceOfferingPlan> retrieveServiceOfferingServiceClassDescriptions(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingPlansDao - retrieveServiceOfferingServiceClassDescriptions");

        HashMap<Integer, ServiceOfferingPlan> serviceOfferingServiceClassDescriptions;

        try {
            if (retrieveServiceOfferingServiceClassDescriptions == null) {

                StringBuilder query = new StringBuilder();
                query.append("SELECT  * FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" INNER JOIN ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ? ")
//                        .append(" AND ")
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" ASC ");

                retrieveServiceOfferingServiceClassDescriptions = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveServiceOfferingServiceClassDescriptions);

            serviceOfferingServiceClassDescriptions = jdbcTemplate.query(retrieveServiceOfferingServiceClassDescriptions, new ServicePlanClassDescriptionsExtractor(), planId);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - retrieveServiceOfferingServiceClassDescriptions");
        return serviceOfferingServiceClassDescriptions;
    }

    @LogExecutionTime
    public int addRecordToServiceOfferingPlan(AddServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - addRecordToServiceOfferingPlan");

        try {
            if (addRecordToServiceOfferingPlan == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(",").append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME)
                        .append(") ")
                        .append("values ( ?,?)");
                addRecordToServiceOfferingPlan = query.toString();

            }
            int res = jdbcTemplate.update(addRecordToServiceOfferingPlan, request.getPlanId(), request.getPlanName());
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addRecordToServiceOfferingPlan);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - addRecordToServiceOfferingPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int updateRecordToServiceOfferingPlan(UpdateServiceOfferingPlanRequest request) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - updateRecordToServiceOfferingPlan");

        try {
            if (updateRecordToServiceOfferingPlan == null) {
                StringBuilder query = new StringBuilder();
                query.append("update ").
                        append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME)
                        .append(" = ? ").append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append(" = ? ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED).append(" = 0");
                updateRecordToServiceOfferingPlan = query.toString();

            }
            int res = jdbcTemplate.update(updateRecordToServiceOfferingPlan, request.getPlanName(), request.getPlanId());
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateRecordToServiceOfferingPlan);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - updateRecordToServiceOfferingPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteServiceOfferingPlan(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - deleteServiceOfferingPlan");

        try {
            if (deleteServiceOfferingPlan == null) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED)
                        .append("= ?")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID)
                        .append("= ?");

                deleteServiceOfferingPlan = query.toString();

            }
            int res = jdbcTemplate.update(deleteServiceOfferingPlan, 1, planId);
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteServiceOfferingPlan);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteServiceOfferingPlan");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int[] addBitsToServiceOfferingPlanBits(List<Integer> bits, int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - addBitsToServiceOfferingPlanBits");

        try {
            if (addBitsToServiceOfferingPlanBitsQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("Insert into ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(" ( ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID)
                        .append(" , ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.BIT_POSITION)
                        .append(" , ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.IS_ENABLED)
                        .append(" ) ")
                        .append("values ( ?,?,?)");
                ;

                addBitsToServiceOfferingPlanBitsQuery = query.toString();
            }


            int[] res = this.jdbcTemplate.batchUpdate(
                    addBitsToServiceOfferingPlanBitsQuery,
                    new BatchPreparedStatementSetter() {

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, planId);
                            ps.setInt(2, bits.get(i));
                            ps.setInt(3, 1);
                        }

                        public int getBatchSize() {
                            return bits.size();
                        }

                    });

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addBitsToServiceOfferingPlanBitsQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - addBitsToServiceOfferingPlanBits");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


    @LogExecutionTime
    public int deleteBitsFromServiceOfferingPlanBits(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - deleteBitsFromServiceOfferingPlanBits");

        try {
            if (deleteBitsToServiceOfferingPlanBits == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID)
                        .append("= ?");

                deleteBitsToServiceOfferingPlanBits = query.toString();

            }
            int res = jdbcTemplate.update(deleteBitsToServiceOfferingPlanBits, planId);
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteBitsToServiceOfferingPlanBits);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteBitsFromServiceOfferingPlanBits");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


//    @LogExecutionTime
//    public int addSOServiceClassDescription(Integer serviceClassId, ServiceOfferingPlanDescModel descModel) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - addSOServiceClassDescription()");
//
//        try {
//            if (addSOServiceClassDescription == null) {
//                StringBuilder query = new StringBuilder();
//                query.append("INSERT INTO ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME).append("(")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
//                        .append(",").append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)
//                        .append(",").append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION)
//                        .append(") ")
//                        .append("values ( ?,?,?)");
//                addSOServiceClassDescription = query.toString();
//
//            }
//            int res = jdbcTemplate.update(addSOServiceClassDescription, descModel.getPlanId(), serviceClassId, descModel.getPlanDescription());
//            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + addSOServiceClassDescription);
//            CCATLogger.DEBUG_LOGGER.info("result is " + res);
//            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - addSOServiceClassDescription()");
//            return res;
//        } catch (Exception e) {
//            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
//            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
//            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
//        }
//    }
//
//
//    @LogExecutionTime
//    public int updateSOServiceClassDescription(UpdateServiceClassPlanDescriptionRequest request) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - updateSOServiceClassDescription()");
//
//        try {
//            if (updateSOServiceClassDescription == null) {
//                StringBuilder query = new StringBuilder();
//                query.append("update ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
//                        .append(" SET ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION)
//                        .append(" = ? ")
//                        .append(" WHERE ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
//                        .append(" = ? ")
//                        .append(" AND ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID).append(" = ?");
//                updateSOServiceClassDescription = query.toString();
//
//            }
//            int res = jdbcTemplate.update(updateSOServiceClassDescription, request.getDescription(), request.getPlanId(), request.getServiceClassId());
//            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateSOServiceClassDescription);
//            CCATLogger.DEBUG_LOGGER.info("result is " + res);
//            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - updateSOServiceClassDescription()");
//            return res;
//        } catch (Exception e) {
//            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
//            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
//            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
//        }
//    }
//
//    @LogExecutionTime
//    public int deleteSOServiceClassDescription(DeleteServiceClassPlanDescriptionRequest request) throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - deleteSOServiceClassDescription()");
//
//        try {
//            if (deleteSOServiceClassDescription == null) {
//                StringBuilder query = new StringBuilder();
//                query.append("DELETE FROM ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
//                        .append(" WHERE ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID)
//                        .append("= ?")
//                        .append(" AND ")
//                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
//                        .append("= ?");
//                deleteSOServiceClassDescription = query.toString();
//
//            }
//            int res = jdbcTemplate.update(deleteSOServiceClassDescription, request.getServiceClassId(), request.getPlanId());
//            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteSOServiceClassDescription);
//            CCATLogger.DEBUG_LOGGER.info("result is " + res);
//            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteSOServiceClassDescription()");
//            return res;
//        } catch (Exception e) {
//            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
//            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
//            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
//        }
//    }


    @LogExecutionTime
    public int deleteServiceOfferingPlanFromSOSCTable(int planId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingPlansDao - deleteServiceOfferingPlanFromSOSCTable()");

        try {
            if (deleteServiceOfferingPlanFromSOSCTable == null) {
                StringBuilder query = new StringBuilder();
                query.append("DELETE FROM ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID)
                        .append("= ?");
                deleteServiceOfferingPlanFromSOSCTable = query.toString();

            }
            int res = jdbcTemplate.update(deleteServiceOfferingPlanFromSOSCTable, planId);
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteServiceOfferingPlanFromSOSCTable);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteServiceOfferingPlanFromSOSCTable()");
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingPlansDao - deleteServiceOfferingPlanFromSOSCTable()");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public Integer findServiceClassDescFoeServiceOfferingPlan(int planId, int serviceClassId) throws LookupException {

        try {
            if (findServiceClass == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(1) FROM ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_FAF_PLANS.PLAN_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID).append("= ?");
                findServiceClass = query.toString();

            }
            return jdbcTemplate.queryForObject(findServiceClass.toString(), Integer.class,
                    planId, serviceClassId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findServiceClass);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findServiceClass, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    @LogExecutionTime
    public ServiceOfferingPlan findServiceOfferingPlanWithId(int planId) throws LookupException {

        try {
            if (findServicePlanWithId == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED).append(" = 0");

                findServicePlanWithId = query.toString();

            }
            return jdbcTemplate.queryForObject(findServicePlanWithId.toString(), new ServiceOfferingPlanRowMapper(),
                    planId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findServicePlanWithId);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findServicePlanWithId, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    @LogExecutionTime
    public ServiceOfferingPlan findServiceOfferingPlanWithName(String planName) throws LookupException {

        try {
            if (findServicePlanWithName == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED).append(" = 0");
                ;

                findServicePlanWithName = query.toString();

            }
            return jdbcTemplate.queryForObject(findServicePlanWithName, new ServiceOfferingPlanRowMapper(),
                    planName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findServicePlanWithName);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findServicePlanWithName, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

}
