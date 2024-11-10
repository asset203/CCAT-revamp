package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.UpdateBalanceAndDateMapper;
import com.asset.ccat.air_service.models.requests.UpdateAccumulatorModel;
import com.asset.ccat.air_service.models.requests.UpdateAccumulatorsRequest;
import com.asset.ccat.air_service.parsers.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Mahmoud Shehab
 */
@Component
public class UpdateAccumulatorService {

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
    UpdateBalanceAndDateMapper balanceAndDateMapper;


    public void updateAccumulators(UpdateAccumulatorsRequest updateAccumulatorsRequest) throws AIRServiceException, AIRException {
        try {
            String requestVal = generateValuesXML(updateAccumulatorsRequest.getList()).get("normal");
//            String setVal = generateValuesXML(updateAccumulatorsRequest.getList()).get("set");

            CCATLogger.DEBUG_LOGGER.debug("The Request Value = {}  --> to be replaced as the ACCUMULATORS_INFO", requestVal);
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, updateAccumulatorsRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, updateAccumulatorsRequest.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .addPlaceholder(AIRDefines.UPDATE_ACCUMULATORS_PLACEHOLDER.ACCUMULATORS_INFO, requestVal)
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_ACCUMULATORS));

            CCATLogger.DEBUG_LOGGER.debug(" AIR update accumulators request is {}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            balanceAndDateMapper.map(updateAccumulatorsRequest.getMsisdn(), resultMap);
//            Float totalsAmounts = 0.0f;
//            Integer actionType = -1;
//            for (UpdateAccumulatorModel account : updateAccumulatorsRequest.getList()) {
//                if (Objects.nonNull(account.getAdjustmentAmount()) && Objects.nonNull(account.getAdjustmentMethod())) {
//                    totalsAmounts += account.getAdjustmentAmount();
//                    actionType = account.getAdjustmentMethod();
//                }
//            }
//            if (actionType != -1 || actionType != 0) {
//                UpdateLimitRequest updateLimitRequest = new UpdateLimitRequest(updateAccumulatorsRequest.getUserId(),
//                        actionType,
//                        totalsAmounts,
//                        0.0f);
//                updateLimitRequest.setToken(updateAccumulatorsRequest.getToken());
//                CCATLogger.DEBUG_LOGGER.debug("UpdateAccumulatorService -> updateAccumulators() : Starting Update User Limits ");
//                userLimitsService.updateLimits(updateLimitRequest);
//                CCATLogger.DEBUG_LOGGER.debug("UpdateAccumulatorService -> updateAccumulators() : Ending Update User Limits ");
//
//            }

        } catch (AIRServiceException | AIRException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.error("IOException|SAXException while parsing response ", ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred in updateAccumulators()", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred in updateAccumulators()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private HashMap<String, String> generateValuesXML(List<UpdateAccumulatorModel> accumulators) throws AIRServiceException {
        StringBuilder accumulatorsXML = new StringBuilder();
        HashMap<String, String> map = new HashMap();
        try {
            if (!accumulators.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("#Accumulators = {}", accumulators.size());
                for (UpdateAccumulatorModel accumulatorModel : accumulators) {
                    if (Boolean.TRUE.equals(accumulatorModel.getIsReset())) {
                        accumulatorModel.setAdjustmentAmount(0f);
                        accumulatorModel.setStartDate(new Date());
                        accumulatorModel.setAdjustmentMethod(AIRDefines.UPDATE_BALANCE_SETAMT);
                        accumulatorModel.setIsDateEdited(true);
                    }
                    accumulatorsXML.append(updateAccumulatorXML(accumulatorModel));
                }
                map.put("normal", accumulatorsXML.toString());
//                if (!accumaltorsXML.equals("")) {
//                    map.put("normal", accumaltorsXML.toString());
//                }
//                if (Objects.nonNull(accumaltorsSetXML) && !accumaltorsSetXML.equals("")) {
//                    map.put("set", accumaltorsSetXML.toString());
//                }
            }
            return map;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Exception in generateValuesXML(). ", e);
            CCATLogger.ERROR_LOGGER.error("Exception in generateValuesXML", e);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        }
    }

    private String updateAccumulatorXML(UpdateAccumulatorModel accumulatorModel) throws AIRServiceException {
        try {
            String accumulatorItem = determineAccumulatorStructure(accumulatorModel);
            if (accumulatorItem.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("dedicatedAccount {} skipped", accumulatorModel.getId());
                return accumulatorItem;
            }

            String accumulatorID = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accumulatorID)
                    .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(accumulatorModel.getId()))
                    .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_INT);

