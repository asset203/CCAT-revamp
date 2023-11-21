package com.asset.ccat.springcloudconfigserver.database.dao;

import com.asset.ccat.springcloudconfigserver.database.extractors.SystemPropertiesExtractor;
import com.asset.ccat.springcloudconfigserver.database.extractors.SystemPropertiesGetAllPasswordsExtractor;
import com.asset.ccat.springcloudconfigserver.defines.DatabaseStructs;
import com.asset.ccat.springcloudconfigserver.defines.Defines;
import com.asset.ccat.springcloudconfigserver.defines.ErrorCodes;
import com.asset.ccat.springcloudconfigserver.exceptions.ConfigServerException;
import com.asset.ccat.springcloudconfigserver.loggers.CCATLogger;
import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Component
public class AdmSystemPropertiesDao {

    @Autowired
    private SystemPropertiesExtractor systemPropertiesExtractor;
    @Autowired
    private SystemPropertiesGetAllPasswordsExtractor systemPropertiesPasswordsExtractor;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String retrieveSysPropByProfileAndLabelQuery;
    private String retrieveSysPropByProfilePasswords;
    private String updateSysPropByProfileAndLabelQuery;

    public HashMap<String, List<SystemConfigurationModel>> retrieveSystemPropertiesByProfileAndLabel(String profile, String label) throws ConfigServerException {
        try {
            if (retrieveSysPropByProfileAndLabelQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT * FROM ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.PROFILE).append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.LABEL).append(" = ? ");
                retrieveSysPropByProfileAndLabelQuery = queryBuilder.toString();
            }
            return jdbcTemplate.query(retrieveSysPropByProfileAndLabelQuery, systemPropertiesExtractor, profile, label);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSysPropByProfileAndLabelQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveSysPropByProfileAndLabelQuery, e);
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    public int[] updateSystemPropertiesByProfileAndLabel(String profile, String label, List<SystemConfigurationModel> configurations) throws ConfigServerException {
        try {
            if (updateSysPropByProfileAndLabelQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("UPDATE ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.VALUE).append(" = ? ")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.CODE).append(" = ? ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.PROFILE).append(" = ? ")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.LABEL).append(" = ? ");
                updateSysPropByProfileAndLabelQuery = queryBuilder.toString();
            }
            int[] res = jdbcTemplate.batchUpdate(updateSysPropByProfileAndLabelQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, configurations.get(i).getValue());
                    ps.setString(2, configurations.get(i).getKey());
                    ps.setString(3, profile);
                    ps.setString(4, label);
                }

                @Override
                public int getBatchSize() {
                    return configurations.size();
                }
            });
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSysPropByProfileAndLabelQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveSysPropByProfileAndLabelQuery, e);
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
    public HashMap<String, SystemConfigurationModel> retrieveSystemPropertiesPasswords(String profile, String label) throws ConfigServerException {
        try {
            if (retrieveSysPropByProfilePasswords == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT * FROM ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.PROFILE).append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.LABEL).append(" = ? ")
                        .append(" AND ").append(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TYPE).append(" = 4 ");
                retrieveSysPropByProfilePasswords = queryBuilder.toString();
            }
            return jdbcTemplate.query(retrieveSysPropByProfilePasswords, systemPropertiesPasswordsExtractor, profile, label);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSysPropByProfilePasswords);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveSysPropByProfilePasswords, e);
            throw new ConfigServerException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }
}
