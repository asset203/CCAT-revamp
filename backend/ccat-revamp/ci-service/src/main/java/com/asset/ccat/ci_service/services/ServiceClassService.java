package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.ServiceClassConversionRequest;
import com.asset.ccat.ci_service.parser.CIParser;
import com.asset.ccat.ci_service.proxy.ServiceClassProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wael.mohamed
 */
@Service
public class ServiceClassService {

    @Autowired
    Properties properties;
    @Autowired
    ServiceClassProxy serviceClassProxy;
    @Autowired
    private CIParser cIParser;

    public void serviceClassConversion(ServiceClassConversionRequest request) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug(" Started successfully.");
        String urlRequest = properties.getScConversionUrl();
        urlRequest = urlRequest.replace(CIDefines.SERVICE_CLASSES.CI_MSISDN, request.getMsisdn());
        urlRequest = urlRequest.replace(CIDefines.SERVICE_CLASSES.CI_SERVICE_ID, request.getId().toString());
        urlRequest = urlRequest.replace(CIDefines.SERVICE_CLASSES.CI_PACKAGE_NAME, request.getCiPackageName());
        CCATLogger.DEBUG_LOGGER.debug("CI ServiceClassConverdion urlRequest is " + urlRequest);
        String response = serviceClassProxy.serviceClassConversion(urlRequest);
        CCATLogger.DEBUG_LOGGER.debug("CI ServiceClassConverdion response is " + response);
        String responseCode = cIParser.getMigrationCode(response);
        CCATLogger.DEBUG_LOGGER.debug(" Ended successfully with responseCode[" + responseCode + "].");
    }

}
