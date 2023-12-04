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
import com.asset.ccat.lookup_service.database.dao.LkPamPriorityDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.LkPamModel;

/**
 *
 * @author wael.mohamed
 */
@Service
public class LkPamPriorityService {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private LkPamPriorityDao pamDao;

    public List<LkPamModel> retrieveCachedPamPrioritys() throws LookupException {
        List<LkPamModel> lkPamPriorities = cachedLookups.getPamPriorities();
        return lkPamPriorities;
    }

    public List<LkPamModel> retrievePamPrioritys() throws LookupException {
        List<LkPamModel> lkPamPriorities = pamDao.retrievePamPrioritys();
        return lkPamPriorities;
    }

    public void addPamPriority(Integer id, String description) throws LookupException {
        pamDao.addPamPriority(id, description);
    }

    public void updatePamPriority(Integer id, String description) throws LookupException {
        pamDao.updatePamPriority(id, description);
    }

    public void deletePamPriority(Integer id) throws LookupException {
        pamDao.deletePamPriority(id);
    }

}
