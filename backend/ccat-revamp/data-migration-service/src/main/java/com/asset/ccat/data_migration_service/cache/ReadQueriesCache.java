package com.asset.ccat.data_migration_service.cache;


import com.asset.ccat.data_migration_service.constants.Tables;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Assem.Hassan
 */
@Component
public class ReadQueriesCache {

    private ConcurrentHashMap<String, String> queriesMap;


    @PostConstruct
    private void postConstruct() {
        queriesMap = new ConcurrentHashMap<>();
        queriesMap.put(Tables.ADM_SERVICE_CLASS_DA.name(), "SELECT sc.CODE,da.DESCRIPTION,da.DA_ID,da.RATING_FACTOR "
                + "FROM " + Tables.ADM_SERVICE_CLASS_DA + " da "
                + "INNER JOIN " + Tables.ADM_SERVICE_CLASSES + " sc "
                + "ON da.SERVICE_CLASS_ID = sc.ID");

        queriesMap.put(Tables.ADM_SERVICE_CLASS_ACC.name(), "SELECT sc.CODE,acc.DESCRIPTION,acc.ACC_ID "
                + "FROM " + Tables.ADM_SERVICE_CLASS_ACC + " acc "
                + "INNER JOIN " + Tables.ADM_SERVICE_CLASSES + " sc "
                + "ON acc.SERVICE_CLASS_ID = sc.ID");
        queriesMap.put(Tables.ADM_SYSTEM_PROPERTIES.name(), "SELECT ENTRY_KEY,ITEM_VALUE "
                + " FROM " + Tables.ADM_SYSTEM_PROPERTIES);
    }

    public ConcurrentHashMap<String, String> getQueriesMap() {
        return queriesMap;
    }

    public void setQueriesMap(ConcurrentHashMap<String, String> queriesMap) {
        this.queriesMap = queriesMap;
    }
}