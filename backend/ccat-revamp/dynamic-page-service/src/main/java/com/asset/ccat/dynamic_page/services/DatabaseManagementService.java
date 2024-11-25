package com.asset.ccat.dynamic_page.services;

import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.database.dao.*;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.manager.DynamicPageConnectionManager;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.asset.ccat.dynamic_page.models.dynamic_page.mapping.DynamicPageModelForMapping;
import com.asset.ccat.dynamic_page.models.dynamic_page.mapping.StepModelForMapping;
import com.asset.ccat.dynamic_page.models.requests.ParseProcedureParametersRequest;
import com.asset.ccat.dynamic_page.models.requests.TestDBConnectionRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddDynamicPageRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.AddStepRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ExportDynamicPageSettingRequest;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.AddDynamicPageResponse;
import com.asset.ccat.dynamic_page.utils.ParsingUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class DatabaseManagementService {
    private final DynamicPageConnectionManager dynamicPageConnectionManager;
    private final ParsingUtil parsingUtil;
    private final DynamicPagesDao dynamicPagesDao;
    private final ProcedureConfigurationDao procedureConfigurationDao;
    private final HttpConfigurationDao httpConfigurationDao;
    private final ProcedureCursorMappingDao procedureCursorMappingDao;
    private final HttpResponseMappingDao httpResponseMappingDao;
    private final DynamicPagesService dynamicPagesService;
    private final StepService stepService;

    public DatabaseManagementService(DynamicPageConnectionManager dynamicPageConnectionManager, ParsingUtil parsingUtil, DynamicPagesDao dynamicPagesDao, ProcedureConfigurationDao procedureConfigurationDao, HttpConfigurationDao httpConfigurationDao, ProcedureCursorMappingDao procedureCursorMappingDao, HttpResponseMappingDao httpResponseMappingDao, DynamicPagesService dynamicPagesService, StepService stepService) {
        this.dynamicPageConnectionManager = dynamicPageConnectionManager;
        this.parsingUtil = parsingUtil;
        this.dynamicPagesDao = dynamicPagesDao;
        this.procedureConfigurationDao = procedureConfigurationDao;
        this.httpConfigurationDao = httpConfigurationDao;
        this.procedureCursorMappingDao = procedureCursorMappingDao;
        this.httpResponseMappingDao = httpResponseMappingDao;
        this.dynamicPagesService = dynamicPagesService;
        this.stepService = stepService;
    }

    public void testDBConnection(TestDBConnectionRequest testRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementService - testDBConnection()");
        CCATLogger.DEBUG_LOGGER.info("Start serving test DB connection request");
        dynamicPageConnectionManager.testDBConnection(testRequest.getDbUrl(), testRequest.getDbUsername(), testRequest.getDbPassword());
        CCATLogger.DEBUG_LOGGER.info("Finished serving test DB connection request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended DatabaseManagementService - testDBConnection()");
    }

    public List<ProcedureParameterModel> parseProcedureParametersQuery(ParseProcedureParametersRequest parseRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementService - parseProcedureParametersQuery()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse procedure parameters query request");
        List<ProcedureParameterModel> parameters = parsingUtil.parseProcedureParameters(parseRequest.getQuery());
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse procedure parameters query request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended DatabaseManagementService - parseProcedureParametersQuery()");
        return parameters;
    }
    public DynamicPageModel importDynamicPageSettings(Integer pageId) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementService - importDynamicPageSettings()");
        CCATLogger.DEBUG_LOGGER.info("Start serving import Dynamic Page Date Request");

        HashMap<Integer, DynamicPageModel> dynamicPageModelHashMap = dynamicPagesDao.retrieveDynamicPageWithSteps(pageId);
        DynamicPageModel dynamicPageModel = dynamicPageModelHashMap.values().stream().findFirst().get();
        List<StepModel> pgSteps = dynamicPageModel.getSteps();

        for(StepModel stepModel : pgSteps){
            if(stepModel.getStepType().equals(StepTypes.PROCEDURE.type)){
                try {
                    Optional<ProcedureConfigurationModel> optionalProcedureConfigurationModel = procedureConfigurationDao
                            .retrieveProcedureConfigWithParametersByStepId(stepModel.getId())
                            .values().stream().findFirst();
                    if(optionalProcedureConfigurationModel.isPresent()){
                        ProcedureConfigurationModel currentStepConfigurations = optionalProcedureConfigurationModel.get();
                        List<ProcedureParameterModel> parameters = currentStepConfigurations.getParameters();
                        for(ProcedureParameterModel parameter : parameters){
                            try {
                                List<ProcedureCursorMappingModel> cursorMappings = procedureCursorMappingDao
                                        .retrieveCursorMappingsByParameterId(parameter.getId());
                                    parameter.setCursorParameterMappings(cursorMappings);

                            } catch (DynamicPageException e) {
                                CCATLogger.DEBUG_LOGGER.error("Failed to get ProcedureCursorMappingModel ", e);
                                CCATLogger.DEBUG_LOGGER.info("Failed to get ProcedureCursorMappingModel ", e);
                                try {
                                    throw new DynamicPageException(ErrorCodes.ERROR.EXPORTING_DYNAMIC_PAGE_DATA_ERROR, Defines.SEVERITY.ERROR);
                                } catch (DynamicPageException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                        currentStepConfigurations.setParameters(parameters);
                        stepModel.setStepConfiguration(currentStepConfigurations);
                    }

                } catch (DynamicPageException e) {
                    CCATLogger.DEBUG_LOGGER.error("Failed to get SP Step For Dynamic Page ", e);
                    CCATLogger.DEBUG_LOGGER.info("Failed to get SP Step For Dynamic Page ", e);
                    throw new DynamicPageException(ErrorCodes.ERROR.EXPORTING_DYNAMIC_PAGE_DATA_ERROR, Defines.SEVERITY.ERROR);
                }
            }
            else if(stepModel.getStepType().equals(StepTypes.HTTP.type)){
                try {
                    Optional<HttpConfigurationModel> optionalHttpConfigurationModel = httpConfigurationDao
                            .retrieveHTTPConfigWithParametersByStepId(stepModel.getId())
                            .values().stream().findFirst();
                    if(optionalHttpConfigurationModel.isPresent()){
                        HttpConfigurationModel currentStepConfigurations = optionalHttpConfigurationModel.get();
                        stepModel.setStepConfiguration(currentStepConfigurations);
                        List<HttpParameterModel> parameters = currentStepConfigurations.getParameters();
                        for(HttpParameterModel parameter : parameters){
                            try {
                                List<HttpResponseMappingModel> httpResponseMappingModels  = httpResponseMappingDao.
                                        retrieveHttpResponseMappingsByParameterId(parameter.getId());
                                    parameter.setHttpResponseMappingModels(httpResponseMappingModels);
                            } catch (DynamicPageException e) {
                                CCATLogger.DEBUG_LOGGER.error("Failed to get httpResponseMappingModels ", e);
                                CCATLogger.DEBUG_LOGGER.info("Failed to get httpResponseMappingModels ", e);
                                try {
                                    throw new DynamicPageException(ErrorCodes.ERROR.EXPORTING_DYNAMIC_PAGE_DATA_ERROR, Defines.SEVERITY.ERROR);
                                } catch (DynamicPageException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                        currentStepConfigurations.setParameters(parameters);
                        stepModel.setStepConfiguration(currentStepConfigurations);
                    }
                } catch (DynamicPageException e) {
                    CCATLogger.DEBUG_LOGGER.error("Failed to get HTTP Step For Dynamic Page ", e);
                    CCATLogger.DEBUG_LOGGER.info("Failed to get HTTP Step For Dynamic Page ", e);
                    throw new DynamicPageException(ErrorCodes.ERROR.EXPORTING_DYNAMIC_PAGE_DATA_ERROR, Defines.SEVERITY.ERROR);
                }
            }
        }
        dynamicPageModel.setSteps(pgSteps);
        CCATLogger.DEBUG_LOGGER.debug("Ended DatabaseManagementService - importDynamicPageSettings() Successfully");
        CCATLogger.DEBUG_LOGGER.info("Ended serving import Dynamic Page Date Request Successfully");

        return dynamicPageModel;
    }

    @Transactional(rollbackFor = Exception.class)
    public void exportDynamicPageSettings(ExportDynamicPageSettingRequest request) throws IOException, DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementService - exportDynamicPageSettings()");
        CCATLogger.DEBUG_LOGGER.info("Start serving export Dynamic Page Date Request");
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(request.getFile().getInputStream());
        try{
            DynamicPageModelForMapping dynamicPageModel = gson.fromJson(reader,DynamicPageModelForMapping.class);
            // Adding new Dynamic Page
            AddDynamicPageRequest addDynamicPageRequest = new AddDynamicPageRequest();
            addDynamicPageRequest.setPageName(dynamicPageModel.getPageName());
            addDynamicPageRequest.setPrivilegeName(dynamicPageModel.getPrivilegeName());
            addDynamicPageRequest.setRequireSubscriber(dynamicPageModel.getRequireSubscriber());
            AddDynamicPageResponse addDynamicPageResponse =  dynamicPagesService.addAdminDynamicPage(addDynamicPageRequest);

            //Adding All Steps
            List<StepModelForMapping> stepModelForMappings = dynamicPageModel.getSteps();
            for(StepModelForMapping stepModelForMapping : stepModelForMappings){
                AddStepRequest addStepRequest = new AddStepRequest();
                addStepRequest.setPageId(addDynamicPageResponse.getPageId());

                StepModel stepModel = new StepModel();
                stepModel.setPageId(stepModelForMapping.getPageId());
                stepModel.setStepType(stepModelForMapping.getStepType());
                stepModel.setStepName(stepModelForMapping.getStepName());
                stepModel.setStepOrder(stepModelForMapping.getStepOrder());
                stepModel.setIsHidden(stepModelForMapping.getIsHidden());

                JsonElement jsonElement = gson.toJsonTree(stepModelForMapping.getStepConfiguration());
                if(stepModelForMapping.getStepType().equals(StepTypes.PROCEDURE.type)){
                    ProcedureConfigurationModel spConfigurations = gson.fromJson(jsonElement,ProcedureConfigurationModel.class);
                    stepModel.setStepConfiguration(spConfigurations);
                }
                else if (stepModelForMapping.getStepType().equals(StepTypes.HTTP.type)){
                    HttpConfigurationModel HTTPConfigurations = gson.fromJson(jsonElement,HttpConfigurationModel.class);
                    if(Objects.isNull(HTTPConfigurations.getHeaders())){
                        HTTPConfigurations.setHeaders("");
                    }
                    stepModel.setStepConfiguration(HTTPConfigurations);
                }
                addStepRequest.setStep(stepModel);
                stepService.addStep(addStepRequest);
            }
        }catch (DynamicPageException e){
            CCATLogger.DEBUG_LOGGER.error("Failed to parsing data from Dynamic Page setting file ", e);
            CCATLogger.DEBUG_LOGGER.info("Failed to parsing data from Dynamic Page setting file  ", e);
            throw new DynamicPageException(ErrorCodes.ERROR.EXPORTING_DYNAMIC_PAGE_DATA_ERROR, Defines.SEVERITY.ERROR);

        }

        CCATLogger.DEBUG_LOGGER.debug("Ended DatabaseManagementService - exportDynamicPageSettings() Successfully");
        CCATLogger.DEBUG_LOGGER.info("Ended serving export Dynamic Page Date Request Successfully");

    }

}
