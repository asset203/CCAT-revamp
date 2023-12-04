/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors.migration;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author assem.hassan
 */
@Component
public class ServiceClassesMigrationExtractor implements ResultSetExtractor<MigrationModel> {

    private String tableName;

    @Override
    public MigrationModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        MigrationModel model = new MigrationModel();
        List<HashMap<String, String>> modelDataList = new ArrayList<>();
        model.setHeaders(new ArrayList<>());
        //set model headers
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            model.getHeaders().add(rsMetaData.getColumnName(i));
        }
        while (resultSet.next()) {
            HashMap<String, String> dataMap = new HashMap<>();
            for (int i = 0; i < count; i++) {
                String columnLabel = model.getHeaders().get(i);
                dataMap.put(columnLabel, String.valueOf(resultSet.getObject(columnLabel) == null ? "" : resultSet.getObject(columnLabel)));
            }
            modelDataList.add(dataMap);
        }
        model.setTableName(this.tableName);
        model.setData(modelDataList);
        model.setKeyIdentifier(DatabaseStructs.ADM_SERVICE_CLASSES.CODE);

        return model;
    }

    public String getTableName() {
        return tableName;
    }

    public ServiceClassesMigrationExtractor setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }
}
