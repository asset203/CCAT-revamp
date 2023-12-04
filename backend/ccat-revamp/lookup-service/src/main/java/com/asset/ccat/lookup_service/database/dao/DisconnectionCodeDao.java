/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.database.row_mapper.DisconnectionCodeRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author wael.mohamed
 */
@Repository
public class DisconnectionCodeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String retrieveDisconnectionCodeQuery;
    private String deleteDisconnectionCodeByIdQuery;
    private String addDisconnectionCodeQuery;
    private String updateDisconnectionCodeQuery;

    public List<DisconnectionCodeModel> retrieveDisconnectionCodes() {
        CCATLogger.DEBUG_LOGGER.debug("Starting DisconnectionCodeDao - retrieveDisconnectionCodes");
        List<DisconnectionCodeModel> result = null;
        try {
            if (retrieveDisconnectionCodeQuery == null) {
                retrieveDisconnectionCodeQuery = "SELECT * FROM "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.IS_DELETED + " = 0 "
                        + " ORDER BY " + DatabaseStructs.LK_DISCONNECTION_CODES.CREATION_DATE + " DESC";
            }
            return jdbcTemplate.query(retrieveDisconnectionCodeQuery, new DisconnectionCodeRowMapper());
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveDisconnectionCodeQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveDisconnectionCodeQuery, ex);
        }
        return result;
    }

    public boolean addDisconnectionCode(Integer code, String name) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DisconnectionCodeDao - addDisconnectionCode");
        boolean isAdded;

        try {
            if (addDisconnectionCodeQuery == null) {
                addDisconnectionCodeQuery = "INSERT INTO "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.TABLE_NAME
                        + " ("
                        + DatabaseStructs.LK_DISCONNECTION_CODES.ID
                        + ", "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.CODE_ID
                        + ", "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.NAME
                        + ") "
                        + "VALUES (" + DatabaseStructs.SEQUENCE.S_LK_DISCONNECTION_CODES + ".nextval,?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addDisconnectionCodeQuery);

            isAdded = jdbcTemplate.update(addDisconnectionCodeQuery, code, name) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending DisconnectionCodeDao - addDisconnectionCode");
        return isAdded;
    }

    public boolean updateDisconnectionCode(Integer id, String name) throws LookupException {
        int rows;
        try {
            if (updateDisconnectionCodeQuery == null) {
                updateDisconnectionCodeQuery
                        = "UPDATE " + DatabaseStructs.LK_DISCONNECTION_CODES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.NAME + " = ? "
                        + " WHERE "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("updateDisconnectionCodeQuery  = " + updateDisconnectionCodeQuery);
            rows = jdbcTemplate.update(updateDisconnectionCodeQuery, name, id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateDisconnectionCodeQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateDisconnectionCodeQuery, ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows > 0;
    }

    public boolean deleteDisconnectionCode(Integer id) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting DisconnectionCodeDao - deleteDisconnectionCode");
        boolean res;
        try {
            if (deleteDisconnectionCodeByIdQuery == null) {
                deleteDisconnectionCodeByIdQuery
                        = "UPDATE " + DatabaseStructs.LK_DISCONNECTION_CODES.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.IS_DELETED + " = 1 "
                        + " WHERE "
                        + DatabaseStructs.LK_DISCONNECTION_CODES.ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteDisconnectionCodeByIdQuery);
            res = jdbcTemplate.update(deleteDisconnectionCodeByIdQuery, id) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("DisconnectionCodeDao - deleteDisconnectionCode EXCEPTION -->", e);
            CCATLogger.DEBUG_LOGGER.error("DisconnectionCodeDao - deleteDisconnectionCode EXCEPTION -->", e);
            throw new LookupException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending DisconnectionCodeDao - deleteDisconnectionCode");
        return res;
    }

}
