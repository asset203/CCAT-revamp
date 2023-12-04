package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.users.LkHLRProfileModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllHLRProfileResponse {

    List<LkHLRProfileModel> hlrProfiles;

    public GetAllHLRProfileResponse(List<LkHLRProfileModel> lkHLRProfiles) {
        this.hlrProfiles = lkHLRProfiles;
    }

    public List<LkHLRProfileModel> getHlrProfiles() {
        return hlrProfiles;
    }

    public void setHlrProfiles(List<LkHLRProfileModel> hlrProfiles) {
        this.hlrProfiles = hlrProfiles;
    }

}
