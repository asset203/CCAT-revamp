/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.managers;

import com.asset.ccat.ods_service.cache.CachedLookups;
import com.asset.ccat.ods_service.cache.DSSCache;
import com.asset.ccat.ods_service.database.dao.DSSInterfacesDao;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.DSSNodesModel;
import com.asset.ccat.ods_service.models.ods_models.*;
import com.asset.ccat.ods_service.proxy.LookupProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
@Component
public class EngineManager {

    @Autowired
    private CachedLookups cachedLookups;
    @Autowired
    private DatasourceManager datasourceManager;
    @Autowired
    private LookupProxy lookupProxy;

    @Autowired
    private DSSInterfacesDao dssInterfacesDao;

    @Autowired
    DSSCache dssCache;

    @EventListener
    public void startupEvent(ApplicationReadyEvent event) throws ODSException {
        retrieveCachedLookups();
        datasourceManager.init();
        dssCache.setDssInterfaceModelMap(dssInterfacesDao.getDSSInterfaces());
    }

    @Scheduled(fixedDelay = 30000)
    public void refreshLookups() {
        retrieveCachedLookups();
        datasourceManager.refreshDataSourceNodes();
    }

    public void retrieveCachedLookups() {
        CCATLogger.DEBUG_LOGGER.info("Refresh ods-service...");
        try {
            CCATLogger.DEBUG_LOGGER.info("Start retrieve account history activities");
            HashMap<String, ODSActivityModel> odsActivities = lookupProxy.getOdsActivities();
            cachedLookups.setAccountHistoryActivities(odsActivities);
            CCATLogger.DEBUG_LOGGER.info("retrieve account history activities with size[" + ((odsActivities == null) ? 0 : odsActivities.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve account history activities headers");
            HashMap<Integer, ODSActivityHeader> odsActivitiesHeaders = lookupProxy.getOdsActivitiesHeaders();
            cachedLookups.setAccountHistoryActivitiesHeader(odsActivitiesHeaders);
            CCATLogger.DEBUG_LOGGER.info("retrieve account history activities headers with size[" + ((odsActivitiesHeaders == null) ? 0 : odsActivitiesHeaders.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve account history activities headers mapping ");
            HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> odsActivitiesHeadersMapping = lookupProxy.getOdsActivitiesHeadersMapping();
            cachedLookups.setAccountHistoryActivitiesHeaderMapping(odsActivitiesHeadersMapping);
            CCATLogger.DEBUG_LOGGER.info("retrieve account history activities headers mapping with size[" + ((odsActivitiesHeadersMapping == null) ? 0 : odsActivitiesHeadersMapping.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve account history activities details mapping ");
            HashMap<Integer, List<ODSActivityDetailsMapping>> odsActivitiesDetailsMapping = lookupProxy.getOdsActivitiesDetailsMapping();
            cachedLookups.setAccountHistoryActivitiesDetailsMapping(odsActivitiesDetailsMapping);
            CCATLogger.DEBUG_LOGGER.info("retrieve account history activities details mapping with size[" + ((odsActivitiesDetailsMapping == null) ? 0 : odsActivitiesDetailsMapping.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve account flags");
            HashMap<String, String> accountFlags = lookupProxy.getAccountFlags();
            cachedLookups.setAccountFlags(accountFlags);
            CCATLogger.DEBUG_LOGGER.info("retrieve account flags with size[" + ((accountFlags == null) ? 0 : accountFlags.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve tx types");
            HashMap<String, String> types = lookupProxy.getTransactionTypes();
            cachedLookups.setTransactionTypes(types);
            CCATLogger.DEBUG_LOGGER.info("retrieve tx types with size[" + ((types == null) ? 0 : types.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve tx codes");
            HashMap<String, String> codes = lookupProxy.getTransactionCodes();
            cachedLookups.setTransactionCodes(codes);
            CCATLogger.DEBUG_LOGGER.info("retrieve tx codes with size[" + ((codes == null) ? 0 : codes.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve tx linkes");
            HashMap<String, String> linkes = lookupProxy.getTransactionLinkes();
            cachedLookups.setTransactionlinks(linkes);
            CCATLogger.DEBUG_LOGGER.info("retrieve tx linkes with size[" + ((linkes == null) ? 0 : linkes.size()) + "]");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve DSSColumnNames");
            HashMap<String, HashMap<String, String>> retrieveDSSColumnNames = lookupProxy.getDssReportsColumns();
            cachedLookups.setRetrieveDSSColumnNames(retrieveDSSColumnNames);
            CCATLogger.DEBUG_LOGGER.info("DSSColumnNames retrieved successfully with count [" + (retrieveDSSColumnNames == null ? "0" : retrieveDSSColumnNames.size()) + "] ");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve btStatus");
            HashMap<String, String> btStatus = lookupProxy.getBtStatus();
            cachedLookups.setBtStatus(btStatus);
            CCATLogger.DEBUG_LOGGER.info("btStatus retrieved successfully with count [" + (btStatus == null ? "0" : btStatus.size()) + "] ");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve odsNodes");
            List<ODSNodesModel> odsNodes = lookupProxy.getODSNodes();
            cachedLookups.setODSNodes(odsNodes);
            CCATLogger.DEBUG_LOGGER.info("odsNodes retrieved successfully with count [" + (odsNodes == null ? "0" : odsNodes.size()) + "] ");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve dssNodes");
            List<DSSNodesModel> dssNodes = lookupProxy.getDSSNodes();
            cachedLookups.setDSSNodes(dssNodes);
            CCATLogger.DEBUG_LOGGER.info("dssNodes retrieved successfully with count [" + (dssNodes == null ? "0" : dssNodes.size()) + "] ");

            CCATLogger.DEBUG_LOGGER.info("Start retrieve flexHistoryNodes");
            List<FlexShareHistoryNodeModel> flexHistoryNodes = lookupProxy.getFlexHistoryNodes();
            cachedLookups.setFlexHistoryNodes(flexHistoryNodes);
            CCATLogger.DEBUG_LOGGER.info("flexHistoryNodes retrieved successfully with count [" + (flexHistoryNodes == null ? "0" : flexHistoryNodes.size()) + "] ");

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("ERROR while start ods-service " + ex);
            CCATLogger.ERROR_LOGGER.error("ERROR while start ods-service ", ex);
        }
    }
}
