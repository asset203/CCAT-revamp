package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.database.dao.ProfileDao;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.profile.ProfileFeaturesModel;
import com.asset.ccat.user_management.models.responses.profile.GetAllProfilesFeaturesResponse;
import com.asset.ccat.user_management.models.responses.profile.GetAllProfilesResponse;
import com.asset.ccat.user_management.models.responses.profile.GetProfileResponse;
import com.asset.ccat.user_management.models.responses.profile.GetProfileUsersResponse;
import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import com.asset.ccat.user_management.models.shared.UserExtractModel;
import com.asset.ccat.user_management.models.users.MenuModel;
import com.asset.ccat.user_management.models.users.ProfileModel;
import com.asset.ccat.user_management.models.users.UserProfileModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class ProfileService {

    @Autowired
    private ProfileDao profileDao;

    public HashMap<Integer, UserProfileModel> retrieveAllUsersProfiles() throws UserManagementException {

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all users profiles");
        HashMap<Integer, UserProfileModel> profilesMap = profileDao.retrieveProfilesWithFeatures();
        if (profilesMap == null || profilesMap.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No profiles found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_PROFILES_FOUND, Defines.SEVERITY.FATAL);
        }

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all profiles menus profiles");
        HashMap<Integer, List<MenuModel>> profileMenus = profileDao.retrieveProfilesMenus();
        profilesMap.forEach((k, v) -> v.setMenus(profileMenus.get(k)));

        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all users profiles");
        return profilesMap;
    }

    public GetAllProfilesResponse retrieveAllProfiles() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all profiles");
        List<ProfileModel> profiles = profileDao.retrieveProfiles();
        if (profiles == null || profiles.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No profiles found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_PROFILES_FOUND, Defines.SEVERITY.ERROR);
        }
        return new GetAllProfilesResponse(profiles);
    }

    public GetProfileResponse retrieveProfile(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile with id [{}]", profileId);
        ProfileModel profile = profileDao.retrieveProfileWithFeatures(profileId);

        if (profile == null) {
            CCATLogger.DEBUG_LOGGER.debug("Profile with id [{}]", profileId);
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile monetary limits");
        profile.setMonetaryLimits(profileDao.retrieveProfileMonetaryLimits(profileId));

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile service classes");
        profile.setServiceClasses(profileDao.retrieveProfileServiceClasses(profileId));

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile menus");
        profile.setMenus(profileDao.retrieveUserProfileMenus(profileId).get(profileId));

        CCATLogger.DEBUG_LOGGER.debug("Done retrieving profile with id [{}]", profileId);
        return new GetProfileResponse(profile);
    }

    public UserProfileModel retrieveUserProfile(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile with id [" + profileId + "]");
        UserProfileModel profile = profileDao.retrieveUserProfile(profileId).get(profileId);

        if (profile == null) {
            CCATLogger.DEBUG_LOGGER.debug("Profile with id [" + profileId + "] was not found");
            throw new UserManagementException(ErrorCodes.ERROR.PROFILE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile menus");
        profile.setMenus(profileDao.retrieveUserProfileMenus(profileId).get(profileId));

        CCATLogger.DEBUG_LOGGER.debug("Done retrieving profile with id [" + profileId + "]");
        return profile;
    }

    public void addProfile(ProfileModel profile) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding profile [" + profile.getProfileName() + "]");
        Integer profileId = profileDao.insertProfile(profile);

        if (profileId == null) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add profile");
            throw new UserManagementException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "profile");
        }

        if (profile.getMonetaryLimits() != null && !profile.getMonetaryLimits().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start adding profile monetary limits");
            profileDao.addProfileMonetaryLimits(profileId, profile.getMonetaryLimits());
        }

        List<LkFeatureModel> features = new ArrayList<>();
        if (profile.getCcFeatures() != null && !profile.getCcFeatures().isEmpty()) {
            features.addAll(profile.getCcFeatures());
        }
        if (profile.getSysFeatures() != null && !profile.getSysFeatures().isEmpty()) {
            features.addAll(profile.getSysFeatures());
        }
        if (profile.getDssReportsFeatures() != null && !profile.getDssReportsFeatures().isEmpty()) {
            features.addAll(profile.getDssReportsFeatures());
        }
        if (!features.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start adding profile features");
            profileDao.addProfileFeatures(profileId, features);
        }

        if (profile.getServiceClasses() != null && !profile.getServiceClasses().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start adding profile service classes");
            profileDao.addProfileServiceClasses(profileId, profile.getServiceClasses());
        }

//        if (profile.getMenus() != null && !profile.getMenus().isEmpty()) {
//            CCATLogger.debug("Start adding profile menus");
//            profileDao.addProfileMenus(profileId, profile.getMenus());
//        }
        CCATLogger.DEBUG_LOGGER.debug("Added profile successfull with ID[" + profileId + "]");
    }

    public void updateProfile(ProfileModel profile) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating profile [{}]", profile.getProfileName());
        int updated = profileDao.updateProfile(profile);
        CCATLogger.DEBUG_LOGGER.debug("#Updated Profiles = {}", updated);
        if (updated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update profile");
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "profile");
        }

        if (profile.getMonetaryLimits() != null)
            syncProfileMonetaryLimits(profile);

        if (profile.getCcFeatures() != null || profile.getSysFeatures() != null || profile.getDssReportsFeatures() != null)
            syncProfileFeatures(profile);

        if (profile.getServiceClasses() != null)
            syncProfileServiceClasses(profile);

