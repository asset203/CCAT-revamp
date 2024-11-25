package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceOfferingPlanRowMapper implements RowMapper<ServiceOfferingPlan> {

    @Override
    public ServiceOfferingPlan mapRow(ResultSet rs, int rowNum) throws SQLException {

        ServiceOfferingPlan serviceOfferingPlan = new ServiceOfferingPlan();
        serviceOfferingPlan.setPlanId(rs.getInt("PLAN_ID"));
        serviceOfferingPlan.setName(rs.getString("NAME"));

        return serviceOfferingPlan;

    }
}
