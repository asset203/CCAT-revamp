package com.asset.ccat.gateway.models.requests.admin.user;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class DeleteUserRequest extends BaseRequest {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeleteUserRequest{" +
                "userId=" + userId +
                '}';
    }
}
