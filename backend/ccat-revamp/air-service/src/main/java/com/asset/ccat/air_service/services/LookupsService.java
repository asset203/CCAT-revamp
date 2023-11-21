package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.*;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingBitLookupModel;
import com.asset.ccat.air_service.models.customer_care.service_offering_details_models.lookup_models.ServiceOfferingPlanBitLookupModel;
import com.asset.ccat.air_service.models.requests.AddBarringReasonRequest;
import com.asset.ccat.air_service.models.requests.DeleteBarringReasonRequest;
import com.asset.ccat.air_service.models.requests.customer_care.BarringRequest;
import com.asset.ccat.air_service.models.responses.offer.OfferResponse;
import com.asset.ccat.air_service.models.responses.offer.OfferStateResponse;
import com.asset.ccat.air_service.models.responses.offer.OfferTypeResponse;
import com.asset.ccat.air_service.models.shared.*;
import com.asset.ccat.air_service.proxy.LookupProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Mahmoud Shehab
 */
@Service
public class LookupsService {

    @Autowired
    LookupProxy lookupProxy;

    public List<AIRServer> getAirServers() throws AIRServiceException {
        return lookupProxy.getAirServer();
    }

    private List<VoucherServerModel> getVoucherServers() throws AIRServiceException {
        return lookupProxy.getVoucherServers();
    }

    public AIRServer getAirServer() throws AIRServiceException {
        Random random = new java.util.Random();
        List<AIRServer> airServers = getAirServers();
        int randomPos = random.nextInt(airServers.size());
        return airServers.get(randomPos);
    }

    public synchronized VoucherServerModel getVoucherServer(int serverIndex, int serialNumberLength) throws AIRServiceException {
        VoucherServerModel voucherServer = null;
        List<VoucherServerModel> voucherServersList = getVoucherServers();
        for (VoucherServerModel voucherServerModel : voucherServersList) {
            if (voucherServerModel.getServerIndex().equals(serverIndex)) {
                if (serialNumberLength > voucherServerModel.getVoucherSerialLength()) {
                    voucherServer = voucherServerModel;
                } else if (serialNumberLength <= voucherServerModel.getVoucherSerialLength()) {
                    voucherServer = voucherServerModel;
                    break;
                }
            }
        }
        return voucherServer;
    }

