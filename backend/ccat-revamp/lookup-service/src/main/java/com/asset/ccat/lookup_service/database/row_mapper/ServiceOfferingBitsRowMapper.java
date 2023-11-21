package com.asset.ccat.lookup_service.database.row_mapper;


import com.asset.ccat.lookup_service.models.ServiceOfferingBitModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceOfferingBitsRowMapper implements RowMapper<ServiceOfferingBitModel> {

    @Override
    public ServiceOfferingBitModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        ServiceOfferingBitModel serviceOfferingBitsModel = new ServiceOfferingBitModel();
        serviceOfferingBitsModel.setBitName(rs.getString("BIT_NAME"));
        serviceOfferingBitsModel.setBitPosition(rs.getInt("BIT_POSITION"));

        return serviceOfferingBitsModel;

    }
}
