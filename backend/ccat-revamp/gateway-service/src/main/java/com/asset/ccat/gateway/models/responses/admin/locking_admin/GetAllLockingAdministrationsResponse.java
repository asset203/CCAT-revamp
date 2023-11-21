package com.asset.ccat.gateway.models.responses.admin.locking_admin;

import com.asset.ccat.gateway.redis.model.LockingAdministration;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllLockingAdministrationsResponse {

    private List<LockingAdministration> lockingList;

    public GetAllLockingAdministrationsResponse() {
    }

    public GetAllLockingAdministrationsResponse(List<LockingAdministration> lockingList) {
        this.lockingList = lockingList;
    }

    public List<LockingAdministration> getLockingList() {
        return lockingList;
    }

    public void setLockingList(List<LockingAdministration> lockingList) {
        this.lockingList = lockingList;
    }

}
