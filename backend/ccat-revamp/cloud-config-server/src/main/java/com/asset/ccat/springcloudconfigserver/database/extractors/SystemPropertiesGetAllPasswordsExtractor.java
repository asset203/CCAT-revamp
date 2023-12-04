package com.asset.ccat.springcloudconfigserver.database.extractors;

import com.asset.ccat.springcloudconfigserver.defines.DatabaseStructs;
import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class SystemPropertiesGetAllPasswordsExtractor implements ResultSetExtractor<HashMap<String, SystemConfigurationModel>> {
    @Override
    public HashMap<String, SystemConfigurationModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, SystemConfigurationModel> systemConfigMap = new HashMap<>();
        String code;
        String value;
        Integer type;

        while (resultSet.next()) {
            code = resultSet.getString(DatabaseStructs.ADM_SYSTEM_PROPERTIES.CODE);
            value = resultSet.getString(DatabaseStructs.ADM_SYSTEM_PROPERTIES.VALUE);
            type = resultSet.getInt(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TYPE);
            //Type 4 for passwords
            if(type==4){
                systemConfigMap.put(code,new SystemConfigurationModel(code, value, type));
            }

        }

        return systemConfigMap;
    }
}
