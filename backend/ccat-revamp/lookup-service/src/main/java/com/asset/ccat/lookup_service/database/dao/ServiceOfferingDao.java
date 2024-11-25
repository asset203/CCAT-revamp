package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.database.extractors.ServiceOfferingBitsDetailsExtractor;
import com.asset.ccat.lookup_service.database.extractors.ServiceOfferingPlansBitsExtractor;
import com.asset.ccat.lookup_service.database.extractors.ServiceOfferingPlansBitsWithDetailsExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@Repository
@RestController
public class ServiceOfferingDao {

    private final JdbcTemplate jdbcTemplate;
    private String retrieveServiceOfferingBitListQuery;
    private String retrieveServiceOfferingPlansQuery;
    private String retrieveServiceOfferingPlansBitWithDetailsQuery;

    private final ServiceOfferingPlansBitsExtractor plansBitsExtractor;
    private final ServiceOfferingBitsDetailsExtractor bitsDetailsExtractor;


    public ServiceOfferingDao(JdbcTemplate jdbcTemplate, ServiceOfferingPlansBitsExtractor plansBitsExtractor, ServiceOfferingBitsDetailsExtractor bitsDetailsExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.plansBitsExtractor = plansBitsExtractor;
        this.bitsDetailsExtractor = bitsDetailsExtractor;
    }

    public HashMap<Integer, ServiceOfferingBitModel> retrieveServiceOfferingBitsDetails() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDao -> retrieveServiceOfferingBitList() : Started");
        try {
            if(Objects.isNull(retrieveServiceOfferingBitListQuery)){
                StringBuilder query = new StringBuilder();
                query.append(" select * FROM ")

                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.TABLE_NAME)

                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.TABLE_NAME)
                        .append(" ON ")

                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_POSITION)

                        .append(" = ")

                        .append(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.BIT_POSITION)

                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.BIT_POSITION);

                retrieveServiceOfferingBitListQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceOfferingBitList() Start Executing Query :"+retrieveServiceOfferingBitListQuery);
            return jdbcTemplate.query(retrieveServiceOfferingBitListQuery, bitsDetailsExtractor);
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveServiceOfferingBitListQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveServiceOfferingBitListQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    private HashMap<Integer, ServiceOfferingPlanBitModel> retrieveServiceOfferingPlans() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDao -> retrieveServiceOfferingPlans() : Started");
        try {
            if(Objects.isNull(retrieveServiceOfferingPlansQuery)){
                StringBuilder query = new StringBuilder();
                query.append(" select ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID).append(" , ")

                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME).append(" , ")

                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID).append(" , ")

                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION)

                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)

                        .append(" LEFT JOIN ")
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
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED)
                        .append(" = 0 ")

                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME);

                retrieveServiceOfferingPlansQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceOfferingPlans() Start Executing Query :"+retrieveServiceOfferingPlansQuery);
            return jdbcTemplate.query(retrieveServiceOfferingPlansQuery, plansBitsExtractor);
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveServiceOfferingPlansQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveServiceOfferingPlansQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashMap<Integer, ServiceOfferingPlanBitModel> retrieveServiceOfferingPlanBitDetails() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingDao -> retrieveServiceOfferingPlanBitDetails() : Started");
        try {
            if(Objects.isNull(retrieveServiceOfferingPlansBitWithDetailsQuery)){
                StringBuilder query = new StringBuilder();
                query.append(" SELECT * FROM ")

                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)

                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID);

                retrieveServiceOfferingPlansBitWithDetailsQuery = query.toString();
            }
            ServiceOfferingPlansBitsWithDetailsExtractor extractor = new ServiceOfferingPlansBitsWithDetailsExtractor(
                    retrieveServiceOfferingPlans(),
                    retrieveServiceOfferingBitsDetails());
            CCATLogger.DEBUG_LOGGER.debug("retrieveServiceOfferingPlanBitDetails() Start Executing Query :"+retrieveServiceOfferingPlansQuery);
            return jdbcTemplate.query(retrieveServiceOfferingPlansBitWithDetailsQuery, extractor);
        }catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveServiceOfferingPlansBitWithDetailsQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveServiceOfferingPlansBitWithDetailsQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
