package com.asset.ccat.dynamic_page.database.dao;

import com.asset.ccat.dynamic_page.annotation.LogExecutionTime;
import com.asset.ccat.dynamic_page.defines.DatabaseStructs;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.shared.LkFeatureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author assem.hassan
 */
@Repository
public class LKFeaturesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String insertFeatureQuery;
    private String updateFeatureLabelQuery;
    private String deleteFeatureQuery;

    @LogExecutionTime
    public Integer addLKFeature(LkFeatureModel lkFeatureModel) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started LKFeaturesDao - addLKFeature()");
        try {
            Integer featureId = getNextId();
            if (insertFeatureQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("INSERT INTO ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" (").append(DatabaseStructs.LK_FEATURES.ID).append(",")
                        .append(DatabaseStructs.LK_FEATURES.NAME).append(",")
                        .append(DatabaseStructs.LK_FEATURES.PAGE_NAME).append(",")
                        .append(DatabaseStructs.LK_FEATURES.TYPE).append(",")
                        .append(DatabaseStructs.LK_FEATURES.URL).append(",")
                        .append(DatabaseStructs.LK_FEATURES.LABEL).append(") ")
                        .append("VALUES (?, ?, ?, ?, ?, ?)");
                insertFeatureQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + insertFeatureQuery);
            jdbcTemplate.update(insertFeatureQuery, featureId,
                    lkFeatureModel.getName(), "MENU",
                    1, ("customer-care/dynamic-page/" + String.valueOf(featureId)),
                    lkFeatureModel.getLabel());
            CCATLogger.DEBUG_LOGGER.debug("Ending LKFeaturesDao - addLKFeature()");
            return featureId;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + insertFeatureQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + insertFeatureQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    private Integer getNextId() throws Exception {
        String query = "SELECT (MAX(" + DatabaseStructs.LK_FEATURES.ID + ") + 1) FROM " + DatabaseStructs.LK_FEATURES.TABLE_NAME;
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public boolean updateFeatureLabel(Integer featureId, String label) throws DynamicPageException {

        CCATLogger.DEBUG_LOGGER.debug("Started LKFeaturesDao - updateFeatureLabel()");
        try {
            if (updateFeatureLabelQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("UPDATE ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.LK_FEATURES.LABEL).append(" = ?")
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_FEATURES.ID).append(" = ?");
                updateFeatureLabelQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateFeatureLabelQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending LKFeaturesDao - updateFeatureLabel()");
            return jdbcTemplate.update(updateFeatureLabelQuery, label,
                    featureId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateFeatureLabelQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateFeatureLabelQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

    public boolean deleteFeature(Integer featureId) throws DynamicPageException {

        CCATLogger.DEBUG_LOGGER.debug("Started LKFeaturesDao - deleteFeature()");
        try {
            if (deleteFeatureQuery == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("DELETE FROM ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_FEATURES.ID).append(" = ?");
                deleteFeatureQuery = queryBuilder.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteFeatureQuery);
            CCATLogger.DEBUG_LOGGER.debug("Ending LKFeaturesDao - deleteFeature()");
            return jdbcTemplate.update(deleteFeatureQuery,
                    featureId) != 0;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteFeatureQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteFeatureQuery, e);
            throw new DynamicPageException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }

    }

}
