package com.asset.ccat.user_management.models.requests.user;

import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.UserModel;
import java.io.Serializable;

/**
 *
 * @author marwa.elshawarby
 */
public class UpdateUserRequest extends BaseRequest implements Serializable {

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

}
