/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.transaction.AddTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.AddTransactionTypeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.DeleteTransactionRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionLinkRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.GetAllTransactionTypeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionCodeRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionLinkRequest;
import com.asset.ccat.gateway.models.requests.admin.transaction.UpdateTransactionTypeRequest;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionCodeResponse;
import com.asset.ccat.gateway.models.responses.admin.transaction.GetAllTransactionTypeResponse;
import com.asset.ccat.gateway.proxy.admin.AdmTransactionProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class AdmTransactionService {

    @Autowired
    private AdmTransactionProxy transactionProxy;

    public GetAllTransactionTypeResponse getAllTransactionTypes(GetAllTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionTypes");
        GetAllTransactionTypeResponse types = transactionProxy.getAllTransactionTypes(request);
        return types;
    }

    public GetAllTransactionCodeResponse getAllTransactionCodes(GetAllTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionCodes");
        GetAllTransactionCodeResponse codes = transactionProxy.getAllTransactionCodes(request);
        return codes;
    }

    public GetAllTransactionCodeResponse getAllTransactionLinks(GetAllTransactionLinkRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionLinks");
        GetAllTransactionCodeResponse links = transactionProxy.getAllTransactionLinks(request);
        return links;
    }

    public void updateTransactionType(UpdateTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - updateTransactionType");
        transactionProxy.updateTransactionType(request);
    }

    public void updateTransactionCode(UpdateTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - updateTransactionCode");
        transactionProxy.updateTransactionCode(request);
    }

    public void updateTransactionLink(UpdateTransactionLinkRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - updateTransactionLink");
        transactionProxy.updateTransactionLink(request);
    }

    public void addTransactionCode(AddTransactionCodeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - addTransactionCode");
        transactionProxy.addTransactionCode(request);
    }

    public void addTransactionType(AddTransactionTypeRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - addTransactionType");
        transactionProxy.addTransactionType(request);
    }

    public void deleteTransactionCode(DeleteTransactionRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - deleteTransactionCode");
        transactionProxy.deleteTransactionCode(request);
    }

    public void deleteTransactionType(DeleteTransactionRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - deleteTransactionType");
        transactionProxy.deleteTransactionType(request);
    }

}
