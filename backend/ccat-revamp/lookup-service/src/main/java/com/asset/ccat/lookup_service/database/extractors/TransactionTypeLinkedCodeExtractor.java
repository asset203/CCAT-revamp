/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.TransactionCode;
import com.asset.ccat.lookup_service.models.TransactionTypeLinkedCode;
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
public class TransactionTypeLinkedCodeExtractor implements ResultSetExtractor<List<TransactionTypeLinkedCode>> {

    @Override
    public List<TransactionTypeLinkedCode> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, TransactionTypeLinkedCode> map = new HashMap<>();
        List<TransactionCode> features;
        while (resultSet.next()) {
            Integer id = resultSet.getInt(DatabaseStructs.ADM_TX_TYPES.ID);
            if (map.get(id) == null) {
                TransactionTypeLinkedCode transactionType = new TransactionTypeLinkedCode();
                transactionType.setId(id);
                transactionType.setName(resultSet.getString("TYPE_ID"));
                transactionType.setValue(resultSet.getString("TYPE_NAME"));
                //transactionType.setDescription(resultSet.getString("TYPE_DESCRIPTION"));
                transactionType.setDescription(resultSet.getString("TYPE_VALUE"));

                TransactionCode code = new TransactionCode();
                features = new ArrayList<>();

                code.setId(resultSet.getInt("CODE_ID"));
                code.setName(resultSet.getString("CODE_NAME"));
                code.setValue(resultSet.getString("CODE_VALUE"));

                features.add(code);
                transactionType.setCodes(features);
                map.put(id, transactionType);
            } else {
                TransactionTypeLinkedCode transactionType = map.get(id);
                TransactionCode codes = new TransactionCode();
                codes.setId(resultSet.getInt("CODE_ID"));
                codes.setName(resultSet.getString("CODE_NAME"));
                codes.setValue(resultSet.getString("CODE_VALUE"));
                transactionType.getCodes().add(codes);
            }
        }
        return new ArrayList<>(map.values());
    }

}
