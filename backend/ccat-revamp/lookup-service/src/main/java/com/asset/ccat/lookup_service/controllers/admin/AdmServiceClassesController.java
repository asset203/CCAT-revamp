/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers.admin;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AdmServiceClassModel;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.requests.service_class.AddServiceClassRequest;
import com.asset.ccat.lookup_service.models.requests.service_class.ImportServiceClassesRequest;
import com.asset.ccat.lookup_service.models.requests.service_class.UpdateServiceClassRequest;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import com.asset.ccat.lookup_service.models.responses.ImportServiceClassesResponse;
import com.asset.ccat.lookup_service.models.responses.migration.ServiceClassesMigrationResponse;
import com.asset.ccat.lookup_service.services.AdmServiceClassesService;
import com.asset.ccat.lookup_service.util.Utils;

import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wael.mohamed
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.SERVICE_CLASSES)
public class AdmServiceClassesController {

    @Autowired
    private AdmServiceClassesService admServiceClassesService;
    @Autowired
    private Utils utils;

    @GetMapping(value = Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<List<AdmServiceClassResponse>> getAllServiceClasses() throws LookupException {
        List<AdmServiceClassResponse> response = admServiceClassesService.getAllServiceClasses();
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }

    @GetMapping(value = Defines.WEB_ACTIONS.GET)
    public BaseResponse<AdmServiceClassModel> getServiceClassById(
            @RequestParam("serviceClassID") Integer serviceClassID) throws LookupException {
        utils.isFieldInteger(serviceClassID);
        AdmServiceClassModel serviceClass = admServiceClassesService.getServiceClassById(serviceClassID);

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, serviceClass);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.UPDATE)
    public BaseResponse<AdmServiceClassModel> updateServiceClass(@RequestBody UpdateServiceClassRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        utils.isFieldInteger(request.getServiceClass().getCode());
        admServiceClassesService.updateServiceClass(request.getServiceClass());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @PostMapping(value = Defines.WEB_ACTIONS.ADD)
    public BaseResponse<AdmServiceClassModel> addServiceClass(@RequestBody AddServiceClassRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        admServiceClassesService.addServiceClassWithDetails(request.getServiceClass());
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @GetMapping(value = Defines.WEB_ACTIONS.DELETE)
    public BaseResponse deleteServiceClassById(@RequestParam("serviceClassID") Integer serviceClassID) throws LookupException {
        utils.isFieldInteger(serviceClassID);
        admServiceClassesService.deleteServiceClass(serviceClassID);
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, null);
    }

    @GetMapping(value = Defines.WEB_ACTIONS.EXPORT)
    public BaseResponse<ServiceClassesMigrationResponse> exportServiceClassesTables() throws LookupException {
        ServiceClassesMigrationResponse response = admServiceClassesService.getAllServiceClassesTables();

        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                response);
    }
    @PostMapping(value = Defines.WEB_ACTIONS.IMPORT)
    public BaseResponse<ImportServiceClassesResponse> importServiceClasses(@RequestBody ImportServiceClassesRequest request) throws LookupException {
        ThreadContext.put("requestId", request.getRequestId());
        ThreadContext.put("sessionId", request.getSessionId());
        CCATLogger.DEBUG_LOGGER.info("Received import service classes request");
       ImportServiceClassesResponse response = admServiceClassesService.importServiceClasses(request.getMigrationModels());
        CCATLogger.DEBUG_LOGGER.info("Import service classes request finished successfully.");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", Defines.SEVERITY.CLEAR, response);
    }

}
