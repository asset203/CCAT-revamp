package com.asset.ccat.data_migration_service.database.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GenericExtractor implements ResultSetExtractor<List<LinkedHashMap<String, Object>>> {

    @Override
    public List<LinkedHashMap<String, Object>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<LinkedHashMap<String, Object>> result = new ArrayList<>();
        ResultSetMetaData metadata = resultSet.getMetaData();
        while (resultSet.next()) {
            LinkedHashMap<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                String columnName = metadata.getColumnName(i);
                row.put(columnName, resultSet.getObject(columnName));
            }
            result.add(row);
        }

        return result;
    }
}
