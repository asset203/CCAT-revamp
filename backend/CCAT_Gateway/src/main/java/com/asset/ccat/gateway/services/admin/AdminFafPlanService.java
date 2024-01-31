package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.AdminFafPlanModel;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.AddAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.DeleteAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.GetAllAdminFafPlansRequest;
import com.asset.ccat.gateway.models.requests.admin.admin_faf_plan.UpdateAdminFafPlanRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.AddCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.DeleteCommunityAdminRequest;
import com.asset.ccat.gateway.models.requests.admin.community_admin.UpdateCommunityAdminRequest;
import com.asset.ccat.gateway.models.shared.FafPlanModel;
import com.asset.ccat.gateway.proxy.admin.AdminFafPlanServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mohamed.metwaly
 */
@Service
public class AdminFafPlanService {
    @Autowired
    AdminFafPlanServiceProxy adminFafPlanServiceProxy;
    public List<FafPlanModel> getAllAdminFafPlans(GetAllAdminFafPlansRequest request) throws  GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started successfully.");
        List<AdminFafPlanModel> adminFafPlans = new ArrayList<>();
        CCATLogger.DEBUG_LOGGER.info("Start getting faf plans");
          List<FafPlanModel> fafPlans = adminFafPlanServiceProxy.getAllFafPlans(request);
        CCATLogger.DEBUG_LOGGER.info("Faf plans has gotten");
        CCATLogger.DEBUG_LOGGER.debug("Ended successfully.");
        return fafPlans;
    }


    public void updateFafPlan(UpdateAdminFafPlanRequest req) throws GatewayException {
        adminFafPlanServiceProxy.updateFafPlan(req);
    }

    public void addFafPlan(AddAdminFafPlanRequest request) throws  GatewayException {
        adminFafPlanServiceProxy.addFafPlan(request);
    }
    public void deleteFafPlan(DeleteAdminFafPlanRequest request) throws GatewayException {
        adminFafPlanServiceProxy.deleteFafPlan(request);
    }
}
