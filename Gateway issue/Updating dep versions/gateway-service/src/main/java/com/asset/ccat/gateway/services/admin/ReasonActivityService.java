package com.asset.ccat.gateway.services.admin;


import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.requests.admin.call_activity_admin.*;
import com.asset.ccat.gateway.models.requests.admin.service_offering_plans.AddServiceOfferingPlanRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.proxy.admin.ReasonActivityProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReasonActivityService {


    @Autowired
    private ReasonActivityProxy reasonActivityProxy;

    public List<ReasonActivityModel> getAllActivityReasons(GetAllActivitiesWithTypeRequest req) throws GatewayException {

        List<ReasonActivityModel> reasons = reasonActivityProxy.getAllActivitiesReasons(req);
        return reasons;
    }

    public BaseResponse addReasonActivity(AddActivityReasonRequest request) throws GatewayException {
        BaseResponse response = reasonActivityProxy.addReasonActivity(request);
        return response;
    }

    public BaseResponse updateReasonActivity(UpdateReasonActivityRequest request) throws GatewayException {
        BaseResponse response = reasonActivityProxy.updateReasonActivity(request);
        return response;
    }

    public BaseResponse deleteReasonActivity(DeleteReasonActivityRequest request) throws GatewayException {
        BaseResponse response = reasonActivityProxy.deleteReasonActivity(request);
        return response;
    }


    public ResponseEntity<Resource> downloadActivities(DownloadActivitiesRequest  request) throws GatewayException {
        ResponseEntity<Resource> response = reasonActivityProxy.downloadActivities(request);
        return response;
    }

    public BaseResponse uploadActivities(UploadActivitiesFileRequest  request) throws GatewayException {
        BaseResponse response = reasonActivityProxy.uploadActivities(request);
        return response;

    }

}
