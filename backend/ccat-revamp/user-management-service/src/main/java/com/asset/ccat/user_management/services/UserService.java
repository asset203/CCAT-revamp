package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.configurations.constants.FileType;
import com.asset.ccat.user_management.configurations.constants.LkMonetaryLimits;
import com.asset.ccat.user_management.configurations.constants.OperationType;
import com.asset.ccat.user_management.database.dao.UsersDao;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.FilesException;
import com.asset.ccat.user_management.exceptions.LoginException;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.file.handler.UsersFileHandler;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.manager.UsersManager;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.responses.LoginResponse;
import com.asset.ccat.user_management.models.responses.user.GetAllUsersResponse;
import com.asset.ccat.user_management.models.responses.user.GetUserResponse;
import com.asset.ccat.user_management.models.users.UserProfileModel;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.security.JwtTokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UserService {

    @Autowired
    Properties properties;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsersDao usersDao;

    @Autowired
    ProfileService profileService;

    @Autowired
    LdapService ldapService;

    @Autowired
    UsersFileHandler usersFileHandler;


    @Autowired
    MessagesCache messageCache;

    public LoginResponse login(String name, String password, String machineName) throws UserManagementException {
        long startTime = System.currentTimeMillis();
        LoginResponse resp;
        if (Boolean.TRUE.equals(properties.getLdapAuthenticationFlag())) {
            CCATLogger.DEBUG_LOGGER.info("Start integration with LDAP with ntAccount[{}]", name);
            ldapService.authenticateUser(name, password);
            CCATLogger.DEBUG_LOGGER.info("Integration with LDAP done successfully in {} ms.", (System.currentTimeMillis() - startTime));
        }
//        CCATLogger.DEBUG_LOGGER.info("Start retrieve model from cachedUsers");
        UserModel user; //= UsersManager.cachedUsers.get(name.toLowerCase());

        //if (user == null) {
        try {
//            CCATLogger.DEBUG_LOGGER.info("Start retrieve model from database");
            user = retrieveUserByName(name);
        } catch (UserManagementException ex) {
            throw new LoginException(ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD, Defines.SEVERITY.ERROR, "Invalid username or password.");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Failed to retrieve user from database");
            throw new UserManagementException(ErrorCodes.ERROR.UNKNOWN_ERROR, Defines.SEVERITY.ERROR);
        }
        //}

        CCATLogger.DEBUG_LOGGER.info("Start generate token for user = {}", user);
        String authToken = generateToken(user, machineName);

        resp = new LoginResponse();
        resp.setUser(user);
        resp.setToken(authToken);
        resp.setUserProfile(user.getProfileModel());
        resp.setSessionId(String.valueOf(jwtTokenUtil.extractDataFromToken(authToken).get(Defines.SecurityKeywords.SESSION_ID)));
        return resp;
    }

    private String generateToken(UserModel userModel, String machineName) throws UserManagementException {
        final String token = jwtTokenUtil.generateToken(userModel, machineName);
        return token;
    }

    public HashMap<String, UserModel> retrieveUsersWithDetails() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all users with details");
        HashMap<String, UserModel> usersMap = new HashMap<>();
        List<UserModel> users = usersDao.retrieveUsers();
        if (users == null || users.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No users were found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_USERS_FOUND, Defines.SEVERITY.FATAL);
        }
        CCATLogger.DEBUG_LOGGER.debug("Retrieving active users profiles");
        HashMap<Integer, UserProfileModel> profilesMap = profileService.retrieveAllUsersProfiles();

        users.forEach((u) -> {
            u.setProfileModel(profilesMap.get(u.getProfileId()));
            usersMap.put(u.getNtAccount(), u);
        });
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all users with details");
        return usersMap;
    }

    public GetAllUsersResponse retrieveUsers() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all users list");
        List<UserModel> users = usersDao.retrieveUsers();
        if (users == null || users.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No users were found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_USERS_FOUND, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all users list");
        return new GetAllUsersResponse(users);
    }

    public UserModel retrieveUserByName(String userName) throws UserManagementException {
        UserModel user = usersDao.retrieveUserByName(userName);
        if (user == null) {
            CCATLogger.DEBUG_LOGGER.warn("User not found");
            throw new LoginException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        UserProfileModel profile = profileService.retrieveUserProfile(user.getProfileId());
        user.setProfileModel(profile);
        user.setProfileName(profile.getProfileName());
        return user;
    }

    public Integer addUser(UserModel user) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding user [" + user.getNtAccount() + "]");
        Integer id = usersDao.insertUser(user);
        if (id == null || id.equals(0)) {
            CCATLogger.DEBUG_LOGGER.error("adding user [" + user.getNtAccount() + "] failed");
            throw new UserManagementException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "User");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding user [" + user.getNtAccount() + "] with ID [" + id + "}");
        return id;
    }

    public int deleteUser(Integer userId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting user with ID[" + userId + "]");
        int deleted = usersDao.deleteUser(userId);
        if (deleted <= 0) {
            CCATLogger.DEBUG_LOGGER.error("deleting user with id [" + userId + "] failed");
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "User");
        }

        CCATLogger.DEBUG_LOGGER.debug("Start deleting user debit limit");
        usersDao.deleteUserMonetaryLimit(userId, LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id);

        CCATLogger.DEBUG_LOGGER.debug("Start deleting user rebate limit");
        usersDao.deleteUserMonetaryLimit(userId, LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id);

        CCATLogger.DEBUG_LOGGER.debug("Done deleting user with ID [" + userId + "}");
        return deleted;

    }

    public GetUserResponse retrieveUser(Integer userId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving user with ID[" + userId + "]");
        UserModel user = usersDao.retrieveUserById(userId);

        if (user == null) {
            CCATLogger.DEBUG_LOGGER.debug("User with ID [" + userId + "] was not found");
            throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.ERROR);
        }

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving user limits");
        List<Map<String, Object>> userMonetaryLimits = usersDao.retrieveUserMonetaryLimits(userId);

        for (Map<String, Object> userLimit : userMonetaryLimits) {

            if (userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID) != null
                    && (Integer.parseInt(userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).toString())) == LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id
                    && userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE) != null) {
                // set debit limit
                user.setDebitLimit(Long.parseLong(userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE).toString()));
            } else if (userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID) != null
                    && (Integer.parseInt(userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.LIMIT_ID).toString())) == LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id
                    && userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE) != null) {
                // set rebate limit
                user.setRebateLimit(Long.parseLong(userLimit.get(DatabaseStructs.TX_USER_MONETARY_TOTALS.CURR_VALUE).toString()));
            }
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving user with ID[" + userId + "]");
        return new GetUserResponse(user);
    }

    public void validateUserExistence(Integer userId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if user[" + userId + "] exists");
        if (userId == null || !this.isUserExists(userId)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating user Failed , User with ID [" + userId + "] does not exist");
            throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND, Defines.SEVERITY.VALIDATION);
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating if user exists successfully");
    }

    public Boolean isUserExists(Integer userId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if user with ID[" + userId + "] exists in DB");
        return usersDao.findUser(userId) > 0;
    }

    public void updateUser(UserModel user, Boolean resetDebit, Boolean resetRebate, Boolean resetSessionCounter) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating user with ID [" + user.getUserId() + "]");
        int result = usersDao.updateUser(user, resetSessionCounter);
        if (result <= 0) {
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "User");
        }
        if (resetDebit != null && resetDebit) {
            usersDao.resetUserMonetaryLimit(user.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id);
        }
        if (resetRebate != null && resetRebate) {
            usersDao.resetUserMonetaryLimit(user.getUserId(), LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating user with ID [" + user.getUserId() + "]");
    }

    public void updateDeletedUser(UserModel user, Boolean resetDebit, Boolean resetRebate, Boolean resetSessionCounter) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating user with ID [" + user.getUserId() + "]");
        int result = usersDao.updateDeletedUser(user, resetSessionCounter);
        if (result <= 0) {
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "User");
        }
        if (resetDebit != null && resetDebit) {
            usersDao.resetUserMonetaryLimit(user.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id);
        }
        if (resetRebate != null && resetRebate) {
            usersDao.resetUserMonetaryLimit(user.getUserId(), LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating user with ID [" + user.getUserId() + "]");
    }


    public Integer retrieveUsersByProfileId(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Retrieving users with profile ID [" + profileId + "]");
        return usersDao.retrieveUsersByProfileId(profileId);
    }

    public byte[] uploadUsers(MultipartFile file, String ext, String operationType) throws UserManagementException {
        // parsing file
        CCATLogger.DEBUG_LOGGER.debug("Start parsing file [" + file.getOriginalFilename() + "]");
        List<UserModel> users = usersFileHandler.handleFileParsing(file, ext, operationType.equals(OperationType.ADD_OR_UPDATE.id)); // set required all to true in case of add/update
        if (users == null || users.isEmpty()) {
            throw new UserManagementException(ErrorCodes.ERROR.EMPTY_FILE, Defines.SEVERITY.ERROR);
        }

        //check opertion type, 1>>addORUpdate 2>>Delete
        if (operationType.equals(OperationType.ADD_OR_UPDATE.id)) {
            addOrUpdateUsers(users);
        } else if (operationType.equals(OperationType.DELETE.id)) {
            deleteUsers(users);
        } else {
            CCATLogger.DEBUG_LOGGER.error("Unsupported operation type [" + operationType + "]");
            throw new UserManagementException(ErrorCodes.ERROR.UNSUPPORTED_OPERATION_TYPE, Defines.SEVERITY.ERROR);
        }

        //write back operation summary
        CCATLogger.DEBUG_LOGGER.debug("Start writing users summary");
        byte[] summaryContent = usersFileHandler.handleFileWriting(users, FileType.CSV.ext);
        return summaryContent;
    }

    public void addOrUpdateUsers(List<UserModel> users) throws UserManagementException {

        String status = "";
        String statusMessage = "";
        for (UserModel user : users) {
            // check if user was invalidated during parsing(invalid input)
            if (user.getStatus() != null || user.getStatusMessage() != null) {
                continue;
            }
            try {
                //validating user
                Integer profileId = profileService.retrieveProfileIdByName(user.getProfileName());
                if (profileId == null || profileId.equals(0)) {
                    throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND);
                }
                user.setProfileId(profileId);
                //check if user is uploaded for add or update
                CCATLogger.DEBUG_LOGGER.debug("Start retrieving user by name");
                UserModel retrievedUser = usersDao.retrieveUserByName(user.getNtAccount());

                if (retrievedUser == null) {
                    UserModel retrievedDeletedUser = usersDao.retrieveUserDeletedByName(user.getNtAccount());
                    if (retrievedDeletedUser != null) {
                        // update user
                        CCATLogger.DEBUG_LOGGER.debug("Start updating deleted user [" + user.getNtAccount() + "]");
                        user.setUserId(retrievedDeletedUser.getUserId());
                        updateDeletedUser(user, null, null, null);
                        status = "SUCCESS";
                        statusMessage = "updated user successfully.";
                    } else {
                        // add user
                        CCATLogger.DEBUG_LOGGER.debug("Start adding deleted user [" + user.getNtAccount() + "]");
                        Integer id = addUser(user);
                        status = "SUCCESS";
                        statusMessage = "Added user successfully with ID [" + id + "]";
                    }


                } else {
                    // update user
                    CCATLogger.DEBUG_LOGGER.debug("Start updating user [" + user.getNtAccount() + "]");
                    user.setUserId(retrievedUser.getUserId());
                    updateUser(user, null, null, null);
                    status = "SUCCESS";
                    statusMessage = "updated user successfully.";
                }
            } catch (UserManagementException ex) {
                CCATLogger.DEBUG_LOGGER.error("Failed to add/update user [" + user.getNtAccount() + "]");
                status = "FAILED";
                statusMessage = messageCache.getErrorMsg(ex.getErrorCode());
                if (ex.getArgs() != null) {
                    statusMessage = messageCache.replaceArgument(statusMessage, ex.getArgs());
                }
            } finally {
                user.setStatus(status);
                user.setStatusMessage(statusMessage);
            }

        }
    }

    public void deleteUsers(List<UserModel> users) throws UserManagementException {
        String status = "";
        String statusMessage = "";
        for (UserModel user : users) {
            status = "";
            statusMessage = "";

            // check if user was invalidated during parsing(invalid input)
            if (user.getStatus() != null || user.getStatusMessage() != null) {
                continue;
            }
            try {

                //check if user exits and retrieve userId
                CCATLogger.DEBUG_LOGGER.debug("Start retrieving user by name");
                UserModel retrievedUser = usersDao.retrieveUserByName(user.getNtAccount());

                if (retrievedUser == null) {
                    CCATLogger.DEBUG_LOGGER.error("User [" + user.getNtAccount() + "] does not exist ");
                    throw new UserManagementException(ErrorCodes.ERROR.USER_NOT_FOUND);
                }
                // deleting user
                CCATLogger.DEBUG_LOGGER.debug("Start deleting user [" + user.getNtAccount() + "]");
                deleteUser(retrievedUser.getUserId());
                status = "SUCCESS";
                statusMessage = "Deleted user successfully.";

            } catch (UserManagementException ex) {
                CCATLogger.DEBUG_LOGGER.error("Failed to Delete user [" + user.getNtAccount() + "]");
                status = "FAILED";
                statusMessage = messageCache.getErrorMsg(ex.getErrorCode());
                if (ex.getArgs() != null) {
                    statusMessage = messageCache.replaceArgument(statusMessage, ex.getArgs());
                }
            } finally {
                //setting summary
                user.setStatus(status);
                user.setStatusMessage(statusMessage);
            }
        }
    }

    public byte[] extractAllUsersProfiles() throws FilesException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start getting all users profiles");
            ExtractAllUsersProfilesWrapper res = usersDao.extractAllUsersProfiles();
            byte[] usersProfilesContent = usersFileHandler.handleFileWritingForUsersProfiles(res, FileType.CSV.ext);
            CCATLogger.DEBUG_LOGGER.debug("Receiving the users profiles file Successfully ");
            return usersProfilesContent;
        } catch (Exception ex){
            throw new FilesException();
        }

    }

    public boolean checkUserPrivilege(Integer profileId, Integer featureId) throws UserManagementException {
        int count = usersDao.checkUserPrivilege(profileId, featureId);
        if (count > 0) {
            CCATLogger.DEBUG_LOGGER.debug("User has privilege.");
            return true;
        } else {
            CCATLogger.DEBUG_LOGGER.debug("User does not has privilege.");
            return false;
        }
    }
}
