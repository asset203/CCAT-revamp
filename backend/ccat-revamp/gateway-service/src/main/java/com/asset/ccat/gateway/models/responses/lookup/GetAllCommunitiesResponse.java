/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.LookupModel;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllCommunitiesResponse {

    List<LookupModel> communities;

    public GetAllCommunitiesResponse(List<LookupModel> communities) {
		this.communities = communities;
	}

	public List<LookupModel> getCommunities() {
        return communities;
    }

    public void setCommunities(List<LookupModel> communities) {
        this.communities = communities;
    }

}
