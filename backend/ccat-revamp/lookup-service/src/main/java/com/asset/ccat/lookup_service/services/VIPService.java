package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.database.dao.VIPDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.requests.VIPListRequest;
import com.asset.ccat.lookup_service.models.responses.VIPListsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VIPService {
    private final VIPDao vipDao;

    public VIPService(VIPDao vipDao) {
        this.vipDao = vipDao;
    }

    public VIPListsResponse getVIPLists() throws LookupException {
        List<String> vipMsisdnList = vipDao.getVIPList();
        List<String> viPagesList = vipDao.getVIPPagesList();
        CCATLogger.DEBUG_LOGGER.debug("# VIP MSISDN = {} || # VIP Pages = {}", vipMsisdnList.size(), viPagesList.size());
        return new VIPListsResponse(vipMsisdnList, viPagesList);
    }

    public void addVIPMsisdn(VIPListRequest addVipMsisdnRequest) throws LookupException {
        int affectedRows = vipDao.addVIPMsisdn(addVipMsisdnRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("#AddedRows={}", affectedRows);
    }
}