    public HashMap<String, LookupModel> getLookups(String name) throws AIRServiceException {
        List<LookupModel> list = lookupProxy.getLookups(name);
        HashMap<String, LookupModel> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getKey(), model));
        }
        return result;
    }

    public HashMap<String, LookupModel> getUnitTypes() throws AIRServiceException {
        return getLookups(Defines.LOOKUPS.UNIT_TYPES);
    }

    public HashMap<String, LookupModel> getLanguages() throws AIRServiceException {
        return getLookups(Defines.LOOKUPS.LK_LANGUAGES);
    }

    public HashMap<String, LookupModel> getUsageCountersDesc() throws AIRServiceException {
        return getLookups(Defines.LOOKUPS.LK_USAGE_COUNTER_DESC);
    }

    public HashMap<Integer, LkPamModel> getPamClasses() throws AIRServiceException {
        List<LkPamModel> list = lookupProxy.getPamClass();
        HashMap<Integer, LkPamModel> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getId(), model));
        }
        return result;
    }

    public HashMap<Integer, LkPamModel> getPamSchedule() throws AIRServiceException {
        List<LkPamModel> list = lookupProxy.getPamSchedule();
        HashMap<Integer, LkPamModel> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getId(), model));
        }
        return result;
    }

    public HashMap<Integer, ServiceClassModel> getServiceClasses() throws AIRServiceException {
        List<ServiceClassModel> list = lookupProxy.getServiceClasses();
        HashMap<Integer, ServiceClassModel> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getCode(), model));
        }
        return result;
    }

    public HashMap<Integer, OfferResponse> getOffers() throws AIRServiceException {
        List<OfferResponse> list = lookupProxy.getOffers();
        HashMap<Integer, OfferResponse> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getOfferId(), model));
        }
        return result;
    }

    public HashMap<Integer, OfferTypeResponse> getOffersTypes() throws AIRServiceException {
        List<OfferTypeResponse> list = lookupProxy.getOfferTypes();
        HashMap<Integer, OfferTypeResponse> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getTypeId(), model));
        }
        return result;
    }

    public HashMap<Integer, OfferStateResponse> getOfferStates() throws AIRServiceException {
        List<OfferStateResponse> list = lookupProxy.getOfferStates();
        HashMap<Integer, OfferStateResponse> result = new HashMap<>();
        if (Objects.nonNull(list) && !list.isEmpty()) {
            list.forEach(model -> result.put(model.getStateId(), model));
        }
        return result;
    }

    public HashMap<Integer, List<ServiceClassModel>> getProfilesServiceClasses() throws AIRServiceException {
        HashMap<Integer, List<ServiceClassModel>> map = lookupProxy.getProfilesServiceClasses();
        return map;
    }

    public HashMap<Integer, ServiceOfferingPlan> getServiceOfferingPlansWithBits() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingPlan> map = lookupProxy.getServiceOfferingPlansWithBits();
        return map;
    }

    public HashMap<Integer, HashMap<Integer, DedicatedAccount>> getDedicatedAccountsMap() throws AIRServiceException {
        HashMap<Integer, HashMap<Integer, DedicatedAccount>> map = lookupProxy.getDedicatedAccountsMap();
        return map;
    }

    public void addBarringReason(BarringRequest barringRequest, int barringType) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Calling LookupServiceProxy - addBarringReason()");
            BarringReasonModel reasonModel = new BarringReasonModel(barringRequest.getMsisdn(), barringType,
                    barringRequest.getBarringReason(),
                    barringRequest.getRequestedBy(),
                    barringRequest.getUserId() + "");
            AddBarringReasonRequest addBarringReasonRequest = new AddBarringReasonRequest();
            addBarringReasonRequest.setRequestId(barringRequest.getRequestId());
            addBarringReasonRequest.setSessionId(barringRequest.getSessionId());
            addBarringReasonRequest.setToken(barringRequest.getToken());
            addBarringReasonRequest.setReasonModel(reasonModel);
            lookupProxy.addBarringReason(addBarringReasonRequest);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add barring reason for subscriber [" + barringRequest.getMsisdn() + "]", ex);
        }
    }

    public void deleteBarringReason(BarringRequest barringRequest, int barringType) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Calling LookupServiceProxy - deleteBarringReason()");
            DeleteBarringReasonRequest deleteBarringReasonRequest = new DeleteBarringReasonRequest();
            deleteBarringReasonRequest.setRequestId(barringRequest.getRequestId());
            deleteBarringReasonRequest.setSessionId(barringRequest.getSessionId());
            deleteBarringReasonRequest.setToken(barringRequest.getToken());
            deleteBarringReasonRequest.setMsisdn(barringRequest.getMsisdn());
            deleteBarringReasonRequest.setBarringType(barringType);
            lookupProxy.deleteBarringReason(deleteBarringReasonRequest);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete barring reason for subscriber [" + barringRequest.getMsisdn() + "]", ex);
        }
    }

    public List<RestrictionModel> getFafBlackList() throws AIRServiceException {
        List<RestrictionModel> fafBlackList = lookupProxy.getFafBlackList();
        return fafBlackList;
    }

    public List<RestrictionModel> getFafWhiteList() throws AIRServiceException {
        List<RestrictionModel> fafWhiteList = lookupProxy.getFafWhiteList();
        return fafWhiteList;
    }

    public HashMap<Integer, CommunitiesModel> getCommunities() throws AIRServiceException {
        HashMap<Integer, CommunitiesModel> map = lookupProxy.getCommunities();
        return map;
    }

    public HashMap<Integer, FafPlanModel> getFafPlans() throws AIRServiceException {
        HashMap<Integer, FafPlanModel> map = lookupProxy.getFafPlans();
        return map;
    }

    public HashMap<Integer, ServiceOfferingBitLookupModel> getServiceOfferingBitsLookup() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingBitLookupModel> map = lookupProxy.getBitsLookup();
        return map;
    }

    public HashMap<Integer, ServiceOfferingPlanBitLookupModel> getServiceOfferingPlansLookup() throws AIRServiceException {
        HashMap<Integer, ServiceOfferingPlanBitLookupModel> map = lookupProxy.getServiceOfferingPlans();
        return map;
    }

    public HashMap<Integer, SuperFlexLookupModel> getSuperFlexAddonOffers() throws AIRServiceException {
        HashMap<Integer, SuperFlexLookupModel> map = lookupProxy.getSuperFlexAddonOffers();
        return map;
    }

    public HashMap<Integer, AccountGroupWithBitsModel> getAccountGroupsWithBits() throws AIRServiceException {
        HashMap<Integer, AccountGroupWithBitsModel> map = lookupProxy.retrieveAccountGroupsWithBits();
        return map;
    }

    public List<AccountGroupBitDescModel> getAccountGroupBitsLookup() throws AIRServiceException {
        List<AccountGroupBitDescModel> map = lookupProxy.getAccountGroupBitsLookup();
        return map;
    }
}
