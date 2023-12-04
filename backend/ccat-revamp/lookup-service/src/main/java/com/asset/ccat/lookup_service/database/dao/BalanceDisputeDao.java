package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.defines.DatabaseStructs.ADM_PROFILE_FEATURES;
import com.asset.ccat.lookup_service.defines.DatabaseStructs.LK_BD_DETAILS_CONFIGURATION;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.LkBalanceDisputeDetailsConfigModel;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BalanceDisputeDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BalanceDisputeDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> retrieveBDDetailsConfiguration(
      Integer profileId) {
    LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> result = null;
    StringBuilder sql = new StringBuilder();
    try {
      sql.append("SELECT * FROM ")
          .append(LK_BD_DETAILS_CONFIGURATION.TABLE_NAME)
          .append(" LEFT JOIN ")
          .append(ADM_PROFILE_FEATURES.TABLE_NAME)
          .append(" ON ")
          .append(LK_BD_DETAILS_CONFIGURATION.TABLE_NAME).append(".")
          .append(LK_BD_DETAILS_CONFIGURATION.PRIVILEGE_ID).append(" = ")
          .append(ADM_PROFILE_FEATURES.TABLE_NAME).append(".")
          .append(ADM_PROFILE_FEATURES.FEATURE_ID)
          .append(" WHERE ")
          .append(ADM_PROFILE_FEATURES.PROFILE_ID).append(" = ?")
          .append(" ORDER BY ")
          .append(LK_BD_DETAILS_CONFIGURATION.TABLE_NAME).append(".")
          .append(LK_BD_DETAILS_CONFIGURATION.COLUMN_ORDER);
      result = jdbcTemplate.query(sql.toString(), (ResultSet rs) -> {
        LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> map = new LinkedHashMap<>();
        while (rs.next()) {
          LkBalanceDisputeDetailsConfigModel temp = new LkBalanceDisputeDetailsConfigModel();
          temp.setColumnName(rs.getString(LK_BD_DETAILS_CONFIGURATION.COLUMN_NAME));
          temp.setDisplayName(rs.getString(LK_BD_DETAILS_CONFIGURATION.DISPLAY_NAME));
          temp.setColumnOrder(rs.getInt(LK_BD_DETAILS_CONFIGURATION.COLUMN_ORDER));
          temp.setPrivilegeId(rs.getInt(LK_BD_DETAILS_CONFIGURATION.PRIVILEGE_ID));
          map.put(temp.getColumnName(), temp);
        }
        return map;
      }, profileId);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving BDDetailsConfiguration" + sql);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving BDDetailsConfiguration " + sql, ex);
    }
    return result;
  }
}
