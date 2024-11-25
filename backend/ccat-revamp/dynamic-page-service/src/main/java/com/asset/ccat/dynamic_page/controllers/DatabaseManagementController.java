package com.asset.ccat.dynamic_page.controllers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.dynamic_page.models.requests.ParseProcedureParametersRequest;
import com.asset.ccat.dynamic_page.models.requests.TestDBConnectionRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ExportDynamicPageSettingRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ImportDynamicPageSettingsRequest;
import com.asset.ccat.dynamic_page.models.responses.BaseResponse;
import com.asset.ccat.dynamic_page.services.DatabaseManagementService;
import com.google.gson.Gson;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Defines.ContextPaths.DATABASE_MANAGEMENT)
public class DatabaseManagementController {

    @Autowired
    private DatabaseManagementService databaseManagementService;

    @PostMapping(value = Defines.ContextPaths.TEST_DB_CONNECTION)
    public ResponseEntity<BaseResponse> testDBConnection(
            @RequestBody TestDBConnectionRequest testDBConnectionRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementController - testDBConnection()");
        ThreadContext.put("sessionId", testDBConnectionRequest.getSessionId());
        ThreadContext.put("requestId", testDBConnectionRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received testDBConnection Request");
        databaseManagementService.testDBConnection(testDBConnectionRequest);
        CCATLogger.DEBUG_LOGGER.info("testDBConnection request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DatabaseManagementController - testDBConnection()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.PARSE_QUERY)
    public ResponseEntity<BaseResponse<List<ProcedureParameterModel>>> parseQuery(
            @RequestBody ParseProcedureParametersRequest parseProcedureParametersRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DatabaseManagementController - parseQuery()");
        ThreadContext.put("sessionId", parseProcedureParametersRequest.getSessionId());
        ThreadContext.put("requestId", parseProcedureParametersRequest.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received parseQuery Request");
        List<ProcedureParameterModel> params = databaseManagementService.parseProcedureParametersQuery(parseProcedureParametersRequest);
        CCATLogger.DEBUG_LOGGER.info("parseQuery request finished successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DatabaseManagementController - parseQuery()");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                params), HttpStatus.OK);
    }

    @PostMapping(Defines.WEB_ACTIONS.IMPORT)
    public ResponseEntity<Resource> importDynamicPageSettings(@RequestBody ImportDynamicPageSettingsRequest dynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.info("Start importing dynamic page settings");
        CCATLogger.DEBUG_LOGGER.debug("Start importDynamicPageSettings");
        ThreadContext.put("sessionId", dynamicPageRequest.getSessionId());
        ThreadContext.put("requestId", dynamicPageRequest.getRequestId());
        DynamicPageModel dynamicPageModel = databaseManagementService.importDynamicPageSettings(dynamicPageRequest.getPageId());
        Gson gson = new Gson();
        String dynamicPageJson = gson.toJson(dynamicPageModel);
        byte[] dynamicPageJsonContent = dynamicPageJson.getBytes();
        ByteArrayResource resource = new ByteArrayResource(dynamicPageJsonContent);
        String fileName = dynamicPageModel.getPageName().replaceAll("\\s+","_");
        fileName+="_Settings.json";
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        CCATLogger.DEBUG_LOGGER.info("End importing dynamic page settings Successfully ");
        CCATLogger.DEBUG_LOGGER.debug("End importDynamicPageSettings Successfully ");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(dynamicPageJsonContent.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse exportDynamicPageSettings(@ModelAttribute ExportDynamicPageSettingRequest request) throws IOException, DynamicPageException {
        CCATLogger.DEBUG_LOGGER.info("Start exporting dynamic page settings");
        CCATLogger.DEBUG_LOGGER.debug("Start exportDynamicPageSettings");
        ThreadContext.put("sessionId", request.getSessionId());
        ThreadContext.put("requestId", request.getRequestId());
        databaseManagementService.exportDynamicPageSettings(request);
        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS, "success", 0,
                null);
    }

}
