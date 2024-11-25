package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.CIDefines;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.SubscriberRequest;
import com.asset.ccat.ci_service.models.responses.GetPrepaidProfileResponse;
//import com.asset.ccat.ci_service.proxy.CIProxy;

import java.io.StringReader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import com.asset.ccat.ci_service.proxy.ChargingInterfaceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class GetPrepaidProfileService {

    @Autowired
    Properties properties;

    @Autowired
    ChargingInterfaceProxy cIProxy;

    private JAXBContext context;

    public GetPrepaidProfileResponse getPrepaidProfile(SubscriberRequest subscriberRequest) throws CIServiceException {
        try {
            String xmlRequest = properties.getGetPreProfileUrl();
            xmlRequest = xmlRequest.replace(CIDefines.GET_PREPAID_PROFILE_PH.MSISDN, subscriberRequest.getMsisdn());
            CCATLogger.DEBUG_LOGGER.info("CI getPreProfile request is " + xmlRequest);
            String result = cIProxy.chargingRequest(xmlRequest,"getPrepaidProfile()");
            CCATLogger.DEBUG_LOGGER.info("CI getPreProfile response is " + result);
            GetPrepaidProfileResponse model = parseGetPrepaidProfileResponse(result);
            return model;
        } catch (CIServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error("Error while parsing response ", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public GetPrepaidProfileResponse parseGetPrepaidProfileResponse(String xml) throws CIServiceException {
        GetPrepaidProfileResponse response;
        CCATLogger.DEBUG_LOGGER.debug("parseGetPrepaidProfileResponse | Starting");
        long t1 = System.currentTimeMillis();
        try {
            if (context == null) {
                context = JAXBContext.newInstance(GetPrepaidProfileResponse.class);
            }
            Unmarshaller unmarshaller = context.createUnmarshaller();
            response = (GetPrepaidProfileResponse) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error parsing CI response : " + ex);
            CCATLogger.ERROR_LOGGER.error("Error parsing CI response : " + ex, ex);
            throw new CIServiceException(ErrorCodes.ERROR.INVALID_AIR_RESPONSE);
        }
        long t2 = System.currentTimeMillis() - t1;
        CCATLogger.DEBUG_LOGGER.debug("Finished in " + t2 + " msecs");
        return response;
    }
}
