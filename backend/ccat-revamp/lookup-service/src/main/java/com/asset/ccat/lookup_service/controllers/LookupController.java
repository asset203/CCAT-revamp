/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.controllers;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.constants.SuperFlexOffersType;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import com.asset.ccat.lookup_service.models.AdmAccountGroup;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.FeatureModel;
import com.asset.ccat.lookup_service.models.FootPrintPageModel;
import com.asset.ccat.lookup_service.models.HlrProfileModel;
import com.asset.ccat.lookup_service.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.lookup_service.models.LookupModel;
import com.asset.ccat.lookup_service.models.MonetaryLimitModel;
import com.asset.ccat.lookup_service.models.OfferModel;
import com.asset.ccat.lookup_service.models.OfferStateModel;
import com.asset.ccat.lookup_service.models.OfferTypeModel;
import com.asset.ccat.lookup_service.models.ReasonActivityModel;
import com.asset.ccat.lookup_service.models.RefillPaymentProfileModel;
import com.asset.ccat.lookup_service.models.RestrictionModel;
import com.asset.ccat.lookup_service.models.ServiceClassModel;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.SmsActionModel;
import com.asset.ccat.lookup_service.models.SmsTemplateModel;
import com.asset.ccat.lookup_service.models.SmsTemplateParamModel;
import com.asset.ccat.lookup_service.models.SuperFlexLookupModel;
import com.asset.ccat.lookup_service.models.TxCodeRenewalValue;
import com.asset.ccat.lookup_service.models.VoucherServerModel;
import com.asset.ccat.lookup_service.models.ods_models.DSSNodeModel;
import com.asset.ccat.lookup_service.models.ods_models.FlexShareHistoryNodeModel;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import com.asset.ccat.lookup_service.services.BalanceDisputeService;
import com.asset.ccat.lookup_service.services.DisconnectionCodeService;
import com.asset.ccat.lookup_service.services.LkPamClassService;
import com.asset.ccat.lookup_service.services.LookupService;
import com.asset.ccat.lookup_service.services.ServiceOfferingPlansService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahmoud Shehab
 */
@RestController
@RequestMapping(value = Defines.ContextPaths.CACHED_LOOKUPS)
public class LookupController {

  @Autowired
  Environment environment;
  @Autowired
  CachedLookups cachedLookups;
  @Autowired
  LookupService lookupService;
  @Autowired
  LkPamClassService lkPamService;
  @Autowired
  private DisconnectionCodeService codeService;

  @Autowired
  private ServiceOfferingPlansService serviceOfferingPlansService;
  @Autowired
  private BalanceDisputeService balanceDisputeService;

  @GetMapping(value = Defines.ContextPaths.CODES_RENEWAL_VALUE)
  public BaseResponse<Map<Integer, TxCodeRenewalValue>> getAllCodesRenewalValue(
      HttpServletRequest req) throws LookupException {
    CCATLogger.DEBUG_LOGGER.debug(
        "[ LookupController -> getAllCodesRenewalValue()  ] Started successfully.");
    Map<Integer, TxCodeRenewalValue> response = cachedLookups.getTxCodeRenewalValue();
    ;
    CCATLogger.DEBUG_LOGGER.debug(
        "[ LookupController -> getAllCodesRenewalValue()  ] Ended successfully.");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", Defines.SEVERITY.CLEAR, response);
  }

