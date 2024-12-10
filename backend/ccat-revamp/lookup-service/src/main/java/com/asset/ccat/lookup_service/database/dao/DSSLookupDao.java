package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.dss.DssFlagModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DSSLookupDao {
    private final JdbcTemplate jdbcTemplate;

    public DSSLookupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, List<DssFlagModel>> getDssPagesFlags() throws LookupException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM ")
                .append(DatabaseStructs.DSS_FLAGS.TABLE_NAME);
        try {
            return jdbcTemplate.query(sqlQuery.toString(), rs -> {
                Map<String, List<DssFlagModel>> pagesFlagsMap = new HashMap<>();
                while (rs.next()) {
                    DssFlagModel flag = new DssFlagModel();
                    flag.setPageName(rs.getString(DatabaseStructs.DSS_FLAGS.PAGE_NAME));
                    flag.setFlagName(rs.getString(DatabaseStructs.DSS_FLAGS.FLAG_NAME));
                    flag.setFlagValue(rs.getInt(DatabaseStructs.DSS_FLAGS.FLAG_VALUE));
                    pagesFlagsMap.computeIfAbsent(flag.getPageName(), k -> new ArrayList<>()).add(flag);
                }
                return pagesFlagsMap;
            });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting DSS flags. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting DSS flags. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
