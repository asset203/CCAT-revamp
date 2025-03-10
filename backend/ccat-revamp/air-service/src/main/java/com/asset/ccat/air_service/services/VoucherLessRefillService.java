package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.VoucherLessMapper;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.VoucherLessRequest;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author wael.mohamed
 */
@Service
public class VoucherLessRefillService {

    @Autowired
    Properties properties;
    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    VoucherLessMapper voucherLessMapper;

    public void submitVoucherLessRefill(VoucherLessRequest voucherRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.VOUCHER_LESS_PLACEHOLDER.REFILL_PROFILE_ID, voucherRequest.getPaymentProfileId())
                    .addPlaceholder(AIRDefines.VOUCHER_LESS_PLACEHOLDER.TRANSACTION_AMOUNT, aIRUtils.amountInPT(voucherRequest.getAmount()))
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, voucherRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, voucherRequest.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.VOUCHER_REFILL));

            CCATLogger.DEBUG_LOGGER.debug("AIR submitVoucherLess request is:\n{} ", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            voucherLessMapper.map(voucherRequest.getMsisdn(), resultMap);
            //TODO: Use Flag to
            //TODO: Calling History-Service to savePendingTransactions()-> insertIntoHistory() [H_REFILL_VOUCHER]
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("submitVoucherLess Ended with AIRException.");
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("submitVoucherLess Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occured while parsing voucherless refill request. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occured while parsing voucherless refill request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
