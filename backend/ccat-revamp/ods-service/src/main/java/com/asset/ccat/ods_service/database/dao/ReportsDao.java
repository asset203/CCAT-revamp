package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.asset.ccat.ods_service.utils.SPUtil;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReportsDao {
    private final DatasourceManager datasourceManager;
    private final SPUtil spUtil;

    public ReportsDao(DatasourceManager datasourceManager, SPUtil spUtils) {
        this.datasourceManager = datasourceManager;
        this.spUtil = spUtils;
    }

    public Map<String, Object> executeStoredProcedure(String spName, Map<String, Object> parameterMap) throws ODSException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start executing SP[{}] with params={}", spName, parameterMap);
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("DSS_NODES");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
            List<SqlParameter> sqlInParameters = spUtil.getSPInputParams(spName,
                    parameterMap.values().stream()
                            .map(Object::toString) // Convert all values to String
                            .toArray(String[]::new)
            );
            List<SqlOutParameter> sqlOutParameters = spUtil.getSPOutParams(spName);
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName(spName)
                    .withoutProcedureColumnMetaDataAccess()
                    .declareParameters(sqlInParameters.toArray(new SqlParameter[0]))
                    .declareParameters(sqlOutParameters.toArray(new SqlOutParameter[0]));
            return jdbcCall.execute(parameterMap);
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while SP execution. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while SP execution. ", ex);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
