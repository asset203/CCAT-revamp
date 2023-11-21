package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.RestrictionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FafRestrictionsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RestrictionModel> retrieveFafWhiteList() {
        List<RestrictionModel> whiteList = null;
        String query = null;
        try {
            query = "SELECT * FROM " + DatabaseStructs.ADM_FAF_WHITE_RESTRICTIONS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.ADM_FAF_WHITE_RESTRICTIONS.IS_DELETED + " = 0"
                    + " Order By "
                    + DatabaseStructs.ADM_FAF_WHITE_RESTRICTIONS.DESCRIPTION
                    +" ASC ";
            whiteList = jdbcTemplate.query(query, new BeanPropertyRowMapper<RestrictionModel>(RestrictionModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving FAF white list" + query);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FAF white list " + query, ex);
        }
        return whiteList;
    }

    public List<RestrictionModel> retrieveFafBlackList() {
        List<RestrictionModel> whiteList = null;
        String query = null;
        try {
            query = "SELECT * FROM " + DatabaseStructs.ADM_FAF_BLACK_RESTRICTIONS.TABLE_NAME
                    + " WHERE " + DatabaseStructs.ADM_FAF_BLACK_RESTRICTIONS.IS_DELETED + " = 0"
                    + " Order By "
                    + DatabaseStructs.ADM_FAF_BLACK_RESTRICTIONS.DESCRIPTION
                    +" ASC ";
            whiteList = jdbcTemplate.query(query, new BeanPropertyRowMapper<RestrictionModel>(RestrictionModel.class));
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving FAF black list" + query);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving FAF black list " + query, ex);
        }
        return whiteList;
    }
}
