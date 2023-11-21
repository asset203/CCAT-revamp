package com.asset.ccat.ci_service.services;

import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.ci_service.models.responses.BaseResponse;
import com.asset.ccat.ci_service.proxy.UserLimitsProxy;
import org.springframework.stereotype.Service;

@Service
public class UserLimitsService {
    private final UserLimitsProxy userLimitsProxy;

    public UserLimitsService(UserLimitsProxy userLimitsProxy) {
        this.userLimitsProxy = userLimitsProxy;
    }

    public BaseResponse updateLimits(UpdateLimitRequest updateLimitRequest) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.debug("UserLimitsService -> updateLimits() : Start Calling updateLimits proxy");
        BaseResponse response = userLimitsProxy.updateLimit(updateLimitRequest);
        CCATLogger.DEBUG_LOGGER.debug("UserLimitsService -> updateLimits() : updateLimits proxy Finished Successfully");
        return response;
    }
}
