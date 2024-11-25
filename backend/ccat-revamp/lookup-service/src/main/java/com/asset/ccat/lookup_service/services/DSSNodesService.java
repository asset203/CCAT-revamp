package com.asset.ccat.lookup_service.services;

import java.util.List;
import java.util.Objects;

import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.database.dao.DSSNodesDao;
import com.asset.ccat.lookup_service.defines.Defines.SEVERITY;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.responses.dss_nodes.GetAllDSSNodesResponse;
import com.asset.ccat.lookup_service.models.responses.dss_nodes.GetDSSNodeResponse;

/**
 * @author Assem.Hassan
 */
@Service
public class DSSNodesService {

    @Autowired
    private DSSNodesDao dssNodesDao;
    @Autowired
    private CryptoUtils cryptoUtils;
    @Autowired
    private Properties properties;


    public GetAllDSSNodesResponse getAllDSSNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("DSSNodesService - getAllDSSNodes");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all DSSNodes.");
        List<DSSNodeModel> dssNodeModelList = dssNodesDao.retrieveDSSNodes();
        if (dssNodeModelList == null || dssNodeModelList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No DSSNodes were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, SEVERITY.ERROR, "getAllDSSNodes");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all DSSNodes with size[" + dssNodeModelList.size() + "].");

        return new GetAllDSSNodesResponse(dssNodeModelList);
    }

    public GetDSSNodeResponse getDSSNodeById(Integer dssNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("DSSNodesService - getDSSNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving DSSNode with ID[" + dssNodeId + "].");
        DSSNodeModel dssNodeModel = dssNodesDao.retrieveDSSNodeById(dssNodeId);
        if (dssNodeModel == null) {
            CCATLogger.DEBUG_LOGGER.error("DSSNode with id [" + dssNodeId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, SEVERITY.ERROR, "dssNodeId " + dssNodeId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving DSSNode with ID[" + dssNodeId + "].");

        return new GetDSSNodeResponse(dssNodeModel);
    }

    public void addDSSNode(DSSNodeModel dssNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("DSSNodesService - addDSSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding DSSNode with Address[" + dssNodeModel.getAddress() + "].");
        String password = dssNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            dssNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
        }
        boolean isAdded = dssNodesDao.addDSSNode(dssNodeModel);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add DSSNode.");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR, "DSSNode with Address " + dssNodeModel.getAddress());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding DSSNode with Address[" + dssNodeModel.getAddress() + "].");
    }

    public void deleteDSSNode(Integer dssNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("DSSNodesService - deleteDSSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting DSSNod with ID[" + dssNodeId + "].");
        boolean isDeleted = dssNodesDao.deleteDSSNodeById(dssNodeId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete DSSNode.");
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "dssNodeId " + dssNodeId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting DSSNode with ID[" + dssNodeId + "].");
    }

    public void updateDSSNode(DSSNodeModel dssNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("DSSNodesService - updateDSSNode");
        CCATLogger.DEBUG_LOGGER.debug("Start updating DSSNode with ID[" + dssNodeModel.getId() + "].");
        boolean isUpdated;
        String password = dssNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            dssNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
            isUpdated = dssNodesDao.updateDSSNodeWithPassword(dssNodeModel);
        } else {
            isUpdated = dssNodesDao.updateDSSNodeWithoutPassword(dssNodeModel);
        }
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add DSSNode.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, SEVERITY.ERROR, "dssNodeId " + dssNodeModel.getId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding DSSNode with ID[" + dssNodeModel.getId() + "].");
    }
}
