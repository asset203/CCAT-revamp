package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.database.mapper.AccountHistoryMapper;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author wael.mohamed
 */
@Repository
public class AccountHistoryDao {

    private final DatasourceManager datasourceManager;
    private final Properties properties;
    private final AccountHistoryMapper accountHistoryMapper;

    @Autowired
    public AccountHistoryDao(DatasourceManager datasourceManager, Properties properties, AccountHistoryMapper accountHistoryMapper) {
        this.datasourceManager = datasourceManager;
        this.properties = properties;
        this.accountHistoryMapper = accountHistoryMapper;
    }

    public List<SubscriberActivityModel> getAccountHistory(String msisdn, long fromDate, long toDate) throws ODSException {
        HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("ODS_NODES");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        String procedureName = properties.getAccountHistoryProcedure();

        Date sqlStartDate = new Date(fromDate);
        Date sqlEndDate = new Date(toDate);
        CCATLogger.DEBUG_LOGGER.debug("Starting procedure={} \nwith SQL-Parameters: MSISDN = {} START_DATE = {}, END_DATE ={}", procedureName, msisdn, sqlStartDate, sqlEndDate);

        try {
            simpleJdbcCall.withProcedureName(procedureName)
                    .declareParameters(
                            new SqlParameter("MSISDN", OracleTypes.VARCHAR),
                            new SqlParameter("START_DATE", OracleTypes.DATE),
                            new SqlParameter("END_DATE", OracleTypes.DATE),
                            new SqlOutParameter("CCAT_LIST", OracleTypes.ARRAY, properties.getAccountHistoryArrayName()),
                            new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER),
                            new SqlOutParameter("ERROR_MESSAGE", OracleTypes.VARCHAR)
                    ).withoutProcedureColumnMetaDataAccess();
            Map<String, Object> result = simpleJdbcCall.execute(msisdn, sqlStartDate, sqlEndDate);
            Array ccatList = (Array) result.get("CCAT_LIST");
            String errorCode = result.get("ERROR_CODE").toString();
            String errorMessage = (String) result.get("ERROR_MESSAGE");
            CCATLogger.DEBUG_LOGGER.debug("Procedure Response is: [errorCode={}, errorMessage={}]", errorCode, errorMessage);
            if (!"0".equals(errorCode))
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, errorMessage);
            return extractSubscriberActivitiesFromSQLArray(ccatList, msisdn);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting account-history. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting account-history. ", ex);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public List<SubscriberActivityModel> extractSubscriberActivitiesFromSQLArray(Array sqlArray, String msisdn) {
        List<SubscriberActivityModel> activities = new ArrayList<>();
        int counter = 0;
        int ignoredCounter = 0;
        try (ResultSet rs = sqlArray.getResultSet()) {
            CCATLogger.DEBUG_LOGGER.debug("CCAT_LIST size = {}", countSQLArrayOccurrence(sqlArray));
            while (rs.next()) {

                int currentRow = rs.getRow();
                Struct resultData = (Struct) rs.getObject(2); // The actual array data is in column 2, 1st column always the index
                Object[] resultValues = resultData.getAttributes();

                String tableType = ((String) resultValues[0]).trim();
                CCATLogger.INTERFACE_LOGGER.debug("[R={}] TableType = {} --- Row values = {}", currentRow, tableType, resultValues);

                SubscriberActivityModel activityModel = accountHistoryMapper.mapRow(resultData, msisdn);
                if (activityModel != null) {
                    activityModel.setIdentifier(++counter);
                    activities.add(activityModel);
                } else
                    ignoredCounter++;
            }
            CCATLogger.DEBUG_LOGGER.debug("#Ignored activities = {}, countedActivities = {}", ignoredCounter, counter);
        } catch (SQLException e) {
            CCATLogger.DEBUG_LOGGER.error("SQLException getting subscriber activities ", e);
            CCATLogger.ERROR_LOGGER.error("SQLException getting subscriber activities ", e);
        }
        return activities;
    }

    public int countSQLArrayOccurrence(Array sqlArray) throws SQLException {
        try {
            if (sqlArray != null) {
                Object[] ccatListArray = (Object[]) sqlArray.getArray();
                return ccatListArray.length;
            }
        } catch (SQLException ex){
            CCATLogger.DEBUG_LOGGER.warn("SQLException caught.");
        }
        return 0;
    }
}
