/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.AdmServiceClassModel;
import com.asset.ccat.gateway.models.requests.admin.service_class.AddServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.GetAllServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.GetServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.UpdateServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.ImportServiceClassesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.GetAllServiceClassesResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.GetServiceClassResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.ServiceClassResponse;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.admin.AdmServiceClassesService;
import com.asset.ccat.gateway.validators.admins.AdmServiceClassesValidator;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author wael.mohamed
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = Defines.ContextPaths.ADMIN + Defines.ContextPaths.SERVICE_CLASSES)
public class AdmServiceClassesController {

    @Autowired
    private AdmServiceClassesService admServiceClassesService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdmServiceClassesValidator admServiceClassesValidator;

    @PostMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<GetAllServiceClassesResponse> getAllServiceClasses(HttpServletRequest req,
                                                                           @RequestBody GetAllServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Class Request [" + request + "]");
        List<ServiceClassResponse> response = admServiceClassesService.getAllServiceClasses();
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Class Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                new GetAllServiceClassesResponse(response));
    }

    @PostMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<GetServiceClassResponse> getServiceClassById(HttpServletRequest req,
                                                                     @RequestBody GetServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get Service Class Request [" + request + "]");
        admServiceClassesValidator.validateServiceClass(request);
        AdmServiceClassModel response = admServiceClassesService.getServiceClassById(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get Service Class Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                new GetServiceClassResponse(response));
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse updateServiceClass(HttpServletRequest servletRequest,
                                           @RequestBody UpdateServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Update Service Class Request [" + request + "]");
        admServiceClassesValidator.validateUpdateServiceClass(request);
        admServiceClassesService.updateServiceClass(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Update Service Class Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse addServiceClass(HttpServletRequest servletRequest,
                                        @RequestBody AddServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.debug("Add Service Class request started with body = {}", request);
        admServiceClassesValidator.validateAddServiceClass(request);
        admServiceClassesService.addServiceClass(request);
        CCATLogger.DEBUG_LOGGER.debug("Add Service Class request ended.");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteServiceClass(HttpServletRequest servletRequest,
                                           @RequestBody GetServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Delete Service Class Request [" + request + "]");
        admServiceClassesValidator.validateServiceClass(request);
        admServiceClassesService.deleteServiceClassById(request);
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Delete Service Class Request Successfully!!");

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success",
                Defines.SEVERITY.CLEAR,
                request.getRequestId(),
                null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.EXPORT)
    public ResponseEntity<Resource> exportServiceClasses(HttpServletRequest req,
                                                         @RequestBody GetAllServiceClassRequest request) throws GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Get All Service Class Request [" + request + "]");
        InputStreamResource file = new InputStreamResource(admServiceClassesService.exportServiceClasses());
        String filename = "ServiceClasses.xlsx";
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Get All Service Class Request Successfully!!");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


    @RequestMapping(value = Defines.WEB_ACTIONS.IMPORT, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Resource> importServiceClasses(@ModelAttribute ImportServiceClassesRequest request) throws AuthenticationException, GatewayException {
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("Extracted token data | sessionId=[" + sessionId + "] username=[" + username + "]");
        request.setUsername(username);
        request.setRequestId(UUID.randomUUID().toString());
        request.setSessionId(sessionId);
        ThreadContext.put("sessionId", sessionId);
        ThreadContext.put("requestId", request.getRequestId());
        CCATLogger.DEBUG_LOGGER.info("Received Import Service Class Request [" + request + "]");
        // validate request
        admServiceClassesValidator.validateImportServiceClass(request);
        // call adm service classes import
        byte[] fileAsBytes = admServiceClassesService.importServiceClasses(request);
        ByteArrayResource resource = new ByteArrayResource(fileAsBytes);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=summary.csv");
        CCATLogger.DEBUG_LOGGER.info("Finished Serving Import Service Class Request Successfully!!");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileAsBytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
