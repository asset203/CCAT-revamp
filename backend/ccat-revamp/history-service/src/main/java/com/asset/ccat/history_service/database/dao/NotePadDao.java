/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.database.dao;

import com.asset.ccat.history_service.defines.DBStructs;
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

    public List<NotePadModel> getAllNotePad(String msisdn, String activePartition, String otherMSISDN) throws DataAccessException {

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
        query.append(DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_USERNAME);
        query.append(" FROM ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME);
        query.append(" PARTITION (");
        query.append(activePartition);
        query.append(") Where  ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.MSISDN);
        query.append(" IN ( '");
        query.append(msisdn);
        query.append("','");
        query.append(otherMSISDN);
        query.append("') Order By ");
        query.append(DBStructs.H_NOTEPAD_ENTRIES.ENTRY_DATE);
        query.append(" Desc");
        CCATLogger.DEBUG_LOGGER.debug("select query : " + query.toString());
        List<NotePadModel> list = jdbcTemplate.query(query.toString(), new BeanPropertyRowMapper<>(NotePadModel.class));
        return list;
    }

    public boolean addNotePad(NotePadModel notePad) throws DataAccessException {
        String sql
                = "INSERT INTO "
                + DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME
                + "(" + DBStructs.H_NOTEPAD_ENTRIES.MSISDN
                + "," + DBStructs.H_NOTEPAD_ENTRIES.MSISDN_MOD_X
                + "," + DBStructs.H_NOTEPAD_ENTRIES.USER_ID + ","
                + DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_ENTRY + ","
                + DBStructs.H_NOTEPAD_ENTRIES.NOTEPAD_USERNAME
                + ") " + "VALUES (?,?,?,?,?)";
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
                notePad.getNotepadEntry(), notePad.getUserName()) != 0;
    }

    public boolean deleteNotePadEntries(String msisdn, String activePartition, String otherMSISDN) throws DataAccessException {
        CCATLogger.DEBUG_LOGGER.debug("Starting NotePadDAO - deleteNotePadEntries");
        String sqlStatement = "DELETE FROM "
                + DBStructs.H_NOTEPAD_ENTRIES.TABLE_NAME
                + " PARTITION (" + activePartition
                + ") WHERE "
                + DBStructs.H_NOTEPAD_ENTRIES.MSISDN
                + " IN ( '"
                + msisdn
                + "','"
                + otherMSISDN + "')";
        CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + sqlStatement);
        CCATLogger.DEBUG_LOGGER.debug("Ending NotePadDAO - deleteNotePadEntries");
        return jdbcTemplate.update(sqlStatement) != 0;
    }
}
