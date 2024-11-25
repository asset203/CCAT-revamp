package com.asset.ccat.user_management.database.dao;

import com.asset.ccat.user_management.annotation.LogExecutionTime;

import com.asset.ccat.user_management.configurations.constants.LkMonetaryLimits;
import com.asset.ccat.user_management.database.extractors.AllUsersProfilesExtractor;

import com.asset.ccat.user_management.database.extractors.ProfileLimitsExtractor;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.shared.UsersProfilesModel;
import com.asset.ccat.user_management.models.users.LkProfileLimit;
import com.asset.ccat.user_management.models.users.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UsersDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileLimitsExtractor limitsExtractor;
    @Autowired
    private AllUsersProfilesExtractor allUsersProfilesExtractor;
    private final BeanPropertyRowMapper<UserModel> userMapper = new BeanPropertyRowMapper<>(UserModel.class);

    private String retrieveUsersQuery;
    private String retrieveUsersByProfileId;
    private String retrieveUserByIdQuery;
    private String retrieveUserByNameQuery;
    private String retrieveDeletedUserByNameQuery;
    private String retrieveUserMonetaryLimitsQuery;
    private String insertUserQuery;
    private String deleteUserQuery;
    private String findUserQuery;
    private String resetUserLimitQuery;
    private String deleteUserLimitQuery;
    private String retrieveProfileLimitQuery;
    private String retrieveSumofDailyMonetaryLimitsQuery;
    private String updateDailyLimitQuery;
    private String extractAllUsersProfiles;

    @LogExecutionTime
    public List<UserModel> retrieveUsers() throws UserManagementException {
        try {
            if (retrieveUsersQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".*")
                        .append(", ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME)
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");
                retrieveUsersQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveUsersQuery, userMapper);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveUsersQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveUsersQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int insertUser(UserModel user) throws UserManagementException {
        try {
            if (insertUserQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ").append(DatabaseStructs.ADM_USERS.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_USERS.USER_ID).append(",")
                        .append(DatabaseStructs.ADM_USERS.NT_ACCOUNT).append(",")
                        .append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(") VALUES (").append(DatabaseStructs.ADM_USERS.SEQUENCE_NAME).append(".nextval,?,?)");

                insertUserQuery = query.toString();
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        insertUserQuery, new String[]{DatabaseStructs.ADM_USERS.USER_ID});
                ps.setString(1, user.getNtAccount());
                ps.setInt(2, user.getProfileId());
                return ps;
            }, keyHolder);
            Integer userId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            user.setUserId(userId);
            return userId;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + insertUserQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + insertUserQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public UserModel retrieveUserById(Integer userId) throws UserManagementException {

        try {
            if (retrieveUserByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.ADM_USERS.USER_ID)
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");
                retrieveUserByIdQuery = query.toString();
            }

            return jdbcTemplate.queryForObject(retrieveUserByIdQuery, userMapper, userId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUserByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUserByIdQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public UserModel retrieveUserByName(String ntAccount) throws UserManagementException {

        try {
            if (retrieveUserByNameQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE LOWER(").append(DatabaseStructs.ADM_USERS.NT_ACCOUNT).append(")")
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");
                retrieveUserByNameQuery = query.toString();
            }

            return jdbcTemplate.queryForObject(retrieveUserByNameQuery, userMapper, ntAccount.toLowerCase());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUserByNameQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUserByNameQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public UserModel retrieveUserDeletedByName(String ntAccount) throws UserManagementException {

        try {
            if (retrieveDeletedUserByNameQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE LOWER(").append(DatabaseStructs.ADM_USERS.NT_ACCOUNT).append(")")
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 1");
                retrieveDeletedUserByNameQuery = query.toString();
            }

            return jdbcTemplate.queryForObject(retrieveDeletedUserByNameQuery, userMapper, ntAccount);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUserByNameQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUserByNameQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


    @LogExecutionTime
    public List<Map<String, Object>> retrieveUserMonetaryLimits(Integer userId) throws UserManagementException {
        try {
            if (retrieveUserMonetaryLimitsQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT * FROM ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID).append(" = ?")
                        .append(" AND")
                        .append(" TRUNC(").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_DATE).append(") = TRUNC(SYSDATE)")
                        .append(" AND (").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).append(" = ? OR ")
                        .append(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).append(" = ?)");
                retrieveUserMonetaryLimitsQuery = query.toString();
            }

            return jdbcTemplate.queryForList(retrieveUserMonetaryLimitsQuery, userId,
                    LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id,
                    LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUserMonetaryLimitsQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUserMonetaryLimitsQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteUser(Integer userId) throws UserManagementException {
        try {
            if (deleteUserQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.ADM_USERS.IS_DELETED).append(" = 1")
                        .append(" WHERE ").append(DatabaseStructs.ADM_USERS.USER_ID).append(" = ?");
                deleteUserQuery = query.toString();
            }
            return jdbcTemplate.update(deleteUserQuery, userId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteUserQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteUserQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer findUser(Integer userId) throws UserManagementException {
        try {
            if (findUserQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(*) FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.ADM_USERS.USER_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");
                findUserQuery = query.toString();
            }
            return jdbcTemplate.queryForObject(findUserQuery, Integer.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findUserQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findUserQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int updateUser(UserModel user, Boolean resetSessionCounter) throws UserManagementException {
        String updateUserQuery = "";
        try {
            StringBuilder query = new StringBuilder("");
            query.append("UPDATE ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                    .append(" SET ").append(DatabaseStructs.ADM_USERS.NT_ACCOUNT).append(" = ?,")
                    .append(DatabaseStructs.ADM_USERS.PROFILE_ID).append(" = ?");
            if (resetSessionCounter != null && resetSessionCounter) {
                query.append(",").append(DatabaseStructs.ADM_USERS.SESSION_COUNTER).append(" = 0");
            }
            query.append(" WHERE ").append(DatabaseStructs.ADM_USERS.USER_ID).append(" = ?")
                    .append(" AND ")
                    .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");

            updateUserQuery = query.toString();

            return jdbcTemplate.update(updateUserQuery,
                    user.getNtAccount(),
                    user.getProfileId(),
                    user.getUserId());

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateUserQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateUserQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int updateDeletedUser(UserModel user, Boolean resetSessionCounter) throws UserManagementException {
        String updateDeletedUserQuery = "";
        try {
            StringBuilder query = new StringBuilder("");
            query.append("UPDATE ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                    .append(" SET ")
                    .append(DatabaseStructs.ADM_USERS.PROFILE_ID).append(" = ?,")
                    .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0 ");
            if (resetSessionCounter != null && resetSessionCounter) {
                query.append(",").append(DatabaseStructs.ADM_USERS.SESSION_COUNTER).append(" = 0");
            }

            updateDeletedUserQuery = query.toString();

            return jdbcTemplate.update(updateDeletedUserQuery,
                    user.getProfileId());

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateDeletedUserQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateDeletedUserQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int resetUserMonetaryLimit(Integer userId, int limitId) throws UserManagementException {
        try {
            if (resetUserLimitQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.TABLE_NAME)
                        .append(" SET ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE).append(" = 0")
                        .append(" WHERE ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID).append(" = ?")
                        .append(" AND")
                        .append(" TRUNC(").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_DATE).append(") = TRUNC(SYSDATE)")
                        .append(" AND ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).append(" = ?");
                resetUserLimitQuery = query.toString();
            }
            return jdbcTemplate.update(resetUserLimitQuery, userId, limitId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + resetUserLimitQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + resetUserLimitQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteUserMonetaryLimit(Integer userId, int limitId) throws UserManagementException {
        try {
            if (deleteUserLimitQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID).append(" = ?")
                        .append(" AND ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).append(" = ?");
                deleteUserLimitQuery = query.toString();
            }
            return jdbcTemplate.update(deleteUserLimitQuery, userId, limitId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteUserLimitQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteUserLimitQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer retrieveUsersByProfileId(Integer profileId) throws UserManagementException {
        try {
            if (retrieveUsersByProfileId == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(*) FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.ADM_USERS.PROFILE_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.IS_DELETED).append("= 0");
                retrieveUsersByProfileId = query.toString();
            }
            return jdbcTemplate.queryForObject(retrieveUsersByProfileId, Integer.class, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUsersByProfileId);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUsersByProfileId, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Float retrieveSumofDailyMonetaryLimits(Integer userId, Integer limitId) throws UserManagementException {
        try {
            if (retrieveSumofDailyMonetaryLimitsQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE)
                        .append(" FROM ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.TABLE_NAME)
                        .append(" WHERE ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID).append(" = ?")
                        .append(" AND")
                        .append(" TRUNC(").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_DATE).append(") = TRUNC(SYSDATE)")
                        .append(" AND ").append(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).append(" = ?");
                retrieveSumofDailyMonetaryLimitsQuery = query.toString();
            }

            Float result = jdbcTemplate.queryForObject(retrieveSumofDailyMonetaryLimitsQuery, Float.class, userId, limitId);
            return result == null ? 0f : result;
        } catch (EmptyResultDataAccessException ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSumofDailyMonetaryLimitsQuery + "\n" + ex);
            return 0f;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveSumofDailyMonetaryLimitsQuery + "\n" + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveSumofDailyMonetaryLimitsQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public HashMap<Integer, LkProfileLimit> retrieveProfileLimits(Integer userId) throws UserManagementException {
        try {
            if (retrieveProfileLimitQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ")
                        .append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_ID)
                        .append(" , ")
                        .append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_NAME)
                        .append(" , ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.VALUE)
                        .append(" FROM ").append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" LEFT JOIN  ").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.PROFILE_ID)
                        .append(" LEFT JOIN ").append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME)
                        .append(" ON ").append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.LIMIT_ID)
                        .append(" WHERE ").append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.USER_ID)
                        .append(" = ? ");
                retrieveProfileLimitQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfileLimitQuery, limitsExtractor, userId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUsersByProfileId + " \n" + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUsersByProfileId, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }

    }

    public int updateDailyLimit(Integer userId, Integer limitId, Float value) throws UserManagementException {
        try {
            if (updateDailyLimitQuery == null) {
                updateDailyLimitQuery = "MERGE INTO " + DatabaseStructs.TX_USER_MONETARY_TOTALS.TABLE_NAME + " t USING "
                        + " ( "
                        + " SELECT "
                        + " ? AS " + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID
                        + " , "
                        + " ? AS " + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID
                        + " , "
                        + " ? AS " + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE
                        + " FROM DUAL "
                        + " ) x ON (t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID + " = x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID
                        + " AND t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID + " = x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID
                        + " AND TRUNC(t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_DATE + ") = TRUNC(SYSDATE)) "
                        + " WHEN MATCHED THEN "
                        + " UPDATE SET t."
                        + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE
                        + " = t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE + " + x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE
                        + " WHERE t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID + " = x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID
                        + " And t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID + " = x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID
                        + " And TRUNC(t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_DATE + ") = TRUNC(SYSDATE) "
                        + " WHEN NOT MATCHED THEN "
                        + " INSERT (t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID
                        + ", t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID
                        + ", t." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE
                        + ") VALUES (x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.USER_ID
                        + ", x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID
                        + ", x." + DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE + ")";
            }

            return jdbcTemplate.update(updateDailyLimitQuery, userId, limitId, value);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateDailyLimitQuery + " \n" + ex);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateDailyLimitQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public ExtractAllUsersProfilesWrapper extractAllUsersProfiles() throws UserManagementException {
        try {
            if (extractAllUsersProfiles == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_USERS.NT_ACCOUNT)
                        .append(", ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_USERS.USER_ID)
                        .append(", ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(", ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME)
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.IS_DELETED).append(" = 0");
                extractAllUsersProfiles = query.toString();
                CCATLogger.DEBUG_LOGGER.debug("SQL-Query = {}", extractAllUsersProfiles);
            }
            return jdbcTemplate.query(extractAllUsersProfiles, allUsersProfilesExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting users' profiles. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting users' profiles. ", ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    public int checkUserPrivilege(Integer profileId, Integer featureId) throws UserManagementException {
        String query = "";
        try {
            query = "SELECT COUNT(*) FROM " + DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME
                    + " WHERE " + DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID + " = ?"
                    + " AND " + DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID + " = ?";
            return jdbcTemplate.queryForObject(query, Integer.class, profileId, featureId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + query);
            CCATLogger.ERROR_LOGGER.error("error while execute " + query, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
