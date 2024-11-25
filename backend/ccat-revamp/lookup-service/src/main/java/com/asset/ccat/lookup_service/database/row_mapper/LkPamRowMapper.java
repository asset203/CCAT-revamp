/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.row_mapper;

import com.asset.ccat.lookup_service.constants.PamType;
import com.asset.ccat.lookup_service.models.LkPamModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author wael.mohamed
 */
public class LkPamRowMapper implements RowMapper<LkPamModel> {

    private final PamType pamTypeId;

    public LkPamRowMapper(PamType pamTypeId) {
        this.pamTypeId = pamTypeId;
    }

    @Override
    public LkPamModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        LkPamModel model = new LkPamModel();
        model.setId(rs.getInt("ID"));
        model.setDescription(rs.getString("NAME"));
        model.setPamTypeId(pamTypeId.getId());
        return model;

    }
}
