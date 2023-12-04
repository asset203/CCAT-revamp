package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.configurations.Properties;
import com.asset.ccat.ods_service.database.mapper.DssReportMapper;
import com.asset.ccat.ods_service.defines.DBStructs;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 * @author wael.mohamed
 */
@Repository
public class DSSDao {

    @Autowired
    Properties properties;

    @Autowired
    private DatasourceManager datasourceManager;

    @Autowired
    DssReportMapper dssReportMapper;

    public DSSResponseModel retrieveRecords(String procedureName, String msisdn, Long fromDate, Long toDate, Integer serviceFlag, String pageName) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " | Starting DSSDao - retrieveRecords for ProcedureName " + procedureName);
        DSSResponseModel dssModel = new DSSResponseModel();
        long startTime = System.currentTimeMillis();
        long procedureExecutionTime, endTime;
        int maxRecordsAllowed = 0;
        try {
            try {
                maxRecordsAllowed = DBStructs.MAX_RETRIEVAL_DSS_PAGES;      //from ADM_SYSTEM
                CCATLogger.DEBUG_LOGGER.debug(" max size allowed for " + procedureName + " retrieveRecords is " + maxRecordsAllowed);
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("exception when parsing maxRecordsAllowed", ex);
                CCATLogger.DEBUG_LOGGER.debug(" exception when parsing maxRecordsAllowed and it will be 0 , no checking on maximum records");
                maxRecordsAllowed = 0;
            }
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("DSS_NODES");
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(procedureName)
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(
                            new SqlParameter("MSISDN", OracleTypes.VARCHAR),
                            new SqlParameter("FROM_DATE", OracleTypes.DATE),
                            new SqlParameter("TO_DATE", OracleTypes.DATE),
                            new SqlParameter("SERVICE_FLAG", OracleTypes.NUMBER),
                            new SqlOutParameter("OUTPUT_CURSOR", OracleTypes.REF_CURSOR),
                            new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER),
                            new SqlOutParameter("ERROR_DESCRIPTION", OracleTypes.VARCHAR));
            Map<String, Object> inParamMap = new HashMap<>();
            inParamMap.put("MSISDN", msisdn);
            inParamMap.put("FROM_DATE", new java.sql.Date(fromDate));
            inParamMap.put("TO_DATE", new java.sql.Date(toDate));
            inParamMap.put("SERVICE_FLAG", serviceFlag);
            Map<String, Object> resultMap = jdbcCall.execute(new MapSqlParameterSource(inParamMap));
            procedureExecutionTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("Procedure Execution Time is = " + procedureExecutionTime);
            BigDecimal statusCode = (BigDecimal) resultMap.get("ERROR_CODE");
            String statusMessage = (String) resultMap.get("ERROR_DESCRIPTION");
            ArrayList<LinkedCaseInsensitiveMap> array = (ArrayList) resultMap.get("OUTPUT_CURSOR");
            dssModel = dssReportMapper.mapRow(array, pageName);
            dssModel.setExternalCode(statusCode.intValue());
            dssModel.setExternalDescription(statusMessage);
            endTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("retrieveRecords method execution Time is = " + endTime);
            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " Ending DSSDao - retrieveRecords - ProcedureName " + procedureName);

            return dssModel;

        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + "| Exception in retrieveRecords " + e);
            CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|" + pageName + " | Exception in retrieveRecords ", e);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public DSSResponseModel retrieveUSSDRecords(String procedureName, String msisdn, Long fromDate, Long toDate, String pageName) throws ODSException {
        CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " | Starting DSSDao - retrieveRecords for ProcedureName " + procedureName);
        DSSResponseModel dssModel = new DSSResponseModel();
        long startTime = System.currentTimeMillis();
        long procedureExecutionTime, endTime;
        int maxRecordsAllowed = 0;
        try {

            try {
                maxRecordsAllowed = DBStructs.MAX_RETRIEVAL_DSS_PAGES;      //from ADM_SYSTEM
                CCATLogger.DEBUG_LOGGER.debug(" max size allowed for " + procedureName + " retrieveRecords is " + maxRecordsAllowed);
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("Exception when parsing maxRecordsAllowed", ex);
                CCATLogger.DEBUG_LOGGER.debug("Exception when parsing maxRecordsAllowed and it will be 0 , no checking on maximum records");
                maxRecordsAllowed = 0;
            }
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("DSS_NODES");
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(procedureName)
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(
                            new SqlParameter("MSISDN", OracleTypes.VARCHAR),
                            new SqlParameter("FROM_DATE", OracleTypes.DATE),
                            new SqlParameter("TO_DATE", OracleTypes.DATE),
                            new SqlOutParameter("OUTPUT_CURSOR", OracleTypes.REF_CURSOR),
                            new SqlOutParameter("ERROR_CODE", OracleTypes.NUMBER),
                            new SqlOutParameter("ERROR_DESCRIPTION", OracleTypes.VARCHAR));
            Map<String, Object> inParamMap = new HashMap<>();
            inParamMap.put("MSISDN", msisdn);
            inParamMap.put("FROM_DATE", new java.sql.Date(fromDate));
            inParamMap.put("TO_DATE", new java.sql.Date(toDate));
            Map<String, Object> resultMap = jdbcCall.execute(new MapSqlParameterSource(inParamMap));
            procedureExecutionTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("Procedure Execution Time is = " + procedureExecutionTime);
            BigDecimal statusCode = (BigDecimal) resultMap.get("ERROR_CODE");
            String statusMessage = (String) resultMap.get("ERROR_DESCRIPTION");
            ArrayList<LinkedCaseInsensitiveMap> array = (ArrayList) resultMap.get("OUTPUT_CURSOR");
            int counter = 0;
            int ignoredCounter = 0;
            dssModel = dssReportMapper.mapRow(array, pageName);
            dssModel.setExternalCode(statusCode.intValue());
            dssModel.setExternalDescription(statusMessage);
            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " Ending DSSDao - retrieveRecords - ProcedureName " + procedureName);
            endTime = System.currentTimeMillis() - startTime;
            CCATLogger.DEBUG_LOGGER.info("retrieveRecords method execution Time is = " + endTime);
            return dssModel;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + "| Exception in retrieveRecords " + e);
            CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|" + pageName + " | Exception in retrieveRecords ", e);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

