package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.GetAllCommunitiesRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.UpdateCommunityAdminRequest;
import com.asset.ccat.gateway.models.shared.CommunitiesModel;
import com.asset.ccat.gateway.proxy.admin.CommunityAdminProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mohamed.metwaly
 */

@Service
public class CommunityAdminService {

    @Autowired
    CommunityAdminProxy communityAdminProxy;

    public List<CommunitiesModel> getAllCommunitiesAdmin(GetAllCommunitiesRequest req) throws GatewayException {
        List<CommunitiesModel> communitiesAdmin = communityAdminProxy.getAllCommunitiesAdmin(req);
        return communitiesAdmin;
    }

    public void updateCommunityAdmin(UpdateCommunityAdminRequest req) throws GatewayException {
        communityAdminProxy.updateCommunityAdmin(req);
    }

    public void addCommunityAdmin(AddCommunityAdminRequest request) throws GatewayException {
        communityAdminProxy.addCommunityAdmin(request);
    }

    public void deleteCommunityAdmin(DeleteCommunityAdminRequest request) throws GatewayException {
        communityAdminProxy.deleteCommunityAdmin(request);
    }
}
