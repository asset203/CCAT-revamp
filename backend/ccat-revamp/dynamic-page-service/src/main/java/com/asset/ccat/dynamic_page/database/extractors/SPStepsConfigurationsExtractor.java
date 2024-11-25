package com.asset.ccat.dynamic_page.database.extractors;

import com.asset.ccat.dynamic_page.constants.InputMethod;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DropdownItem;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureCursorMappingModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
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
public class SPStepsConfigurationsExtractor implements ResultSetExtractor<HashMap<Integer, ProcedureConfigurationModel>> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public HashMap<Integer, ProcedureConfigurationModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, ProcedureConfigurationModel> stepConfigMap = new HashMap<>();
        HashMap<Integer, HashMap<Integer, ProcedureParameterModel>> parameters = new HashMap<>();
        while (resultSet.next()) {
            Integer stepId = resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.STEP_ID);
            if (stepConfigMap.get(stepId) == null) {
                //extracting config model base details
                ProcedureConfigurationModel config = new ProcedureConfigurationModel();
                config.setStepId(stepId);
                config.setId(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.ID));
                config.setDatabaseURL(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_URL));
                config.setDatabaseUsername(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_USERNAME));
                config.setDatabasePassword(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.DB_PASSWORD));
                config.setProcedureName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.PROCEDURE_NAME));
                config.setMaxRecords(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.MAX_RECORDS));
                config.setSuccessCode(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.SUCCESS_CODE));
                config.setExtraConfigurations(resultSet.getString(DatabaseStructs.DYN_STEP_SP_CONFIGURATION.EXTRA_CONFIGURATIONS));

                parameters.put(stepId, new HashMap<>());
                stepConfigMap.put(stepId, config);
            }

            Integer paramId = resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.ID);
            if (parameters.get(stepId).get(paramId) == null) {
                //extracting config model params
                ProcedureParameterModel param = new ProcedureParameterModel();
                param.setId(paramId);
                param.setConfigId(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.CONFIG_ID));
                param.setParameterName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_NAME));
                param.setDisplayName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DISPLAY_NAME));
                param.setInputMethod(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.INPUT_METHOD));
                if (param.getInputMethod() != null && param.getInputMethod().equals(InputMethod.MENU.id)) {
                    String menuAsJson = resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.STATIC_DATA);
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
                param.setDefaultValue(resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DEFAULT_VALUE));
                param.setParameterDataType(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_DATA_TYPE));
                param.setParameterType(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_TYPE));
                param.setParameterOrder(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.PARAMETER_ORDER));
                param.setDisplayOrder(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DISPLAY_ORDER));
                param.setResponseCode(resultSet.getBoolean(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_CODE));
                param.setResponseDescription(resultSet.getBoolean(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_RESPONSE_DESCRIPTION));
                param.setHidden(resultSet.getBoolean(DatabaseStructs.DYN_STEP_SP_PARAMETERS.IS_HIDDEN));
                param.setSourceStepParameterId(resultSet.getInt(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_ID));
                if (param.getSourceStepParameterId().equals(0)) {
                    param.setSourceStepParameterId(null);
                }
                param.setSourceStepParameterName(resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.SOURCE_STEP_PARAMETER_NAME));
                param.setDateFormat(resultSet.getString(DatabaseStructs.DYN_STEP_SP_PARAMETERS.DATE_FORMAT));
                parameters.get(stepId).put(paramId, param);
            }

            Integer cursorMappingId = resultSet.getInt(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ID);
            if (cursorMappingId != null && !cursorMappingId.equals(0)) {
                //extracting parameter cursor mapping if present
                ProcedureParameterModel parameter = parameters.get(stepId).get(paramId);
                if (parameter.getCursorParameterMappings() == null) {
                    parameter.setCursorParameterMappings(new ArrayList<>());
                }
                parameter.getCursorParameterMappings().add(new ProcedureCursorMappingModel(
                        cursorMappingId,
                        paramId,
                        resultSet.getString(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.ACTUAL_COLUMN_NAME),
                        resultSet.getString(DatabaseStructs.DYN_STEP_SP_CURSOR_MAPPING.DISPLAY_COLUMN_NAME)));
            }
        }

        //setting parameter list for each configuration model
        stepConfigMap.forEach((stepId, config) -> {
            config.setParameters(new ArrayList<>(parameters.get(stepId).values()));
        });

        return stepConfigMap;
    }
}
