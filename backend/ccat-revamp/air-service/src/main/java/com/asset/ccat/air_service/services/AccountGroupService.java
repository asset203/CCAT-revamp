package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.AccountGroupBitModel;
import com.asset.ccat.air_service.models.AccountGroupModel;
import com.asset.ccat.air_service.models.requests.GetAccountGroupRequest;
import com.asset.ccat.air_service.models.requests.UpdateAccountGroupRequest;
import com.asset.ccat.air_service.models.shared.AccountGroupBitDescModel;
import com.asset.ccat.air_service.models.shared.AccountGroupWithBitsModel;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class AccountGroupService {

    @Autowired
    private AIRProxy airProxy;
    @Autowired
    private AIRParser airParser;
    @Autowired
    private AIRUtils airUtils;
    @Autowired
    private Properties properties;
    @Autowired
    private LookupsService lookupsService;
    @Autowired
    private AIRRequestsCache aIRRequestsCache;
    @Autowired
    private GetAccountDetailsService getAccountDetailsService;


    public AccountGroupModel getCurrentAccountGroup(GetAccountGroupRequest request) throws AIRServiceException, AIRException {
        HashMap map = getAccountDetailsService.getAccountDetailsMap(request);
        CCATLogger.DEBUG_LOGGER.debug("Retrieved subscriber account details map[" + map + "]");
        Integer accountGroupId = map.get(AIRDefines.accountGroupID) == null ? null : Integer.valueOf((String) map.get(AIRDefines.accountGroupID));
        CCATLogger.DEBUG_LOGGER.info("Retrieved accountGroupID [" + accountGroupId + "]");
        //Initialize model
        AccountGroupModel accountGroupModel = new AccountGroupModel();
        accountGroupModel.setId(accountGroupId);
        accountGroupModel.setName("Unknown (" + accountGroupId + ")");
        accountGroupModel.setBits(new ArrayList<>());
        HashMap<Integer, AccountGroupWithBitsModel> accountGroupsLookup = lookupsService.getAccountGroupsWithBits();
        List<AccountGroupBitDescModel> bitList = null;

        // use account group lookup model(if exists) to generate account group model
        if (accountGroupsLookup != null && !accountGroupsLookup.isEmpty() && accountGroupsLookup.containsKey(accountGroupId)) {
            CCATLogger.DEBUG_LOGGER.debug("Map Account Group to lookup model");
            AccountGroupWithBitsModel accountGroupLookupModel = accountGroupsLookup.get(accountGroupId);
            accountGroupModel.setName(accountGroupLookupModel.getName());
            bitList = accountGroupLookupModel.getBits();
        }

        // if no lookup found for account group, instead use Account Group Bits Lookup for mapping bits
        if (bitList == null || bitList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Getting Account Group Bits From Lookups");
            bitList = lookupsService.getAccountGroupBitsLookup();
        }

        CCATLogger.DEBUG_LOGGER.debug("Mapping account id binary representation to bit model");
        String[] idAsBinary = Integer.toBinaryString(accountGroupId).split("");
        for (int start = 0, end = idAsBinary.length; start < bitList.size(); start++, end--) {
            AccountGroupBitModel bitModel = new AccountGroupBitModel();
            AccountGroupBitDescModel lookup = bitList.get(start);

            bitModel.setBitPosition(lookup.getBitPosition());
            // use service class bit description as name if exists, else use original bit name
            if (Objects.nonNull(lookup.getServiceClassBitDescriptions())
                    && !lookup.getServiceClassBitDescriptions().isEmpty()
                    && lookup.getServiceClassBitDescriptions().containsKey(request.getServiceClassId())) {
                bitModel.setBitName(lookup.getServiceClassBitDescriptions().get(request.getServiceClassId()));
            } else {
                bitModel.setBitName(lookup.getBitName());
            }
            bitModel.setIsEnabled(end > 0 && idAsBinary[end - 1].equals("1"));
            accountGroupModel.getBits().add(bitModel);
            CCATLogger.DEBUG_LOGGER.debug("Mapped Account group model [" + bitModel + "]");
        }

        return accountGroupModel;
    }

    public void updateCurrentAccountGroup(UpdateAccountGroupRequest request) throws AIRServiceException, AIRException {
        try {
            Integer accountGroupId = getIdFromBits(request.getNewAccountGroup().getBits());
            CCATLogger.DEBUG_LOGGER.debug("Account Group number obtained from bit list >> " + accountGroupId);
            String xmlRequest = buildUpdateAccountGroupXml(request.getMsisdn(), request.getUsername(), accountGroupId);
            CCATLogger.DEBUG_LOGGER.debug("Update account group Air request is : " + xmlRequest);
            long t1 = System.currentTimeMillis();
            String airResponse = airProxy.sendAIRRequest(xmlRequest);
            long t2 = System.currentTimeMillis();
            CCATLogger.DEBUG_LOGGER.debug("Parsing air response");
            HashMap responseMap = airParser.parse(airResponse);
            CCATLogger.DEBUG_LOGGER.debug("Validating air response code");
            airUtils.validateAIRResponse(responseMap, "Update-Account-Group", t2 - t1, request.getMsisdn());
            String responseCode = (String) responseMap.get(AIRDefines.responseCode);
            airUtils.validateUCIPResponseCodes(responseCode);
            CCATLogger.DEBUG_LOGGER.debug("Finished serving update account group request successfully");
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (SAXException | IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse air response | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse air response", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in updateCurrentAccountGroup() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in updateCurrentAccountGroup()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    private Integer getIdFromBits(List<AccountGroupBitModel> bits) {
        Double decimalValue = 0.0;
        for (AccountGroupBitModel bit : bits) {
            if(Boolean.TRUE.equals(bit.getIsEnabled()))
                decimalValue += Math.pow(2, bit.getBitPosition() - 1d);
        }
        return decimalValue.intValue();
    }

    private String buildUpdateAccountGroupXml(String msisdn, String username, Integer accountNumber) {
        String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_ACCOUNT_GROUP);
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, username.toLowerCase());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, airUtils.getCurrentFormattedDate());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
        xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, msisdn);
        xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_ACCOUNT_GROUP.ACCOUNT_GROUP_ID, String.valueOf(accountNumber));

        return xmlRequest;
    }
}
