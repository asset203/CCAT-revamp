package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.cache.LookupsCache;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.DisconnectionCodeModel;
import com.asset.ccat.gateway.models.admin.ReasonActivityModel;
import com.asset.ccat.gateway.models.customer_care.LkOfferModel;
import com.asset.ccat.gateway.models.customer_care.service_offering_lookup_models.ServiceOfferingPlanBitDetailsModel;
import com.asset.ccat.gateway.models.dss.DssFlagModel;
import com.asset.ccat.gateway.models.requests.lookup.GetAllCallActivitiesRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetAllODSActivityHeaderRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetSmsActionParamMapRequest;
import com.asset.ccat.gateway.models.requests.lookup.GetSmsActionsRequest;
import com.asset.ccat.gateway.models.responses.admin.disconnection_codes.GetAllDisconnectionCodesResponse;
import com.asset.ccat.gateway.models.responses.lookup.*;
import com.asset.ccat.gateway.models.shared.*;
import com.asset.ccat.gateway.models.users.LkFeatureModel;
import com.asset.ccat.gateway.models.users.LkHLRProfileModel;
import com.asset.ccat.gateway.models.users.LkMonetaryLimitModel;
import com.asset.ccat.gateway.models.users.MenuItem;
import com.asset.ccat.gateway.proxy.LookupsServiceProxy;
import com.asset.ccat.gateway.redis.model.OffersRedisModel;
import com.asset.ccat.gateway.redis.repository.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author marwa.elshawarby
 */
@Service
public class LookupsService {

    @Autowired
    private LookupsServiceProxy lookupServiceProxy;

    @Autowired
    private LookupsCache lookupsCache;

    private OffersRepository offersRepo;


