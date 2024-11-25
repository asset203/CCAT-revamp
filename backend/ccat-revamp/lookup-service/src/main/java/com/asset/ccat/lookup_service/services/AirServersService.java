package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.database.dao.AirServersDao;
import com.asset.ccat.lookup_service.defines.Defines.SEVERITY;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.responses.air_servers.GetAirServerResponse;
import com.asset.ccat.lookup_service.models.responses.air_servers.GetAllAirServersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Service
public class AirServersService {

    @Autowired
    private AirServersDao airServersDao;

    @Autowired
    private CachedLookups cachedLookups;

    public List<AIRServer> getAllCachedODSNodes() throws LookupException {
        return cachedLookups.getAirServers();
    }

    public GetAllAirServersResponse retrieveAllAirServers() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AirServersService - retrieveAllAirServers");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all AirServers.");
        List<AIRServer> airServersList = airServersDao.retrieveAllAirServers();
        if (Objects.isNull(airServersList) || airServersList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No AirServers were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, SEVERITY.ERROR, "retrieveAllAirServers");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all AirServers with size[" + airServersList.size() + "].");
        return new GetAllAirServersResponse(airServersList);
    }

    public GetAirServerResponse retrieveAirServerById(Integer airServerId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AirServersService - retrieveAirServerById");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving AirServer with ID[" + airServerId + "].");
        AIRServer airServer = airServersDao.retrieveAirServerById(airServerId);
        if (Objects.isNull(airServer)) {
            CCATLogger.DEBUG_LOGGER.error("AirServer with id [" + airServerId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, SEVERITY.ERROR, "airServerId " + airServerId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving AirServer with ID[" + airServerId + "].");
        return new GetAirServerResponse(airServer);
    }

    public void addAirServer(AIRServer airServer) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AirServersService - addAirServer");
        CCATLogger.DEBUG_LOGGER.debug("Start adding ODSNodes with URL[" + airServer.getUrl() + "].");
        boolean isAdded = airServersDao.addAirServer(airServer);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add AirServer.");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR,
                    "AirServer with URL " + airServer.getUrl());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding AirServer with URL[" + airServer.getUrl() + "].");
    }

    public void deleteAirServerById(Integer airServerId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AirServersService - deleteAirServerById");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting AirServer with ID[" + airServerId + "].");
        boolean isDeleted = airServersDao.deleteAirServerById(airServerId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete AirServer.");
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "airServerId " + airServerId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting AirServer with ID[" + airServerId + "].");
    }

    public void updateAirServer(AIRServer airServer) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AirServersService - updateAirServer");
        CCATLogger.DEBUG_LOGGER.debug("Start updating AirServer with ID[" + airServer.getId() + "].");
        boolean isUpdated = airServersDao.updateAirServer(airServer);
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add AirServer.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, SEVERITY.ERROR, "airServerId " + airServer.getId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding AirServer with ID[" + airServer.getId() + "].");
    }
}
