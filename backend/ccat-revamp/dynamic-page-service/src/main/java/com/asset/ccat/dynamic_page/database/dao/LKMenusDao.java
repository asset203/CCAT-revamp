package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class LKMenusDao {
    private final JdbcTemplate jdbcTemplate;

    public LKMenusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //INSERT INTO LK_MENUS (MENU_ID , LABEL, PARENT_ID, "ORDERING") VALUES(MENU_SEQ.nextVal, ?, 15, MENU_SEQ.nextVal);
    public int addMenuPage(String label) throws DynamicPageException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT INTO ")
                .append(DatabaseStructs.LK_MENUS.TABLE_NAME)
                .append("(")
                .append(DatabaseStructs.LK_MENUS.MENU_ID).append(", ")
                .append(DatabaseStructs.LK_MENUS.LABEL).append(",")
                .append(DatabaseStructs.LK_MENUS.PARENT_ID).append(", ")
                .append(DatabaseStructs.LK_MENUS.ORDERING)
                .append(")")

                .append(" VALUES (")
                .append(DatabaseStructs.LK_MENUS.SEQ).append(".nextVal, ?, 15, ")
                .append(DatabaseStructs.LK_MENUS.SEQ).append(".nextVal)");
        try {
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), new String[] { DatabaseStructs.LK_MENUS.MENU_ID });
                ps.setString(1, label);
                return ps;
            }, keyHolder);

            return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("error while adding menu entry. ", ex );
            CCATLogger.ERROR_LOGGER.error("error while adding menu entry. ", ex );
            return -1;
        }
    }
}