  @GetMapping(value = Defines.ContextPaths.AIR_SERVERS)
  public BaseResponse<List<AIRServer>> getAirServers(HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));
    List<AIRServer> response = cachedLookups.getAirServers();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.REGIONS)
  public BaseResponse<HashMap<String, String>> getRegions(HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));
    HashMap<String, String> response = cachedLookups.getRegions();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.VOUCHER_SERVERS)
  public BaseResponse<List<VoucherServerModel>> getVoucherServers(HttpServletRequest req)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));
    List<VoucherServerModel> response = cachedLookups.getVoucherServers();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.SERVICE_CLASSES)
  public BaseResponse<List<ServiceClassModel>> getServiceClasses(HttpServletRequest req)
      throws Exception {
    List<ServiceClassModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getServiceClasses();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  //    @PostMapping(value = Defines.ContextPaths.SERVICE_CLASSES)
//    public BaseResponse<List<ServiceClassModel>> getAllServiceClasses(HttpServletRequest req) throws AuthenticationException, Exception {
//        List<ServiceClassModel> response = null;
//        CCATLogger.info("IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty("server.port"));
//        response = cachedLookups.getServiceClasses();
//        if (response == null || response.isEmpty()) {
//            return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
//                    "No Data Found", 0,
//                    null);
//        }
//        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
//                "success", 0,
//                response);
//    }
  @GetMapping(value = Defines.WEB_ACTIONS.GET)
  public BaseResponse<List<LookupModel>> getLookups(HttpServletRequest req,
      @RequestParam("lookup-name") String lookupName) throws Exception {
    List<LookupModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    if (Defines.LOOKUPS.UNIT_TYPES.equals(lookupName)) {
      response = cachedLookups.getUnitTypes();
    } else if (Defines.LOOKUPS.LK_LANGUAGES.equals(lookupName)) {
      response = cachedLookups.getLanguage();
    } else if (Defines.LOOKUPS.LK_USAGE_COUNTER_DESC.equals(lookupName)) {
      response = cachedLookups.getUsageCountersDesc();
    }

    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.REFILL_PROFILES)
  public BaseResponse<List<RefillPaymentProfileModel>> getLookups(HttpServletRequest req)
      throws Exception {
    List<RefillPaymentProfileModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getRefillProfiles();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.OFFERS)
  public BaseResponse<List<OfferModel>> getLookupsOffers(HttpServletRequest req) throws Exception {
    List<OfferModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getOffers();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.OFFERS_STATES)
  public BaseResponse<List<OfferStateModel>> getLookupsOfferStates(HttpServletRequest req)
      throws Exception {
    List<OfferStateModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getOfferStates();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.OFFERS_TYPES)
  public BaseResponse<List<OfferTypeModel>> getLookupsOfferTypes(HttpServletRequest req)
      throws Exception {
    List<OfferTypeModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getOfferTypes();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.FEATURES)
  public BaseResponse<List<FeatureModel>> getFeaturesLookup(HttpServletRequest req)
      throws Exception {
    List<FeatureModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = lookupService.getFeaturesLookup();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.MONETARY_LIMITS)
  public BaseResponse<List<MonetaryLimitModel>> getMonetaryLimitsLookup(HttpServletRequest req)
      throws Exception {
    List<MonetaryLimitModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = lookupService.getMonetaryLimitsLookup();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.HLR_PROFILES)
  public BaseResponse<List<HlrProfileModel>> getHlrProfilesLookup(HttpServletRequest req)
      throws UnknownHostException, LookupException {
    List<HlrProfileModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostName() + environment.getProperty(
            "server.port"));
    response = cachedLookups.getHlrProfiles();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.LANGUAGE)
  public BaseResponse getLanguages(HttpServletRequest req) throws Exception {
    List<LookupModel> response = null;
    CCATLogger.DEBUG_LOGGER.info(
        "IP => " + InetAddress.getLocalHost().getHostAddress() + environment.getProperty(
            "server.port"));
    if (!cachedLookups.getLanguage().isEmpty()) {
      CCATLogger.DEBUG_LOGGER.info("Languages" + cachedLookups.getLanguage());
      response = cachedLookups.getLanguage();
    }
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0, response);
  }

  @GetMapping(value = Defines.ContextPaths.DISCONNECTION_CODE)
  public BaseResponse<List<DisconnectionCodeModel>> getAllDisconnectionCodes(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached disconnection codes");

    List<DisconnectionCodeModel> allDisconnectionCodesResponse = codeService.getAllCachedDisconnectionCodes();
    CCATLogger.DEBUG_LOGGER.info(" All cached disconnection codes are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        allDisconnectionCodesResponse);
  }

  @GetMapping(value = Defines.ContextPaths.ACCOUNT_GROUPS)
  public BaseResponse<List<AdmAccountGroup>> getAllAccountGroups(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached account groups");

    List<AdmAccountGroup> allAccountGroups = cachedLookups.getAccountGroups();
    CCATLogger.DEBUG_LOGGER.info(" All cached account groups are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        allAccountGroups);
  }

  @GetMapping(value = Defines.ContextPaths.ACCOUNT_GROUPS_WITH_BITS + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, AccountGroupWithBitsModel>> getAccountGroupsWithBits(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached account groups with bits");

    HashMap<Integer, AccountGroupWithBitsModel> allAccountGroups = cachedLookups.getAccountGroupsWithBits();
    CCATLogger.DEBUG_LOGGER.info(" All cached account groups with bits are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        allAccountGroups);
  }


  @GetMapping(value = Defines.ContextPaths.ACCOUNT_GROUP_BITS_LK + Defines.WEB_ACTIONS.GET)
  public BaseResponse<List<AccountGroupBitDescModel>> getAccountGroupBitsLookup(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached account group bits lookup");

    List<AccountGroupBitDescModel> bitsLookup = cachedLookups.getAccountGroupBitsLookup();
    CCATLogger.DEBUG_LOGGER.info(
        " All cached account groups bits lookup are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        bitsLookup);
  }


  @GetMapping(value = Defines.ContextPaths.PROFILES_SERVICE_CLASSES)
  public BaseResponse<HashMap<Integer, List<AdmServiceClassResponse>>> getAllProfilesServiceClasses(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached profiles service classes");

    HashMap<Integer, List<AdmServiceClassResponse>> allProfilesSC = cachedLookups.getProfilesServiceClasses();
    CCATLogger.DEBUG_LOGGER.info(" All cached profiles service classes are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        allProfilesSC);
  }

  @GetMapping(value = Defines.ContextPaths.SERVICE_OFFERING_PLANS)
  public BaseResponse<List<ServiceOfferingPlan>> getAllServiceOfferingPlans(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached service offering plans");

    List<ServiceOfferingPlan> serviceOfferingPlans = cachedLookups.getServiceOfferingPlans();
    CCATLogger.DEBUG_LOGGER.info(" All cached service offering plans are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        serviceOfferingPlans);
  }

  @GetMapping(value = Defines.ContextPaths.SERVICE_OFFERING_PLANS_WITH_BITS)
  public BaseResponse<HashMap<Integer, ServiceOfferingPlan>> getAllServiceOfferingPlansWithBits(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached service offering plans with bits");

    HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlansWithBits = cachedLookups.getServiceOfferingPlansWithBits();
    CCATLogger.DEBUG_LOGGER.info(
        " All cached service offering plans with bits are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        serviceOfferingPlansWithBits);
  }

  @GetMapping(value = Defines.ContextPaths.LK_MAIN_PRODUCT_TYPES)
  public BaseResponse<HashMap<String, String>> getAllMainProductTypes(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached main product types");

    HashMap<String, String> mainProdyctTypes = cachedLookups.getMainProductTypes();
    CCATLogger.DEBUG_LOGGER.info(" All cached main product types are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        mainProdyctTypes);
  }

  @GetMapping(value = Defines.ContextPaths.ODS_NODES)
  public BaseResponse<List<ODSNodeModel>> getODSNodes(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached ods nodes");
    List<ODSNodeModel> response = cachedLookups.getOdsNodes();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.info("All ods nodes are Retrieved Successfully " + response.toString());
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.FLEX_SHARE_HISTORY_NODES + Defines.WEB_ACTIONS.GET_ALL)
  public BaseResponse<List<FlexShareHistoryNodeModel>> getFlexShareHistoryNodes(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached flex share history nodes");
    List<FlexShareHistoryNodeModel> response = cachedLookups.getFlexShareHistoryNodes();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.info(
        "All flex share history nodes are Retrieved Successfully " + response.toString());
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.DSS_NODES)
  public BaseResponse<List<DSSNodeModel>> getDSSNodes(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached dss nodes");
    List<DSSNodeModel> response = cachedLookups.getDssNodes();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.info("All dss nodes are Retrieved Successfully " + response.toString());
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.LK_ACCOUNT_FLAGS)
  public BaseResponse<HashMap<String, String>> getAllAccountFlag(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached account flags");
    HashMap<String, String> mainProductsTypes = cachedLookups.getAccountFlags();
    CCATLogger.DEBUG_LOGGER.info("All cached account flags are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        mainProductsTypes);
  }

  @GetMapping(value = Defines.ContextPaths.DSS_COLUMNS)
  public BaseResponse<HashMap<String, HashMap<String, String>>> getAllDssColumns(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached dss reports");
    HashMap<String, HashMap<String, String>> dssReport = cachedLookups.getRetrieveDSSColumnNames();
    CCATLogger.DEBUG_LOGGER.info("All cached dss reports are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        dssReport);
  }

  @GetMapping(value = Defines.ContextPaths.BT_STATUS)
  public BaseResponse<HashMap<String, String>> getBtStatus(HttpServletRequest req)
      throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached bt status");
    HashMap<String, String> result = cachedLookups.getBtStatus();
    CCATLogger.DEBUG_LOGGER.info("All bt status are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        result);
  }

  @GetMapping(value = Defines.ContextPaths.DEDICATED_ACCOUNTS)
  public BaseResponse<HashMap<Integer, HashMap<Integer, DedicatedAccount>>> getDedicatedAccountsMap(
      HttpServletRequest req) throws AuthenticationException, LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started Getting cached dedicated accounts map");
    HashMap<Integer, HashMap<Integer, DedicatedAccount>> result = cachedLookups.getDedicatedAccountsMap();
    CCATLogger.DEBUG_LOGGER.info("All dedicated accounts are Retrieved Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        result);
  }

  @GetMapping(value = Defines.ContextPaths.COMMUNITIES)
  public BaseResponse<HashMap<Integer, CommunityAdminModel>> getCommunities(HttpServletRequest req)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached communities list");
    HashMap<Integer, CommunityAdminModel> response = null;
    response = cachedLookups.getCommunities();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached communities list");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.FAF_PLANS)
  public BaseResponse<HashMap<Integer, FafPlanModel>> getFafPlans(HttpServletRequest req)
      throws Exception {
    HashMap<Integer, FafPlanModel> response = null;
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached FAF plans");
    response = cachedLookups.getFafPlans();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached FAF plans");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.FAF_WHITE_LIST)
  public BaseResponse<List<RestrictionModel>> getFafWhiteList(HttpServletRequest req)
      throws Exception {
    List<RestrictionModel> response = null;
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached FAF white list");
    response = cachedLookups.getFafWhiteList();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached FAF white list");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.FAF_BLACK_LIST)
  public BaseResponse<List<RestrictionModel>> getFafBlackList(HttpServletRequest req)
      throws Exception {
    List<RestrictionModel> response = null;
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached FAF black list");
    response = cachedLookups.getFafBlackList();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached FAF black list");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }


  @GetMapping(value = Defines.ContextPaths.FOOTPRINT_PAGES)
  public BaseResponse<HashMap<String, FootPrintPageModel>> getFootPrintPages(HttpServletRequest req)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached footprint pages");
    HashMap<String, FootPrintPageModel> response = null;
    response = cachedLookups.getFootPrintPagesMap();
    if (Objects.isNull(response) || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached footprint pages");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.MARED_CARDS)
  public BaseResponse<List<LookupModel>> getMaredCardsList(HttpServletRequest req)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("Started getting cached mared cards");
    List<LookupModel> response = null;
    response = cachedLookups.getMaredCardsList();
    if (response == null || response.isEmpty()) {
      return new BaseResponse<>(ErrorCodes.ERROR.NO_DATA_FOUND,
          "No Data Found", 0,
          null);
    }
    CCATLogger.DEBUG_LOGGER.debug("Finished getting cached mared cards");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        response);
  }

  @GetMapping(value = Defines.ContextPaths.CALL_ACTIVITIES + Defines.WEB_ACTIONS.GET)
  public BaseResponse<List<ReasonActivityModel>> getCallActivities(HttpServletRequest req,
      @RequestParam("activitiesKey") String activitesKey) throws Exception {
    List<ReasonActivityModel> list = cachedLookups.getCallActivities().get(activitesKey);

    if (list == null || list.isEmpty()) {
      CCATLogger.DEBUG_LOGGER.info("No activities found for given key [" + activitesKey + "]");
      throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND);
    }

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        list);
  }

  @GetMapping(value = Defines.ContextPaths.SERVICE_OFFERING_PLANS_BIT_DETAILS
      + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, ServiceOfferingPlanBitModel>> getAllServiceOfferingPlansBitsWithDetails(
      HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("getAllServiceOfferingPlansBitsWithDetails() : Started");
    HashMap<Integer, ServiceOfferingPlanBitModel> allServicePlansBitsDetails = cachedLookups.getServiceOfferingPlansWithBitsDetails();
    CCATLogger.DEBUG_LOGGER.debug(
        "getAllServiceOfferingPlansBitsWithDetails() : Ended Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        allServicePlansBitsDetails);
  }

  @GetMapping(value = Defines.ContextPaths.SERVICE_OFFERING_BITS_DETAILS + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, ServiceOfferingBitModel>> getServiceOfferingBitsDetails(
      HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("getServiceOfferingBitsDetails() : Started");
    HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitsDetails = cachedLookups.getServiceOfferingBitsDetails();
    CCATLogger.DEBUG_LOGGER.debug("getServiceOfferingBitsDetails() : Ended Successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        serviceOfferingBitsDetails);
  }


  @GetMapping(value = Defines.ContextPaths.SMS_ACTIONS + Defines.WEB_ACTIONS.GET_ALL)
  public BaseResponse<List<SmsActionModel>> getSmsActions(HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.info("Started GetSmsActions request");
    List<SmsActionModel> payload = cachedLookups.getSmsActions();
    CCATLogger.DEBUG_LOGGER.info("GetSmsActions request Ended successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }

  @GetMapping(value = Defines.ContextPaths.SMS_TEMPLATES + Defines.WEB_ACTIONS.GET_ALL)
  public BaseResponse<List<SmsTemplateModel>> getSmsTemplates(HttpServletRequest req)
      throws Exception {
    CCATLogger.DEBUG_LOGGER.info("Started GetSMSTemplates request");
    List<SmsTemplateModel> payload = cachedLookups.getSmsTemplates();
    CCATLogger.DEBUG_LOGGER.info("GetSMSTemplates request Ended successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }

  @GetMapping(value = Defines.ContextPaths.SMS_ACTION_PARAM_MAP + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, List<SmsTemplateParamModel>>> getSmsActionParamMap(
      HttpServletRequest req) throws Exception {
    CCATLogger.DEBUG_LOGGER.debug("Started GetSmsActionParamMap request");
    HashMap<Integer, List<SmsTemplateParamModel>> payload = cachedLookups.getActionParamMap();
    CCATLogger.DEBUG_LOGGER.debug("GetSmsActionParamMap request Ended successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }

  @GetMapping(value = Defines.ContextPaths.SUPER_FLEX_ADDON_OFFERS + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, SuperFlexLookupModel>> getSuperFlexAddonOffers()
      throws LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started getSuperFlexAddonOffers request");
    HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = cachedLookups.getSuperFlexOffersMap();
    HashMap<Integer, SuperFlexLookupModel> payload;
    if (superFlexOffersMap == null || superFlexOffersMap.isEmpty()) {
      CCATLogger.DEBUG_LOGGER.info("No offers were found");
      payload = new HashMap<>();
    } else {
      payload = superFlexOffersMap.get(SuperFlexOffersType.ADDON.id);
    }
    CCATLogger.DEBUG_LOGGER.debug("getSuperFlexAddonOffers request Ended successfully");
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }

  @GetMapping(value = Defines.ContextPaths.SUPER_FLEX_THRESHOLD_OFFERS + Defines.WEB_ACTIONS.GET)
  public BaseResponse<HashMap<Integer, SuperFlexLookupModel>> getSuperFlexThresholdOffers()
      throws LookupException {
    HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = cachedLookups.getSuperFlexOffersMap();
    HashMap<Integer, SuperFlexLookupModel> payload;
    if (superFlexOffersMap == null || superFlexOffersMap.isEmpty()) {
      CCATLogger.DEBUG_LOGGER.info("No offers were found");
      payload = new HashMap<>();
    } else {
      payload = superFlexOffersMap.get(SuperFlexOffersType.THRESHOLD.id);
    }
    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }

  @PostMapping(Defines.ContextPaths.BD_DETAILS_CONFIG)
  public BaseResponse<LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel>> getUserBalanceDisputeDetailsConfig(
      @RequestBody Integer profileId)
      throws LookupException {
    CCATLogger.DEBUG_LOGGER.debug("Started getUserBalanceDisputeDetailsConfig request");
    LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> payload =
        balanceDisputeService.getBalanceDisputeDetailsConfiguration(profileId);
    CCATLogger.DEBUG_LOGGER.debug("getUserBalanceDisputeDetailsConfig request Ended successfully");

    return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success", 0,
        payload);
  }
}
