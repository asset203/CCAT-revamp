package com.asset.ccat.user_management.models.dtoWrappers;

import com.asset.ccat.user_management.models.shared.UsersProfilesModel;

import java.util.LinkedList;
import java.util.List;

public class ExtractAllUsersProfilesWrapper {
    private List<UsersProfilesModel> allUsersProfilesList;

    private LinkedList<String> profilesName;

    public ExtractAllUsersProfilesWrapper() {
    }

    public ExtractAllUsersProfilesWrapper(List<UsersProfilesModel> allUsersProfilesList, LinkedList<String> profilesName) {
        this.allUsersProfilesList = allUsersProfilesList;
        this.profilesName = profilesName;
    }

    public LinkedList<String> getProfilesName() {
        return profilesName;
    }

    public void setProfilesName(LinkedList<String> profilesName) {
        this.profilesName = profilesName;
    }

    public List<UsersProfilesModel> getAllUsersProfilesList() {
        return allUsersProfilesList;
    }

    public void setAllUsersProfilesList(List<UsersProfilesModel> allUsersProfilesList) {
        this.allUsersProfilesList = allUsersProfilesList;
    }
}
