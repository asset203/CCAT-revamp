package com.asset.ccat.dynamic_page.services;

import com.asset.ccat.dynamic_page.configurations.Properties;
import com.asset.ccat.dynamic_page.constants.InputMethod;
import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.database.dao.*;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.manager.DynamicPageConnectionManager;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.DeleteStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.UpdateStepRequest;
import com.asset.ccat.dynamic_page.utils.CryptoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author assem.hassan
 */
@Component
public class StepService {

    @Autowired
    private StepDao stepDao;
    @Autowired
    private ProcedureConfigurationDao procedureConfigurationDao;
    @Autowired
    private HttpConfigurationDao httpConfigurationDao;
    @Autowired
    private ProcedureParameterDao parameterDao;
    @Autowired
    private HttpParameterDao httpParameterDao;
    @Autowired
    private ProcedureCursorMappingDao cursorMappingDao;
    @Autowired
    private HttpResponseMappingDao httpResponseMappingDao;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CryptoUtils cryptoUtils;
    @Autowired
    private Properties properties;
    @Autowired
    private DynamicPageConnectionManager dynamicPageConnectionManager;

    @Transactional(rollbackFor = Exception.class)
    public StepModel addStep(AddStepRequest request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - addStep()");
        CCATLogger.DEBUG_LOGGER.debug("Start adding Step [" + request.getStep().getStepName() + "]");
        Integer stepId = stepDao.addStep(request);
        if (Objects.isNull(stepId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Add Step[" + request.getStep().getStepName() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "AddStep");
        }
        CCATLogger.DEBUG_LOGGER.debug("Added Step successfully with ID [" + stepId + "]");
        CCATLogger.DEBUG_LOGGER.debug("Start adding Step Configurations");
        if (request.getStep().getStepType() == StepTypes.PROCEDURE.type) {
            addSPConfigAndParameters(request.getStep().getStepConfiguration(), stepId);
            StepModel addedStep = retrieveStepWithSPConfig(stepId);
            ((ProcedureConfigurationModel) addedStep.getStepConfiguration()).setDatabasePassword(null);
            CCATLogger.DEBUG_LOGGER.debug("Added Step Configurations successfully");
            CCATLogger.DEBUG_LOGGER.debug("Ending StepService - addStep()");
            return addedStep;
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Start Adding Http Configurations");
            addHttpConfigAndParameters(request.getStep().getStepConfiguration(), stepId);
            StepModel addedStep = retrieveStepWithAnyConfig(stepId);
            CCATLogger.DEBUG_LOGGER.debug("Added Step Configurations successfully");
            CCATLogger.DEBUG_LOGGER.debug("Ending StepService - addStep()");
            return addedStep;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public StepModel updateStep(UpdateStepRequest request) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - updateStep()");
        CCATLogger.DEBUG_LOGGER.debug("Start updating Step [" + request.getStep().getStepName() + "]");
        StepModel stepModel = request.getStep();
        boolean isUpdated = stepDao.updateStep(request);
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Update Step[" + request.getStep().getStepName() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "UpdateStep");
        }
        CCATLogger.DEBUG_LOGGER.debug("Step updated successfully");
        CCATLogger.DEBUG_LOGGER.debug("Start updating Step Configurations");
        if (stepModel.getStepType().equals(StepTypes.PROCEDURE.type)) {
            if (!Objects.isNull(stepModel.getStepConfiguration())) {
                updateSPConfigAndParameters(stepModel.getStepConfiguration());
            }
            CCATLogger.DEBUG_LOGGER.debug("Updated Step Configurations successfully");
            StepModel updatedStep = retrieveStepWithSPConfig(stepModel.getId());
            ((ProcedureConfigurationModel) updatedStep.getStepConfiguration()).setDatabasePassword(null);
            CCATLogger.DEBUG_LOGGER.debug("Ending StepService - updateStep()");
            return updatedStep;
        } else {
            if (Objects.nonNull(stepModel.getStepConfiguration())) {
                //Update here
                updateHTTPConfigAndParameters(stepModel.getStepConfiguration());
            }
            CCATLogger.DEBUG_LOGGER.debug("Updated Step HTTP Configurations successfully");
            StepModel updatedStep = retrieveStepWithAnyConfig(stepModel.getId());
            CCATLogger.DEBUG_LOGGER.debug("Ending StepService - updateStep()");
            return updatedStep;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteStep(DeleteStepRequest request, boolean forceDelete) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - deleteStep()");
        CCATLogger.DEBUG_LOGGER.debug("Start Deleting Step with ID [" + request.getStepId() + "]");
        StepModel stepModel = retrieveStepWithAnyConfig(request.getStepId());
        if (stepModel.getStepType().equals(StepTypes.PROCEDURE.type)) {
            CCATLogger.DEBUG_LOGGER.debug("Start Deleting Step Procedure Configurations ID [" + request.getStepId() + "]");
            deleteSPConfigAndParameters(stepModel.getStepConfiguration(), forceDelete);
        } else if (stepModel.getStepType().equals(StepTypes.HTTP.type)) {
            CCATLogger.DEBUG_LOGGER.debug("Start Deleting Step HTTP Configurations ID [" + request.getStepId() + "]");
            deleteHttpConfigAndParameters(stepModel.getStepConfiguration(), forceDelete);
        }
        boolean isDeleted = stepDao.deleteStep(request.getStepId());
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed To delete step with ID [" + request.getStepId() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "Step");
        }
    }

    private void addSPConfigAndParameters(StepConfigurationModel stepConfigurationModel, Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - addSPConfigAndParameters()");
        ProcedureConfigurationModel spConfigModel = (ProcedureConfigurationModel) stepConfigurationModel;
        spConfigModel.setStepId(stepId);
        String password = spConfigModel.getDatabasePassword();
        spConfigModel.setDatabasePassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
        Integer procedureConfigurationId = procedureConfigurationDao.addProcedureConfiguration(spConfigModel);
        if (Objects.isNull(procedureConfigurationId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Add ProcedureConfiguration");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "ProcedureConfiguration");
        }
        for (ProcedureParameterModel parameter : spConfigModel.getParameters()) {
            parameter.setConfigId(procedureConfigurationId);
            addProcedureParameter(parameter);
        }

        CCATLogger.DEBUG_LOGGER.debug("Try creating datasource for added Step");
        dynamicPageConnectionManager.createDataSource(spConfigModel.getDatabaseURL(),
                spConfigModel.getDatabaseUsername(),
                password,
                spConfigModel.getExtraConfigurations());

        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - addSPConfigAndParameters()");
    }

    private void addHttpConfigAndParameters(StepConfigurationModel stepConfigurationModel, Integer stepId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService -> addHttpConfigAndParameters()");
        HttpConfigurationModel httpConfigModel = (HttpConfigurationModel) stepConfigurationModel;
        httpConfigModel.setStepId(stepId);
        Integer httpConfigurationId = httpConfigurationDao.addHttpConfiguration(httpConfigModel);
        if (Objects.isNull(httpConfigurationId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Add addHttpConfigAndParameters");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "addHttpConfigAndParameters");
        }
        for (HttpParameterModel parameter : httpConfigModel.getParameters()) {
            parameter.setConfigId(httpConfigurationId);
            addHttpParameter(parameter);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - addHttpConfigAndParameters()");
    }


    private void updateSPConfigAndParameters(StepConfigurationModel stepConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - updateSPConfigAndParameters()");
        ProcedureConfigurationModel procedureConfigModel = (ProcedureConfigurationModel) stepConfigurationModel;
        String password = procedureConfigModel.getDatabasePassword();
        if (password != null && !password.trim().isEmpty()) {
            procedureConfigModel.setDatabasePassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
        }
        boolean updatedSPConfig = procedureConfigurationDao.updateProcedureConfiguration(procedureConfigModel);
        if (!updatedSPConfig) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Update Procedure Configuration");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "ProcedureConfiguration");
        }
        List<ProcedureParameterModel> parameters = procedureConfigModel.getParameters();
        if (!Objects.isNull(parameters) && !parameters.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start syncing parameters list");
            syncProcedureParameters(parameters, procedureConfigModel.getId());
            CCATLogger.DEBUG_LOGGER.debug("Finished syncing parameters list");
        }

        CCATLogger.DEBUG_LOGGER.debug("Try updating created datasource");
        ProcedureConfigurationModel oldConfig = procedureConfigurationDao.retrieveProcedureStepConfigurations(procedureConfigModel.getId());
        if (password == null || password.isBlank()) {
            password = cryptoUtils.decrypt(oldConfig.getDatabasePassword(), properties.getEncryptionKey());
        }
        dynamicPageConnectionManager.updateDataSource(oldConfig.getDatabaseURL(),
                procedureConfigModel.getDatabaseURL(),
                procedureConfigModel.getDatabaseUsername(),
                password,
                procedureConfigModel.getExtraConfigurations());
        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - updateSPConfigAndParameters()");
    }

    private void deleteSPConfigAndParameters(StepConfigurationModel stepConfigurationModel, boolean forceDelete) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Starting StepService - updateSPConfigAndParameters()");
        ProcedureConfigurationModel procedureConfigModel = (ProcedureConfigurationModel) stepConfigurationModel;
        List<ProcedureParameterModel> parameters = procedureConfigModel.getParameters();
        if (!Objects.isNull(parameters)) {
            for (ProcedureParameterModel parameter : parameters) {
                if (!forceDelete && parameterDao.getDependentParametersCount(parameter.getId()) > 0) {
                    throw new DynamicPageException(ErrorCodes.ERROR.DELETE_PROHIBITED, Defines.SEVERITY.ERROR, "Step parameters has children");
                }
                if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)) {
                    cursorMappingDao.batchDeleterCursorMapping(parameter.getId());
                }
                boolean isParameterDeleted = parameterDao.deleteProcedureParameter(parameter.getId());
                if (!isParameterDeleted) {
                    CCATLogger.DEBUG_LOGGER.error("Failed To Delete Procedure Parameter");
                    throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "ProcedureParameter");
                }
            }
        }
        boolean isDeletedSPConfig = procedureConfigurationDao.deleteProcedureConfiguration(procedureConfigModel.getId());
        if (!isDeletedSPConfig) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Delete Procedure Configuration");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "ProcedureConfiguration");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - deleteSPConfigAndParameters()");
    }


    private StepModel retrieveStepWithSPConfig(Integer stepId) throws DynamicPageException {
        StepModel stepModel = stepDao.retrieveStepById(stepId);
        if (stepModel == null) {
            CCATLogger.DEBUG_LOGGER.error("Failed to retrieve step with ID [" + stepId + "] ");
            throw new DynamicPageException(ErrorCodes.ERROR.RETRIEVAL_FAILED, Defines.SEVERITY.ERROR, "Step");
        }
        stepModel.setId(stepId);
        HashMap<Integer, ProcedureConfigurationModel> configMap = procedureConfigurationDao.retrieveProcedureConfigWithParametersByStepId(stepId);
        if (configMap.get(stepId) == null) {
            CCATLogger.DEBUG_LOGGER.error("Failed to retrieve step configurations for step with ID [" + stepId + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.RETRIEVAL_FAILED, Defines.SEVERITY.ERROR, "Step Configurations");
        }
        stepModel.setStepConfiguration(configMap.get(stepId));
        return stepModel;
    }


    private void syncProcedureParameters(List<ProcedureParameterModel> parameters, Integer configId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing procedure parameters ids");
        HashSet<Integer> existingParameters = parameterDao.retrieveAllParametersIdsByConfigId(configId);

        for (ProcedureParameterModel param : parameters) {
            if (param.getId() == null || !existingParameters.contains(param.getId())) {
                CCATLogger.DEBUG_LOGGER.debug("Adding new parameter with name [" + param.getParameterName() + "]");
                param.setConfigId(configId);
                addProcedureParameter(param);
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Updating existing parameter with name [" + param.getParameterName() + "]");
                existingParameters.remove(param.getId());
                updateProcedureParameter(param);
            }
        }
        CCATLogger.DEBUG_LOGGER.debug("Delete remaining parameters");
        for (Integer paramId : existingParameters) {
            CCATLogger.DEBUG_LOGGER.debug("Delete parameter with ID [" + paramId + "]");
            deleteProcedureParameter(paramId);
        }
    }

    private void addProcedureParameter(ProcedureParameterModel parameter) throws DynamicPageException {
        if (parameter.getInputMethod() != null
                && parameter.getInputMethod().equals(InputMethod.MENU.id)
                && parameter.getDropdownList() != null) {
            try {
                parameter.setStaticData(objectMapper.writeValueAsString(parameter.getDropdownList()));
            } catch (JsonProcessingException e) {
                CCATLogger.DEBUG_LOGGER.error("Failed to serialize dropdownlist for parameter [" + parameter.getParameterName() + "]", e);
            }
        }
        Integer parameterId = parameterDao.addProcedureParameter(parameter);
        if (Objects.isNull(parameterId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Add Procedure Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "ProcedureParameter");
        }
        if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)
                && parameter.getCursorParameterMappings() != null
                && !parameter.getCursorParameterMappings().isEmpty()) {
            cursorMappingDao.batchInsertCursorMapping(parameter.getCursorParameterMappings()
                    , parameter.getCursorParameterMappings().size(), parameterId);
        }
    }

    private void addHttpParameter(HttpParameterModel parameter) throws DynamicPageException {
        if (parameter.getInputMethod() != null
                && parameter.getInputMethod().equals(InputMethod.MENU.id)
                && parameter.getDropdownList() != null) {
            try {
                parameter.setStaticData(objectMapper.writeValueAsString(parameter.getDropdownList()));
            } catch (JsonProcessingException e) {
                CCATLogger.DEBUG_LOGGER.error("Failed to serialize dropdown-list for parameter [" + parameter.getParameterName() + "]", e);
            }
        }
        Integer parameterId = httpParameterDao.addHttpParameter(parameter);
        if (Objects.isNull(parameterId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Add Http Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "HTTPParameter");
        }
        if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)
                && parameter.getHttpResponseMappingModels() != null
                && !parameter.getHttpResponseMappingModels().isEmpty()) {
            httpResponseMappingDao.batchInsertResponseMapping(parameter.getHttpResponseMappingModels()
                    , parameter.getHttpResponseMappingModels().size(), parameterId);
        }
    }

    private void updateProcedureParameter(ProcedureParameterModel parameter) throws DynamicPageException {
        if (parameter.getInputMethod() != null
                && parameter.getInputMethod().equals(InputMethod.MENU.id)
                && parameter.getDropdownList() != null) {
            try {
                parameter.setStaticData(objectMapper.writeValueAsString(parameter.getDropdownList()));
            } catch (JsonProcessingException e) {
                CCATLogger.DEBUG_LOGGER.error("Failed to serialize dropdownlist for parameter [" + parameter.getParameterName() + "]", e);
            }
        }
        boolean isParameterUpdated = parameterDao.updateProcedureParameter(parameter);
        if (!isParameterUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Update Procedure Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "ProcedureParameter");
        }
        if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)
                && !Objects.isNull(parameter.getCursorParameterMappings())) {
            // drop previous mappings
            cursorMappingDao.deleteParameterCursorMappings(parameter.getId());
            // add updated mappings
            if (!parameter.getCursorParameterMappings().isEmpty()) {
                cursorMappingDao.batchInsertCursorMapping(parameter.getCursorParameterMappings()
                        , parameter.getCursorParameterMappings().size(), parameter.getId());
            }
        }

    }

    private void deleteProcedureParameter(Integer parameterId) throws DynamicPageException {
        cursorMappingDao.batchDeleterCursorMapping(parameterId);
        boolean isParameterDeleted = parameterDao.deleteProcedureParameter(parameterId);
        if (!isParameterDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Delete Procedure Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "ProcedureParameter");
        }
    }


    private StepModel retrieveStepWithAnyConfig(Integer stepId) throws DynamicPageException {
        StepModel stepModel = stepDao.retrieveStepById(stepId);
        if (stepModel == null) {
            CCATLogger.DEBUG_LOGGER.error("Failed to retrieve step with ID [" + stepId + "] ");
            throw new DynamicPageException(ErrorCodes.ERROR.RETRIEVAL_FAILED, Defines.SEVERITY.ERROR, "Step");
        }
        stepModel.setId(stepId);
        if (stepModel.getStepType().equals(StepTypes.HTTP.type)) {
            HashMap<Integer, HttpConfigurationModel> configMap = httpConfigurationDao.retrieveHTTPConfigWithParametersByStepId(stepId);
            if (configMap.get(stepId) == null) {
                CCATLogger.DEBUG_LOGGER.error("Failed to retrieve step configurations for step with ID [" + stepId + "]");
                throw new DynamicPageException(ErrorCodes.ERROR.RETRIEVAL_FAILED, Defines.SEVERITY.ERROR, "Step Configurations");
            }
            stepModel.setStepConfiguration(configMap.get(stepId));

        } else if (stepModel.getStepType().equals(StepTypes.PROCEDURE.type)) {
            HashMap<Integer, ProcedureConfigurationModel> configMap = procedureConfigurationDao.retrieveProcedureConfigWithParametersByStepId(stepId);
            if (configMap.get(stepId) == null) {
                CCATLogger.DEBUG_LOGGER.error("Failed to retrieve step configurations for step with ID [" + stepId + "]");
                throw new DynamicPageException(ErrorCodes.ERROR.RETRIEVAL_FAILED, Defines.SEVERITY.ERROR, "Step Configurations");
            }
            stepModel.setStepConfiguration(configMap.get(stepId));
        }
        return stepModel;
    }


    private void updateHTTPConfigAndParameters(StepConfigurationModel stepConfigurationModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started StepService - updateHTTPConfigAndParameters()");
        HttpConfigurationModel httpConfigModel = (HttpConfigurationModel) stepConfigurationModel;

        boolean updatedHTTPConfig = httpConfigurationDao.updateHTTPConfiguration(httpConfigModel);
        if (!updatedHTTPConfig) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Update HTTP Configuration");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "HTTPConfiguration");
        }
        List<HttpParameterModel> parameters = httpConfigModel.getParameters();
        if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start syncing parameters list");
            syncHttpParameters(parameters, httpConfigModel.getId());
            CCATLogger.DEBUG_LOGGER.debug("Finished syncing parameters list");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - updateHTTPConfigAndParameters()");
    }


    private void syncHttpParameters(List<HttpParameterModel> parameters, Integer configId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving existing http parameters ids");
        HashSet<Integer> existingParameters = httpParameterDao.retrieveAllParametersIdsByConfigId(configId);

        for (HttpParameterModel param : parameters) {
            if (param.getId() == null || !existingParameters.contains(param.getId())) {
                CCATLogger.DEBUG_LOGGER.debug("Adding new parameter with name [" + param.getParameterName() + "]");
                param.setConfigId(configId);
                addHttpParameter(param);
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Updating existing parameter with name [" + param.getParameterName() + "]");
                existingParameters.remove(param.getId());
                updateHttpParameter(param);
//                addHttpParameter(param);
            }
        }
        CCATLogger.DEBUG_LOGGER.debug("Delete remaining parameters");
        for (Integer paramId : existingParameters) {
            CCATLogger.DEBUG_LOGGER.debug("Delete parameter with ID [" + paramId + "]");
            deleteHttpParameter(paramId);
        }
    }


    private void deleteHttpParameter(Integer parameterId) throws DynamicPageException {
        httpResponseMappingDao.batchDeleterHttpResponseMapping(parameterId);
        boolean isParameterDeleted = httpParameterDao.deleteHttpParameter(parameterId);
        if (!isParameterDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Delete Procedure Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "HttpParameter");
        }
    }


    private void deleteHttpConfigAndParameters(StepConfigurationModel stepConfigurationModel, boolean forceDelete) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Starting StepService - deleteHttpConfigAndParameters()");
        HttpConfigurationModel httpConfigModel = (HttpConfigurationModel) stepConfigurationModel;
        List<HttpParameterModel> parameters = httpConfigModel.getParameters();
        if (!Objects.isNull(parameters)) {
            for (HttpParameterModel parameter : parameters) {
                if (!forceDelete && httpParameterDao.getDependentHttpParametersCount(parameter.getId()) > 0) {
                    throw new DynamicPageException(ErrorCodes.ERROR.DELETE_PROHIBITED, Defines.SEVERITY.ERROR, "Step parameters has children");
                }
                if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)) {
                    httpResponseMappingDao.batchDeleterHttpResponseMapping(parameter.getId());
                }
                boolean isParameterDeleted = httpParameterDao.deleteHttpParameter(parameter.getId());
                if (!isParameterDeleted) {
                    CCATLogger.DEBUG_LOGGER.error("Failed To Delete HTTP Parameter");
                    throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "HttpParameter");
                }
            }
        }
        boolean isDeletedHTTPConfig = httpConfigurationDao.deleteHttpConfiguration(httpConfigModel.getId());
        if (!isDeletedHTTPConfig) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Delete HTTP Configuration");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "HttpConfiguration");
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending StepService - deleteHttpConfigAndParameters()");
    }

    private void updateHttpParameter(HttpParameterModel parameter) throws DynamicPageException {
        if (parameter.getInputMethod() != null
                && parameter.getInputMethod().equals(InputMethod.MENU.id)
                && parameter.getDropdownList() != null) {
            try {
                parameter.setStaticData(objectMapper.writeValueAsString(parameter.getDropdownList()));
            } catch (JsonProcessingException e) {
                CCATLogger.DEBUG_LOGGER.error("Failed to serialize dropdownlist for parameter [" + parameter.getParameterName() + "]", e);
            }
        }
        boolean isParameterUpdated = httpParameterDao.updateHttpParameter(parameter);
        if (!isParameterUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed To Update HTTP Parameter");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "HttpParameter");
        }
        if (parameter.getParameterDataType().equals(ParameterDataTypes.CURSOR.id)
                && !Objects.isNull(parameter.getHttpResponseMappingModels())) {
            // drop previous mappings
            httpResponseMappingDao.deleteParameterResponseMappings(parameter.getId());
            // add updated mappings
            if (!parameter.getHttpResponseMappingModels().isEmpty()) {
                httpResponseMappingDao.batchInsertResponseMapping(parameter.getHttpResponseMappingModels()
                        , parameter.getHttpResponseMappingModels().size(), parameter.getId());
            }
        }
    }

}
