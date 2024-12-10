/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.database.mapper;

import com.asset.ccat.ods_service.cache.CachedLookups;
import com.asset.ccat.ods_service.defines.DBStructs;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

/**
 *
 * @author wael.mohamed
 */
@Component
public class DssReportMapper {

    @Autowired
    CachedLookups cachedLookups;

    public DSSResponseModel mapRow2(ResultSet resultSet, String pageName) throws SQLException {
        DSSResponseModel dssModel = new DSSResponseModel();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        HashMap<Integer, String> headersMap = new HashMap<>();
        HashMap<Integer, String> detailsMap;
        List<HashMap<Integer, String>> detailsMapsList = new ArrayList();
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            headersMap.put(i, getDisplayName(pageName, rsmd.getColumnName(i)));
        }
        int numOfRecords = 0;
        while (resultSet.next()) {
            numOfRecords++;
            if (DBStructs.MAX_RETRIEVAL_DSS_PAGES != 0) {
                if (numOfRecords > DBStructs.MAX_RETRIEVAL_DSS_PAGES) {
                    dssModel.setExceedsAllowed(true);
                    numOfRecords--;
                    break;
                }
            }
            detailsMap = new HashMap();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                detailsMap.put(i, (resultSet.getObject(rsmd.getColumnName(i)) == null) ? "" : resultSet.getObject(rsmd.getColumnName(i)).toString());
            }
            detailsMapsList.add(detailsMap);
        }

        dssModel.setHeaders(headersMap);
        dssModel.setData(detailsMapsList);
        return dssModel;
    }

    public DSSResponseModel mapRow(ArrayList<LinkedCaseInsensitiveMap> array, String pageName) {
        DSSResponseModel dssModel = new DSSResponseModel();
        HashMap<Integer, String> headersDisplayNameMap = new HashMap<>();
        HashSet<String> headersOriginalNameSet = new LinkedHashSet<>();
        HashMap<Integer, String> detailsMap = new HashMap<>();
        List<HashMap<Integer, String>> detailsMapsList = new ArrayList();

        int headerCount = 1;
        if (array != null && !array.isEmpty() && array.get(0) != null) {
            for (Object keyObj : array.get(0).keySet()) {
                String key = (String) keyObj;
                headersDisplayNameMap.put(headerCount++, getDisplayName(pageName, key));
                headersOriginalNameSet.add(key);
            }

            int numOfRecords = 0;
            if (!headersDisplayNameMap.isEmpty()) {
                for (LinkedCaseInsensitiveMap row : array) {
                    int columnIdx = 0;
                    numOfRecords++;
                    if (DBStructs.MAX_RETRIEVAL_DSS_PAGES != 0) {
                        if (numOfRecords > DBStructs.MAX_RETRIEVAL_DSS_PAGES) {
                            dssModel.setExceedsAllowed(true);
                            numOfRecords--;
                            break;
                        }
                    }
                    detailsMap = new HashMap();
                    for (String columnName : headersOriginalNameSet) {
                        String cellVal = row.get(columnName) == null ? "" : String.valueOf(row.get(columnName));
                        detailsMap.put(++columnIdx, cellVal);
                    }
                    detailsMapsList.add(detailsMap);
                }
            }

        }

        dssModel.setHeaders(headersDisplayNameMap);
        dssModel.setData(detailsMapsList);
        return dssModel;
    }

    private String getDisplayName(String pageName, String rsColumnName) {// throws ODSException {
        String displayName = rsColumnName;
        if (cachedLookups.getRetrieveDSSColumnNames() != null) {
            if (cachedLookups.getRetrieveDSSColumnNames().containsKey(pageName)) {
                HashMap<String, String> columnsMap = cachedLookups.getRetrieveDSSColumnNames().get(pageName);
                if (columnsMap != null && !columnsMap.isEmpty() && columnsMap.containsKey(rsColumnName)) {
                    displayName = columnsMap.get(rsColumnName);
                }
            }
        }
        CCATLogger.DEBUG_LOGGER.debug(" PageName:" + pageName + " ,columnName: " + rsColumnName + ",DisplayName : " + displayName);
        return displayName;
    }

}
