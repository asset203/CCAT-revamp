package com.asset.ccat.gateway.models.requests.admin.user;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.users.UserModel;

/**
 * @author nour.ihab
 */
public class AddUserRequest extends BaseRequest {

    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddUserRequest{" +
                "user=" + user +
                '}';
    }
}
