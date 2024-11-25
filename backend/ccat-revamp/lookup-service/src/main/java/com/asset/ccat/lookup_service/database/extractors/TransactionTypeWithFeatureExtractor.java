/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.TransactionType;
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
 * @author wael.mohamed
 */
@Component
public class TransactionTypeWithFeatureExtractor implements ResultSetExtractor<List<TransactionType>> {

    @Override
    public List<TransactionType> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, TransactionType> map = new HashMap<>();
        List<Integer> features;
        while (resultSet.next()) {
            Integer id = resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID);
            if (map.get(id) == null) {
                TransactionType transactionType = new TransactionType();
                features = new ArrayList<>();
                transactionType.setId(id);
                transactionType.setName(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                transactionType.setValue(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.VALUE));
                transactionType.setDescription(resultSet.getString(DatabaseStructs.ADM_TX_TYPES.NAME));
                Integer ccFeature = resultSet.getInt(DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID);
                if (ccFeature > 0) {
                    features.add(ccFeature);
                }
                transactionType.setCcFeatures(features);
                map.put(id, transactionType);
            } else {
                TransactionType transactionType = map.get(id);
                Integer ccFeature = resultSet.getInt(DatabaseStructs.ADM_TX_FEATURE_TYPES.FEATURE_ID);
                if (ccFeature > 0) {
                    transactionType.getCcFeatures().add(ccFeature);
                }
            }
        }
        return new ArrayList<>(map.values());
    }

}
