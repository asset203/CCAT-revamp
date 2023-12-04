package com.asset.ccat.user_management.models.requests.user;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import java.io.Serializable;

/**
 *
 * @author marwa.elshawarby
 */
public class GetUserRequest extends BaseRequest implements Serializable {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
