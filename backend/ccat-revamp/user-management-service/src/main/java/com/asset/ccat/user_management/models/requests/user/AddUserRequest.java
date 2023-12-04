package com.asset.ccat.user_management.models.requests.user;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.UserModel;
import java.io.Serializable;

/**
 *
 * @author marwa.elshawarby
 */
public class AddUserRequest extends BaseRequest implements Serializable{
    
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
    
    
}
