package com.asset.ccat.lookup_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asset.ccat.lookup_service.database.dao.LookupsDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.models.FeatureModel;
import com.asset.ccat.lookup_service.models.MonetaryLimitModel;

/**
 *
 * @author marwa.elshawarby
 */
@Service
public class LookupService {

    @Autowired
    private LookupsDao lookupsDao;

    public List<FeatureModel> getFeaturesLookup() throws LookupException {
        return lookupsDao.retrieveAllFeatures();
    }

    public List<MonetaryLimitModel> getMonetaryLimitsLookup() throws LookupException {
        return lookupsDao.retrieveAllMonetaryLimits();
    }

}
