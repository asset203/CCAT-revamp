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
import com.asset.ccat.lookup_service.database.dao.LkPamScheduleDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.LkPamModel;

/**
 *
 * @author wael.mohamed
 */
@Service
public class LkPamScheduleService {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private LkPamScheduleDao pamDao;

    public List<LkPamModel> retrieveCachedPamSchedules() throws LookupException {
        List<LkPamModel> lkPamSchedules = cachedLookups.getPamSchedules();
        return lkPamSchedules;
    }

    public List<LkPamModel> retrievePamSchedules() throws LookupException {
        List<LkPamModel> lkPamSchedules = pamDao.retrievePamSchedules();
        return lkPamSchedules;
    }

    public void addPamSchedule(Integer id, String description) throws LookupException {
        pamDao.addPamSchedule(id, description);
    }

    public void updatePamSchedule(Integer id, String description) throws LookupException {
        pamDao.updatePamSchedule(id, description);
    }

    public void deletePamSchedule(Integer id) throws LookupException {
        pamDao.deletePamSchedule(id);
    }

}