//        if (profile.getMenus() != null) {
//            CCATLogger.debug("Start syncing profile menus");
//            syncProfileMenus(profile);
//        }

    }

    private void syncProfileMonetaryLimits(ProfileModel profile) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Revoking old profile monetary limits...");
        int deleted = profileDao.deleteProfileMonetaryLimits(profile.getProfileId());
        CCATLogger.DEBUG_LOGGER.debug("# deleted monetary limits = {} ", deleted);
        if (!profile.getMonetaryLimits().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Adding new profile monetary limits...");
            profileDao.addProfileMonetaryLimits(profile.getProfileId(), profile.getMonetaryLimits());
        }
    }

    private void syncProfileServiceClasses(ProfileModel profile) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Revoking old profile service classes...");
        int deleted = profileDao.deleteProfileServiceClasses(profile.getProfileId());
        CCATLogger.DEBUG_LOGGER.debug("#Deleted prof-SCs = {}", deleted);
        if (!profile.getServiceClasses().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Adding new profile service classes...");
            profileDao.addProfileServiceClasses(profile.getProfileId(), profile.getServiceClasses());
        }
    }

    private void syncProfileFeatures(ProfileModel profile) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start syncing profile features");
        int deleted = profileDao.deleteProfileFeatures(profile.getProfileId());
        CCATLogger.DEBUG_LOGGER.debug("#Deleted prof-features = {}", deleted);

        ArrayList<LkFeatureModel> features = new ArrayList<>();
        if (profile.getCcFeatures() != null && !profile.getCcFeatures().isEmpty()) {
            features.addAll(profile.getCcFeatures());
        }
        if (profile.getSysFeatures() != null && !profile.getSysFeatures().isEmpty()) {
            features.addAll(profile.getSysFeatures());
        }
        if (profile.getDssReportsFeatures() != null && !profile.getDssReportsFeatures().isEmpty()) {
            features.addAll(profile.getDssReportsFeatures());
        }
        if (!features.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Adding new profile features...");
            profileDao.addProfileFeatures(profile.getProfileId(), features);
        }
    }

//    private void syncProfileMenus(ProfileModel profile) throws UserManagementException {
//        CCATLogger.debug("Revoking old profile features...");
//        int deleted = profileDao.deleteProfileMenus(profile.getProfileId());
//
//        if (!profile.getMenus().isEmpty()) {
//            CCATLogger.debug("Adding new profile menus...");
//            profileDao.addProfileMenus(profile.getProfileId(), profile.getMenus());
//        }
//    }

    public void deleteProfile(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting profile with ID [" + profileId + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start deleting profile features");
        profileDao.deleteProfileFeatures(profileId);

        CCATLogger.DEBUG_LOGGER.debug("Start deleting profile monetary limits");
        profileDao.deleteProfileMonetaryLimits(profileId);

        CCATLogger.DEBUG_LOGGER.debug("Start deleting profile service classes");
        profileDao.deleteProfileServiceClasses(profileId);

//        CCATLogger.debug("Start deleting profile menus");
//        profileDao.deleteProfileMenus(profileId);

        CCATLogger.DEBUG_LOGGER.debug("Start deleting profile definition");
        profileDao.deleteProfile(profileId);

    }

    public Boolean isProfileExists(Integer profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if profile with ID[" + profileId + "] exists in DB");
        return profileDao.findProfileById(profileId) > 0;
    }

    public Integer retrieveProfileIdByName(String profileName) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("retrieving profile id for profile [" + profileName + "]");
        return profileDao.findProfileByName(profileName);
    }

    public GetProfileUsersResponse retrieveProfileUsers(Long profileId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving profile users");
        List<UserExtractModel> users = profileDao.retrieveProfileUsers(profileId);
        if (users == null || users.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No users found");
            throw new UserManagementException(ErrorCodes.ERROR.NO_USERS_FOUND, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving profile users");
        return new GetProfileUsersResponse(users);
    }
    public GetAllProfilesFeaturesResponse retrieveAllProfilesWithFeatures() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all profiles features");
        List<ProfileFeaturesModel> profileFeatures = profileDao.retrieveAllProfilesWithFeatures();
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all profiles features");
        return new GetAllProfilesFeaturesResponse(profileFeatures) ;
    }
}
