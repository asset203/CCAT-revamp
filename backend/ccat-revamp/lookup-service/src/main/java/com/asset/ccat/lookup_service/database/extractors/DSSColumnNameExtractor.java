/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class DSSColumnNameExtractor implements ResultSetExtractor<HashMap<String, HashMap<String, String>>> {

    @Override
    public HashMap<String, HashMap<String, String>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, HashMap<String, String>> dssPageColumnsMap = new HashMap<>();
        while (resultSet.next()) {
            String pageName = resultSet.getString(DatabaseStructs.DSS_COLUMNS.DSS_PAGE_NAME);
            String columnName = resultSet.getString(DatabaseStructs.DSS_COLUMNS.COLUMN_NAME);
            String displayName = resultSet.getString(DatabaseStructs.DSS_COLUMNS.DISPLAY_NAME);
            HashMap<String, String> columnsMap = new HashMap<>();
            columnsMap.put(columnName, displayName);
            if (!dssPageColumnsMap.containsKey(pageName)) {
                dssPageColumnsMap.put(pageName, columnsMap);
            } else {
                dssPageColumnsMap.get(pageName).putAll(columnsMap);
            }
        }
        return dssPageColumnsMap;
    }

}