//        public DSSResponseModel retrieveRecords(String procedureName, String msisdn, String fromDate, String toDate, Integer serviceFlag, String pageName) throws ODSException {
//        CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " | Starting DSSDAO - retrieveRecords for ProcedureName " + procedureName);
//        DSSResponseModel dssModel = new DSSResponseModel();
//        long t1 = System.currentTimeMillis();
//        long t3 = t1;
//        int maxRecordsAllowed = 0;
//        try {
//
//            try {
//                maxRecordsAllowed = DBStructs.MAX_RETRIEVAL_DSS_PAGES;      //from ADM_SYSTEM
//                CCATLogger.DEBUG_LOGGER.debug(" max size allowed for " + procedureName + " retrieveRecords is " + maxRecordsAllowed);
//            } catch (Exception ex) {
//                CCATLogger.ERROR_LOGGER.error("exception when parsing maxRecordsAllowed", ex);
//                CCATLogger.DEBUG_LOGGER.debug(" exception when parsing maxRecordsAllowed and it will be 0 , no cheching on maximum records");
//                maxRecordsAllowed = 0;
//            }
//
//            simpleJdbcCall.withProcedureName(procedureName).declareParameters(
//                    new SqlParameter("MSISDN", Types.VARCHAR),
//                    new SqlParameter("FROM_DATE", Types.VARCHAR),
//                    new SqlParameter("TO_DATE", Types.VARCHAR),
//                    new SqlParameter("SERVICE_FLAG", Types.INTEGER),
//                    //new SqlOutParameter("OUTPUT_CURSOR", Types.REF_CURSOR, new DssReportMapper()),
//                    new SqlOutParameter("ERROR_CODE", Types.INTEGER),
//                    new SqlOutParameter("ERROR_DESCRIPTION", Types.VARCHAR)
//            ).addDeclaredRowMapper("OUTPUT_CURSOR", new DssReportMapper(pageName, cachedLookups));
//            MapSqlParameterSource paramMap = new MapSqlParameterSource()
//                    .addValue("MSISDN", msisdn)
//                    .addValue("FROM_DATE", fromDate)
//                    .addValue("TO_DATE", toDate)
//                    .addValue("SERVICE_FLAG", serviceFlag);
//
//            simpleJdbcCall.getJdbcTemplate().setQueryTimeout(DBStructs.QUERY_TIME_OUT);
//            Map<String, Object> resultMap = simpleJdbcCall.execute(paramMap);
//            Integer statusCode = (Integer) resultMap.get("ERROR_CODE");
//            String statusMessage = (String) resultMap.get("ERROR_DESCRIPTION");
//            List<DSSResponseModel> list = (ArrayList) resultMap.get("OUTPUT_CURSOR");
//            dssModel = (DSSResponseModel) list.get(0);
//
//            dssModel.setExternalCode(statusCode);
//            dssModel.setExternalDescription(statusMessage);
//            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + " Ending DSSDAO - retrieveRecords - ProcedureName " + procedureName);
//            return dssModel;
//        } catch (Exception e) {
//            CCATLogger.DEBUG_LOGGER.debug("MSISDN: " + msisdn + "|" + pageName + "| Exception in retrieveRecords " + e);
//            CCATLogger.ERROR_LOGGER.error("MSISDN: " + msisdn + "|" + pageName + " | Exception in retrieveRecords ", e);
//            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//    }
}
