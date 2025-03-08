/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.database.dao;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.models.NotePadModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class NotePadDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<NotePadModel> getAllNotePad(String msisdn, String otherMSISDN) throws DataAccessException {

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.ENTRY_DATE);
        query.append(",");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.MSISDN);
        query.append(",");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.USER_ID);
        query.append(",");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_ENTRY);
        query.append(",");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.PAGE_NAME);
        query.append(",");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_USERNAME);
        query.append(" FROM ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME);
        query.append(" Where  ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.MSISDN);
        query.append(" IN ( '");
        query.append(msisdn);
        query.append("','");
        query.append(otherMSISDN);
        query.append("') AND ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.MSISDN_MOD_X);
        query.append(" = ? ");
        query.append("Order By ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.ENTRY_DATE);
        query.append(" Desc");
        CCATLogger.DEBUG_LOGGER.debug("select query : {}", query);
        return jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(NotePadModel.class), msisdn.substring(msisdn.length() - 1));
    }

    public int addNotePad(NotePadModel notePad) throws DataAccessException {
        String sql
                = "INSERT INTO "
                + DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME
                + "(" + DBStructs.H_NOTEPAD_ENTRIES.MSISDN
                + "," + DBStructs.H_NOTEPAD_ENTRIES.MSISDN_MOD_X
                + "," + DBStructs.H_NOTEPAD_ENTRIES.USER_ID + ","
                + DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_ENTRY + ","
                + DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_USERNAME + ","
                + DBStructs.H_NOTEPAD_ENTRIES.PAGE_NAME
                + ") " + "VALUES (?,?,?,?,?,?)";
//                + "VALUES ('"
//                + notePad.getMsisdn()
//                + "'," + notePad.getMsisdnModX()
//                + "," + notePad.getUserId()
//                + ",'" + notePad.getNotepadEntry()
//                + "','" + notePad.getUserName()
//                + "')";
        CCATLogger.DEBUG_LOGGER.debug("insert query : " + sql);
        return jdbcTemplate.update(sql, notePad.getMsisdn(),
                notePad.getMsisdnModX(), notePad.getUserId(),
                notePad.getNotepadEntry(), notePad.getUserName(), notePad.getPageName());
    }

    public int deleteNotePadEntries(String msisdn, String otherMSISDN) throws DataAccessException, HistoryException {
        String sqlStatement = "DELETE FROM "
                + DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME
                + " WHERE "
                + DBStructs.H_NOTEPAD_ENTRIES.MSISDN
                + " IN ( '"
                + msisdn
                + "','"
                + otherMSISDN + "')";
        CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlStatement);
        try {
            return jdbcTemplate.update(sqlStatement);
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while notepad deletion. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while notepad deletion. ", ex);
            throw new HistoryException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
