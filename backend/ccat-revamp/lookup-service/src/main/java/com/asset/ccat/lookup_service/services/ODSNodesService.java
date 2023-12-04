package com.asset.ccat.lookup_service.services;

import java.util.List;
import java.util.Objects;

import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.database.dao.ODSNodesDao;
import com.asset.ccat.lookup_service.defines.Defines.SEVERITY;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.responses.ods_nodes.GetAllODSNodesResponse;
import com.asset.ccat.lookup_service.models.responses.ods_nodes.GetODSNodeResponse;

/**
 * @author Assem.Hassan
 */
@Service
public class ODSNodesService {

    @Autowired
    private ODSNodesDao odsNodesDao;
    @Autowired
    private CryptoUtils cryptoUtils;
    @Autowired
    private Properties properties;


    public GetAllODSNodesResponse getAllODSNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ODSNodesService - getAllODSNodes");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all ODSNodes.");
        List<ODSNodeModel> odsNodeModelList = odsNodesDao.retrieveODSNodes();
        if (Objects.isNull(odsNodeModelList) || odsNodeModelList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No ODSNodes were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, SEVERITY.ERROR, "getAllODSNodes");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all ODSNodes with size[" + odsNodeModelList.size() + "].");
        return new GetAllODSNodesResponse(odsNodeModelList);
    }

    public GetODSNodeResponse getODSNodeById(Integer odsNodesId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ODSNodesService - getODSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving ODSNodes with ID[" + odsNodesId + "].");
        ODSNodeModel odsNodeModel = odsNodesDao.retrieveODSNodeById(odsNodesId);
        if (Objects.isNull(odsNodeModel)) {
            CCATLogger.DEBUG_LOGGER.error("ODSNodes with id [" + odsNodesId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, SEVERITY.ERROR, "odsNodesId " + odsNodesId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving ODSNodes with ID[" + odsNodesId + "].");
        return new GetODSNodeResponse(odsNodeModel);
    }

    public void addODSNode(ODSNodeModel odsNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ODSNodesService - addODSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding ODSNode with Address[" + odsNodeModel.getAddress() + "].");
        String password = odsNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            odsNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
        }
        boolean isAdded = odsNodesDao.addODSNode(odsNodeModel);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add ODSNode.");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR, "ODSNodes with Address " + odsNodeModel.getAddress());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding ODSNodes with Address[" + odsNodeModel.getAddress() + "].");
    }

    public void deleteODSNode(Integer odsNodesId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ODSNodesService - deleteODSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting ODSNode with ID[" + odsNodesId + "].");
        boolean isDeleted = odsNodesDao.deleteODSNodeById(odsNodesId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete ODSNode.");
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "odsNodesId " + odsNodesId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting ODSNode with ID[" + odsNodesId + "].");
    }

    public void updateODSNode(ODSNodeModel odsNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("ODSNodesService - updateODSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start updating ODSNode with ID[" + odsNodeModel.getId() + "].");
        boolean isUpdated;
        String password = odsNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            odsNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
            isUpdated = odsNodesDao.updateODSNodeWithPassword(odsNodeModel);
        } else {
            isUpdated = odsNodesDao.updateODSNodeWithoutPassword(odsNodeModel);
        }
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add ODSNode.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, SEVERITY.ERROR, "odsNodesId " + odsNodeModel.getId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding ODSNodes with ID[" + odsNodeModel.getId() + "].");
    }
}
