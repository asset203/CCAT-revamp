package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.database.dao.ServiceOfferingBitsDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.responses.service_offering_description.GetAllServiceOfferingDescriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceOfferingBitsService {


    @Autowired
    private ServiceOfferingBitsDao serviceOfferingBitsDao;

    public GetAllServiceOfferingDescriptionResponse getAllServiceOfferingBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all serviceOfferingBits.");
        List<ServiceOfferingBitModel> serviceOfferingBits = serviceOfferingBitsDao.retrieveServiceOfferingBits();
        if (serviceOfferingBits == null || serviceOfferingBits.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No serviceOfferingBits were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "serviceOfferingBits");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all serviceOfferingBits with size[" + serviceOfferingBits.size() + "].");
        return new GetAllServiceOfferingDescriptionResponse(serviceOfferingBits);
    }


    public void updateServiceOfferingBit(ServiceOfferingBitModel serviceOffering) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating serviceOfferingPlan with BIT_POSITION[" + serviceOffering.getBitPosition() + "].");
        int isUpdated = serviceOfferingBitsDao.updateServiceOfferingBit(serviceOffering);
        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update service offering bit.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "bitPosition " + serviceOffering.getBitPosition());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating serviceOfferingBit with BIT_POSITION[" + serviceOffering.getBitPosition() + "].");
    }


}
