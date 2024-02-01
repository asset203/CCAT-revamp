package com.asset.ccat.gateway.models.responses.admin.user;

import com.asset.ccat.gateway.models.users.GetAllUsersModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllUsersResponse {

    private List<GetAllUsersModel> users;

    public List<GetAllUsersModel> getUsers() {
        return users;
    }

    public void setUsers(List<GetAllUsersModel> users) {
        this.users = users;
    }

}
