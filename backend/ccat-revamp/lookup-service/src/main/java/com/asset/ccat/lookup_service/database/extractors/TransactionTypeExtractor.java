/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.LkTransactionType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class TransactionTypeExtractor implements ResultSetExtractor<HashMap<Integer, List<LkTransactionType>>> {

    @Override
    public HashMap<Integer, List<LkTransactionType>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, List<LkTransactionType>> map = new HashMap<>();
        while (resultSet.next()) {
            Integer feature = resultSet.getInt(DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID);
            if (map.get(feature) == null) {
                ArrayList<LkTransactionType> aDMTransactionTypes = new ArrayList();
                LkTransactionType aDMTransactionType = new LkTransactionType();
                aDMTransactionType.setId(resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID));
                aDMTransactionType.setName(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                aDMTransactionType.setValue(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.VALUE));
                aDMTransactionType.setIsDefault(resultSet.getBoolean(DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT));
                aDMTransactionTypes.add(aDMTransactionType);
                map.put(feature, aDMTransactionTypes);
            } else {
                ArrayList<LkTransactionType> aDMTransactionTypes = (ArrayList<LkTransactionType>) map.get(feature);
                LkTransactionType aDMTransactionType = new LkTransactionType();
                aDMTransactionType.setId(resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID));
                aDMTransactionType.setName(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                aDMTransactionType.setValue(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.VALUE));
                aDMTransactionType.setIsDefault(resultSet.getBoolean(DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT));
                aDMTransactionTypes.add(aDMTransactionType);
            }
        }

        return map;
    }

}
