package com.asset.ccat.user_management.models.responses.user;

import com.asset.ccat.user_management.models.users.UserModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetAllUsersResponse {

    private List<UserModel> users;

    public GetAllUsersResponse() {
    }

    public GetAllUsersResponse(List<UserModel> users) {
        this.users = users;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

}
