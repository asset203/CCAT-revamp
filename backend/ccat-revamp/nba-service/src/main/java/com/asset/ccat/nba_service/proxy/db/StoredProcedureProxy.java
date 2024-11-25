/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.proxy.db;

import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.manager.DataSourceManager;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mahmoud Shehab
 */
@Repository
public class StoredProcedureProxy {

    @Autowired
    DataSourceManager dataSourceManager;

    public HashMap<String, Object> callStoredProcedure(HashMap parameters) throws NBAException {
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            HikariDataSource hikariDataSource = dataSourceManager.retrieveDataSource();
            String spName = (String) parameters.get(Defines.SP_CONSTANTS.SP_NAME);
            CCATLogger.DEBUG_LOGGER.debug("Start Calling NBA Stored procedure " + spName);
            CCATLogger.INTERFACE_LOGGER.debug("Requested Paramter: " + parameters);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(hikariDataSource)
                    .withProcedureName(spName);
            MapSqlParameterSource paramaters2 = (MapSqlParameterSource) parameters.get("input");
            Map<String, Object> storedProcedureResponseParameters = jdbcCall.execute(paramaters2);
            HashMap<String, Object> responseMap = new HashMap();
            for (String responseKey : storedProcedureResponseParameters.keySet()) {
                Object responseValue = storedProcedureResponseParameters.get(responseKey);
                responseMap.put(responseKey.toLowerCase(), responseValue);
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + responseMap);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
            return responseMap;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error occured while get all gifts");
            CCATLogger.ERROR_LOGGER.error("Error occured while get all gifts", ex);
            throw new NBAException(ErrorCodes.ERROR.NBA_ERROR, ex.getMessage());
        }
    }
}
