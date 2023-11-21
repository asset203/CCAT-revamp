package com.asset.ccat.data_migration_service.cache;


import com.asset.ccat.data_migration_service.constants.Tables;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Assem.Hassan
 */
@Component
public class UpdateQueriesCache {

    private ConcurrentHashMap<String, String> queriesMap;


    @PostConstruct
    private void postConstruct() {
        queriesMap = new ConcurrentHashMap<>();
        queriesMap.put(Tables.ADM_SYSTEM_PROPERTIES.name(),
                "UPDATE " + Tables.ADM_SYSTEM_PROPERTIES +
                        " SET VALUE = ?  WHERE CODE = ? AND PROFILE = ? AND LABEL= ? ");
    }

    public ConcurrentHashMap<String, String> getQueriesMap() {
        return queriesMap;
    }

    public void setQueriesMap(ConcurrentHashMap<String, String> queriesMap) {
        this.queriesMap = queriesMap;
    }
}