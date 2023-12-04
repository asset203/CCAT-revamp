/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author wael.mohamed
 */
public class DisconnectionCodeRowMapper implements RowMapper<DisconnectionCodeModel> {

    @Override
    public DisconnectionCodeModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        DisconnectionCodeModel codeModel = new DisconnectionCodeModel();
        codeModel.setId(rs.getInt("ID"));
        codeModel.setCode(rs.getInt("CODE_ID"));
        codeModel.setDescription(rs.getString("NAME"));
        return codeModel;

    }

}
