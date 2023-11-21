package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.TxCodeRenewalValue;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TxCodesRenewalValueExtractor implements ResultSetExtractor<Map<Integer, TxCodeRenewalValue>> {
    @Override
    public Map<Integer, TxCodeRenewalValue> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, TxCodeRenewalValue> codesRenewalsValues = new HashMap<>();
        while (resultSet.next()) {
            Integer ID = resultSet.getInt(DatabaseStructs.ADM_TX_CODES_RENEWAL_VALUE.ID);
            Integer CODE_ID = resultSet.getInt(DatabaseStructs.ADM_TX_CODES_RENEWAL_VALUE.CODE_ID);
            Integer RENEWALS_VALUE = resultSet.getInt(DatabaseStructs.ADM_TX_CODES_RENEWAL_VALUE.RENEWALS_VALUE);
            codesRenewalsValues.put(CODE_ID,new TxCodeRenewalValue(ID, CODE_ID, RENEWALS_VALUE));
        }
        return codesRenewalsValues;
    }
}
