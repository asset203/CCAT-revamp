package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.constants.FlexType;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.flex_share.FlexShareInquiryResponse;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareEligibilityRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareHistoryRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.GetFlexShareStatesRequest;
import com.asset.ccat.gateway.models.requests.customer_care.flex_share.UpdateBWStateRequest;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareEligibilityResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareHistoryResponse;
import com.asset.ccat.gateway.models.responses.customer_care.flex_share.GetFlexShareStatesResponse;
import com.asset.ccat.gateway.models.shared.FlexShareBWStateModel;
import com.asset.ccat.gateway.models.shared.FlexShareProfitableStateModel;
import com.asset.ccat.gateway.proxy.FlexShareProxy;
import org.springframework.stereotype.Service;

@Service
public class FlexShareService {
    private final FlexShareProxy flexShareProxy;

    public FlexShareService(FlexShareProxy flexShareProxy) {
        this.flexShareProxy = flexShareProxy;
    }

    public GetFlexShareStatesResponse flexShareInquiry(GetFlexShareStatesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> flexShareInquiry() : Started Successfully");
        GetFlexShareStatesResponse response = new GetFlexShareStatesResponse();
        FlexShareInquiryResponse inquiryResponse= flexShareProxy.inquiry(request);
        FlexShareBWStateModel bwModel = new FlexShareBWStateModel();
        FlexShareProfitableStateModel profitableState = new FlexShareProfitableStateModel();
        FlexType flexType = inquiryResponse.getFlexType();
        switch (flexType){
            case FS_PROFITABLE:
                profitableState.setCurrentState(flexType.name);
                profitableState.setParameterOut(inquiryResponse.getParameterOut());

                response.setProfitableState(profitableState);
                response.setBlackWhiteState(null);
                return response;
            case FS_BLACKLIST:
                bwModel.setCurrentState(flexType.name);
                bwModel.setDestinationState(FlexType.FS_WHITELIST.name);
                bwModel.setUpdatedValue(FlexType.FS_WHITELIST.id);

                response.setProfitableState(null);
                response.setBlackWhiteState(bwModel);
                return response;
            case FS_WHITELIST:
                bwModel.setCurrentState(flexType.name);
                bwModel.setDestinationState(FlexType.FS_BLACKLIST.name);
                bwModel.setUpdatedValue(FlexType.FS_BLACKLIST.id);

                response.setProfitableState(null);
                response.setBlackWhiteState(bwModel);
                return response;
            default:
                response.setProfitableState(null);
                response.setBlackWhiteState(null);
                return response;
        }

    }

    public void updateFlexShareState(UpdateBWStateRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> updateFlexShareState() : Started Successfully");
        flexShareProxy.update(request);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> updateFlexShareState() : Started Successfully");

    }
    public GetFlexShareEligibilityResponse getFlexShareEligibility(GetFlexShareEligibilityRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> updateFlexShareState() : Started Successfully");
        GetFlexShareEligibilityResponse response = flexShareProxy.getEligibility(request);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> updateFlexShareState() : Started Successfully");
        return response;

    }

    public GetFlexShareHistoryResponse getFlexShareHistory(GetFlexShareHistoryRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> getFlexShareHistory() : Started Successfully");
        GetFlexShareHistoryResponse response = flexShareProxy.getFlexShareHistory(request);
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> getFlexShareHistory() : Started Successfully");
        return response;

    }

}
