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
import com.asset.ccat.lookup_service.database.dao.LkPamClassDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.LkPamModel;

/**
 *
 * @author wael.mohamed
 */
@Service
public class LkPamClassService {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private LkPamClassDao lkPamClassDao;

    public List<LkPamModel> retrieveCachedPamClasses() throws LookupException {
        List<LkPamModel> lkPamClasses = cachedLookups.getPamClasses();
        return lkPamClasses;
    }

    public List<LkPamModel> retrievePamClasses() throws LookupException {
        List<LkPamModel> lkPamClasses = lkPamClassDao.retrievePamClasses();
        return lkPamClasses;
    }

    public void addPamClass(Integer id, String desc) throws LookupException {
        lkPamClassDao.addPamClass(id, desc);
    }

    public void updatePamClass(Integer id, String desc) throws LookupException {
        lkPamClassDao.updatePamClass(id, desc);
    }

    public void deletePamClass(Integer id) throws LookupException {
        lkPamClassDao.deletePamClass(id);
    }
}
