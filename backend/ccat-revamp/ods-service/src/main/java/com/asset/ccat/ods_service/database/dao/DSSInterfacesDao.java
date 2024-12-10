package com.asset.ccat.ods_service.database.dao;

import com.asset.ccat.ods_service.defines.DBStructs;
import com.asset.ccat.ods_service.defines.ErrorCodes;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.managers.DatasourceManager;
import com.asset.ccat.ods_service.models.DssInterfaceModel;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DSSInterfacesDao {

    private final DatasourceManager datasourceManager;

    public DSSInterfacesDao(DatasourceManager datasourceManager) {
        this.datasourceManager = datasourceManager;
    }

    public Map<String, DssInterfaceModel> getDSSInterfaces() throws ODSException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM ")
                .append(DBStructs.DSS_INTERFACES.TABLE_NAME);
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving DSS Interfaces: {}", sqlQuery);
        try {
            HikariDataSource hikariDataSource = datasourceManager.getHikariDataSource("DEFAULT");
            return new JdbcTemplate(hikariDataSource).query(sqlQuery.toString(), rs -> {
                Map<String, DssInterfaceModel> interfacesMap = new HashMap<>();
                while (rs.next()) {
                    DssInterfaceModel interfaceModel = new DssInterfaceModel();
                    interfaceModel.setPageName(rs.getString(DBStructs.DSS_INTERFACES.PAGE_NAME));
                    interfaceModel.setSpName(rs.getString(DBStructs.DSS_INTERFACES.SP_NAME));
                    interfaceModel.setSpInputParams(rs.getString(DBStructs.DSS_INTERFACES.SP_INPUT_PARAMS));
                    interfaceModel.setSpOutputParams(rs.getString(DBStructs.DSS_INTERFACES.SP_OUTPUT_PARAMS));
                    interfacesMap.put(interfaceModel.getSpName(), interfaceModel);
                }
                return interfacesMap;
            });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting DSS interfaces. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting DSS interfaces. ", ex);
            throw new ODSException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
