package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.cache.DSSCache;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.models.DssInterfaceModel;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @param <T> the output report;
 * @param <F> request input;
 * */
public interface DSSReportService<T, F> {
    T getReport(F request) throws ODSException, SQLException;

    Map<String, Object> setInParamNameValueMap(F request);

    DSSResponseModel parseSPResponse(Map<String, Object> spResponse, String spName) throws SQLException;

    default String getSPName(String pageName, DSSCache dssCache){
        Map<String, DssInterfaceModel> dssInterfaces = dssCache.getDssInterfaceModelMap();
        Optional<String> keyOptional = dssInterfaces.entrySet().stream()
                .filter(entry -> entry.getValue().getPageName().equalsIgnoreCase(pageName))
                .map(Map.Entry::getKey)
                .findFirst();
        return keyOptional.orElse("");
    }

    default Map<String, DssInterfaceModel> getDSSInterfaces(DSSCache dssCache) {
        return dssCache.getDssInterfaceModelMap();
    }
}
