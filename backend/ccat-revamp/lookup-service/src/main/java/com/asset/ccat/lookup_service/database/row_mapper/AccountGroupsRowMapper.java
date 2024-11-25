package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;

import com.asset.ccat.lookup_service.models.AccountGroupModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountGroupsRowMapper implements RowMapper<AccountGroupModel> {

    @Override
    public AccountGroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        AccountGroupModel accountGroupModel = new AccountGroupModel();
        accountGroupModel.setAccountGroupId(rs.getInt(DatabaseStructs.ADM_ACCOUNT_GROUPS.GROUP_ID));
        accountGroupModel.setAccountGroupDescription(rs.getString(DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME));

        return accountGroupModel;
    }
}
