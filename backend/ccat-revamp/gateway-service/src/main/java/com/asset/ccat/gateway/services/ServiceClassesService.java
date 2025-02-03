/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.mappers.IMapper;
import com.asset.ccat.gateway.models.customer_care.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassConversionRequest;
import com.asset.ccat.gateway.models.requests.service_class.ServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.UpdateServiceClassRequest;
import com.asset.ccat.gateway.proxy.admin.ServiceClassesProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wael.mohamed
 */
@Service
public class ServiceClassesService {
    @Autowired
    ServiceClassesProxy serviceClassesProxy;

    @Autowired
    @Qualifier("UpdateServiceClassMapper")
    IMapper iUpdateServiceRequestMapper;
    @Autowired
    @Qualifier("ServiceClassConversionMapper")
    IMapper iServiceClassConversionMapper;

    public List<ServiceClassModel> getAllServiceClasses() throws GatewayException {
        return serviceClassesProxy.getAllServiceClasses();
    }

    public void updateServiceClasses(ServiceClassRequest request) throws GatewayException {
        Integer currentCode = request.getCurrentServiceClass().getCode();
        Integer newCode = request.getNewServiceClass().getCode();
        if (currentCode.equals(newCode)) {
            CCATLogger.DEBUG_LOGGER.debug("Source Class code is same destination.");
            throw new GatewayException(ErrorCodes.ERROR.SOURCE_SAME_DESTINATION);
        }

        if (Boolean.FALSE.equals(request.getCurrentServiceClass().getIsAllowedMigration())) {
            CCATLogger.DEBUG_LOGGER.debug("Current Service Class Does not allow migration.");
            throw new GatewayException(ErrorCodes.ERROR.SC_NOT_MIGRATABLE);
        }

        Map<Integer, List<Integer>> serviceClassMigrations = serviceClassesProxy.getAllServiceClassMigrations();
        List<Integer> migrations = serviceClassMigrations.getOrDefault(currentCode, new ArrayList<>());

        if (migrations.isEmpty() || !migrations.contains(newCode)) {
            CCATLogger.DEBUG_LOGGER.debug("Current Service Class cannot migrate to new Service Class.");
            throw new GatewayException(ErrorCodes.ERROR.MIGRATION_NOT_ELIGIBLE);
        }

        CCATLogger.DEBUG_LOGGER.debug("The NewServiceClass CI Conversion Flag = {}",  request.getNewServiceClass().getIsCiConversion());
        if (Boolean.TRUE.equals(request.getNewServiceClass().getIsCiConversion())) {
            CCATLogger.DEBUG_LOGGER.debug("Start calling the CI-Service to update SC");
            ServiceClassConversionRequest serviceClassConversionRequest = (ServiceClassConversionRequest) iServiceClassConversionMapper.mapTo(request);
            serviceClassesProxy.serviceClassConversion(serviceClassConversionRequest);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Start calling AIR-Service to update SC");
            UpdateServiceClassRequest updateServiceClassRequest = (UpdateServiceClassRequest) iUpdateServiceRequestMapper.mapTo(request);
            serviceClassesProxy.updateServiceClass(updateServiceClassRequest);
        }
    }
}
