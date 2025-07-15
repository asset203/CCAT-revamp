package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.configurations.Properties;
import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.manager.DynamicPageConnectionManager;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureCursorMappingModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.dynamic_page.utils.CryptoUtils;
import com.asset.ccat.dynamic_page.utils.GeneralUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StoredProcedureDao {

    @Autowired
    private DynamicPageConnectionManager dynamicPageConnectionManager;

    @Autowired
    private CryptoUtils cryptoUtils;

    @Autowired
    private Properties properties;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    @Autowired
    private GeneralUtils generalUtils;

    public HashMap<String, Object> callStoredProcedure(ProcedureConfigurationModel config, MapSqlParameterSource inputParameters) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.info("Start stored procedure call");
            String url = config.getDatabaseURL();
            String username = config.getDatabaseUsername();
            String password = cryptoUtils.decrypt(config.getDatabasePassword(), properties.getEncryptionKey());
            String extraConfig = config.getExtraConfigurations();

            CCATLogger.DEBUG_LOGGER.debug("Get datasource for usage");
            HikariDataSource dataSource = dynamicPageConnectionManager.getDataSource(url, username, password, extraConfig);
            SimpleJdbcCall jdbcCall;
            String[] sp = config.getProcedureName().split("\\.");
            if(sp.length > 1){
                String spName = sp[1];
                String catalogName = sp[0];
                CCATLogger.DEBUG_LOGGER.debug("Prepare Calling Stored procedure {}.{}", catalogName, spName);
                jdbcCall = new SimpleJdbcCall(dataSource)
                        .withSchemaName(catalogName)
                        .withProcedureName(spName);
            }
            else{
                String spName = sp[0];
                CCATLogger.DEBUG_LOGGER.debug("Prepare Calling Stored procedure {}", spName);
                jdbcCall = new SimpleJdbcCall(dataSource)
                        .withProcedureName(spName);
            }

            CCATLogger.DEBUG_LOGGER.debug("Execute Stored procedure with [{}] input parameters >> {}", inputParameters.getValues().size() ,inputParameters);
            Map<String, Object> storedProcedureResponseParameters = jdbcCall.execute(inputParameters);
            CCATLogger.DEBUG_LOGGER.debug("Execute Stored procedure finished with response >> {}", storedProcedureResponseParameters);

            Integer configuredMaxAllowedRecords = config.getMaxRecords() == null ? 0 : config.getMaxRecords();
            HashMap<String, Object> extractedResponse = extractResponse(config.getParameters(),
                    config.getSuccessCode(),
                    storedProcedureResponseParameters,
                    configuredMaxAllowedRecords);
            CCATLogger.DEBUG_LOGGER.debug("Finished Extracting response from received SP response");
            return extractedResponse;
        } catch (DynamicPageException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while calling SP. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while calling SP. ", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.SP_CALL_FAILED, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    private HashMap<String, Object> extractResponse(List<ProcedureParameterModel> stepParametersList,
                                                    String configuredSuccessCode,
                                                    Map<String, Object> responseMap,
                                                    Integer configuredMaxAllowed)
            throws DynamicPageException {

        try {
            HashMap<String, Object> extractedResponse = new HashMap<>();

            CCATLogger.DEBUG_LOGGER.debug("Get Output parameter list from step parameter list");
            Map<String, ProcedureParameterModel> ouputParamMap = stepParameterUtil.getProcedureOutputParamMap(stepParametersList);

            if (ouputParamMap != null && !ouputParamMap.isEmpty()) {

                CCATLogger.DEBUG_LOGGER.debug("Get Response Code parameter model from output parameter list");
                ProcedureParameterModel resposeCodeParamModel = stepParameterUtil.getSPStepResponseCodeParameterModel(new ArrayList<>(ouputParamMap.values()));
                CCATLogger.DEBUG_LOGGER.debug("Get Response Description parameter model from output parameter list");
                ProcedureParameterModel responseDescParamModel = stepParameterUtil.getSPStepResponseDescParameterModel(new ArrayList<>(ouputParamMap.values()));

                if (resposeCodeParamModel != null) {
                    CCATLogger.DEBUG_LOGGER.debug("Start response code validation");
                    validateResponseCode(resposeCodeParamModel, responseDescParamModel, responseMap, configuredSuccessCode);
                    CCATLogger.DEBUG_LOGGER.debug("Finished response code validation successfully");
                } else {
                    CCATLogger.DEBUG_LOGGER.debug("No response code output param is configured," +
                            " skipping response validation");
                }

                CCATLogger.DEBUG_LOGGER.debug("Start iterating over response map to get desired output parameters");
                for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
                    //intitializations
                    String responseKey = entry.getKey();
                    Object responseVal = entry.getValue();
                    ProcedureParameterModel outputParamModel = ouputParamMap.get(responseKey);

                    if (outputParamModel != null) {
                        CCATLogger.DEBUG_LOGGER.debug("Handling received response with key [" + responseKey + "]");
                        if (outputParamModel.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)) {
                            CCATLogger.DEBUG_LOGGER.debug("Handling cursor output");
                            ArrayList array = (ArrayList) responseVal;
                            List<HashMap<String, Object>> cursorOutput = extractCursorOutput(array,
                                    configuredMaxAllowed,
                                    outputParamModel.getCursorParameterMappings());

                            extractedResponse.put(responseKey, cursorOutput);
                        } else {
                            CCATLogger.DEBUG_LOGGER.debug("Taking response as it is");
                            extractedResponse.put(responseKey, responseVal);
                        }
                    } else {
                        CCATLogger.DEBUG_LOGGER.debug("Skipping response with key [" + responseKey + "]");
                    }
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No configured step outputParameters found");
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished extracting response successfully");
            return extractedResponse;

        } catch (DynamicPageException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occured while extracting response >> " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.debug("Exception occured while extracting response >> " + ex.getMessage(), ex);
            throw new DynamicPageException(ErrorCodes.ERROR.SP_RESPONSE_EXTRACTING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private void validateResponseCode(ProcedureParameterModel responseCodeModel,
                                      ProcedureParameterModel responseDescModel,
                                      Map<String, Object> responseMap,
                                      String successCode) throws DynamicPageException {
        Object responseCode = responseMap.get(responseCodeModel.getParameterNameUpperCase());
        if (responseCode == null) {
            CCATLogger.DEBUG_LOGGER.debug("Response code is missing from received response");
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_RESPONSE, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Response Code as received >> " + responseCode);
        String responseCodeAsString = generalUtils.getString(responseCode, responseCodeModel.getParameterDataType(), null);
        CCATLogger.DEBUG_LOGGER.debug("Response Code as int >> " + responseCodeAsString);

        String responseDesc = "";
        if (responseDescModel != null && responseMap.get(responseDescModel.getParameterNameUpperCase()) != null) {
            responseDesc = (String) responseMap.get(responseDescModel.getParameterNameUpperCase());
            CCATLogger.DEBUG_LOGGER.debug("Response description >> " + responseDesc);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("No response description configured or received");
        }

        if (!responseCodeAsString.equals(successCode)) {
            CCATLogger.DEBUG_LOGGER.debug("Response code is not success");
            // work around for non integer response code TODO: fix this workaround
            String message = "Status Code: " + responseCodeAsString + " Status Msg: " + responseDesc;
            throw new DynamicPageException(ErrorCodes.ERROR.UNKNOWN_ERROR, message, Defines.SEVERITY.ERROR);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Response code is success");
        }
    }

    private List<HashMap<String, Object>> extractCursorOutput(ArrayList list,
                                                              Integer maxAllowed,
                                                              List<ProcedureCursorMappingModel> cursorMappings
    ) throws SQLException, DynamicPageException {

        List<HashMap<String, Object>> extractedRecords = new ArrayList<>();
        int counter = 0;
//        List<String> columnNames = null;
//        if (cursorMappings == null || cursorMappings.isEmpty()) {
//            columnNames = new ArrayList<>();
//            ResultSetMetaData metaData = list.getMetaData();
//            for (int i = 1; i <= metaData.getColumnCount(); i++) {
//                columnNames.add(metaData.getColumnName(i));
//            }
//        }
//        while (list.next()) {
        for (int i = 0; i < list.size(); i++) {
            if (maxAllowed != 0 && counter >= maxAllowed) {
                CCATLogger.DEBUG_LOGGER.debug("Max number of allowed retrieved records is exceeded, stop extraction !");
                break;
            }

            LinkedHashMap<String, Object> record = new LinkedHashMap<>();
            Map<String, Object> entry = (Map<String, Object>) list.get(i);
            if (cursorMappings != null && !cursorMappings.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("Extract data using cursor mappings");
                for (ProcedureCursorMappingModel mapping : cursorMappings) {
                    CCATLogger.DEBUG_LOGGER.debug("In Cursor Mapping: " + mapping.getDisplayColumnName() + " and DataType: " + mapping.getDataType() + " , with value: " + entry.get(mapping.getActualColumnName()) );
                    Object paramValue = generalUtils.castObject(entry.get(mapping.getActualColumnName()), mapping.getDataType(), mapping.getDateFormat());
                    record.put(mapping.getDisplayColumnName(), paramValue);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Extract data using actual column names");
                for (String column : entry.keySet()) {
                    record.put(column, entry.get(column));
                }
            }

            counter++;
            extractedRecords.add(record);

        }
        CCATLogger.DEBUG_LOGGER.debug("Ending extractCursorOutput - with count [" + counter + "]");
        return extractedRecords;
    }
}
