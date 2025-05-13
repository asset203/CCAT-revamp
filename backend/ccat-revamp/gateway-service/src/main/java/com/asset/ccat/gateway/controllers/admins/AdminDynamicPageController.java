package com.asset.ccat.gateway.controllers.admins;


import com.asset.ccat.gateway.annotation.LogFootprint;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.dynamic_page.*;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.dynamic_page.*;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.DynamicPageService;
import com.asset.ccat.gateway.services.DynamicPageStepService;
import com.asset.ccat.gateway.validators.admins.AdminDynamicPageValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ADMIN_DYNAMIC_PAGES)
public class AdminDynamicPageController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DynamicPageStepService dynamicPageStepService;
    @Autowired
    private DynamicPageService dynamicPageService;
    @Autowired
    private AdminDynamicPageValidator adminDynamicPageValidator;


    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public ResponseEntity<BaseResponse<AddDynamicPageResponse>> addAdminDynamicPage(
            @RequestBody AddDynamicPageRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Dynamic Page Request [" + request + "]");
        adminDynamicPageValidator.validateAddDynamicPage(request);
        AddDynamicPageResponse response = dynamicPageService.addDynamicPage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Dynamic Page Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response), HttpStatus.OK);
    }


    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public ResponseEntity<BaseResponse> updateDynamicPage(
            @RequestBody UpdateDynamicPageRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Dynamic Page Request [" + request + "]");
        adminDynamicPageValidator.validateUpdateDynamicPage(request);
        dynamicPageService.updateDynamicPage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Dynamic Page Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetDynamicPageResponse> getDynamicPage(
            @RequestBody GetDynamicPageRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Dynamic Page Request [" + request + "]");
        adminDynamicPageValidator.validateGetDynamicPage(request);
        GetDynamicPageResponse response = dynamicPageService.getDynamicPage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Dynamic Page Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllDynamicPagesResponse> getAllPages(
            @RequestBody GetAllDynamicPagesRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        //TODO validations
        CCATLogger.DEBUG_LOGGER.info("Received Get All Dynamic Page Request [" + request + "]");
        GetAllDynamicPagesResponse response = dynamicPageService.getAllPages(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Dynamic Page Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public ResponseEntity<BaseResponse> deleteDynamicPage(
            @RequestBody DeleteDynamicPageRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Dynamic Page Request [" + request + "]");
        adminDynamicPageValidator.validateDeleteDynamicPage(request);
        dynamicPageService.deletePage(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Dynamic Page Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.STEP + Defines.WEB_ACTIONS.ADD)
    @LogFootprint
    public BaseResponse<AddStepResponse> addStep(
            @RequestBody AddStepRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Add Step Request [" + request + "]");
        adminDynamicPageValidator.validateAddStep(request);
        AddStepResponse response = dynamicPageStepService.addStep(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Add Step Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.STEP + Defines.WEB_ACTIONS.UPDATE)
    @LogFootprint
    public ResponseEntity<BaseResponse<UpdateStepResponse>> updateStep(
            @RequestBody UpdateStepRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Step Request [" + request + "]");
        adminDynamicPageValidator.validateUpdateStep(request);
        UpdateStepResponse response = dynamicPageStepService.updateStep(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Step Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.STEP + Defines.WEB_ACTIONS.DELETE)
    @LogFootprint
    public ResponseEntity<BaseResponse> deleteStep(
            @RequestBody DeleteStepRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Step Request [" + request + "]");
        adminDynamicPageValidator.validateDeleteStep(request);
        dynamicPageStepService.deleteStep(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Step Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.TEST_DB_CONNECTION)
    public ResponseEntity<BaseResponse> testDbConnection(
            @RequestBody TestDBConnectionRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Test DBConnection Request [" + request + "]");
        adminDynamicPageValidator.validateTestDBConnection(request);
        dynamicPageService.testDBConnection(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Test DBConnection Request Successfully!!");

        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                null), HttpStatus.OK);
    }

    @PostMapping(value = Defines.ContextPaths.PARSE_QUERY)
    @LogFootprint
    public BaseResponse<ParseProcedureParametersResponse> parseQuery(
            @RequestBody ParseProcedureParametersRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Parse Procedure Parameters Request [" + request + "]");
        adminDynamicPageValidator.validateParseQuery(request);
        ParseProcedureParametersResponse response = dynamicPageService.parseQuery(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Parse Procedure Parameters Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.HTTP_PARSE_QUERY)
    @LogFootprint
    public BaseResponse<ParseHttpParametersResponse> httpParseQuery(
            @RequestBody ParseHttpParametersRequest request) throws GatewayException {

        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Parse Http Parameters Request [" + request + "]");
        adminDynamicPageValidator.validateHttpParseQuery(request);
        ParseHttpParametersResponse response = dynamicPageService.httpParseQuery(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Parse Http Parameters Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.ContextPaths.PARSE_RESPONSE_PARAMETERS)
    @LogFootprint
    public BaseResponse<ParseResponseParametersResponse> parseResponseHttpParameters(
            @RequestBody ResponseParameterParsingRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Response Parameter Parsing Request [" + request + "]");
        adminDynamicPageValidator.validateResponseParameterParse(request);
        ParseResponseParametersResponse response = dynamicPageService.httpParseResponseParameters(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Response Parameter Parsing Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                request.getRequestId(),
                response);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.IMPORT)
    @LogFootprint
    public ResponseEntity<Resource> importDynamicPageSettings(
            @RequestBody ImportDynamicPageRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Import Dynamic Page Request [" + request + "]");
        adminDynamicPageValidator.validateImportDynamicPage(request);
        ResponseEntity<Resource> response = dynamicPageService.importDynamicPageSettings(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Import Dynamic Page Request Successfully!!");

        return response;
    }

    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @LogFootprint
    public BaseResponse exportDynamicPageSettings(@ModelAttribute ExportDynamicPageSettingRequest request) throws GatewayException {
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Export Dynamic Page Setting Request [" + request + "]");
        adminDynamicPageValidator.validateExportDynamicPage(request);
        dynamicPageService.exportDynamicPageSettings(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Export Dynamic Page Setting Request Successfully!!");

        return new BaseResponse(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                0,
                null);
    }
}
