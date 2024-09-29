package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.database.mapper.AccountHistoryMapper;
import com.asset.ccat.ods_service.defines.DBStructs;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wael.mohamed
 */
@Repository
public class AccountHistoryDao {

    @Autowired
    DatasourceManager datasourceManager;
    @Autowired
    Properties properties;
    @Autowired
    AccountHistoryMapper accountHistoryMapper;


    public List<SubscriberActivityModel> retrieveRecords(String msisdn, long fromDate, long toDate) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AccountHistoryDao - retrieveRecords for ProcedureName " + properties.getAccountHistoryProcedure() + " for MSISDN: " + msisdn);
        List<SubscriberActivityModel> records;
        long startTime = System.currentTimeMillis();
        long procedureExecutionTime, endTime;
        int maxRecordsAllowed;
        try {
            try {
                maxRecordsAllowed = DBStructs.MAX_RETRIEVAL_DSS_PAGES;      //from ADM_SYSTEM
                CCATLogger.DEBUG_LOGGER.debug(" max size allowed for " + properties.getAccountHistoryProcedure() + " retrieveRecords is " + maxRecordsAllowed);
            } catch (Exception ex) {
                CCATLogger.DEBUG_LOGGER.error("exception when parsing maxRecordsAllowed", ex);
                CCATLogger.DEBUG_LOGGER.debug(" exception when parsing maxRecordsAllowed and it will be 0 , no checking on maximum records");
                maxRecordsAllowed = 0;
            }
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("ODS_NODES");
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withSchemaName(hikariDataSource.getSchema())
                    .withProcedureName(properties.getAccountHistoryProcedure())
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(
                            new SqlParameter("MSISDN", OracleTypes.VARCHAR),
                            new SqlParameter("START_DATE", OracleTypes.DATE),
                            new SqlParameter("END_DATE", OracleTypes.DATE),
                            new SqlOutParameter(properties.getAccountHistoryArrayName(), OracleTypes.ARRAY, properties.getAccountHistoryArrayType()),
                            new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER),
                            new SqlOutParameter("ERROR_MESSAGE", OracleTypes.VARCHAR));
            Map<String, Object> inParamMap = new HashMap<>();
            inParamMap.put("MSISDN", msisdn);
            inParamMap.put("START_DATE", new java.sql.Date(fromDate));
            inParamMap.put("END_DATE", new java.sql.Date(toDate));
            Map<String, Object> resultMap = jdbcCall.execute(new MapSqlParameterSource(inParamMap));
            procedureExecutionTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("Procedure Execution Time is = [" + procedureExecutionTime + "] ms");
            CCATLogger.DEBUG_LOGGER.debug("Max size allowed for " + properties.getAccountHistoryProcedure() + " retrieveRecords is " + maxRecordsAllowed);
            BigDecimal statusCode = (BigDecimal) resultMap.get(properties.getAccountHistoryErrorCode());
            String statusMessage = (String) resultMap.get(properties.getAccountHistoryErrorMessage());
            ARRAY array = (ARRAY) resultMap.get(properties.getAccountHistoryArrayName());
            if (statusCode.compareTo(BigDecimal.ZERO) != 0) {
                CCATLogger.DEBUG_LOGGER.debug("Stored procedure return [" + statusCode + "] and message [" + statusMessage + "]");
                CCATLogger.ERROR_LOGGER.error("Stored procedure return [" + statusCode + "] and message [" + statusMessage + "]");
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, statusMessage);
            }
            ResultSet resultSet = array.getResultSet();
            records = extractActivityListFromResultSet(resultSet, msisdn);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountHistoryDao - retrieveRecords - ProcedureName " + properties.getAccountHistoryProcedure());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|Exception in retrieveRecords " + e);
            CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|Exception in retrieveRecords ", e);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
        endTime = System.currentTimeMillis() - startTime;
        CCATLogger.DEBUG_LOGGER.info("retrieveRecords method execution Time is = [" + endTime + "] ms");

        return records;
    }

    public List<SubscriberActivityModel> retrieveNewRecords(String msisdn, long fromDate, long toDate) throws ODSException{
        CCATLogger.DEBUG_LOGGER.debug("Starting retrieving NewRecords with ProcedureName = {}", properties.getAccountHistoryProcedure());
        List<SubscriberActivityModel> records;
        long startTime = System.currentTimeMillis();
        long endTime;
        int maxRecordsAllowed;
        try {
            try {
                maxRecordsAllowed = DBStructs.MAX_RETRIEVAL_DSS_PAGES;      //from ADM_SYSTEM
                CCATLogger.DEBUG_LOGGER.debug(" max size allowed for " + properties.getAccountHistoryProcedure() + " retrieveRecords is " + maxRecordsAllowed);
            } catch (Exception ex) {
                CCATLogger.DEBUG_LOGGER.error("exception when parsing maxRecordsAllowed", ex);
                CCATLogger.DEBUG_LOGGER.debug(" exception when parsing maxRecordsAllowed and it will be 0 , no checking on maximum records");
                maxRecordsAllowed = 0;
            }
            Date sqlStartDate = new Date(fromDate);
            Date sqlEndDate = new Date(toDate);
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("ODS_NODES");
            CallableStatement callableStatement = null;
            ResultSet resultSet = null;
            CCATLogger.DEBUG_LOGGER.debug("ODS_NODES datasource : { DB_URL : {}  \nschema_name : {} \nuser_name : {} \nconnection_time_out : {} \npool_name : {} }",
                    hikariDataSource.getJdbcUrl(),
                    hikariDataSource.getSchema(),
                    hikariDataSource.getUsername(),
                    hikariDataSource.getConnectionTimeout(),
                    hikariDataSource.getPoolName()
                    );
            callableStatement = hikariDataSource.getConnection().prepareCall("call " + properties.getAccountHistoryProcedure() + " (?,?,?,?,?,?)");
            callableStatement.setString(1, msisdn);
            callableStatement.setDate(2, sqlStartDate);
            callableStatement.setDate(3, sqlEndDate);
            callableStatement.registerOutParameter(4, OracleTypes.ARRAY, "CCAT_LIST");
            callableStatement.registerOutParameter(5, Types.NUMERIC);//error code
            callableStatement.registerOutParameter(6, Types.VARCHAR);//error Message
            CCATLogger.DEBUG_LOGGER.debug("Stored procedure parameters { START_DATE:{} , \nEND_DATE:{} , \nmsisdn:{} }",sqlStartDate,sqlEndDate ,msisdn);
            callableStatement.execute();
            int eCode = callableStatement.getInt(5);
            String eDesc = callableStatement.getString(6);
            CCATLogger.DEBUG_LOGGER.debug("Stored procedure responseCode : {} , description  {} ", eCode, eDesc);
            if (eCode != 0) {
                CCATLogger.DEBUG_LOGGER.debug("Stored procedure return [{}] and message [{}]", eCode, eDesc);
                CCATLogger.ERROR_LOGGER.debug("Stored procedure return [{}] and message [{}]", eCode, eDesc);
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, eDesc);
            }
            oracle.sql.ARRAY results = (oracle.sql.ARRAY) callableStatement.getArray(4);
            CCATLogger.DEBUG_LOGGER.debug("Stored procedure resultArray size (number of records): {} ",results.getLength());
            resultSet = results.getResultSet();

            records = extractActivityListFromResultSet(resultSet, msisdn);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountHistoryDao - retrieve ({}) Records - ProcedureName {} " ,records.size(),properties.getAccountHistoryProcedure());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveRecords ",  e);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveRecords ",  e);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
        endTime = System.currentTimeMillis() - startTime;
        CCATLogger.DEBUG_LOGGER.info("retrieveRecords method execution Time is = [{}] ms", endTime);

        return records;
    }


    private List<SubscriberActivityModel> extractActivityListFromResultSet(ResultSet resultSet, String msisdn) throws SQLException {
        List<SubscriberActivityModel> extractedRecords = new ArrayList<>();
        int ignoredCounter = 0;
        int counter = 0;
        while (resultSet.next()) {
            // since it's an array of objects, get and display the value of the underlying object
            try {
                Struct obj = (Struct) resultSet.getObject(2);
                Object[] values = obj.getAttributes();
                String tableType = ((String) values[0]).trim();
                CCATLogger.DEBUG_LOGGER.debug(tableType);
                SubscriberActivityModel activityModel = accountHistoryMapper.mapRow(obj, msisdn);
                if (activityModel != null) {
                    activityModel.setIdentifier(++counter);
                    extractedRecords.add(activityModel);
                } else {
                    ignoredCounter++;
                }
            } catch (SQLException e) {
                CCATLogger.DEBUG_LOGGER.error("MSISDN: " + msisdn + "|Exception in extractActivityListFromResultSet().", e);
                CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|Exception in extractActivityListFromResultSet() ", e);
            }
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending AccountHistoryDao - extractListFromResultSet() - with count [" + counter + "] and ignored count is [" + ignoredCounter + "]");

        return extractedRecords;
    }
}
