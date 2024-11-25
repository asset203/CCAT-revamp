package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountGroupsWithBitsExtractor implements ResultSetExtractor<HashMap<Integer, AccountGroupWithBitsModel>> {

    private List<AccountGroupBitDescModel> bitsLookup;

    public AccountGroupsWithBitsExtractor(List<AccountGroupBitDescModel> bitsLookup) {
        this.bitsLookup = bitsLookup;
    }

    @Override
    public HashMap<Integer, AccountGroupWithBitsModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<Integer, AccountGroupWithBitsModel> map = new HashMap<>();
        Integer accountGroupId = -1;
        while (resultSet.next()) {
            accountGroupId = resultSet.getInt(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID);
            AccountGroupWithBitsModel model = new AccountGroupWithBitsModel();
            model.setId(accountGroupId);
            model.setName(resultSet.getString(DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME));
            model.setBits(new ArrayList<>());

            // generate bit list
            String[] idAsBinary = Integer.toBinaryString(accountGroupId).split("");

            for (int start = 0, end = idAsBinary.length; start < bitsLookup.size(); start++, end--) {
                AccountGroupBitDescModel bitModel = new AccountGroupBitDescModel();
                AccountGroupBitDescModel lookup = bitsLookup.get(start);
                bitModel.setBitName(lookup.getBitName());
                bitModel.setBitPosition(lookup.getBitPosition());
                bitModel.setServiceClassBitDescriptions(lookup.getServiceClassBitDescriptions());
                bitModel.setIsEnabled(end > 0 && idAsBinary[end - 1].equals("1") ? true : false);
                model.getBits().add(bitModel);
            }

            map.put(accountGroupId, model);
        }
        return map;
    }

}
