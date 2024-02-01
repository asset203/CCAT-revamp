package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.dynamic_page.DynamicPageModel;
import com.asset.ccat.gateway.models.admin.dynamic_page.HttpParameterModel;
import com.asset.ccat.gateway.models.admin.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.*;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ExecuteDynamicPageStepRequest;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ViewDynamicPageRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.*;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.AddDynamicPageResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.GetAllDynamicPagesResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.GetDynamicPageResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.ParseProcedureParametersResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ExecuteDynamicPageStepResponse;
import com.asset.ccat.gateway.models.responses.customer_care.ViewDynamicPageResponse;
import com.asset.ccat.gateway.models.shared.DynamicPageStepOutputModel;
import com.asset.ccat.gateway.proxy.DynamicPageProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.core.io.Resource;

@Component
public class DynamicPageService {

    @Autowired
    private DynamicPageProxy dynamicPageProxy;

    public ViewDynamicPageResponse viewDynamicPage(ViewDynamicPageRequest viewDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - viewDynamicPage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving view dynamic page request for page with privilege ID [" + viewDynamicPageRequest.getPrivilegeId() + "]");
        ViewDynamicPageResponse response = dynamicPageProxy.viewDynamicPage(viewDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving view dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - viewDynamicPage()");
        return response;
    }

    public AddDynamicPageResponse addDynamicPage(AddDynamicPageRequest addDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - addDynamicPage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving add dynamic page request for page with name [" + addDynamicPageRequest.getPageName() + "]");
        AddDynamicPageResponse response = dynamicPageProxy.addPage(addDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving add dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - addDynamicPage()");
        return response;
    }

    public void updateDynamicPage(UpdateDynamicPageRequest updateDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - updateDynamicPage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving update dynamic page request for page with ID [" + updateDynamicPageRequest.getPageId() + "]");
        dynamicPageProxy.updatePage(updateDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving update dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - updateDynamicPage()");
    }

    public GetDynamicPageResponse getDynamicPage(GetDynamicPageRequest getDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - getDynamicPage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving get dynamic page request for page with ID [" + getDynamicPageRequest.getPageId() + "]");
        DynamicPageModel page = dynamicPageProxy.getPage(getDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving get dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - getDynamicPage()");
        return new GetDynamicPageResponse(page);
    }

    public GetAllDynamicPagesResponse getAllPages(GetAllDynamicPagesRequest getAllDynamicPagesRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - getAllPages()");
        CCATLogger.DEBUG_LOGGER.info("Start serving get all dynamic pages request");
        List<DynamicPageModel> pages = dynamicPageProxy.getAllPages(getAllDynamicPagesRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving get all dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - getAllPages()");
        return new GetAllDynamicPagesResponse(pages);
    }

    public void deletePage(DeleteDynamicPageRequest deleteDynamicPageRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - deletePage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving delete dynamic page request for page with ID [" + deleteDynamicPageRequest.getPageId() + "]");
        dynamicPageProxy.deletePage(deleteDynamicPageRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving delete dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - deletePage()");
    }

    public void testDBConnection(TestDBConnectionRequest testDBConnectionRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - testDBConnection()");
        CCATLogger.DEBUG_LOGGER.info("Start serving test DB connection request");
        dynamicPageProxy.testDBConnection(testDBConnectionRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving test DB connection request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - testDBConnection()");
    }

    public ParseProcedureParametersResponse parseQuery(ParseProcedureParametersRequest parseProcedureParametersRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - parseQuery()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse procedure parameters query request");
        List<ProcedureParameterModel> params = dynamicPageProxy.parseQuery(parseProcedureParametersRequest);
        if(params.isEmpty()||params.size()==0){
            throw new GatewayValidationException(ErrorCodes.WARNING.NO_PARAMETERS_PARSED);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse procedure parameters query request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - parseQuery()");
        return new ParseProcedureParametersResponse(params);
    }

    public ParseHttpParametersResponse httpParseQuery(ParseHttpParametersRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - parseQuery()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse HTTP parameters query request");
        List<HttpParameterModel> params = dynamicPageProxy.httpParseQuery(request);
        if(params.isEmpty()||params.size()==0){
            throw new GatewayValidationException(ErrorCodes.WARNING.NO_PARAMETERS_PARSED);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse HTTP parameters query request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - parseQuery()");
        return new ParseHttpParametersResponse(params);
    }

    public ExecuteDynamicPageStepResponse executeDynamicPageStep(ExecuteDynamicPageStepRequest executeDynamicPageStepRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - executeDynamicPageStep()");
        CCATLogger.DEBUG_LOGGER.info("Start serving execute dynamic page step request");
        List<DynamicPageStepOutputModel> ouputList = dynamicPageProxy.executeStep(executeDynamicPageStepRequest);
        CCATLogger.DEBUG_LOGGER.info("Finished serving execute dynamic page step request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - executeDynamicPageStep()");
        return new ExecuteDynamicPageStepResponse(ouputList);
    }

    public ParseResponseParametersResponse httpParseResponseParameters(ResponseParameterParsingRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - httpParseResponseParameters()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse Response parameters query request");
        ParseResponseParametersResponse params = dynamicPageProxy.httpParseResponseParameters(request);
        if(params.getParameters().isEmpty()||params.getParameters().size()==0){
            throw new GatewayValidationException(ErrorCodes.WARNING.NO_PARAMETERS_PARSED);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse Response parameters query request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - httpParseResponseParameters()");
        return params;
    }

    public ResponseEntity<Resource> importDynamicPageSettings(ImportDynamicPageRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - importDynamicPageSettings()");
        CCATLogger.DEBUG_LOGGER.info("Start serving Import Dynamic Page Settings request");
        ResponseEntity<Resource> response = dynamicPageProxy.importDynamicPageSettings(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving Import Dynamic Page Settings request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - importDynamicPageSettings()");
        return response;
    }
    public BaseResponse exportDynamicPageSettings(ExportDynamicPageSettingRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPageService - importDynamicPageSettings()");
        CCATLogger.DEBUG_LOGGER.info("Start serving Import Dynamic Page Settings request");
        BaseResponse response = dynamicPageProxy.exportDynamicPageSettings(request);
        CCATLogger.DEBUG_LOGGER.info("Finished serving Import Dynamic Page Settings request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPageService - importDynamicPageSettings()");
        return response;
    }

}
