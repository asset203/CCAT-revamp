package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AccountGroupBitsWithDescExtractor implements ResultSetExtractor<List<AccountGroupBitDescModel>> {
    @Override
    public List<AccountGroupBitDescModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        LinkedHashMap<Integer, AccountGroupBitDescModel> bitsMap = new LinkedHashMap<>();
        Integer bitId = -1;
        Integer serviceClassId = -1;
        while (resultSet.next()) {
            bitId = resultSet.getInt(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_POSITION);
            if (!bitsMap.containsKey(bitId)) {
                AccountGroupBitDescModel model = new AccountGroupBitDescModel();
                model.setBitPosition(bitId);
                model.setBitName(resultSet.getString(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_NAME));
                model.setServiceClassBitDescriptions(new HashMap<>());
                bitsMap.put(bitId, model);
            }

            serviceClassId = resultSet.getInt(DatabaseStructs.ADM_AG_BITS_SC_DESCRIPTION.SERVICE_CLASS_ID);
            if (!serviceClassId.equals(0)) {
                bitsMap.get(bitId).getServiceClassBitDescriptions()
                        .put(serviceClassId, resultSet.getString(DatabaseStructs.ADM_AG_BITS_SC_DESCRIPTION.DESCRIPTION));
            }

        }
        return new ArrayList<>(bitsMap.values());
    }
}
