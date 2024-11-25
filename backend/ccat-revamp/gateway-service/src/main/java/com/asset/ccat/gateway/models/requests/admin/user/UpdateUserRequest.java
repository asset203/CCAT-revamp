package com.asset.ccat.gateway.models.requests.admin.user;

import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.users.UserModel;

/**
 * @author nour.ihab
 */
public class UpdateUserRequest extends BaseRequest {

    private UserModel user;
    private Boolean resetRebateLimit;
    private Boolean resetDebitLimit;
    private Boolean resetSessionCount;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Boolean getResetRebateLimit() {
        return resetRebateLimit;
    }

    public void setResetRebateLimit(Boolean resetRebateLimit) {
        this.resetRebateLimit = resetRebateLimit;
    }

    public Boolean getResetDebitLimit() {
        return resetDebitLimit;
    }

    public void setResetDebitLimit(Boolean resetDebitLimit) {
        this.resetDebitLimit = resetDebitLimit;
    }

    public Boolean getResetSessionCount() {
        return resetSessionCount;
    }

    public void setResetSessionCount(Boolean resetSessionCount) {
        this.resetSessionCount = resetSessionCount;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "user=" + user +
                ", resetRebateLimit=" + resetRebateLimit +
                ", resetDebitLimit=" + resetDebitLimit +
                ", resetSessionCount=" + resetSessionCount +
                '}';
    }
}
