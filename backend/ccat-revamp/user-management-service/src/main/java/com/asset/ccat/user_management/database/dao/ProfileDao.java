package com.asset.ccat.user_management.database.dao;

import com.asset.ccat.user_management.annotation.LogExecutionTime;
import com.asset.ccat.user_management.database.extractors.*;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.profile.ProfileFeaturesModel;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import com.asset.ccat.user_management.models.shared.UserExtractModel;
import com.asset.ccat.user_management.models.users.MenuItem;
import com.asset.ccat.user_management.models.users.MenuModel;
import com.asset.ccat.user_management.models.shared.LkMonetaryLimitModel;
import com.asset.ccat.user_management.models.users.ProfileModel;
import com.asset.ccat.user_management.models.users.ServiceClassModel;
import com.asset.ccat.user_management.models.users.UserProfileModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class ProfileDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProfileMenusExtractor profileMenusExtractor;

    @Autowired
    private ProfilesExtractor profilesExtractor;

    @Autowired
    private ProfileUsersExtractor profileUsersExtractor;
    @Autowired
    private ProfileFeaturesExtractor profileFeaturesExtractor;

    @Autowired
    private ProfileExtractor profileExtractor;

    private final BeanPropertyRowMapper<ProfileModel> profileMapper = new BeanPropertyRowMapper<>(ProfileModel.class);
    private final BeanPropertyRowMapper<LkMonetaryLimitModel> monetaryLimitMapper = new BeanPropertyRowMapper<>(LkMonetaryLimitModel.class);
    private final BeanPropertyRowMapper<ServiceClassModel> serviceClassMapper = new BeanPropertyRowMapper<>(ServiceClassModel.class);
    private final BeanPropertyRowMapper<MenuItem> menuMapper = new BeanPropertyRowMapper<>(MenuItem.class);

    private String retrieveProfilesQuery;
    private String retrieveProfileQuery;
    private String retrieveAllProfilesWithFeaturesQuery;
    private String retrieveProfilesWithFeaturesQuery;
    private String retrieveUserProfileWithFeaturesQuery;
    private String retrieveProfileUsersWithProfileIdQuery;
    private String retrieveProfilesMenusQuery;
    private String retrieveUserProfileMenusQuery;
    private String retrieveProfileLimitsQuery;
    private String retrieveProfileServiceClassesQuery;
    private String retrieveProfileMenusQuery;

    private String insertProfileQuery;
    private String updateProfileQuery;
    private String deleteProfileQuery;
    private String findProfileByIdQuery;
    private String findProfileByNameQuery;
    private String addProfileMonetaryLimitsQuery;
    private String addProfileFeaturesQuery;
    private String addProfileServiceClassesQuery;
    private String addProfileMenusQuery;
    private String deleteProfileMonetaryLimitsQuery;
    private String deleteProfileFeaturesQuery;
    private String deleteProfileServiceClassesQuery;
    private String deleteProfileMenusQuery;


    @LogExecutionTime
    public List<ProfileModel> retrieveProfiles() throws UserManagementException {
        try {
            if (retrieveProfilesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append(" = 0")
                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_PROFILES.CREATION_DATE)
                        .append(" DESC ");
                retrieveProfilesQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfilesQuery, profileMapper);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveProfilesQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveProfilesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public ProfileModel retrieveProfileWithFeatures(Integer profileId) throws UserManagementException {
        try {
            if (retrieveProfileQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append(" = 0");
                retrieveProfileQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfileQuery, profileExtractor, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveProfileQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, UserProfileModel> retrieveProfilesWithFeatures() throws UserManagementException {
        try {
            if (retrieveProfilesWithFeaturesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_SOB.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_SOB.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_SOB.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID);
                retrieveProfilesWithFeaturesQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfilesWithFeaturesQuery, profilesExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveProfilesWithFeaturesQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveProfilesWithFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, UserProfileModel> retrieveUserProfile(Integer profileId) throws UserManagementException {
        try {
            if (retrieveUserProfileWithFeaturesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_SOB.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_SOB.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_SOB.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" = ?");
                retrieveUserProfileWithFeaturesQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveUserProfileWithFeaturesQuery, profilesExtractor, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while execute " + retrieveUserProfileWithFeaturesQuery);
            CCATLogger.ERROR_LOGGER.error("error while execute " + retrieveUserProfileWithFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, List<MenuModel>> retrieveProfilesMenus() throws UserManagementException {
        try {
            if (retrieveProfilesMenusQuery == null) {

                StringBuilder query = new StringBuilder("");
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.PAGE_NAME)
                        .append(" = ?")
                        .append(" OR ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.PAGE_NAME)
                        .append(" = ?")
                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID).append(" ASC, ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(".").append(DatabaseStructs.LK_FEATURES.TYPE).append(" ASC, ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.ORDERING).append(" ASC");

                retrieveProfilesMenusQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfilesMenusQuery, profileMenusExtractor, "MENU", "PARENT_MENU");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveProfilesMenusQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveProfilesMenusQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public HashMap<Integer, List<MenuModel>> retrieveUserProfileMenus(Integer profileId) throws UserManagementException {
        try {
            if (retrieveUserProfileMenusQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ? ")
                        .append("AND (")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.PAGE_NAME)
                        .append(" = ?")
                        .append(" OR ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.LK_FEATURES.PAGE_NAME)
                        .append(" = ? ) ")
                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID).append(" ASC, ")
                        .append(DatabaseStructs.LK_FEATURES.TABLE_NAME)
                        .append(".").append(DatabaseStructs.LK_FEATURES.TYPE).append(" ASC, ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.ORDERING).append(" ASC");

                retrieveUserProfileMenusQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveUserProfileMenusQuery, profileMenusExtractor, profileId, "MENU", "PARENT_MENU");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveUserProfileMenusQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveUserProfileMenusQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
    @LogExecutionTime
    public List<LkMonetaryLimitModel> retrieveProfileMonetaryLimits(Integer profileId) throws UserManagementException {
        try {
            if (retrieveProfileLimitsQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME)
                        .append(" INNER JOIN ").append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.LIMIT_ID)
                        .append(" = ")
                        .append(DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.PROFILE_ID)
                        .append(" = ?");

                retrieveProfileLimitsQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfileLimitsQuery, monetaryLimitMapper, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveProfileLimitsQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveProfileLimitsQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<ServiceClassModel> retrieveProfileServiceClasses(Integer profileId) throws UserManagementException {
        try {
            if (retrieveProfileServiceClassesQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME)
                        .append(" INNER JOIN ").append(DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.SERVICE_CLASS_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_SERVICE_CLASSES.CODE)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID)
                        .append(" = ?");

                retrieveProfileServiceClassesQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfileServiceClassesQuery, serviceClassMapper, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveProfileServiceClassesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveProfileServiceClassesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    //    @LogExecutionTime
//    public List<MenuItem> retrieveProfileMenus(Integer profileId) throws UserManagementException {
//        try {
//            if (retrieveProfileMenusQuery == null) {
//                StringBuilder query = new StringBuilder("");
//                query.append("SELECT *")
//                        .append(" FROM ")
//                        .append(DatabaseStructs.ADM_PROFILE_MENUS.TABLE_NAME)
//                        .append(" INNER JOIN ").append(DatabaseStructs.LK_MENUS.TABLE_NAME)
//                        .append(" ON ")
//                        .append(DatabaseStructs.ADM_PROFILE_MENUS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MENUS.MENU_ID)
//                        .append(" = ")
//                        .append(DatabaseStructs.LK_MENUS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MENUS.MENU_ID)
//                        .append(" WHERE ")
//                        .append(DatabaseStructs.ADM_PROFILE_MENUS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_MENUS.PROFILE_ID)
//                        .append(" = ?")
//                        .append(" ORDER BY ")
//                        .append(DatabaseStructs.LK_MENUS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MENUS.PARENT_ID).append(" ASC, ")
//                        .append(DatabaseStructs.LK_MENUS.TABLE_NAME).append(".").append(DatabaseStructs.LK_MENUS.ORDERING).append(" ASC");
//
//                retrieveProfileMenusQuery = query.toString();
//            }
//            return jdbcTemplate.query(retrieveProfileMenusQuery, menuMapper, profileId);
//        } catch (Exception ex) {
//            CCATLogger.debugError("error while executing: " + retrieveProfileMenusQuery);
//            CCATLogger.error("error while executing: " + retrieveProfileMenusQuery, ex);
//            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//    }
    @LogExecutionTime
    public Integer insertProfile(ProfileModel profile) throws UserManagementException {
        try {
            if (insertProfileQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("INSERT INTO ").append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append("(")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(",")
                        .append(DatabaseStructs.ADM_PROFILES.CREATION_DATE).append(",")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME).append(",")
                        .append(DatabaseStructs.ADM_PROFILES.SESSION_LIMIT)
                        .append(") VALUES (").append(DatabaseStructs.ADM_PROFILES.SEQUENCE_NAME).append(".nextval, sysdate , ?,?)");

                insertProfileQuery = query.toString();
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(
                        insertProfileQuery, new String[]{DatabaseStructs.ADM_PROFILES.PROFILE_ID});
                ps.setString(1, profile.getProfileName());
                ps.setInt(2, profile.getSessionLimit());
                return ps;
            }, keyHolder);
            Integer profileId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            profile.setProfileId(profileId);
            return profileId;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + insertProfileQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + insertProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int updateProfile(ProfileModel profile) throws UserManagementException {

        try {
            if (updateProfileQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME).append(" = ?,")
                        .append(DatabaseStructs.ADM_PROFILES.SESSION_LIMIT).append(" = ?")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append("= 0");

                updateProfileQuery = query.toString();
            }

            return jdbcTemplate.update(updateProfileQuery,
                    profile.getProfileName(),
                    profile.getSessionLimit(),
                    profile.getProfileId());

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + updateProfileQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + updateProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int[] addProfileMonetaryLimits(Integer profileId, List<LkMonetaryLimitModel> limits) throws UserManagementException {

        if (addProfileMonetaryLimitsQuery == null) {
            StringBuilder query = new StringBuilder("");
            query.append("INSERT INTO ").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME)
                    .append("( ")
                    .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.PROFILE_ID).append(",")
                    .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.LIMIT_ID).append(",")
                    .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.VALUE).append(" )")
                    .append("VALUES (?, ?, ?)");

            addProfileMonetaryLimitsQuery = query.toString();
        }

        try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(addProfileMonetaryLimitsQuery)) {
            for (LkMonetaryLimitModel limit : limits) {
                int index = 1;

                pstmt.setInt(index++, profileId);
                pstmt.setInt(index++, limit.getLimitId());
                pstmt.setLong(index++, limit.getValue());

                pstmt.addBatch();
            }

            return pstmt.executeBatch();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProfileMonetaryLimitsQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProfileMonetaryLimitsQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int[] addProfileFeatures(Integer profileId, List<LkFeatureModel> features) throws UserManagementException {

        if (addProfileFeaturesQuery == null) {
            StringBuilder query = new StringBuilder("");
            query.append("INSERT INTO ").append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                    .append("( ")
                    .append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID).append(",")
                    .append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID).append(",")
                    .append(DatabaseStructs.ADM_PROFILE_FEATURES.ORDERING).append(" )")
                    .append("VALUES (?, ?, ?)");

            addProfileFeaturesQuery = query.toString();
        }

        try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(addProfileFeaturesQuery)) {
            for (LkFeatureModel feature : features) {
                int index = 1;

                pstmt.setInt(index++, profileId);
                pstmt.setInt(index++, feature.getId());
                pstmt.setObject(index++, feature.getOrder());

                pstmt.addBatch();
            }

            return pstmt.executeBatch();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProfileFeaturesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProfileFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int[] addProfileServiceClasses(Integer profileId, List<ServiceClassModel> serviceClasses) throws UserManagementException {

        if (addProfileServiceClassesQuery == null) {
            StringBuilder query = new StringBuilder("");
            query.append("INSERT INTO ").append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME)
                    .append("( ")
                    .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID).append(",")
                    .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.SERVICE_CLASS_ID).append(" )")
                    .append("VALUES (?, ?)");

            addProfileServiceClassesQuery = query.toString();
        }

        try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(addProfileServiceClassesQuery)) {
            for (ServiceClassModel serviceClassModel : serviceClasses) {
                int index = 1;

                pstmt.setInt(index++, profileId);
                pstmt.setInt(index++, serviceClassModel.getCode());

                pstmt.addBatch();
            }

            return pstmt.executeBatch();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + addProfileServiceClassesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + addProfileServiceClassesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteProfileMonetaryLimits(Integer profileId) throws UserManagementException {
        try {
            if (deleteProfileMonetaryLimitsQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ").append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_MONETARY_LIMIT.PROFILE_ID).append(" = ?");

                deleteProfileMonetaryLimitsQuery = query.toString();
            }

            return jdbcTemplate.update(deleteProfileMonetaryLimitsQuery, profileId);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteProfileMonetaryLimitsQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteProfileMonetaryLimitsQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteProfileFeatures(Integer profileId) throws UserManagementException {

        try {
            if (deleteProfileFeaturesQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ").append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID).append(" = ?");

                deleteProfileFeaturesQuery = query.toString();
            }

            return jdbcTemplate.update(deleteProfileFeaturesQuery, profileId);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteProfileFeaturesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteProfileFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteProfileServiceClasses(Integer profileId) throws UserManagementException {
        try {
            if (deleteProfileServiceClassesQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("DELETE FROM ").append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID).append(" = ?");

                deleteProfileServiceClassesQuery = query.toString();
            }

            return jdbcTemplate.update(deleteProfileServiceClassesQuery, profileId);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteProfileServiceClassesQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteProfileServiceClassesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public int deleteProfile(Integer profileId) throws UserManagementException {

        try {
            if (deleteProfileQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("UPDATE ").append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" SET ")
                        .append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append(" = 1")
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(" = ?");

                deleteProfileQuery = query.toString();
            }

            return jdbcTemplate.update(deleteProfileQuery,
                    profileId);

        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + deleteProfileQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + deleteProfileQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer findProfileById(Integer profileId) throws UserManagementException {
        try {
            if (findProfileByIdQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT COUNT(1) FROM ").append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append("= 0");
                findProfileByIdQuery = query.toString();
            }
            return jdbcTemplate.queryForObject(findProfileByIdQuery, Integer.class, profileId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findProfileByIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findProfileByIdQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public Integer findProfileByName(String profileName) throws UserManagementException {
        try {
            if (findProfileByNameQuery == null) {
                StringBuilder query = new StringBuilder("");
                query.append("SELECT ").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID).append(" FROM ").append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME).append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append("= 0");
                findProfileByNameQuery = query.toString();
            }
            return jdbcTemplate.queryForObject(findProfileByNameQuery, Integer.class, profileName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing: " + findProfileByNameQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing: " + findProfileByNameQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<UserExtractModel> retrieveProfileUsers(Long profileId) throws UserManagementException {
        try {
            if (retrieveProfileUsersWithProfileIdQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT *")
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.PROFILE_ID)
                        .append(" = ?")
                        .append(" AND ")
                        .append(DatabaseStructs.ADM_USERS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_USERS.IS_DELETED)
                        .append(" = 0 ");
                retrieveProfileUsersWithProfileIdQuery = query.toString();
            }
            return jdbcTemplate.query(retrieveProfileUsersWithProfileIdQuery, profileUsersExtractor, profileId);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while executing " + retrieveProfileUsersWithProfileIdQuery);
            CCATLogger.ERROR_LOGGER.error("error while executing " + retrieveProfileUsersWithProfileIdQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public List<ProfileFeaturesModel> retrieveAllProfilesWithFeatures() throws UserManagementException {
        try {
            if (retrieveAllProfilesWithFeaturesQuery == null) {
                StringBuilder query = new StringBuilder();
                query.append("SELECT ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" , ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_NAME)
                        .append(" , ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.FEATURE_ID)
                        .append(" FROM ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(" LEFT JOIN ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_PROFILE_FEATURES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILE_FEATURES.PROFILE_ID)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" WHERE ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME).append(".").append(DatabaseStructs.ADM_PROFILES.IS_DELETED).append(" = 0")
                        .append(" ORDER BY ")
                        .append(DatabaseStructs.ADM_PROFILES.TABLE_NAME)
                        .append(".")
                        .append(DatabaseStructs.ADM_PROFILES.PROFILE_ID)
                        .append(" ASC ");
                retrieveAllProfilesWithFeaturesQuery = query.toString();
                CCATLogger.DEBUG_LOGGER.info("Executing " + retrieveAllProfilesWithFeaturesQuery);

            }
            return jdbcTemplate.query(retrieveAllProfilesWithFeaturesQuery, profileFeaturesExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("error while Executing" + retrieveAllProfilesWithFeaturesQuery);
            CCATLogger.ERROR_LOGGER.error("error while Executing " + retrieveAllProfilesWithFeaturesQuery, ex);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }


//    @LogExecutionTime
//    public int[] addProfileMenus(Integer profileId, List<MenuItem> menus) throws UserManagementException {
//
//        if (addProfileMenusQuery == null) {
//            StringBuilder query = new StringBuilder("");
//            query.append("INSERT INTO ").append(DatabaseStructs.ADM_PROFILE_MENUS.TABLE_NAME)
//                    .append("( ")
//                    .append(DatabaseStructs.ADM_PROFILE_MENUS.PROFILE_ID).append(",")
//                    .append(DatabaseStructs.ADM_PROFILE_MENUS.MENU_ID).append(" )")
//                    .append("VALUES (?, ?)");
//
//            addProfileMenusQuery = query.toString();
//        }
//
//        try (PreparedStatement pstmt = jdbcTemplate.getDataSource().getConnection().prepareStatement(addProfileMenusQuery)) {
//            for (MenuItem menu : menus) {
//                int index = 1;
//
//                pstmt.setInt(index++, profileId);
//                pstmt.setInt(index++, menu.getMenuId());
//
//                pstmt.addBatch();
//            }
//
//            return pstmt.executeBatch();
//        } catch (Exception ex) {
//            CCATLogger.debugError("error while executing: " + addProfileMenusQuery);
//            CCATLogger.error("error while executing: " + addProfileMenusQuery, ex);
//            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//    }
//
//    @LogExecutionTime
//    public int deleteProfileMenus(Integer profileId) throws UserManagementException {
//        try {
//            if (deleteProfileMenusQuery == null) {
//                StringBuilder query = new StringBuilder("");
//                query.append("DELETE FROM ").append(DatabaseStructs.ADM_PROFILE_MENUS.TABLE_NAME)
//                        .append(" WHERE ")
//                        .append(DatabaseStructs.ADM_PROFILE_MENUS.PROFILE_ID).append(" = ?");
//
//                deleteProfileMenusQuery = query.toString();
//            }
//
//            return jdbcTemplate.update(deleteProfileMenusQuery, profileId);
//
//        } catch (Exception ex) {
//            CCATLogger.debugError("error while executing: " + deleteProfileMenusQuery);
//            CCATLogger.error("error while executing: " + deleteProfileMenusQuery, ex);
//            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR);
//        }
//    }
}
