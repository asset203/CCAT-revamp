package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.ServiceClassConversionRequest;
import com.asset.ccat.ci_service.parser.CIParser;
import com.asset.ccat.ci_service.proxy.ServiceClassProxy;
import com.asset.ccat.ci_service.utils.ReplacePlaceholderBuilder;
import org.springframework.stereotype.Service;

/**
 * @author wael.mohamed
 */
@Service
public class ServiceClassService {

    private final Properties properties;
    private final ServiceClassProxy serviceClassProxy;
    private final  CIParser cIParser;

    public ServiceClassService(Properties properties, ServiceClassProxy serviceClassProxy, CIParser cIParser) {
        this.properties = properties;
        this.serviceClassProxy = serviceClassProxy;
        this.cIParser = cIParser;
    }

    public void serviceClassConversion(ServiceClassConversionRequest request) throws CIServiceException {
        validateCIRequestParameters(request);
        String urlRequest = new ReplacePlaceholderBuilder()
                .addPlaceholder(CIDefines.SERVICE_CLASSES.CI_MSISDN, request.getMsisdn())
                .addPlaceholder(CIDefines.SERVICE_CLASSES.CI_SERVICE_ID, request.getId().toString())
                .addPlaceholder(CIDefines.SERVICE_CLASSES.CI_SERVICE_NAME, request.getCiServiceName())
                .addPlaceholder(CIDefines.SERVICE_CLASSES.CI_PACKAGE_NAME, request.getCiPackageName())
                .buildUrl(properties.getScConversionUrl());
        CCATLogger.DEBUG_LOGGER.debug("CI request URL = {}", urlRequest);
        String response = serviceClassProxy.serviceClassConversion(urlRequest);
        cIParser.parseCIResponse(response);
    }

    private void validateCIRequestParameters(ServiceClassConversionRequest request) throws CIServiceException {
        if(request.getMsisdn() == null || request.getCiPackageName() == null || request.getId() == null)
            throw new CIServiceException(ErrorCodes.ERROR.MISSING_INPUT_PARAMETERS);
    }

}
