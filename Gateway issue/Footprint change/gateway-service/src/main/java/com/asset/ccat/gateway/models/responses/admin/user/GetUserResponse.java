package com.asset.ccat.gateway.models.responses.admin.user;

import com.asset.ccat.gateway.models.users.GetUserModel;

/**
 *
 * @author nour.ihab
 */
public class GetUserResponse {

    private GetUserModel user;

    public GetUserModel getUser() {
        return user;
    }

    public void setUser(GetUserModel user) {
        this.user = user;
    }

}
