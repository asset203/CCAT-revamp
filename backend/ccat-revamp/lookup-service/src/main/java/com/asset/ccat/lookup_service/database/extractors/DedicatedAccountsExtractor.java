package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class DedicatedAccountsExtractor implements ResultSetExtractor<HashMap<Integer, HashMap<Integer, DedicatedAccount>>> {
    @Override
    public HashMap<Integer, HashMap<Integer, DedicatedAccount>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, HashMap<Integer, DedicatedAccount>> serviceClassesDedicatedAccountsMap = new HashMap<>();
        HashMap<Integer, DedicatedAccount> dedicatedAccountsMap;
        Integer serviceClassId;
        Integer dedicatedAccountId;
        Float ratingFactor;
        String desc;

        while (resultSet.next()) {
            serviceClassId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID);
            if (serviceClassesDedicatedAccountsMap.get(serviceClassId) == null) {
                serviceClassesDedicatedAccountsMap.put(serviceClassId, new HashMap<>());
            }

            dedicatedAccountsMap = serviceClassesDedicatedAccountsMap.get(serviceClassId);
            dedicatedAccountId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
            ratingFactor = resultSet.getFloat(DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR);
            desc = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION);
            dedicatedAccountsMap.put(dedicatedAccountId, new DedicatedAccount(dedicatedAccountId, desc, ratingFactor));
        }
        return serviceClassesDedicatedAccountsMap;
    }
}
