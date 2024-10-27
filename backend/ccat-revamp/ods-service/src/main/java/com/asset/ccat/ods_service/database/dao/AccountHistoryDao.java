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

        CCATLogger.DEBUG_LOGGER.debug("Starting retrieving NewRecords from ProcedureName = {}", procedureName);
        Date sqlStartDate = new Date(fromDate);
        Date sqlEndDate = new Date(toDate);

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
            CCATLogger.DEBUG_LOGGER.debug("Procedure Response is: [errorCode={}, errorMessage={},And CCAT_LIST Size = {}", errorCode, errorMessage, ccatList.getResultSet().getFetchSize());
            if (!"0".equals(errorCode))
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, errorMessage);
            return extractSubscriberActivitiesFromSQLArray(ccatList, msisdn);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting account-history. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting account-history. ", ex);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        }
    }

    public List<SubscriberActivityModel> retrieveNewRecords(String msisdn, long fromDate, long toDate) throws ODSException {
        String procedureName = properties.getAccountHistoryProcedure();
        CCATLogger.DEBUG_LOGGER.debug("Starting retrieving NewRecords from ProcedureName = {}", procedureName);
        List<SubscriberActivityModel> records;
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            Date sqlStartDate = new Date(fromDate);
            Date sqlEndDate = new Date(toDate);
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("ODS_NODES");
            CCATLogger.DEBUG_LOGGER.debug("ODS_NODES datasource : { DB_URL : {}  \nschema_name : {} \nuser_name : {} \nconnection_time_out : {} \npool_name : {} }",
                    hikariDataSource.getJdbcUrl(),
                    hikariDataSource.getSchema(),
                    hikariDataSource.getUsername(),
                    hikariDataSource.getConnectionTimeout(),
                    hikariDataSource.getPoolName()
            );
            CallableStatement callableStatement = hikariDataSource.getConnection().prepareCall("call " + procedureName + " (?,?,?,?,?,?)");
            callableStatement.setString(1, msisdn);
            callableStatement.setDate(2, sqlStartDate);
            callableStatement.setDate(3, sqlEndDate);
            callableStatement.registerOutParameter(4, OracleTypes.ARRAY, properties.getAccountHistoryArrayName());
            callableStatement.registerOutParameter(5, Types.NUMERIC);//error code
            callableStatement.registerOutParameter(6, Types.VARCHAR);//error Message
            CCATLogger.DEBUG_LOGGER.debug("Stored procedure parameters { START_DATE:{} , END_DATE:{} , msisdn:{} }", sqlStartDate, sqlEndDate, msisdn);

            callableStatement.execute();

            int eCode = callableStatement.getInt(5);
            String eDesc = callableStatement.getString(6);

            CCATLogger.DEBUG_LOGGER.debug("Stored procedure responseCode : {} , description  {} ", eCode, eDesc);
            if (eCode != 0) {
                CCATLogger.DEBUG_LOGGER.warn("Stored procedure return [{}] and message [{}]", eCode, eDesc);
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, eDesc);
            }
            Array results = callableStatement.getArray(4);
            ResultSet resultSet = results.getResultSet();

            records = extractActivityListFromResultSet(resultSet, msisdn);
            return records;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Exception in retrieveRecords ", e);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveRecords ", e);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR);
        } finally {
            endTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("retrieveRecords method execution Time is = [{}] ms", endTime);
        }
    }

    private List<SubscriberActivityModel> extractActivityListFromResultSet(ResultSet resultSet, String msisdn) throws SQLException {
        List<SubscriberActivityModel> extractedRecords = new ArrayList<>();
        int ignoredCounter = 0;
        int counter = 0;

        CCATLogger.DEBUG_LOGGER.debug("Starting to extract records from ResultSet. Total rows expected: {}", resultSet.getFetchSize());

        while (resultSet.next()) {
            int currentRow = resultSet.getRow();
            CCATLogger.DEBUG_LOGGER.debug("Processing row number: {}", currentRow);

            try {
                Object resultObj = resultSet.getObject(1);
                CCATLogger.DEBUG_LOGGER.debug("Column 2 value in row {}: {}", currentRow, resultObj);

                if (resultObj == null) {
                    CCATLogger.DEBUG_LOGGER.warn("Null value found at column 2 in row {}", currentRow);
                    ignoredCounter++;
                    continue;
                }

                Struct obj = (Struct) resultObj;
                Object[] values = obj.getAttributes();
                CCATLogger.DEBUG_LOGGER.debug("Attributes for row {}: {}", currentRow, Arrays.toString(values));

                if (values == null || values.length == 0) {
                    CCATLogger.DEBUG_LOGGER.warn("Empty values found in Struct for row {}", currentRow);
                    ignoredCounter++;
                    continue;
                }

                String tableType = (String) values[0];
                if (tableType == null || tableType.trim().isEmpty()) {
                    CCATLogger.DEBUG_LOGGER.warn("Invalid tableType found in row {}: {}", currentRow, tableType);
                    ignoredCounter++;
                    continue;
                }

                tableType = tableType.trim();
                CCATLogger.DEBUG_LOGGER.debug("tableType for row {}: {}", currentRow, tableType);

                SubscriberActivityModel activityModel = accountHistoryMapper.mapRow(obj, msisdn);
                if (activityModel != null) {
                    activityModel.setIdentifier(++counter);
                    extractedRecords.add(activityModel);
                    CCATLogger.DEBUG_LOGGER.debug("Successfully mapped activity model for row {}: {}", currentRow, activityModel);
                } else {
                    ignoredCounter++;
                    CCATLogger.DEBUG_LOGGER.warn("Mapped activity model is null for row {}", currentRow);
                }

            } catch (SQLException e) {
                CCATLogger.DEBUG_LOGGER.error("SQLException occurred while processing row {}: {}", currentRow, e);
                CCATLogger.ERROR_LOGGER.error("SQLException occurred while processing row {}.", currentRow, e);
            }
        }

        CCATLogger.DEBUG_LOGGER.debug("Ending AccountHistoryDao - extractListFromResultSet() - with count [{}] and ignored count [{}]", counter, ignoredCounter);
        return extractedRecords;
    }

    public List<SubscriberActivityModel> extractSubscriberActivitiesFromSQLArray(Array sqlArray, String msisdn) {
        List<SubscriberActivityModel> activities = new ArrayList<>();
        int counter = 0;
        int ignoredCounter = 0;

        try (ResultSet rs = sqlArray.getResultSet()) {
            while (rs.next()) {
                int currentRow = rs.getRow();
                Struct obj = (Struct) rs.getObject(2); // The actual array data is in column 2, 1st column always the index
                Object[] values = obj.getAttributes();
                String tableType = ((String) values[0]).trim();
                CCATLogger.DEBUG_LOGGER.debug("[R={}] TableType = {} --- Row values = {}", currentRow, tableType, values);
                SubscriberActivityModel activityModel = accountHistoryMapper.mapRow(obj, msisdn);
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
}
