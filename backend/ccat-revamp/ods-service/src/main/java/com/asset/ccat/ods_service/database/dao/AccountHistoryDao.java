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
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            callableStatement.registerOutParameter(4, OracleTypes.ARRAY,  properties.getAccountHistoryArrayName());
            callableStatement.registerOutParameter(5, Types.NUMERIC);//error code
            callableStatement.registerOutParameter(6, Types.VARCHAR);//error Message
            CCATLogger.DEBUG_LOGGER.debug("Stored procedure parameters { START_DATE:{} , \nEND_DATE:{} , \nmsisdn:{} }", sqlStartDate, sqlEndDate, msisdn);

            callableStatement.execute();

            int eCode = callableStatement.getInt(5);
            String eDesc = callableStatement.getString(6);

            CCATLogger.DEBUG_LOGGER.debug("Stored procedure responseCode : {} , description  {} ", eCode, eDesc);
            if (eCode != 0) {
                CCATLogger.DEBUG_LOGGER.warn("Stored procedure return [{}] and message [{}]", eCode, eDesc);
                CCATLogger.ERROR_LOGGER.error("Stored procedure return [{}] and message [{}]", eCode, eDesc);
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
                CCATLogger.DEBUG_LOGGER.debug("Column 1 value in row {}: {}", currentRow, resultObj);

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
}
