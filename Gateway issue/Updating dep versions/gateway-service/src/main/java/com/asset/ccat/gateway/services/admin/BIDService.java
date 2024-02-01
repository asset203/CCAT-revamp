package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.file.handlers.MsisdnFileHandler;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.managers.BIDExecutionManager;
import com.asset.ccat.gateway.models.admin.FailedMsisdnModel;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchDisconnectRequest;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchInstallRequest;
import com.asset.ccat.gateway.models.responses.admin.batch_install_disconnect.BatchDisconnectResponse;
import com.asset.ccat.gateway.models.responses.admin.batch_install_disconnect.BatchInstallResponse;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author marwa.elshawarby
 */
@Service
public class BIDService {

    @Autowired
    private BIDExecutionManager bidExecutionManager;

    @Autowired
    private MsisdnFileHandler msisdnFileHandler;

    public BatchInstallResponse batchInstall(BatchInstallRequest batchInstallRequest) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Start serving batch install request");
        BatchInstallResponse resp = null;

        CCATLogger.DEBUG_LOGGER.debug("Start handling file [" + batchInstallRequest.getFile().getOriginalFilename() + "] for batch install");
        List<String> msisdns = msisdnFileHandler.handleFileParsing(batchInstallRequest.getFile(), batchInstallRequest.getFileExt());
        if (msisdns == null || msisdns.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("File [" + batchInstallRequest.getFile().getOriginalFilename() + "] is empty.");
            throw new GatewayValidationException(ErrorCodes.WARNING.EMPTY_FILE);
        }

        CCATLogger.DEBUG_LOGGER.debug("Recieved [" + msisdns.size() + "] msisdns");

        CCATLogger.DEBUG_LOGGER.debug("Start Executing Batch Install");
        List<FailedMsisdnModel> failedMsisdn = bidExecutionManager.executeBatchInstall(msisdns, batchInstallRequest);
        CCATLogger.DEBUG_LOGGER.debug("Finished Executing Batch Install");

        resp = new BatchInstallResponse();
        resp.setTotalNumberOfMsisdns(msisdns.size());
        resp.setNumberOfFailedMsisdns(failedMsisdn == null ? 0 : failedMsisdn.size());
        resp.setNumberOfSucessMsisdns(failedMsisdn == null ? msisdns.size() : msisdns.size() - failedMsisdn.size());
        resp.setFailedMsisdns(failedMsisdn);

        CCATLogger.DEBUG_LOGGER.debug("Finished serving batch install request successfully");
        return resp;
    }

    public BatchDisconnectResponse batchDisconnect(BatchDisconnectRequest batchDisconnectRequest) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Start serving batch disconnect request");
        BatchDisconnectResponse resp = null;

        CCATLogger.DEBUG_LOGGER.debug("Start handling file [" + batchDisconnectRequest.getFile().getOriginalFilename() + "] for batch action disconnection");
        List<String> msisdns = msisdnFileHandler.handleFileParsing(batchDisconnectRequest.getFile(), batchDisconnectRequest.getFileExt());
        if (msisdns == null || msisdns.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("File [" + batchDisconnectRequest.getFile().getOriginalFilename() + "] is empty.");
            throw new GatewayException(ErrorCodes.WARNING.EMPTY_FILE, Defines.SEVERITY.VALIDATION);
        }

        CCATLogger.DEBUG_LOGGER.debug("Recieved [" + msisdns.size() + "] msisdns");

        CCATLogger.DEBUG_LOGGER.debug("Start Executing Batch Disconnect");
        List<FailedMsisdnModel> failedMsisdn = bidExecutionManager.executeBatchDisconnect(msisdns, batchDisconnectRequest);
        CCATLogger.DEBUG_LOGGER.debug("Finished Executing Batch Disconnect");

        resp = new BatchDisconnectResponse();
        resp.setTotalNumberOfMsisdns(msisdns.size());
        resp.setNumberOfFailedMsisdns(failedMsisdn == null ? 0 : failedMsisdn.size());
        resp.setNumberOfSucessMsisdns(failedMsisdn == null ? msisdns.size() : msisdns.size() - failedMsisdn.size());
        resp.setFailedMsisdns(failedMsisdn);

        CCATLogger.DEBUG_LOGGER.debug("Finished serving batch disconnect request successfully");
        return resp;
    }
}
