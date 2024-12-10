package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.PageModel;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VIPDao {
    private final JdbcTemplate jdbcTemplate;

    public VIPDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getVIPSubscribers() throws LookupException {
        try {
            String sqlQuery = "SELECT " + DatabaseStructs.ADM_VIP_MSISDN.VIP_MSISDN + " FROM " +
                    DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME;
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.queryForList(sqlQuery, String.class);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    /**
     * SELECT  m.MENU_ID, m.LABEL
     * FROM LK_MENUS m
     * WHERE NOT EXISTS (
     * SELECT 1
     * FROM ADM_VIP_PAGES p
     * WHERE m.MENU_ID = p.PAGE_ID)
     **/
    public List<PageModel> getVIPPagesList() throws LookupException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT m.")
                    .append(DatabaseStructs.LK_MENUS.MENU_ID)
                    .append(", m.")
                    .append(DatabaseStructs.LK_MENUS.LABEL)
                    .append(" FROM ")
                    .append(DatabaseStructs.LK_MENUS.TABLE_NAME)
                    .append(" m WHERE NOT EXISTS ( SELECT 1 FROM ")
                    .append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME).append(" p WHERE m.")
                    .append(DatabaseStructs.LK_MENUS.MENU_ID).append(" = p.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(")");

            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.query(sqlQuery.toString(), new BeanPropertyRowMapper<>(PageModel.class));
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public int addVIPMsisdn(String msisdn) throws LookupException {
        try {
            String sqlQuery = "INSERT INTO " + DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME + " VALUES(?) ";
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.update(sqlQuery, msisdn);
        } catch (DuplicateKeyException ex){
            throw new LookupException(ErrorCodes.ERROR.ALREADY_EXIST, Defines.SEVERITY.VALIDATION, "msisdn");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while retrieving VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public int deleteVIPMsisdn(String msisdn) throws LookupException {
        try {
            String sqlQuery = "DELETE FROM " + DatabaseStructs.ADM_VIP_MSISDN.TABLE_NAME +
                    " WHERE " + DatabaseStructs.ADM_VIP_MSISDN.VIP_MSISDN + " = ?";
            CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", sqlQuery);
            return jdbcTemplate.update(sqlQuery, msisdn);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while Deletion of a VIP_MSISDN. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while Deletion of a VIP_MSISDN. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    /**
     * This Query inserts new page record if the record does not exist ||
     * MERGE INTO ADM_VIP_PAGES target
     * USING (SELECT ? AS FEATURE_ID, ? AS PAGE_ID FROM DUAL) SOURCE
     * ON (source.PAGE_ID = target.PAGE_ID AND SOURCE.FEATURE_ID = target.FEATURE_ID)
     * WHEN NOT MATCHED THEN
     * INSERT (FEATURE_ID, PAGE_ID) VALUES (source.FEATURE_ID, source.PAGE_ID)
     */
    public void syncVIPPages(Map<Integer, List<PageModel>> pagesMap) throws LookupException {
        try {
            StringBuilder sqlQuery = new StringBuilder("MERGE INTO ");
            sqlQuery.append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME)
                    .append(" target USING ( SELECT ? AS ")
                    .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(", ? AS ")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID)
                    .append(" FROM DUAL) source ON (source.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(" = target.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(" AND source.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(" = target.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID)
                    .append(") WHEN NOT MATCHED THEN INSERT (")
                    .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(", ")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(") VALUES (source.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(", source.")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID).append(")");
            CCATLogger.DEBUG_LOGGER.debug("Inserting the remaining pages and features --> {}", sqlQuery);

            List<Object[]> batchArgs = new ArrayList<>();
            for (Map.Entry<Integer, List<PageModel>> entry : pagesMap.entrySet()) {
                Integer pageId = entry.getKey();
                List<PageModel> featureList = entry.getValue();
                for (PageModel feature : featureList) {
                    batchArgs.add(new Object[]{feature.getFeatureId(), pageId});
                }
            }

            int[] affectedRows = jdbcTemplate.batchUpdate(sqlQuery.toString(), batchArgs);
            CCATLogger.DEBUG_LOGGER.debug("affected rows = {}", affectedRows);
        } catch (DataIntegrityViolationException ex) {
            CCATLogger.DEBUG_LOGGER.error("DataIntegrityViolationException while syncing VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.PAGE_IS_NOT_EXIST);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while syncing VIP_PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while syncing VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    public void deletePagesExcludingIds(List<Integer> pageIds) throws LookupException {
        StringBuilder sqlQuery = new StringBuilder("DELETE FROM ");
        sqlQuery.append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME);
        if(!pageIds.isEmpty())
            sqlQuery.append(" WHERE ")
                    .append(DatabaseStructs.ADM_VIP_PAGES.PAGE_ID)
                    .append(" NOT IN (:pages)");
        CCATLogger.DEBUG_LOGGER.debug("Delete pages that are not in pagesList --> {}", sqlQuery);
        Map<String, List<Integer>> params = Collections.singletonMap("pages", pageIds);
        try {
            NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(
                    Objects.requireNonNull(jdbcTemplate.getDataSource()));
            int deletedRows = namedJdbcTemplate.update(sqlQuery.toString(), params);
            CCATLogger.DEBUG_LOGGER.debug("Number of deletedRows = {}", deletedRows);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception while deleting VIP_PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while deleting VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }

    /**
     * SELECT URL,
     * 	CASE WHEN
     * 		EXISTS ( SELECT 1 FROM ADM_VIP_PAGES p WHERE p.FEATURE_ID = f.ID)
     * 	THEN 1
     * 	ELSE 0
     * 	END AS IS_VIP_PAGE
     * FROM LK_FEATURES f
     * WHERE
     * 	MENU_ID IS NOT NULL;
     * */
    public Map<String, Boolean> getFeatureVIPMap() throws LookupException {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT f.")
                .append(DatabaseStructs.LK_FEATURES.URL).append(", ")
                .append(" CASE WHEN EXISTS ( SELECT 1 FROM ")
                .append(DatabaseStructs.ADM_VIP_PAGES.TABLE_NAME).append(" p ")
                .append(" WHERE p.")
                .append(DatabaseStructs.ADM_VIP_PAGES.FEATURE_ID).append(" = f.")
                .append(DatabaseStructs.LK_FEATURES.ID)
                .append(") THEN 1 ELSE 0 END AS IS_VIP_PAGE FROM ")
                .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                .append(" f WHERE f.")
                .append(DatabaseStructs.LK_FEATURES.MENU_ID).append(" IS NOT NULL AND f.")
                .append(DatabaseStructs.LK_FEATURES.URL).append(" IS NOT NULL");
        try {
            return jdbcTemplate.query(sqlQuery.toString(), rs -> {
                Map<String, Boolean> result = new HashMap<>();
                while (rs.next()) {
                    String url = rs.getString(DatabaseStructs.LK_FEATURES.URL);
                    Boolean isVIP = rs.getBoolean("IS_VIP_PAGE");
                    result.put(url, isVIP);
                }
                return result;
            });
        } catch (Exception ex){
            CCATLogger.DEBUG_LOGGER.error("Exception while Mapping features VIP_PAGES. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception while Mapping features VIP_PAGES. ", ex);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR);
        }
    }
}
