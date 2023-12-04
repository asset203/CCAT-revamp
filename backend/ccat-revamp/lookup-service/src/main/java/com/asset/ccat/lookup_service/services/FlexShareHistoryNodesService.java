package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.database.dao.FlexShareHistoryNodesDao;
import com.asset.ccat.lookup_service.defines.Defines.SEVERITY;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import com.asset.ccat.lookup_service.models.responses.flex_share_history.GetAllFlexShareHistoryNodesResponse;
import com.asset.ccat.lookup_service.models.responses.flex_share_history.GetFlexShareHistoryNodeResponse;
import com.asset.ccat.lookup_service.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Service
public class FlexShareHistoryNodesService {

    @Autowired
    private FlexShareHistoryNodesDao flexShareHistoryNodesDao;
    @Autowired
    private CryptoUtils cryptoUtils;
    @Autowired
    private Properties properties;


    public GetAllFlexShareHistoryNodesResponse getAllFlexShareHistoryNodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareHistoryNodesService - getAllFlexShareHistoryNodes");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all FlexShareHistoryNodes.");
        List<FlexShareHistoryNodeModel> flexShareHistoryNodeModelNodesList = flexShareHistoryNodesDao.retrieveFlexShareHistoryNodes();
        if (Objects.isNull(flexShareHistoryNodeModelNodesList) || flexShareHistoryNodeModelNodesList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No FlexShareHistoryNodes were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, SEVERITY.ERROR, "getAllFlexShareHistoryNodes");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all FlexShareHistoryNodes with size[" + flexShareHistoryNodeModelNodesList.size() + "].");

        return new GetAllFlexShareHistoryNodesResponse(flexShareHistoryNodeModelNodesList);
    }

    public GetFlexShareHistoryNodeResponse getFlexShareHistoryNodeById(Integer flexShareHistoryNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareHistoryNodesService - getFlexShareHistoryNodeById");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving FlexShareHistoryNode with ID[" + flexShareHistoryNodeId + "].");
        FlexShareHistoryNodeModel flexShareHistoryNode = flexShareHistoryNodesDao.retrieveFlexShareHistoryNodeById(flexShareHistoryNodeId);
        if (Objects.isNull(flexShareHistoryNode)) {
            CCATLogger.DEBUG_LOGGER.error("FlexShareHistoryNode with id [" + flexShareHistoryNodeId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, SEVERITY.ERROR, "flexShareHistoryNodeId " + flexShareHistoryNodeId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving FlexShareHistoryNode with ID[" + flexShareHistoryNodeId + "].");

        return new GetFlexShareHistoryNodeResponse(flexShareHistoryNode);
    }

    public void addFlexShareHistoryNode(FlexShareHistoryNodeModel flexShareHistoryNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareHistoryNodesService - addFlexShareHistoryNode");
        CCATLogger.DEBUG_LOGGER.debug("Start adding FlexShareHistoryNode with Address[" + flexShareHistoryNodeModel.getAddress() + "].");
        String password = flexShareHistoryNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            flexShareHistoryNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
        }
        boolean isAdded = flexShareHistoryNodesDao.addFlexShareHistoryNode(flexShareHistoryNodeModel);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add FlexShareHistoryNode.");
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR, "FlexShareHistoryNode with Address " + flexShareHistoryNodeModel.getAddress());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding FlexShareHistoryNode with Address[" + flexShareHistoryNodeModel.getAddress() + "].");
    }

    public void deleteFlexShareHistoryNode(Integer flexShareHistoryNodeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareHistoryNodesService - deleteFlexShareHistoryNode");
        CCATLogger.DEBUG_LOGGER.debug("Start deleting FlexShareHistoryNod with ID[" + flexShareHistoryNodeId + "].");
        boolean isDeleted = flexShareHistoryNodesDao.deleteFlexShareHistoryNodeById(flexShareHistoryNodeId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete FlexShareHistoryNode.");
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "flexShareHistoryNodeId " + flexShareHistoryNodeId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting FlexShareHistoryNode with ID[" + flexShareHistoryNodeId + "].");
    }

    public void updateFlexShareHistoryNode(FlexShareHistoryNodeModel flexShareHistoryNodeModel) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareHistoryNodesService - updateFlexShareHistoryNode");
        CCATLogger.DEBUG_LOGGER.debug("Start updating FlexShareHistoryNode with ID[" + flexShareHistoryNodeModel.getId() + "].");
        boolean isUpdated;
        String password = flexShareHistoryNodeModel.getPassword();
        if (Objects.nonNull(password) && !password.trim().isEmpty()) {
            flexShareHistoryNodeModel.setPassword(cryptoUtils.encrypt(password, properties.getEncryptionKey()));
            isUpdated = flexShareHistoryNodesDao.updateFlexShareHistoryNodeWithPassword(flexShareHistoryNodeModel);
        } else {
            isUpdated = flexShareHistoryNodesDao.updateFlexShareHistoryNodeWithoutPassword(flexShareHistoryNodeModel);
        }
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add FlexShareHistoryNode.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, SEVERITY.ERROR, "flexShareHistoryNodeId " + flexShareHistoryNodeModel.getId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding FlexShareHistoryNode with ID[" + flexShareHistoryNodeModel.getId() + "].");
    }
}
