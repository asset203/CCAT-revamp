package com.asset.ccat.user_management.models.responses.user;

import com.asset.ccat.user_management.models.users.UserModel;

/**
 *
 * @author marwa.elshawarby
 */
public class GetUserResponse {

    private UserModel user;

    public GetUserResponse() {
    }

    public GetUserResponse(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