    public Map<String, List<DssFlagModel>> getDssFlags() throws GatewayException {
        return lookupServiceProxy.getDssFlags();
    }
    public Map<String, Boolean> getPagesLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting pages lookup service...");
        Map<String, Boolean> pages = lookupServiceProxy.getAppPages();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting pages lookup service successfully");
        return pages;
    }

    public GetMenusLKResponse getMenusLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting menu lookup service...");
        List<MenuItem> menus = lookupServiceProxy.getMenusLookup();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting menu lookup service successfully");
        return new GetMenusLKResponse(menus);
    }

    public GetFeaturesLKResponse getFeaturesLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting features lookup service...");
        List<LkFeatureModel> features = lookupServiceProxy.getFeaturesLookup();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting features lookup service successfully");
        return new GetFeaturesLKResponse(features);
    }

    public GetMonetaryLimitsLKResponse getMonetaryLimitsLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting monetary limits lookup service...");
        List<LkMonetaryLimitModel> monetaryLimits = lookupServiceProxy.getMonetaryLimitsLookup();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting monetary limits lookup service successfully");
        return new GetMonetaryLimitsLKResponse(monetaryLimits);
    }

    public GetOffersLKResponse getOffersLookup(PaginationModel pagination) throws GatewayException {
        List<LkOfferModel> offers = null; //lookupsCache.getLkOffers();
        if (offers == null) {
            CCATLogger.DEBUG_LOGGER.debug("Started getting offers from lookup service...");
            offers = lookupServiceProxy.getOffersLookup();
            lookupsCache.setLkOffers(offers);
        }

//        offers = offers.stream().filter(LkOfferModel::getIsDropDownEnabled).toList();
//
//        CCATLogger.DEBUG_LOGGER.debug("Applying pagination: {} for offers size = {}", pagination, offers.size());
//        int offset = pagination.getOffset() != null ? pagination.getOffset() : 0;
//        int fetchCount = pagination.getFetchCount() != null ? pagination.getFetchCount() : offers.size();
//        int endIndex = Math.min(offset + fetchCount, offers.size());
//
//        // Check for invalid offset
//        if (offset >= offers.size()) {
//            lookupsCache.setLkOffers(null);
//            return new GetOffersLKResponse(Collections.emptyList());
//        }

        return new GetOffersLKResponse(offers);
    }


    public GetAllHLRProfileResponse getHLRProfilesLookup() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting HLRProfiles lookup service...");
        List<LkHLRProfileModel> lkHLRProfiles = lookupServiceProxy.getHLRProfilesLookup();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting HLRProfiles lookup service successfully");
        return new GetAllHLRProfileResponse(lkHLRProfiles);
    }

    public List<ADMTransactionType> getTransactiontypes(Integer featureId) throws GatewayException {
        List<ADMTransactionType> list = lookupServiceProxy.getTransactionTypes(featureId);
        if (list == null || list.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No types for feature " + featureId);
            throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        return list;
    }

    public List<ADMTransactionCode> getTransactionCodes(Integer typeId) throws GatewayException {
        List<ADMTransactionCode> list = lookupServiceProxy.getTransactionCodes(typeId);
        if (list == null || list.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No codes for type " + typeId);
            throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        return list;
    }

    public GetAllLookupPamResponse getAllPams() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Starting getAllPamClasses - call service");
        List<LkPamModel> pams = new ArrayList<>();

        List<LkPamModel> pamServices = lookupServiceProxy.getAllPamServices();
        pamServices.forEach(pamService -> {
            pams.add(pamService);
        });
        List<LkPamModel> pamClasses = lookupServiceProxy.getAllPamClasses();
        pamClasses.forEach(pamService -> {
            pams.add(pamService);
        });
        List<LkPamModel> pamSchedules = lookupServiceProxy.getAllPamSchedules();
        pamSchedules.forEach(pamService -> {
            pams.add(pamService);
        });
        List<LkPamModel> pamPeriods = lookupServiceProxy.getAllPamPeriods();
        pamPeriods.forEach(pamService -> {
            pams.add(pamService);
        });
        List<LkPamModel> pamPriorites = lookupServiceProxy.getAllPamPriorites();
        pamPriorites.forEach(pamService -> {
            pams.add(pamService);
        });
        return new GetAllLookupPamResponse(pams);
    }

    public GetAllDisconnectionCodesResponse getAllDisconnectionCodes() throws GatewayException {

        List<DisconnectionCodeModel> codes = lookupServiceProxy.getAllDisconnectionCodes();
        return new GetAllDisconnectionCodesResponse(codes);
    }

    public GetAllAccountGroupsResponse getAllAccountsGroups() throws GatewayException {

        List<AccountGroupModel> accountGroups = lookupServiceProxy.getAllAccountGroups();
        return new GetAllAccountGroupsResponse(accountGroups);
    }

    public GetAllServiceOfferingResponse getAllServiceOfferings() throws GatewayException {

        List<ServiceOfferingPlanModel> serviceOfferings = lookupServiceProxy.getAllServiceOfferingPlans();
        return new GetAllServiceOfferingResponse(serviceOfferings);
    }

    public GetCommunitiesResponse getAllCommunities() throws GatewayException {
        HashMap<Integer, CommunitiesModel> communities = lookupServiceProxy.getAllCommunities();
        if (communities != null && !communities.isEmpty()) {
            return new GetCommunitiesResponse(new ArrayList<>(communities.values()));
        } else {
            return new GetCommunitiesResponse();
        }
    }

    public HashMap<Integer, FafPlanModel> getFafPlansAsMap() throws GatewayException {
        HashMap<Integer, FafPlanModel> fafPlans = lookupServiceProxy.getAllFafPlans();
        return fafPlans;
    }

    public GetAllFafPlansResponse getAllFafPlans() throws GatewayException {
        HashMap<Integer, FafPlanModel> fafPlans = lookupServiceProxy.getAllFafPlans();
        if (fafPlans != null && !fafPlans.isEmpty()) {
            return new GetAllFafPlansResponse(new ArrayList<>(fafPlans.values()));
        } else {
            return new GetAllFafPlansResponse();
        }

    }

    public HashMap<String, FootPrintPageModel> getFootPrintPages() throws GatewayException {
        return lookupServiceProxy.getFootPrintPages();
    }

    public Map<String, Boolean> getAppPages() throws GatewayException {
        return lookupServiceProxy.getAppPages();
    }

    public List<String> getVIPSubscribers() throws GatewayException {
        return lookupServiceProxy.getVIPSubscribers();
    }

    public GetMaredCardsLKResponse getMaredCards() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting mared cards lookup ...");
        List<LookupModel> maredCards = lookupServiceProxy.getMaredCards();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting mared cards lookup successfully");
        return new GetMaredCardsLKResponse(maredCards);
    }

    public List<FafIndicatorModel> getAllFafIndicators() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting faf indicators lookup ...");
        return lookupServiceProxy.getAllFafIndicators();
    }

    public List<ReasonActivityModel> getCallActivities(GetAllCallActivitiesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting call activities lookup ...");
        String key = request.getActivityType().name + "-" + (request.getParentId() == null ? 0 : request.getParentId());
        CCATLogger.DEBUG_LOGGER.debug("Getting call activities with key : [" + key + "]");
        List<ReasonActivityModel> list = lookupServiceProxy.getCallActivities(key);
        CCATLogger.DEBUG_LOGGER.debug("Ended getting call activities lookup successfully");
        return list;
    }

    public HashMap<Integer, ServiceOfferingPlanBitDetailsModel> getAllServiceOfferingPlansWithBitDetails() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("LookupService -> getAllServiceOfferingPlansWithBitDetails() : Started");
        HashMap<Integer, ServiceOfferingPlanBitDetailsModel> response = lookupServiceProxy.retrieveServiceOfferingPlansWithBitsDetails();
        CCATLogger.DEBUG_LOGGER.debug("LookupService -> getAllServiceOfferingPlansWithBitDetails() : Ended Successfully");
        return response;
    }

    public GetSmsActionsResponse getSmsActions(GetSmsActionsRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting SmsActions lookup ...");
        GetSmsActionsResponse smsActions = lookupServiceProxy.getSmsActions(request);
        CCATLogger.DEBUG_LOGGER.debug("Ended getting SmsActions lookup successfully");
        return smsActions;
    }

    public GetSmsActionParamMapResponse getSmsActionParamMap(GetSmsActionParamMapRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting SmsActionParamMap lookup ...");
        GetSmsActionParamMapResponse smsActions = lookupServiceProxy.getSmsActionParamMap(request);
        CCATLogger.DEBUG_LOGGER.debug("Ended getting SmsActionParamMap lookup successfully");
        return smsActions;
    }

    public HashMap<Integer, AccountGroupWithBitsModel> getAccountGroupsWithBits() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting accountGroupsWithBits lookup ...");
        HashMap<Integer, AccountGroupWithBitsModel> accountGroups = lookupServiceProxy.getAccountGroupsWithBits();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting accountGroupsWithBits lookup successfully");
        return accountGroups;
    }

    public GetODSActivityHeaderResponse getODSActivityHeader(GetAllODSActivityHeaderRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started getting ODSActivityHeader lookup ...");
        GetODSActivityHeaderResponse odsActivityHeader = lookupServiceProxy.getODSActivityHeader();
        CCATLogger.DEBUG_LOGGER.debug("Ended getting ODSActivityHeader lookup successfully");
        return odsActivityHeader;
    }
}
