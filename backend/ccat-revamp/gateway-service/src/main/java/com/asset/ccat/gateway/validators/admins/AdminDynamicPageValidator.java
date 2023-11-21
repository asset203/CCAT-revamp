package com.asset.ccat.gateway.validators.admins;

import java.util.Objects;

import com.asset.ccat.gateway.constants.ParametersResponseType;
import com.asset.ccat.gateway.models.admin.dynamic_page.*;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.*;
import org.springframework.stereotype.Component;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;

@Component
public class AdminDynamicPageValidator {

    public void validateGetDynamicPage(GetDynamicPageRequest getDynamicPageRequest) throws GatewayValidationException {
        if (Objects.isNull(getDynamicPageRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
    }

    public void validateImportDynamicPage(ImportDynamicPageRequest importDynamicPageRequest) throws GatewayValidationException {
        if (Objects.isNull(importDynamicPageRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
    }
    public void validateExportDynamicPage(ExportDynamicPageSettingRequest request) throws GatewayValidationException {
        if (Objects.isNull(request.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "File");
        }
    }

    public void validateAddDynamicPage(AddDynamicPageRequest addDynamicPageRequest) throws GatewayValidationException {
        if (Objects.isNull(addDynamicPageRequest.getPrivilegeName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "privilegeName");
        }
        if (Objects.isNull(addDynamicPageRequest.getPageName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageName");
        }
        if (Objects.isNull(addDynamicPageRequest.getRequireSubscriber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "requireSubscriber");
        }

    }

    public void validateUpdateDynamicPage(UpdateDynamicPageRequest updateDynamicPageRequest)
            throws GatewayValidationException {
        if (Objects.isNull(updateDynamicPageRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(updateDynamicPageRequest.getPrivilegeId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "privilegeId");
        }
        if (Objects.isNull(updateDynamicPageRequest.getPageName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageName");
        }
        if (Objects.isNull(updateDynamicPageRequest.getRequireSubscriber())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "requireSubscriber");
        }
    }

    public void validateDeleteDynamicPage(DeleteDynamicPageRequest deleteDynamicPageRequest)
            throws GatewayValidationException {
        if (Objects.isNull(deleteDynamicPageRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
    }

    public void validateAddStep(AddStepRequest addStepRequest) throws GatewayValidationException {
        if (Objects.isNull(addStepRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(addStepRequest.getStep())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "step");
        }
        if (Objects.isNull(addStepRequest.getStep().getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(addStepRequest.getStep().getStepType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepType");
        }
        if (Objects.isNull(addStepRequest.getStep().getStepName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepName");
        }
        if (Objects.isNull(addStepRequest.getStep().getStepOrder())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepOrder");
        }
        if (Objects.isNull(addStepRequest.getStep().getStepConfiguration())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepConfiguration");
        }
        if (addStepRequest.getStep().getStepType().equals(1)) {
            ProcedureConfigurationModel procedure = (ProcedureConfigurationModel) addStepRequest.getStep()
                    .getStepConfiguration();
            if (Objects.isNull(procedure.getProcedureName())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "procedureName");
            }
            if (Objects.isNull(procedure.getProcedureName())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "procedureName");
            }
            if (Objects.isNull(procedure.getDatabaseURL())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "databaseUrl");
            }
            if (Objects.isNull(procedure.getDatabaseUsername())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "databaseUsername");
            }
            if (Objects.isNull(procedure.getMaxRecords())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "maxRecords");
            }
            if (Objects.isNull(procedure.getSuccessCode())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "successCode");
            }
            if (Objects.isNull(procedure.getParameters()) || procedure.getParameters().isEmpty()) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameters");
            }
            for (ProcedureParameterModel parameter : procedure.getParameters()) {
//                if (Objects.isNull(parameter.getConfigId())) {
//                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "configId");
//                }
                if (Objects.isNull(parameter.getParameterName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterName");
                }
                if (Objects.isNull(parameter.getParameterDataType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterDataType");
                }
                if (Objects.isNull(parameter.getParameterType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterType");
                }
                if (Objects.isNull(parameter.getParameterOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterOrder");
                }
                if (Objects.isNull(parameter.getDisplayOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "displayOrder");
                }
                if (!Objects.isNull(parameter.getCursorParameterMappings()) && !parameter.getCursorParameterMappings().isEmpty()) {
                    for (ProcedureCursorMappingModel procedureCursor : parameter.getCursorParameterMappings()) {
                        if (Objects.isNull(procedureCursor.getDisplayColumnName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "displayColumnName");
                        }
                        if (Objects.isNull(procedureCursor.getActualColumnName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "actualColumnName");
                        }
                    }
                }
            }
        }
        else if(addStepRequest.getStep().getStepType().equals(2)){
            HttpConfigurationModel httpConfig = (HttpConfigurationModel) addStepRequest.getStep()
                    .getStepConfiguration();
            if (Objects.isNull(httpConfig.getHttpMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Http Method");
            }
            if (Objects.isNull(httpConfig.getHttpURL())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "HttpURL");
            }
            if (Objects.isNull(httpConfig.getResponseContentType())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "ResponseContentType");
            }

            if (Objects.isNull(httpConfig.getMaxRecords())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "maxRecords");
            }
            if (Objects.isNull(httpConfig.getSuccessCode())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "successCode");
            }
            if (Objects.isNull(httpConfig.getParameters()) || httpConfig.getParameters().isEmpty()) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameters");
            }
            for (HttpParameterModel parameter : httpConfig.getParameters()) {
                if (Objects.isNull(parameter.getParameterName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterName");
                }
                if (Objects.isNull(parameter.getParameterDataType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterDataType");
                }
                if (Objects.isNull(parameter.getParameterType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterType");
                }
                if (Objects.isNull(parameter.getParameterOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterOrder");
                }
                if (Objects.isNull(parameter.getDisplayOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "displayOrder");
                }
                if (!Objects.isNull(parameter.getHttpResponseMappingModels()) && !parameter.getHttpResponseMappingModels().isEmpty()) {
                    for (HttpResponseMappingModel procedureCursor : parameter.getHttpResponseMappingModels()) {
                        if (Objects.isNull(procedureCursor.getHeaderDisplayName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "headerDisplayName");
                        }
                        if (Objects.isNull(procedureCursor.getHeaderName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "headerName");
                        }
                    }
                }
            }
        }
    }

    public void validateUpdateStep(UpdateStepRequest updateStepRequest) throws GatewayValidationException {
        if (Objects.isNull(updateStepRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(updateStepRequest.getStep())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "step");
        }
        if (Objects.isNull(updateStepRequest.getStep().getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Id");
        }
        if (Objects.isNull(updateStepRequest.getStep().getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(updateStepRequest.getStep().getStepType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepType");
        }
        if (Objects.isNull(updateStepRequest.getStep().getStepName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepName");
        }
        if (Objects.isNull(updateStepRequest.getStep().getStepOrder())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepOrder");
        }
        if (Objects.isNull(updateStepRequest.getStep().getStepConfiguration())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepConfiguration");
        }
        if (updateStepRequest.getStep().getStepType() == 1) {
            ProcedureConfigurationModel procedure = (ProcedureConfigurationModel) updateStepRequest.getStep()
                    .getStepConfiguration();
            if (Objects.isNull(procedure.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Id");
            }
            if (Objects.isNull(procedure.getStepId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepId");
            }
            if (Objects.isNull(procedure.getProcedureName())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "procedureName");
            }
            if (Objects.isNull(procedure.getDatabaseURL())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "databaseUrl");
            }
            if (Objects.isNull(procedure.getDatabaseUsername())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "databaseUsername");
            }
            if (Objects.isNull(procedure.getMaxRecords())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "maxRecords");
            }
            if (Objects.isNull(procedure.getSuccessCode())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "successCode");
            }
            if (Objects.isNull(procedure.getParameters()) || procedure.getParameters().isEmpty()) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameters");
            }
            for (ProcedureParameterModel parameter : procedure.getParameters()) {
                if (!Objects.isNull(parameter.getId()) && Objects.isNull(parameter.getConfigId())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "configId");
                }
                if (Objects.isNull(parameter.getParameterName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterName");
                }
                if (Objects.isNull(parameter.getParameterDataType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterDataType");
                }
                if (Objects.isNull(parameter.getParameterType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterType");
                }
                if (Objects.isNull(parameter.getParameterOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterOrder");
                }
                if (Objects.isNull(parameter.getDisplayOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "displayOrder");
                }
                if (!Objects.isNull(parameter.getCursorParameterMappings()) && !parameter.getCursorParameterMappings().isEmpty()) {
                    for (ProcedureCursorMappingModel procedureCursor : parameter.getCursorParameterMappings()) {
                        if (Objects.isNull(procedureCursor.getDisplayColumnName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "displayColumnName");
                        }
                        if (Objects.isNull(procedureCursor.getActualColumnName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "actualColumnName");
                        }
                    }
                }
            }
        }
        else if(updateStepRequest.getStep().getStepType().equals(2)){
            HttpConfigurationModel httpConfig = (HttpConfigurationModel) updateStepRequest.getStep()
                    .getStepConfiguration();
            if (Objects.isNull(httpConfig.getHttpMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Http Method");
            }
            if (Objects.isNull(httpConfig.getHttpURL())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "HttpURL");
            }
            if (Objects.isNull(httpConfig.getResponseContentType())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "ResponseContentType");
            }

            if (Objects.isNull(httpConfig.getMaxRecords())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "maxRecords");
            }
            if (Objects.isNull(httpConfig.getSuccessCode())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "successCode");
            }
            if (Objects.isNull(httpConfig.getParameters()) || httpConfig.getParameters().isEmpty()) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameters");
            }
            for (HttpParameterModel parameter : httpConfig.getParameters()) {
                if (Objects.isNull(parameter.getParameterName())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterName");
                }
                if (Objects.isNull(parameter.getParameterDataType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterDataType");
                }
                if (Objects.isNull(parameter.getParameterType())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterType");
                }
                if (Objects.isNull(parameter.getParameterOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parameterOrder");
                }
                if (Objects.isNull(parameter.getDisplayOrder())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "displayOrder");
                }
                if (!Objects.isNull(parameter.getHttpResponseMappingModels()) && !parameter.getHttpResponseMappingModels().isEmpty()) {
                    for (HttpResponseMappingModel procedureCursor : parameter.getHttpResponseMappingModels()) {
                        if (Objects.isNull(procedureCursor.getHeaderDisplayName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "headerDisplayName");
                        }
                        if (Objects.isNull(procedureCursor.getHeaderName())) {
                            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD,
                                    "headerName");
                        }
                    }
                }
            }
        }
    }

    public void validateDeleteStep(DeleteStepRequest deleteStepRequest) throws GatewayValidationException {
        if (Objects.isNull(deleteStepRequest.getPageId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
        }
        if (Objects.isNull(deleteStepRequest.getStepId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepId");
        }
    }

    public void validateTestDBConnection(TestDBConnectionRequest testDBConnectionRequest)
            throws GatewayValidationException {
        if (Objects.isNull(testDBConnectionRequest.getDbUrl())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dbUrl");
        }
        if (Objects.isNull(testDBConnectionRequest.getDbUsername())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dbUsername");
        }
        if (Objects.isNull(testDBConnectionRequest.getDbPassword())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dbPassword");
        }
    }

    public void validateParseQuery(ParseProcedureParametersRequest parseProcedureParametersRequest)
            throws GatewayValidationException {
        if (Objects.isNull(parseProcedureParametersRequest.getQuery())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "query");
        }
    }
    public void validateHttpParseQuery(ParseHttpParametersRequest request)
            throws GatewayValidationException {
        if (Objects.isNull(request.getQuery())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "query");
        }
    }
    public void validateResponseParameterParse(ResponseParameterParsingRequest request)
            throws GatewayValidationException {
        if (Objects.isNull(request.getFileContent())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "XML Content");
        }if (Objects.isNull(request.getType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Type");
        }if (!request.getType().equals(ParametersResponseType.JSON.id) &&
                !request.getType().equals(ParametersResponseType.XML.id) &&
                !request.getType().equals(ParametersResponseType.XSD.id) ){
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "Type");
        }
    }
}
