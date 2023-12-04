package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.requests.UpdateLimitRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import com.asset.ccat.air_service.proxy.UserLimitsProxy;
import org.springframework.stereotype.Service;

@Service
public class UserLimitsService {
    private final UserLimitsProxy userLimitsProxy;

    public UserLimitsService(UserLimitsProxy userLimitsProxy) {
        this.userLimitsProxy = userLimitsProxy;
    }

    public BaseResponse updateLimits(UpdateLimitRequest updateLimitRequest) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("UserLimitsService -> updateLimits(): Starting Update User Limits");
        BaseResponse response = userLimitsProxy.updateLimit(updateLimitRequest);
        CCATLogger.DEBUG_LOGGER.debug("UserLimitsService -> updateLimits(): Ended Update User Limits Successfully");

        return response;
    }
}
