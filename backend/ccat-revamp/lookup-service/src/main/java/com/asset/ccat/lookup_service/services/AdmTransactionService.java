/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asset.ccat.lookup_service.constants.LinkType;
import com.asset.ccat.lookup_service.database.dao.SequenceTableDao;
import com.asset.ccat.lookup_service.database.dao.TransactionsDao;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.TransactionCode;
import com.asset.ccat.lookup_service.models.TransactionLink;
import com.asset.ccat.lookup_service.models.TransactionType;
import com.asset.ccat.lookup_service.models.requests.pam_admin.DeletePamRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.AddTransactionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.AddTransactionTypeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionLinkRequest;
import com.asset.ccat.lookup_service.models.requests.transaction.UpdateTransactionTypeRequest;
import com.asset.ccat.lookup_service.models.responses.transaction.GetAllTransactionCodeResponse;
import com.asset.ccat.lookup_service.models.responses.transaction.GetAllTransactionTypeResponse;

/**
 *
 * @author wael.mohamed
 */
@Service
public class AdmTransactionService {

    @Autowired
    private TransactionsDao transactionsDao;
    @Autowired
    private SequenceTableDao sequenceTableDao;

    public GetAllTransactionTypeResponse getAllTransactionTypes() throws LookupException {
        List<TransactionType> types = transactionsDao.retrieveAllTransactionTypesWithFeatures();
        return new GetAllTransactionTypeResponse(types);
    }

    public GetAllTransactionCodeResponse getAllTransactionCodes() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionCodes");
        List<TransactionCode> codes = transactionsDao.retrieveAllTransactionCodes();
        return new GetAllTransactionCodeResponse(codes);
    }

    public GetAllTransactionCodeResponse getAllTransactionCodes(Integer typeId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionCodes");
        List<TransactionCode> codes = transactionsDao.retrieveAllLinkedCodeByTypeId(typeId);
        return new GetAllTransactionCodeResponse(codes);
    }

    public List<TransactionLink> getAllTransactionLinks() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - getAllTransactionLinks");
        List<TransactionLink> typeLinkedCodes = transactionsDao.retrieveAllLinkes();
        return typeLinkedCodes;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void updateTransactionType(UpdateTransactionTypeRequest updatePamRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - updateTransactionType");
        transactionsDao.updateTransactionType(updatePamRequest.getId(),
                updatePamRequest.getName(), updatePamRequest.getValue());
        transactionsDao.deleteFeaturesByTypeId(updatePamRequest.getId());
        if (updatePamRequest.getCcFeatures() != null && !updatePamRequest.getCcFeatures().isEmpty()) {
            transactionsDao.addFeaturesByTypeId(updatePamRequest.getId(), updatePamRequest.getCcFeatures());
        }
    }

    public void updateTransactionCode(UpdateTransactionCodeRequest updatePamRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - updateTransactionCode");
        transactionsDao.updateTransactionCode(updatePamRequest.getId(),
                updatePamRequest.getName(), updatePamRequest.getValue(),
                updatePamRequest.getDescription());
    }

    public void addTransactionCode(AddTransactionCodeRequest addPamRequest) throws LookupException {
        int updatedRows = transactionsDao.addTransactionCode(addPamRequest.getName(), addPamRequest.getValue(), addPamRequest.getDescription());
        CCATLogger.DEBUG_LOGGER.debug("#Updated transactionCodes = {}", updatedRows);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void addTransactionType(AddTransactionTypeRequest addPamRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - addTransactionType");
        Integer typeId = sequenceTableDao.getNextId(DatabaseStructs.SEQUENCE.S_ADM_TX_TYPES);
        transactionsDao.addTransactionType(typeId, addPamRequest.getName(), addPamRequest.getValue());
        if (addPamRequest.getCcFeatures() != null && !addPamRequest.getCcFeatures().isEmpty()) {
            transactionsDao.addFeaturesByTypeId(typeId, addPamRequest.getCcFeatures());
        }
    }

    public void deleteTransactionCode(DeletePamRequest deletePamRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - deleteTransactionCode");
        boolean isfound = transactionCodehasChild(deletePamRequest.getId());
        if (isfound) {
            CCATLogger.DEBUG_LOGGER.error("Transaction Code has valid links and may not be deleted");
            throw new LookupException(ErrorCodes.ERROR.TRANSACTION_CODE_HAS_CHILD);
        }
        transactionsDao.deleteTransactionCode(deletePamRequest.getId());
    }

    public void deleteTransactionType(DeletePamRequest deletePamRequest) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("AdmTransactionService - deleteTransactionType");
        boolean isfound = transactionTypehasChild(deletePamRequest.getId());
        if (isfound) {
            CCATLogger.DEBUG_LOGGER.error("Transaction Type has valid links and may not be deleted");
            throw new LookupException(ErrorCodes.ERROR.TRANSACTION_TYPE_HAS_CHILD);
        }
        boolean islinked = transactionTypeisLinked(deletePamRequest.getId());
        if (islinked) {
            CCATLogger.DEBUG_LOGGER.error("Can't Delete this Record since it's linked to transaction code");
            throw new LookupException(ErrorCodes.ERROR.IS_LINKED);
        }
        transactionsDao.deleteTransactionType(deletePamRequest.getId());
    }

    public void updateTransactionLink(UpdateTransactionLinkRequest request) throws LookupException {

        if (request.getLinkType() == LinkType.LINK) {
            addTransactionLink(request);
        } else {
            removeTransactionLink(request);
        }
    }

    public void addTransactionLink(UpdateTransactionLinkRequest request) throws LookupException {

        boolean isFounded = isFoundLinkTransaction(request.getTypeId(), request.getCodeId());
        if (isFounded) {
            CCATLogger.DEBUG_LOGGER.error("Transaction Type and Transaction Code already Linked");
            throw new LookupException(ErrorCodes.ERROR.ALREADY_LINKED);
        }
        transactionsDao.addTransactionLink(request);
    }

    public void removeTransactionLink(UpdateTransactionLinkRequest request) throws LookupException {
        boolean isFounded = isFoundLinkTransaction(request.getTypeId(), request.getCodeId());
        if (!isFounded) {
            CCATLogger.DEBUG_LOGGER.error(" link doesn't exist for this Transaction Type and Transaction Code");
            throw new LookupException(ErrorCodes.ERROR.NOT_LINKED_TO_UNLINK);
        }
        transactionsDao.removeTransactionLink(request.getTypeId(), request.getCodeId());
    }

    public boolean isFoundLinkTransaction(Integer typeId, Integer codeId) throws LookupException {
        return transactionsDao.isFoundLinkTransaction(typeId, codeId);
    }

    public boolean transactionTypeisLinked(int typeId) throws LookupException {
        return transactionsDao.isTransactionTypeLinked(typeId);
    }

    public boolean transactionTypehasChild(int typeId) throws LookupException {
        return transactionsDao.transactionTypehasChild(typeId);
    }

    public boolean transactionCodehasChild(int codeId) throws LookupException {
        return transactionsDao.transactionCodehasChild(codeId);
    }

}
