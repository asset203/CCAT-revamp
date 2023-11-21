package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.FlexShareHistoryModel;
import com.asset.ccat.ods_service.models.responses.GetFlexShareHistoryResponse;
import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlexShareHistoryDao {

    @Autowired
    DatasourceManager datasourceManager;
    @Autowired
    Properties properties;

    private String msisdnPrefix = "20";


    public GetFlexShareHistoryResponse retrieveHistory(String msisdn, String fromDate, String toDate, String flag) throws ODSException {
        long executionTime;
        long start = 0;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started FlexShareHistoryDao - retrieveHistory");
            CCATLogger.DEBUG_LOGGER.info("Start executing flex share history SP");
            start = System.currentTimeMillis();
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("FLEX_HISTORY");
            String spName = properties.getRetrieveFlexHistorySp();
            CCATLogger.DEBUG_LOGGER.debug("Start Calling Flex Share History Stored procedure " + spName);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(spName)
                    .declareParameters(
                            new SqlParameter(Defines.FlexShareHistorySP.MSISDN, OracleTypes.VARCHAR),
                            new SqlParameter(Defines.FlexShareHistorySP.PARTY_TYPE, OracleTypes.VARCHAR),
                            new SqlParameter(Defines.FlexShareHistorySP.START_DATE, OracleTypes.VARCHAR),
                            new SqlParameter(Defines.FlexShareHistorySP.END_DATE, OracleTypes.VARCHAR),
                            new SqlOutParameter(Defines.FlexShareHistorySP.DATA_CURSOR, OracleTypes.REF_CURSOR),
                            new SqlOutParameter(Defines.FlexShareHistorySP.ERROR_CODE, OracleTypes.NUMBER),
                            new SqlOutParameter(Defines.FlexShareHistorySP.ERROR_MESSAGE, OracleTypes.VARCHAR));

            Map<String, Object> inParamMap = new HashMap<>();
            inParamMap.put(Defines.FlexShareHistorySP.MSISDN, msisdnPrefix + msisdn);
            inParamMap.put(Defines.FlexShareHistorySP.PARTY_TYPE, flag);
            inParamMap.put(Defines.FlexShareHistorySP.START_DATE, fromDate);
            inParamMap.put(Defines.FlexShareHistorySP.END_DATE, toDate);

            CCATLogger.DEBUG_LOGGER.debug("Calling Flex Share History Stored procedure " +
                    "with input parameters >> " + inParamMap);
            CCATLogger.INTERFACE_LOGGER.debug("Input parameters: " + inParamMap);

            Map<String, Object> resultMap = jdbcCall.execute(new MapSqlParameterSource(inParamMap));

            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");

            CCATLogger.DEBUG_LOGGER.debug("Start handling response map");
            BigDecimal statusCode = (BigDecimal) resultMap.get(Defines.FlexShareHistorySP.ERROR_CODE);
            String statusMessage = (String) resultMap.get(Defines.FlexShareHistorySP.ERROR_MESSAGE);
            ArrayList array = (ArrayList) resultMap.get(Defines.FlexShareHistorySP.DATA_CURSOR);

            Integer successCode = properties.getFlexHistorySuccessCode() == null ? 0 : properties.getFlexHistorySuccessCode();
            Integer maxAllowed = properties.getFlexHistoryMaxRecords() == null ? 0 : properties.getFlexHistoryMaxRecords();

            CCATLogger.DEBUG_LOGGER.debug("Validating sp returned status code [" + statusCode + "] " +
                    "against configured success code [" + successCode + "]");
            if (statusCode.intValue() != successCode) {
                CCATLogger.DEBUG_LOGGER.debug("Stored procedure return [" + statusCode + "] and message [" + statusMessage + "]");
                CCATLogger.ERROR_LOGGER.error("Stored procedure return [" + statusCode + "] and message [" + statusMessage + "]");
                throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, statusMessage);
            }
            GetFlexShareHistoryResponse response = extractHistoryFromResultSet(array, maxAllowed);

            CCATLogger.DEBUG_LOGGER.info("Finished executing flex share history SP");
            CCATLogger.DEBUG_LOGGER.debug("Ended FlexShareHistoryDao - retrieveHistory");

            return response;
        } catch (ODSException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error occured while retrieving flex share history");
            CCATLogger.ERROR_LOGGER.error("Error occured while retrieving flex share history", ex);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    /**
     * SENDER_MSISDN            VARCHAR2
     * RECEIVER_MSISDN         VARCHAR2
     * FLEXES_AMOUNT           NUMBER
     * FEES_V                   NUMBER
     * STATUS_V                 VARCHAR2
     * FLEXES_START_DATE       VARCHAR2
     * FLEXES_EXP_DATE          VARCHAR2
     * CHANNEL                  VARCHAR2
     */

    private GetFlexShareHistoryResponse extractHistoryFromResultSet(ArrayList list, Integer maxAllowed) {
        List<FlexShareHistoryModel> extractedRecords = new ArrayList<>();
        boolean isMaxExceeded = false;
//        int ignoredCounter = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> entry = (Map<String, Object>) list.get(i);
            if (maxAllowed != 0 && i >= maxAllowed) {
                CCATLogger.DEBUG_LOGGER.debug("Max number of allowed retrieved records is exceeded, stop extraction !");
                isMaxExceeded = true;
                break;
            }
            FlexShareHistoryModel model = new FlexShareHistoryModel();
            model.setSenderMsisdn((String) entry.get(Defines.FlexShareHistorySP.SENDER_MSISDN));
            model.setReceieverMsisdn((String) entry.get(Defines.FlexShareHistorySP.RECEIVER_MSISDN));
            int flexesAmount = ((BigDecimal) entry.get(Defines.FlexShareHistorySP.FLEXES_AMOUNT)).intValue();
            model.setFlexesAmount(String.valueOf(flexesAmount));
            int fees = ((BigDecimal) entry.get(Defines.FlexShareHistorySP.FEES_V)).intValue();
            model.setFeesV(String.valueOf(fees));
            model.setStatusV((String) entry.get(Defines.FlexShareHistorySP.STATUS_V));
            model.setFlexStartDate((String) entry.get(Defines.FlexShareHistorySP.FLEXES_START_DATE));
            model.setFlexExpiryDate((String) entry.get(Defines.FlexShareHistorySP.FLEXES_EXP_DATE));
            model.setChannel((String) entry.get(Defines.FlexShareHistorySP.CHANNEL));

            extractedRecords.add(model);
            // since it's an array of objects, get and display the value of the underlying object
//            try {
//                Struct obj = (Struct) resultSet.getObject(2);
//                Object[] values = obj.getAttributes();
//                String tableType = ((String) values[0]).trim();
//                CCATLogger.DEBUG_LOGGER.debug(tableType);
//                SubscriberActivityModel activityModel = accountHistoryMapper.mapRow(obj, msisdn);
//                if (activityModel != null) {
//                    activityModel.setIdentifier(++counter);
//                    extractedRecords.add(activityModel);
//                } else {
//                    ignoredCounter++;
//                }
//            } catch (SQLException e) {
//                CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|Exception in extractActivityListFromResultSet()");
//                CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|Exception in extractActivityListFromResultSet() ", e);
//            }
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending FlexShareHistoryDao - extractHistoryFromResultSet() - with count [" + extractedRecords.size() + "]");
        return new GetFlexShareHistoryResponse(extractedRecords, isMaxExceeded);
    }
}
