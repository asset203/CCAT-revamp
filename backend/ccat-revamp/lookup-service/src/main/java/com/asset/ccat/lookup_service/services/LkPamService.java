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
import com.asset.ccat.lookup_service.database.dao.LkPamServiceDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.LkPamModel;

/**
 *
 * @author wael.mohamed
 */
@Service
public class LkPamService {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private LkPamServiceDao pamDao;

    public List<LkPamModel> retrieveCachedPamServices() throws LookupException {
        List<LkPamModel> lkPamServices = cachedLookups.getPamServices();
        return lkPamServices;
    }
    public List<LkPamModel> retrievePamServices() throws LookupException {
        List<LkPamModel> lkPamServices = pamDao.retrievePamServices();
        return lkPamServices;
    }

    public void addPamService(Integer id, String description) throws LookupException {
        pamDao.addPamService(id, description);
    }

    public void updatePamService(Integer id, String description) throws LookupException {
        pamDao.updatePamService(id, description);
    }

    public void deletePamService(Integer id) throws LookupException {
        pamDao.deletePamService(id);
    }
}
