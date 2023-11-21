/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.database.extractors.DSSColumnNameExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class DSSColumnNameDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DSSColumnNameExtractor columnNameExtractor;

    private String retrieveDSSColumnNamesQuery;

    public HashMap<String, HashMap<String, String>> retrieveDSSColumnNames() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start  DSSColumnNameDao - retrieveColumnNames ");
        long t = System.currentTimeMillis();
        try {
            if (retrieveDSSColumnNamesQuery == null) {
                retrieveDSSColumnNamesQuery = " SELECT "
                        + DatabaseStructs.DSS_COLUMNS.DSS_PAGE_NAME
                        + " , "
                        + " UPPER( " + DatabaseStructs.DSS_COLUMNS.COLUMN_NAME + " ) " + DatabaseStructs.DSS_COLUMNS.COLUMN_NAME
                        + " , " + DatabaseStructs.DSS_COLUMNS.DISPLAY_NAME
                        + " FROM " + DatabaseStructs.DSS_COLUMNS.TABLE_NAME
                        + " Order By "
                        + DatabaseStructs.DSS_COLUMNS.COLUMN_NAME
                        +" ASC ";
            }
            CCATLogger.DEBUG_LOGGER.debug("retrieveDSSColumnNamesQuery = " + retrieveDSSColumnNamesQuery);
            return jdbcTemplate.query(retrieveDSSColumnNamesQuery, columnNameExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception in retrieveColumnNames \n" + ex);
            CCATLogger.ERROR_LOGGER.error("Exception in retrieveColumnNames ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

}
