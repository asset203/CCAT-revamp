package com.asset.ccat.springcloudconfigserver.database.extractors;

import com.asset.ccat.springcloudconfigserver.defines.DatabaseStructs;
import com.asset.ccat.springcloudconfigserver.models.SystemConfigurationModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class SystemPropertiesExtractor implements ResultSetExtractor<HashMap<String, List<SystemConfigurationModel>>> {
    @Override
    public HashMap<String, List<SystemConfigurationModel>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, List<SystemConfigurationModel>> systemConfigMap = new HashMap<>();
        String applicationName;
        String code;
        String value;
        Integer type;

        while (resultSet.next()) {
            applicationName = resultSet.getString(DatabaseStructs.ADM_SYSTEM_PROPERTIES.APPLICATION);
            if (systemConfigMap.get(applicationName) == null) {
                systemConfigMap.put(applicationName, new ArrayList<>());
            }
            code = resultSet.getString(DatabaseStructs.ADM_SYSTEM_PROPERTIES.CODE);
            value = resultSet.getString(DatabaseStructs.ADM_SYSTEM_PROPERTIES.VALUE);
            type = resultSet.getInt(DatabaseStructs.ADM_SYSTEM_PROPERTIES.TYPE);
            //Type 4 for passwords
            if(type==4){
                value=null;
            }
            systemConfigMap.get(applicationName).add(new SystemConfigurationModel(code, value, type));
        }

        return systemConfigMap;
    }
}