            accumulatorItem = accumulatorItem.replace("$MEMBER_1$", accumulatorID);
            Long value = accumulatorModel.getAdjustmentAmount() == null ? 0 : accumulatorModel.getAdjustmentAmount().longValue();

            if (accumulatorModel.getAdjustmentMethod().equals(AIRDefines.UPDATE_BALANCE_ADD)) {
                String accumulatorValueRelative = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
                accumulatorValueRelative = accumulatorValueRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accumulatorValueRelative);
                accumulatorValueRelative = accumulatorValueRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, value + "");
                accumulatorItem = accumulatorItem.replace("$MEMBER_2$", accumulatorValueRelative);
            } else if (accumulatorModel.getAdjustmentMethod().equals(AIRDefines.UPDATE_BALANCE_SUBTRACT)) {
                String accumulatorValueRelative = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
                accumulatorValueRelative = accumulatorValueRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accumulatorValueRelative);
                accumulatorValueRelative = accumulatorValueRelative.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, "-" + value);
                accumulatorItem = accumulatorItem.replace("$MEMBER_2$", accumulatorValueRelative);
            } else if (accumulatorModel.getAdjustmentMethod().equals(AIRDefines.UPDATE_BALANCE_SETAMT)) {
                String accumulatorValueAbsolute = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
                accumulatorValueAbsolute = accumulatorValueAbsolute.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accumulatorValueAbsolute);
                accumulatorValueAbsolute = accumulatorValueAbsolute.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, value + "");
                accumulatorItem = accumulatorItem.replace("$MEMBER_2$", accumulatorValueAbsolute);
            }
            if (Objects.nonNull(accumulatorModel.getIsDateEdited()) && Boolean.TRUE.equals(accumulatorModel.getIsDateEdited())) {
                String accumulatorStartDate = AIRDefines.AIR_TAGS.TAG_MEMBER_DATE;
                accumulatorStartDate = accumulatorStartDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.accumulatorStartDate);
                accumulatorStartDate = accumulatorStartDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, aIRUtils.formatNewAIR(accumulatorModel.getStartDate()));
                //Check if date time is zeros, so it will be overridden to set time in order to let AIR success
                if (accumulatorStartDate.substring(9).equals("00:00:00+0200"))
                    accumulatorStartDate = accumulatorStartDate.substring(0, 9) + "19:59:16+0200";

                accumulatorItem = !accumulatorModel.getAdjustmentMethod().equals(0) ?
                        accumulatorItem.replace("$MEMBER_3$", accumulatorStartDate) :
                        accumulatorItem.replace("$MEMBER_2$", accumulatorStartDate);
            }
            return accumulatorItem;
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.error("Exception in updateAccumulatorXML ", ex);
            CCATLogger.DEBUG_LOGGER.error("Exception in updateAccumulatorXML ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_WHILE_PARSING_REQUEST);
        }
    }
    private String determineAccumulatorStructure(UpdateAccumulatorModel accumulatorModel) {
        if (Boolean.TRUE.equals(accumulatorModel.getIsDateEdited()) && !accumulatorModel.getAdjustmentMethod().equals(0)) {
            return AIRDefines.AIR_TAGS.TAG_STRUCT_3MEMBERS;
        } else if (Boolean.TRUE.equals(accumulatorModel.getIsDateEdited()) || !accumulatorModel.getAdjustmentMethod().equals(0)) {
            return AIRDefines.AIR_TAGS.TAG_STRUCT_2MEMBERS;
        }
        return "";
    }
}
