package com.asset.ccat.lookup_service.database.row_mapper;


import com.asset.ccat.lookup_service.models.AccountGroupBitModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountGroupsBitsRowMapper  implements RowMapper<AccountGroupBitModel> {

    @Override
    public AccountGroupBitModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        AccountGroupBitModel accountGroupBitModel = new AccountGroupBitModel();
        accountGroupBitModel.setBitName(rs.getString("BIT_NAME"));
        accountGroupBitModel.setBitPosition(rs.getInt("BIT_POSITION"));

        return accountGroupBitModel;
    }
}
