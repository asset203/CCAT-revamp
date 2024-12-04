package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.constants.FeatureType;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.PageModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PagesDao {
    private final JdbcTemplate jdbcTemplate;

    public PagesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT f.*,
     * CASE WHEN EXISTS (
     * SELECT 1 FROM ADM_VIP_PAGES p
     * WHERE p.PAGE_ID = f.MENU_ID
     * ) THEN 1 ELSE 0 END AS IS_VIP_PAGE
     * FROM LK_FEATURES f
     * WHERE
     * f.PAGE_NAME = 'MENU' AND
     * f."TYPE" = 1 AND f.MENU_ID IS NOT NULL
     * ORDER BY f.LABEL;
     */
    public List<PageModel> getAllCustomerCarePages() throws LookupException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT f.*, CASE WHEN EXISTS ( SELECT 1 FROM ")
                .append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME)
                .append(" p WHERE p.").append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID)
                .append(" = f.").append(DatabaseStructs.LK_FEATURES.MENU_ID)
                .append(") THEN 1 ELSE 0 END AS IS_VIP_PAGE FROM ")
                .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(" f ")
                .append(" WHERE f.")
                .append(DatabaseStructs.LK_FEATURES.PAGE_NAME).append("= ? AND f.")
                .append(DatabaseStructs.LK_FEATURES.TYPE).append(" = ? AND f.")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID).append(" IS NOT NULL")
                .append(" ORDER BY f.")
                .append(DatabaseStructs.LK_FEATURES.LABEL);
        CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
        try {
            return jdbcTemplate.query(sqlQuery.toString(), new BeanPropertyRowMapper<>(PageModel.class),
                    "MENU", FeatureType.CUSTOMER_CARE.getId());
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    /**
     * SELECT ID AS FEATURE_ID, MENU_ID
     * FROM LK_FEATURES
     * WHERE
     * MENU_ID IS NOT NULL;
     */
    public Map<Integer, List<Integer>> getMenusFeatures() throws LookupException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT ")
                .append(DatabaseStructs.LK_FEATURES.ID).append(" AS ")
                .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(", ")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID)
                .append(" FROM ")
                .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                .append(" WHERE ")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID)
                .append(" IS NOT NULL");
        try {
            return jdbcTemplate.query(sqlQuery.toString(), rs -> {
                Map<Integer, List<Integer>> result = new HashMap<>();
                while (rs.next()) {
                    Integer menuId = rs.getInt(DatabaseStructs.LK_FEATURES.MENU_ID);
                    Integer featureId = rs.getInt(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID);
                    result.computeIfAbsent(menuId, k -> new ArrayList<>()).add(featureId);
                }
                return result;
            });
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving Features. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving Features. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }


    /**
     * --This query retrieves all the pages in the application;
     * -- if the page has records in the VIP table vip flag should be 1 else 0 ||
     * SELECT
     * f.ID AS FEATURE_ID,
     * f.MENU_ID, f.URL, m.LABEL,
     * CASE WHEN EXISTS (
     * SELECT 1 FROM ADM_VIP_PAGES p
     * WHERE p.PAGE_ID  = f.MENU_ID
     * ) THEN 1
     * ELSE 0
     * END AS IS_VIP_PAGE
     * FROM LK_FEATURES f
     * INNER JOIN LK_MENUS m ON m.MENU_ID = f.MENU_ID
     * WHERE f.MENU_ID IS NOT NULL AND f."TYPE" <></> 2 -- (Customer-Care Pages)
     */
    public List<PageModel> getCustomerCarePages() throws LookupException {
        StringBuilder sqlQuery = new StringBuilder();

        sqlQuery.append("SELECT f.")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID).append(", f.")
                .append(DatabaseStructs.LK_FEATURES.ID).append(" AS ")
                .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(", f.")
                .append(DatabaseStructs.LK_FEATURES.URL).append(", m.")
                .append(DatabaseStructs.LK_MENUS.LABEL)

                .append(", CASE WHEN EXISTS ( SELECT 1 FROM ")
                .append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME).append(" p WHERE p.")
                .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(" = f.")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID)
                .append(" ) THEN 1 ELSE 0 END AS IS_VIP_PAGE FROM ")

                .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(" f ")
                .append(" INNER JOIN ")
                .append(DatabaseStructs.LK_MENUS.TABLE_NAME).append(" m ON m.")
                .append(DatabaseStructs.LK_MENUS.MENU_ID).append(" = f.")

                .append(DatabaseStructs.LK_FEATURES.MENU_ID).append(" WHERE f.")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID).append(" IS NOT NULL")
                .append(" AND \"TYPE\" <> 2");

        try {
            return jdbcTemplate.query(sqlQuery.toString(), new BeanPropertyRowMapper<>(PageModel.class));
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving Pages. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving Pages. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
