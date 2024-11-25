/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.database.dao.DisconnectionCodeDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.CreateDisconnectionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.DeleteDisconnectionCodeRequest;
import com.asset.ccat.lookup_service.models.requests.disconnection_code.UpdateDisconnectionCodeRequest;

/**
 *
 * @author wael.mohamed
 */
@Service
public class DisconnectionCodeService {

    @Autowired
    private DisconnectionCodeDao codeDao;
    @Autowired
    private CachedLookups cachedLookups;

    public List<DisconnectionCodeModel> getAllCachedDisconnectionCodes() throws LookupException {
        List<DisconnectionCodeModel> codes = cachedLookups.getDisconnectionCodes();
        return codes;
    }

    public List<DisconnectionCodeModel> getAllDisconnectionCodes() throws LookupException {
        List<DisconnectionCodeModel> codes = codeDao.retrieveDisconnectionCodes();
        return (codes);
    }

    public void createDisconnectionCode(CreateDisconnectionCodeRequest request) throws LookupException {
        codeDao.addDisconnectionCode(request.getCode(), request.getDescription());
    }

    public void updateDisconnectionCode(UpdateDisconnectionCodeRequest request) throws LookupException {
        codeDao.updateDisconnectionCode(request.getId(), request.getDescription());
    }

    public void deleteDisconnectionCode(DeleteDisconnectionCodeRequest request) throws LookupException {
        codeDao.deleteDisconnectionCode(request.getId());
    }
}
