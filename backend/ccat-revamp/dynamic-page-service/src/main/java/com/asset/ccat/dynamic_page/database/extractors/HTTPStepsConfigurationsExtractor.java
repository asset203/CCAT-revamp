package com.asset.ccat.dynamic_page.database.extractors;

import com.asset.ccat.dynamic_page.constants.InputMethod;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class HTTPStepsConfigurationsExtractor implements ResultSetExtractor<HashMap<Integer, HttpConfigurationModel>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public HashMap<Integer, HttpConfigurationModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, HttpConfigurationModel> stepConfigMap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, HttpParameterModel>> parameters = new HashMap<>();
        while (resultSet.next()) {
            Integer stepId = resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.STEP_ID);
            if (stepConfigMap.get(stepId) == null) {
                //extracting config model base details
                Integer requestContentType = resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_CONTENT_TYPE);
                if (requestContentType.intValue() == 0) {
                    requestContentType = null;
                }
                HttpConfigurationModel config = new HttpConfigurationModel();
                config.setStepId(stepId);
                config.setId(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.ID));
                config.setHttpMethod(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_METHOD));
                config.setHttpURL(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HTTP_URL));
                config.setKeyValueDelimiter(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.KEY_VALUE_DELIMITER));
                config.setMainDelimiter(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAIN_DELIMITER));
                config.setHeaders(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.HEADERS));
                config.setRequestBody(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.REQUEST_BODY));
                config.setRequestContentType(requestContentType);
                config.setResponseContentType(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_CONTENT_TYPE));
                config.setResponseForm(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.RESPONSE_FORM));
                config.setMaxRecords(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.MAX_RECORDS));
                config.setSuccessCode(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_CONFIGURATION.SUCCESS_CODE));

                parameters.put(stepId, new HashMap<>());
                stepConfigMap.put(stepId, config);
            }

            Integer paramId = resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.ID);
            if (parameters.get(stepId).get(paramId) == null) {
                //extracting config model params
                HttpParameterModel param = new HttpParameterModel();
                param.setId(paramId);
                param.setConfigId(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.CONFIG_ID));
                param.setParameterName(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_NAME));
                param.setDisplayName(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DISPLAY_NAME));
                param.setInputMethod(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.INPUT_METHOD));
                if (param.getInputMethod() != null && param.getInputMethod().equals(InputMethod.MENU.id)) {
                    String menuAsJson = resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.STATIC_DATA);
                    param.setStaticData(menuAsJson);
                    if (menuAsJson != null && !menuAsJson.trim().isEmpty()) {
                        try {
                            param.setDropdownList(objectMapper.readValue(menuAsJson, new TypeReference<List<DropdownItem>>() {
                            }));
                        } catch (JsonProcessingException e) {
                            CCATLogger.DEBUG_LOGGER.error("Failed to deserialize dropdownList json [" + menuAsJson + "]");
                        }
                    }
                }
                param.setDefaultValue(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DEFAULT_VALUE));
                param.setParameterDataType(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_DATA_TYPE));
                param.setParameterType(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_TYPE));
                param.setParameterOrder(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.PARAMETER_ORDER));
                param.setDisplayOrder(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DISPLAY_ORDER));
                param.setResponseCode(resultSet.getBoolean(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_CODE));
                param.setResponseDescription(resultSet.getBoolean(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_RESPONSE_DESCRIPTION));
                param.setHidden(resultSet.getBoolean(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.IS_HIDDEN));
                param.setSourceStepParameterId(resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_ID));
                param.setSourceStepParameterName(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME));
                param.setxPath(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.XPATH));
                param.setJsonPath(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.JSON_PATH));
                param.setDateFormat(resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_PARAMETERS.DATE_FORMAT));
                if (param.getSourceStepParameterId().equals(0)) {
                    param.setSourceStepParameterId(null);
                }
                parameters.get(stepId).put(paramId, param);
            }

            Integer responseMappingId = resultSet.getInt(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.ID);
            if (responseMappingId != null && !responseMappingId.equals(0)) {
                //extracting parameter cursor mapping if present
                HttpParameterModel parameter = parameters.get(stepId).get(paramId);
                if (parameter.getHttpResponseMappingModels() == null) {
                    parameter.setHttpResponseMappingModels(new ArrayList<>());
                }
                parameter.getHttpResponseMappingModels().add(new HttpResponseMappingModel(
                        responseMappingId,
                        paramId,
                        resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_NAME),
                        resultSet.getString(DatabaseStructs.DYN_STEP_HTTP_RESPONSE_MAPPING.HEADER_DISPLAY_NAME)));
            }
        }

        //setting parameter list for each configuration model
        stepConfigMap.forEach((stepId, config) -> {
            config.setParameters(new ArrayList<>(parameters.get(stepId).values()));
        });

        return stepConfigMap;
    }
}
