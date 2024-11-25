/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.database.dao;

import com.asset.ccat.user_management.annotation.LogExecutionTime;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.users.MarqueeModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wael.mohamed
 */
@Repository
public class Es2alnyMarqueeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private String getAllMarqueesQuery;
    private String deleteMarqueeByIdQuery;
    private String addMarqueeQuery;
    private String deleteAllMarqueesQuery;
    private String deleteAllMarqueesInQuery;
    private String updateAllMarqueesInQuery;
    private String updateMarqueesQuery;

    @LogExecutionTime
    public List<MarqueeModel> getAllMarquees() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Starting Es2alnyMarqueeDAO - getAllMarquees");
        List<MarqueeModel> marquees = null;
        try {
            if (getAllMarqueesQuery == null) {
                getAllMarqueesQuery = "Select * FROM " + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " ORDER BY " + DatabaseStructs.ADM_MARQUEE_DATA.CREATION_DATE + " DESC";
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + getAllMarqueesQuery);
            marquees = jdbcTemplate.query(getAllMarqueesQuery, new BeanPropertyRowMapper<>(MarqueeModel.class));
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - getAllMarquees EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - getAllMarquees EXCEPTION -->", e);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending Es2alnyMarqueeDAO - getAllMarquees");
        return marquees;
    }

    @LogExecutionTime
    public boolean deleteMarqueeById(Integer marqueeId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Starting Es2alnyMarqueeDAO - deleteMarqueeById");
        boolean res;
        try {
            if (deleteMarqueeByIdQuery == null) {
                deleteMarqueeByIdQuery
                        = "DELETE FROM "
                        + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " WHERE "
                        + DatabaseStructs.ADM_MARQUEE_DATA.ID
                        + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteMarqueeByIdQuery);
            res = jdbcTemplate.update(deleteMarqueeByIdQuery, marqueeId) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - deleteMarqueeById EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - deleteMarqueeById EXCEPTION -->", e);
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending Es2alnyMarqueeDAO - deleteMarqueeById");
        return res;
    }

    @LogExecutionTime
    public boolean addMarquee(MarqueeModel marquee) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Starting Es2alnyMarqueeDAO - addMarquee");
        boolean isAdded;

        try {
            if (addMarqueeQuery == null) {
                addMarqueeQuery = "INSERT INTO "
                        + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " ("
                        + DatabaseStructs.ADM_MARQUEE_DATA.TITLE
                        + ", "
                        + DatabaseStructs.ADM_MARQUEE_DATA.DESCRIPTION
                        + ") " + "VALUES (?,?)";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + addMarqueeQuery);

            isAdded = jdbcTemplate.update(addMarqueeQuery,
                    marquee.getTitle(),
                    marquee.getDescription()) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new UserManagementException(ErrorCodes.ERROR.ADD_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending Es2alnyMarqueeDAO - addMarquee");
        return isAdded;
    }

    @LogExecutionTime
    public boolean updateMarquee(MarqueeModel marquee) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Starting Es2alnyMarqueeDAO - updateMarquee");
        boolean isAdded;

        try {
            if (updateMarqueesQuery == null) {
                updateMarqueesQuery
                        = "UPDATE " + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.ADM_MARQUEE_DATA.TITLE + " = ? , "
                        + DatabaseStructs.ADM_MARQUEE_DATA.DESCRIPTION + " = ?"
                        + " WHERE "
                        + DatabaseStructs.ADM_MARQUEE_DATA.ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("sqlStatement = " + updateMarqueesQuery);

            isAdded = jdbcTemplate.update(updateMarqueesQuery,
                    marquee.getTitle(),
                    marquee.getDescription(),
                    marquee.getId()) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION -->", e);
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending Es2alnyMarqueeDAO - updateMarquee");
        return isAdded;
    }

    @LogExecutionTime
    public boolean deleteAllMarquees() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Starting Es2alnyMarqueeDAO - deleteAllData");
        boolean res = false;
        try {
            if (deleteAllMarqueesQuery == null) {
                deleteAllMarqueesQuery = "DELETE FROM " + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME;
            }
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + deleteAllMarqueesQuery);
            res = jdbcTemplate.update(deleteAllMarqueesQuery) != 0;
        } catch (Exception e) {
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - deleteAllMarquees EXCEPTION -->", e);
            CCATLogger.ERROR_LOGGER.error("Es2alnyMarqueeDAO - deleteAllMarquees EXCEPTION -->", e);
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending Es2alnyMarqueeDAO - deleteAllMarquees");
        return res;
    }

    @LogExecutionTime
    public boolean deleteAllMarquees(List<Integer> marquees) throws UserManagementException {
        boolean isDeleted;
        try {
            if (deleteAllMarqueesInQuery == null) {
                deleteAllMarqueesInQuery = "DELETE FROM "
                        + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " WHERE ID IN ( :id )";
            }
            CCATLogger.DEBUG_LOGGER.debug("deleteAllMarqueesInQuery  = " + deleteAllMarqueesInQuery);
            Map namedParameters = Collections.singletonMap("id", marquees);
            isDeleted = namedJdbcTemplate.update(deleteAllMarqueesInQuery, namedParameters) != 0;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteAllMarqueesInQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteAllMarqueesInQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED);
        }
        return isDeleted;
    }

    @LogExecutionTime
    public boolean updateAllMarquees(List<MarqueeModel> marquees) throws UserManagementException {
        int[] rows;
        try {
            if (updateAllMarqueesInQuery == null) {
                updateAllMarqueesInQuery
                        = "UPDATE " + DatabaseStructs.ADM_MARQUEE_DATA.TABLE_NAME
                        + " SET "
                        + DatabaseStructs.ADM_MARQUEE_DATA.TITLE + " = ? , "
                        + DatabaseStructs.ADM_MARQUEE_DATA.DESCRIPTION + " = ?"
                        + " WHERE "
                        + DatabaseStructs.ADM_MARQUEE_DATA.ID + " = ?";
            }

            CCATLogger.DEBUG_LOGGER.debug("addEligibleServiceClassQuery  = " + updateAllMarqueesInQuery);
            rows = jdbcTemplate.batchUpdate(updateAllMarqueesInQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, marquees.get(i).getTitle());
                    ps.setString(2, marquees.get(i).getDescription());
                    ps.setInt(3, marquees.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return marquees.size();
                }
            });

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateAllMarqueesInQuery + "\n ex " + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateAllMarqueesInQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
        return rows.length > 0;
    }

}
