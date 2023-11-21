package com.asset.ccat.gateway.managers;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.FailedMsisdnModel;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchDisconnectRequest;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchInstallRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.DisconnectSubscriberRequest;
import com.asset.ccat.gateway.models.requests.customer_care.advanced.InstallSubscriberRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.proxy.AdvancedProxy;
import com.asset.ccat.gateway.util.GatewayUtil;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class BIDExecutionManager {

    @Autowired
    private MessagesCache messagesCache;

    @Autowired
    private Properties properties;

    @Autowired
    private AdvancedProxy advancedProxy;
    
    @Autowired
    private GatewayUtil gatewayUtil;

    public List<FailedMsisdnModel> executeBatchInstall(List<String> msisdnList, BatchInstallRequest batchInstallRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Start executing msisdn batch list");
        List<FailedMsisdnModel> failedMsisdnList = new ArrayList<>();
        HashMap<String, Mono<BaseResponse>> responsesMap = new HashMap<>();
        InstallSubscriberRequest installRequest;
        String msisdn = "";
        int numOfReqs = 0;

        int batchSize = properties.getSubInstallBatchSize();
        String invalidMsisdnMessage = messagesCache.replaceArgument(messagesCache.getWarningMsg(ErrorCodes.WARNING.INVALID_INPUT), "msisdn");

        for (int i = 0; i <= msisdnList.size(); i++) {
            if (i != msisdnList.size()) { // added extra iteration to send last msisdn - skip this logic for last iteration
                msisdn = msisdnList.get(i);
                CCATLogger.DEBUG_LOGGER.debug("Processing [" + msisdn + "] for install");

                CCATLogger.DEBUG_LOGGER.debug("Validating [" + msisdn + "]");
                if (!gatewayUtil.isMsisdnValid(msisdn)) {
                    CCATLogger.DEBUG_LOGGER.debug("Msisdn validation failed | [" + msisdn + "] is invalid");
                    failedMsisdnList.add(new FailedMsisdnModel(msisdn,
                            ErrorCodes.WARNING.INVALID_INPUT,
                            invalidMsisdnMessage));
                    continue;
                }
            }

            if (numOfReqs == batchSize
                    || i == msisdnList.size()) {
                // process sent requests
                String sentMsisdn;
                Mono<BaseResponse> responseAsync;
                BaseResponse response;
                for (Map.Entry<String, Mono<BaseResponse>> responseEntry : responsesMap.entrySet()) {
                    sentMsisdn = responseEntry.getKey();
                    responseAsync = responseEntry.getValue();
                    CCATLogger.DEBUG_LOGGER.debug("Recieving install subscriber response for [" + sentMsisdn + "]");
                    try {
                        response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
                        if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                            CCATLogger.DEBUG_LOGGER.debug("Failed to install subscriber [" + sentMsisdn + "] with error message [" + response.getStatusMessage() + "]");
                            failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                    response.getStatusCode(),
                                    response.getStatusMessage()));
                        }
                        CCATLogger.DEBUG_LOGGER.debug("Installing Subscriber [" + sentMsisdn + "] Finished Successfully");
                    } catch (RuntimeException ex) {
                        CCATLogger.DEBUG_LOGGER.debug("Failed to install subscriber [" + sentMsisdn + "] | Service Unreachable");
                        failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
                                messagesCache.getErrorMsg(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE)));
                    } catch (Exception ex) {
                        CCATLogger.DEBUG_LOGGER.debug("Failed to install subscriber [" + sentMsisdn + "] | Unknown Error");
                        failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                ErrorCodes.ERROR.UNKNOWN_ERROR,
                                messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR)));
                    }
                }
                responsesMap = new HashMap<>();
                numOfReqs = 0;
            } else {
                // Check for duplication
                if (responsesMap.containsKey(msisdn)) {
                    //File contains duplicate msisdn
                    CCATLogger.DEBUG_LOGGER.debug("Msisdn [" + msisdn + "] is duplicated in file");
                    failedMsisdnList.add(new FailedMsisdnModel(msisdn,
                            ErrorCodes.ERROR.DUPLICATE_MSISDN,
                            messagesCache.getErrorMsg(ErrorCodes.ERROR.DUPLICATE_MSISDN)));
                    continue;
                }
                // Send Install Subscriber Asynchronous Request
                CCATLogger.DEBUG_LOGGER.debug("Sending [" + msisdn + "] for install");
                installRequest = new InstallSubscriberRequest();
                installRequest.setToken(batchInstallRequest.getToken());
                installRequest.setUsername(batchInstallRequest.getUsername());
                installRequest.setSessionId(batchInstallRequest.getSessionId());
                installRequest.setRequestId(batchInstallRequest.getRequestId());
                installRequest.setSubscriberMsisdn(msisdn);
                installRequest.setServiceClassId(batchInstallRequest.getServiceClassId());
                installRequest.setOrganization(properties.getBidOrganizationId());
                installRequest.setLanguageId(1);
                installRequest.setTemporeryBlocked(properties.getBidTempBlock());
                Mono<BaseResponse> monoResp = advancedProxy.installSubscriber(installRequest);

                //Store response mono for later processing
                responsesMap.put(msisdn, monoResp);
                numOfReqs++;
            }
        }

        return failedMsisdnList;
    }

    public List<FailedMsisdnModel> executeBatchDisconnect(List<String> msisdnList, BatchDisconnectRequest batchDisconnectRequest) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Start executing msisdn disconnect batch list");
        List<FailedMsisdnModel> failedMsisdnList = new ArrayList<>();
        HashMap<String, Mono<BaseResponse>> responsesMap = new HashMap<>();
        DisconnectSubscriberRequest disconnectRequest;
        int batchSize = properties.getSubDisconnectBatchSize();
        int numOfReqs = 0;
        String msisdn = "";
        String invalidMsisdnMessage = messagesCache.replaceArgument(messagesCache.getWarningMsg(ErrorCodes.WARNING.INVALID_INPUT), "msisdn");

        for (int i = 0; i <= msisdnList.size(); i++) {
            if (i != msisdnList.size()) { // added extra iteration to send last msisdn - skip this logic for last iteration
                msisdn = msisdnList.get(i);
                CCATLogger.DEBUG_LOGGER.debug("Processing [" + msisdn + "] for disconnect");

                CCATLogger.DEBUG_LOGGER.debug("Validating [" + msisdn + "]");
                if (!gatewayUtil.isMsisdnValid(msisdn)) {
                    CCATLogger.DEBUG_LOGGER.debug("Msisdn validation failed | [" + msisdn + "] is invalid");
                    failedMsisdnList.add(new FailedMsisdnModel(msisdn,
                            ErrorCodes.WARNING.INVALID_INPUT,
                            invalidMsisdnMessage));
                    continue;
                }
            }

            if (numOfReqs == batchSize
                    || i == (msisdnList.size())) {
                // process sent requests
                String sentMsisdn;
                Mono<BaseResponse> responseAsync;
                BaseResponse response;
                for (Map.Entry<String, Mono<BaseResponse>> responseEntry : responsesMap.entrySet()) {
                    sentMsisdn = responseEntry.getKey();
                    responseAsync = responseEntry.getValue();
                    CCATLogger.DEBUG_LOGGER.debug("Recieving disconnect subscriber response for [" + sentMsisdn + "]");
                    try {
                        response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
                        if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
                            CCATLogger.DEBUG_LOGGER.debug("Failed to disconnect subscriber [" + sentMsisdn + "] with error message [" + response.getStatusMessage() + "]");
                            failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                    response.getStatusCode(),
                                    response.getStatusMessage()));
                        }
                        CCATLogger.DEBUG_LOGGER.debug("Disconnecting subscriber [" + sentMsisdn + "] Finished Successfully");
                    } catch (RuntimeException ex) {
                        CCATLogger.DEBUG_LOGGER.debug("Failed to disconnect subscriber [" + sentMsisdn + "] | Service Unreachable");
                        failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE,
                                messagesCache.getErrorMsg(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE)));
                    } catch (Exception ex) {
                        CCATLogger.DEBUG_LOGGER.debug("Failed to disconnect subscriber [" + sentMsisdn + "] | Unknown Error");
                        failedMsisdnList.add(new FailedMsisdnModel(sentMsisdn,
                                ErrorCodes.ERROR.UNKNOWN_ERROR,
                                messagesCache.getErrorMsg(ErrorCodes.ERROR.UNKNOWN_ERROR)));
                    }
                }

                responsesMap = new HashMap<>();
                numOfReqs = 0;
            } else {
                //Check for duplication
                if (responsesMap.containsKey(msisdn)) {
                    //File contains duplicate msisdn
                    CCATLogger.DEBUG_LOGGER.debug("Msisdn [" + msisdn + "] is duplicated in file");
                    failedMsisdnList.add(new FailedMsisdnModel(msisdn,
                            ErrorCodes.ERROR.DUPLICATE_MSISDN,
                            messagesCache.getErrorMsg(ErrorCodes.ERROR.DUPLICATE_MSISDN)));
                    continue;
                }
                //Send Disconnect Subscriber Asynchronous Request
                CCATLogger.DEBUG_LOGGER.debug("Sending [" + msisdn + "] for disconnect");
                disconnectRequest = new DisconnectSubscriberRequest();
                disconnectRequest.setToken(batchDisconnectRequest.getToken());
                disconnectRequest.setUsername(batchDisconnectRequest.getUsername());
                disconnectRequest.setSessionId(batchDisconnectRequest.getSessionId());
                disconnectRequest.setRequestId(batchDisconnectRequest.getRequestId());
                disconnectRequest.setSubscriberMsisdn(msisdn);
                disconnectRequest.setIsBatch(true);
                disconnectRequest.setValidateDisconnection(disconnectRequest.getValidateDisconnection());
                Mono<BaseResponse> monoResp = advancedProxy.disconnectSubscriber(disconnectRequest);

                //Store response mono for later processing
                responsesMap.put(msisdn, monoResp);

                numOfReqs++;
            }
        }

        return failedMsisdnList;
    }

}
