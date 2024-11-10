package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.GetAccumulatorsMapper;
import com.asset.ccat.air_service.models.AccumulatorModel;
import com.asset.ccat.air_service.models.requests.SubscriberRequest;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @author Mahmoud Shehab
 */
@Component
public class GetAccumulatorsService {
    private final AIRRequestsCache aIRRequestsCache;
    private final AIRProxy aIRProxy;
    private final AIRUtils aIRUtils;
    private final AIRParser aIRParser;
    private final GetAccumulatorsMapper getAccumulatorsMapper;

    @Autowired
    public GetAccumulatorsService(AIRRequestsCache aIRRequestsCache, AIRProxy aIRProxy, AIRUtils aIRUtils, AIRParser aIRParser, GetAccumulatorsMapper getAccumulatorsMapper) {
        this.aIRRequestsCache = aIRRequestsCache;
        this.aIRProxy = aIRProxy;
        this.aIRUtils = aIRUtils;
        this.aIRParser = aIRParser;
        this.getAccumulatorsMapper = getAccumulatorsMapper;
    }

    public List<AccumulatorModel> getAccumulators(SubscriberRequest subscriberRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, subscriberRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, subscriberRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_ACCUMULATORS));
            CCATLogger.DEBUG_LOGGER.debug(" AIR getAccumulators request is {}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            return getAccumulatorsMapper.map(subscriberRequest.getMsisdn(), resultMap);
        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while getting accumulators. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while getting accumulators. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
