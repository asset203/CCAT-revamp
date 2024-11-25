/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.LkTransactionCode;
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
public class TransactionCodeExtractor implements ResultSetExtractor<HashMap<Integer, List<LkTransactionCode>>> {

    @Override
    public HashMap<Integer, List<LkTransactionCode>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, List<LkTransactionCode>> map = new HashMap<>();
        while (resultSet.next()) {
            Integer typeId = resultSet.getInt(DatabaseStructs.ADM_TX_LINKS.TX_TYPE_ID);
            if (map.get(typeId) == null) {
                ArrayList<LkTransactionCode> aDMTransactionCodes = new ArrayList();
                LkTransactionCode aDMTransactionCode = new LkTransactionCode();
                aDMTransactionCode.setId(resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID));
                aDMTransactionCode.setName(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                aDMTransactionCode.setValue(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.VALUE));
                aDMTransactionCode.setIsDefault(resultSet.getBoolean(DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT));
                aDMTransactionCodes.add(aDMTransactionCode);
                map.put(typeId, aDMTransactionCodes);
            } else {
                ArrayList<LkTransactionCode> aDMTransactionCodes = (ArrayList<LkTransactionCode>) map.get(typeId);
                LkTransactionCode aDMTransactionCode = new LkTransactionCode();
                aDMTransactionCode.setId(resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID));
                aDMTransactionCode.setName(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                aDMTransactionCode.setValue(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.VALUE));
                aDMTransactionCode.setIsDefault(resultSet.getBoolean(DatabaseStructs.ADM_TX_TYPES.IS_DEFAULT));
                aDMTransactionCodes.add(aDMTransactionCode);
            }
        }

        return map;
    }

}
