package com.asset.ccat.lookup_service.database.dao;


import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.row_mapper.ServiceOfferingBitsRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceOfferingBitsDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Utils utils;
    private String retrieveServiceOfferingBits;


    private String updateServiceOfferingBitQuery;


    @LogExecutionTime
    public List<ServiceOfferingBitModel> retrieveServiceOfferingBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting ServiceOfferingBitsDao - retrieveServiceOfferingBits");

        List<ServiceOfferingBitModel> serviceOfferingBits;

        try {
            if (retrieveServiceOfferingBits == null) {

                StringBuilder query = new StringBuilder();
                query.append("Select * From ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.TABLE_NAME)
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_POSITION)
                        .append(" ASC ");
                retrieveServiceOfferingBits = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveServiceOfferingBits);

            serviceOfferingBits = jdbcTemplate.query(retrieveServiceOfferingBits.toString(), new ServiceOfferingBitsRowMapper());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingBitsDao - retrieveServiceOfferingBits");
        return serviceOfferingBits;
    }


    @LogExecutionTime
    public int updateServiceOfferingBit(ServiceOfferingBitModel serviceOffering) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting ServiceOfferingBitsDao - updateServiceOfferingBit");

        try {
            if (updateServiceOfferingBitQuery == null) {
                StringBuilder query = new StringBuilder();
                query
                        .append("update " + DatabaseStructs.ADM_SERVICE_OFFERING_BITS.TABLE_NAME
                                + " SET "
                                + DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_NAME + " = ? "
                                + " WHERE "
                                + DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_POSITION + " = ?");
                updateServiceOfferingBitQuery = query.toString();
            }

            int res = jdbcTemplate.update(updateServiceOfferingBitQuery.toString(),
                    serviceOffering.getBitName(),
                    serviceOffering.getBitPosition()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateServiceOfferingBitQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending ServiceOfferingBitsDao - updateServiceOfferingBit");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }


}
