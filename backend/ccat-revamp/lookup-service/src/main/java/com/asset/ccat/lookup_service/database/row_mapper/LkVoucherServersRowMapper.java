package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.models.VoucherServerModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LkVoucherServersRowMapper implements RowMapper<VoucherServerModel> {
    @Override
    public VoucherServerModel mapRow(ResultSet rs, int i) throws SQLException {
        VoucherServerModel voucherServerModel = new VoucherServerModel();
        voucherServerModel.setUrl(rs.getString("URL"));
        voucherServerModel.setAgentName(rs.getString("AGENT_NAME"));
        voucherServerModel.setHost(rs.getString("HOST"));
        voucherServerModel.setAuthorization(rs.getString("AUTHORIZATION"));
        voucherServerModel.setServerIndex(rs.getInt("SERVER_INDEX"));
        voucherServerModel.setVoucherSerialLength(rs.getInt("VOUCHER_SERIAL_LENGTH"));

        return voucherServerModel;
    }
}
