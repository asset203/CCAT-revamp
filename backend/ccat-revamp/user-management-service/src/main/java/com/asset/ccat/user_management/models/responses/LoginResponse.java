package com.asset.ccat.user_management.models.responses;

import com.asset.ccat.user_management.models.users.UserProfileModel;
import com.asset.ccat.user_management.models.users.UserModel;

/**
 *
 * @author marwa.elshawarby
 */
public class LoginResponse {

    private String token;
    private UserModel user;
    private UserProfileModel userProfile;
    private String sessionId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
