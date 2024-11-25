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
import com.asset.ccat.lookup_service.database.dao.LkPamPeriodDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.LkPamModel;

/**
 *
 * @author wael.mohamed
 */
@Service
public class LkPamPeriodService {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private LkPamPeriodDao pamDao;

    public List<LkPamModel> retrieveCachedPamPeriods() throws LookupException {
        List<LkPamModel> lkPamPeriods = cachedLookups.getPamPeriods();
        return lkPamPeriods;
    }

    public List<LkPamModel> retrievePamPeriods() throws LookupException {
        List<LkPamModel> lkPamPeriods = pamDao.retrievePamPeriods();
        return lkPamPeriods;
    }

    public void addPamPeriod(Integer id, String description) throws LookupException {
        pamDao.addPamPeriod(id, description);
    }

    public void updatePamPeriod(Integer id, String description) throws LookupException {
        pamDao.updatePamPeriod(id, description);
    }

    public void deletePamPeriod(Integer id) throws LookupException {
        pamDao.deletePamPeriod(id);
    }

}
