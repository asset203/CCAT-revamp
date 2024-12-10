package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.database.dao.PagesDao;
import com.asset.ccat.lookup_service.database.dao.VIPDao;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.PageModel;
import com.asset.ccat.lookup_service.models.requests.vip.VIPListRequest;
import com.asset.ccat.lookup_service.models.requests.vip.VIPUpdatePagesRequest;
import com.asset.ccat.lookup_service.models.responses.vip.VIPListsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VIPService {
    private final VIPDao vipDao;
    private final PagesDao pagesDao;

    private final CachedLookups cachedLookups;

    public VIPService(VIPDao vipDao, PagesDao pagesDao, CachedLookups cachedLookups) {
        this.vipDao = vipDao;
        this.pagesDao = pagesDao;
        this.cachedLookups = cachedLookups;
    }

    public VIPListsResponse getVIPLists() throws LookupException {
        boolean isVIP = true;
        List<String> vipSubscribers = vipDao.getVIPSubscribers();
        List<PageModel> customerCarePages = pagesDao.getAllCustomerCarePages();
        CCATLogger.DEBUG_LOGGER.debug("#customerCarePages = {}", customerCarePages.size());
        Map<Boolean, List<PageModel>> partitionedPages = customerCarePages.stream()
                .collect(Collectors.partitioningBy(PageModel::getIsVipPage));
        return new VIPListsResponse(vipSubscribers, partitionedPages.get(!isVIP), partitionedPages.get(isVIP));
    }

    public void addVIPMsisdn(VIPListRequest addVipMsisdnRequest) throws LookupException {
        int affectedRows = vipDao.addVIPMsisdn(addVipMsisdnRequest.getMsisdn());
        CCATLogger.DEBUG_LOGGER.debug("#AddedRows={}", affectedRows);
    }

    public void deleteVIPMsisdn(String msisdn) throws LookupException {
        int affectedRows = vipDao.deleteVIPMsisdn(msisdn);
        CCATLogger.DEBUG_LOGGER.debug("#deletedRows={}", affectedRows);
    }

    @Transactional
    public void syncVIPPages(VIPUpdatePagesRequest vipUpdateRequest) throws LookupException {
        vipDao.deletePagesExcludingIds(vipUpdateRequest.getMenuIds());

        if(!vipUpdateRequest.getMenuIds().isEmpty())
        {
            Map<Integer, List<Integer>> menuFeaturesMap = cachedLookups.getMenuFeaturesMap();
            Map<Integer, List<PageModel>> vipPageModels = vipUpdateRequest.getMenuIds()
                    .stream()
                    .collect(Collectors.toMap(
                            menuId -> menuId,
                            menuId -> menuFeaturesMap.getOrDefault(menuId, Collections.emptyList())
                                    .stream()
                                    .map(featureId -> {
                                        PageModel pageModel = new PageModel();
                                        pageModel.setMenuId(menuId);
                                        pageModel.setFeatureId(featureId);
                                        return pageModel;
                                    })
                                    .toList()
                    ));
            CCATLogger.DEBUG_LOGGER.debug("Menu's Features = {}", menuFeaturesMap);
            vipDao.syncVIPPages(vipPageModels);
        }
    }
}
