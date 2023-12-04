/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.models.BusinessPlanModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author wael.mohamed
 */
public class BusinessPlanRowMapper implements RowMapper<BusinessPlanModel> {

    @Override
    public BusinessPlanModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        BusinessPlanModel businessPlanModel = new BusinessPlanModel();
        businessPlanModel.setBusinessPlanId(rs.getInt("ID"));
        businessPlanModel.setBusinessPlanCode(rs.getInt("CODE"));
        businessPlanModel.setBusinessPlanName(rs.getString("NAME"));
        businessPlanModel.setServiceClassId(rs.getInt("SERVICE_CLASS_ID"));
        businessPlanModel.setHlrProfileId(rs.getInt("HLR_ID"));
        return businessPlanModel;

    }
}
