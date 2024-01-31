/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.AdmServiceClassModel;
import com.asset.ccat.gateway.models.admin.MigrationModel;
import com.asset.ccat.gateway.models.requests.admin.service_class.AddServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.GetServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.UpdateServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.ImportServiceClassesLKRequest;
import com.asset.ccat.gateway.models.requests.service_class.ImportServiceClassesRequest;
import com.asset.ccat.gateway.models.responses.admin.migration.ServiceClassesMigrationResponse;
import com.asset.ccat.gateway.models.responses.admin.service_class.ServiceClassResponse;
import com.asset.ccat.gateway.models.shared.ServiceClassesMigrationSummary;
import com.asset.ccat.gateway.proxy.admin.AdmServiceClassesProxy;
import com.asset.ccat.gateway.services.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author wael.mohamed
 */
@Service
@Qualifier("AdmServiceClassService")
public class AdmServiceClassesService {

    @Autowired
    @Qualifier("AdmServiceClassesProxy")
    AdmServiceClassesProxy admServiceClassesProxy;
    @Autowired
    MigrationService migrationService;

    public List<ServiceClassResponse> getAllServiceClasses() throws GatewayException {
        return admServiceClassesProxy.getAllServiceClasses();
    }

    public AdmServiceClassModel getServiceClassById(GetServiceClassRequest request) throws GatewayException {
        return admServiceClassesProxy.getServiceClassById(request.getCode());
    }

    public void deleteServiceClassById(GetServiceClassRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("delete " + request.getCode());
        admServiceClassesProxy.deleteServiceClassById(request.getCode());
    }

    public void updateServiceClass(UpdateServiceClassRequest request) throws GatewayException {
        admServiceClassesProxy.updateServiceClass(request);
    }

    public void addServiceClass(AddServiceClassRequest request) throws GatewayException {
        boolean containsName, containsCode;
        List<ServiceClassResponse> allServiceClasses = admServiceClassesProxy.getAllServiceClasses();
        for (ServiceClassResponse serviceClass : allServiceClasses) {
            containsName = serviceClass.getName().trim().equals(request.getServiceClass().getName().trim());
            containsCode = serviceClass.getCode().equals(request.getServiceClass().getCode());
            if (containsName) {
                throw new GatewayValidationException(ErrorCodes.WARNING.ALREADY_EXISTED, "Service class name");
            } else if (containsCode) {
                throw new GatewayValidationException(ErrorCodes.WARNING.ALREADY_EXISTED, "Service class code");
            }
        }
        admServiceClassesProxy.addServiceClass(request);
    }

    // EXPORT HERE
    public ByteArrayInputStream exportServiceClasses() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving export service classes request");

        CCATLogger.DEBUG_LOGGER.debug("Fetching service classes from lookup service");
        ServiceClassesMigrationResponse res = admServiceClassesProxy.exportAllServiceClassesTables();
        CCATLogger.DEBUG_LOGGER.debug("Finished fetching service classes from lookup service successfully");

        CCATLogger.DEBUG_LOGGER.debug("Start writing service classes to file");
        ByteArrayInputStream fileInputStream = migrationService.exportServiceClasses(res.getServiceClassesMigrationList());
        CCATLogger.DEBUG_LOGGER.debug("Finished writing service classes to file");

        CCATLogger.DEBUG_LOGGER.info("Finished serving export service classes request successfully");
        return fileInputStream;
    }

    public byte[] importServiceClasses(ImportServiceClassesRequest importRequest) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.info("Start serving import service classes request for file [" + importRequest.getFileName() + "]");
        //parse multipart file and return list of migration model

        CCATLogger.DEBUG_LOGGER.info("Start parsing file");
        List<MigrationModel> migrationModelList = migrationService.importServiceClasses(importRequest.getFile(), importRequest.getFileExt());

        //call lookup service to insert migration models
        CCATLogger.DEBUG_LOGGER.info("Call lookupservice - import on parsed file");
        ImportServiceClassesLKRequest request = new ImportServiceClassesLKRequest();
        request.setMigrationModels(migrationModelList);
        request.setRequestId(importRequest.getRequestId());
        request.setSessionId(importRequest.getSessionId());
        request.setUsername(importRequest.getUsername());
        List<ServiceClassesMigrationSummary> summary = admServiceClassesProxy.importServiceClasses(request);
        byte[] summaryFile = migrationService.writeServiceClassesMigrationSummary(summary);
        CCATLogger.DEBUG_LOGGER.info("Finished serving import service classes request successfully");
        return summaryFile;
    }
}
