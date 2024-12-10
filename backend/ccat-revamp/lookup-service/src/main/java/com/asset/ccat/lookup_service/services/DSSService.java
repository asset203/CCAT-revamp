package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.database.dao.DSSLookupDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.dss.DssFlagModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DSSService {
    private final DSSLookupDao dssDao;

    public DSSService(DSSLookupDao dssDao) {
        this.dssDao = dssDao;
    }

    public Map<String, List<DssFlagModel>> getDssFlags() throws LookupException {
        Map<String, List<DssFlagModel>> dssPagesFlags = dssDao.getDssPagesFlags();
        CCATLogger.DEBUG_LOGGER.debug("dssPagesFlags = {}", dssPagesFlags);
        return dssPagesFlags;
    }



}
