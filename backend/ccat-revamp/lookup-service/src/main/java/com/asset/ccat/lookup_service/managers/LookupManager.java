package com.asset.ccat.lookup_service.managers;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.database.dao.*;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.factories.CustomThreadFactory;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.*;
import com.asset.ccat.lookup_service.models.ods_models.*;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import com.asset.ccat.lookup_service.services.AccountGroupsService;
import com.asset.ccat.lookup_service.tasks.RefreshLookupsTask;
import com.asset.ccat.lookup_service.tasks.RefreshServiceClassTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mahmoud Shehab
 */
@Component
public class LookupManager {

  private final ScheduledThreadPoolExecutor poolTaskScheduler;
  private final ReentrantLock reentrantLock;
  private final HashMap<String, Object> cachedLookupsMap;
  @Autowired
  private LookupsDao lookupDao;
  @Autowired
  private LkPamClassDao lkPamClassDao;
  @Autowired
  private LkPamPeriodDao lkPamPeriodDao;
  @Autowired
  private LkPamPriorityDao lkPamPriorityDao;
  @Autowired
  private LkPamServiceDao lkPamServiceDao;
  @Autowired
  private LkPamScheduleDao lkPamScheduleDao;
  @Autowired
  private TransactionsDao transactionsDao;
  @Autowired
  private DisconnectionCodeDao disconnectionDao;
  @Autowired
  private DSSColumnNameDao dSSColumnNameDao;
  @Autowired
  private ODSAccountHistoryDao oDSAccountHistoryDao;
  @Autowired
  private ODSNodesDao odsNodesDao;
  @Autowired
  private DSSNodesDao dssNodesDao;
  @Autowired
  private FlexShareHistoryNodesDao flexShareHistoryNodesDao;
  @Autowired
  private FafPlansDao fafPlansDao;
  @Autowired
  private FafRestrictionsDao fafRestrictionsDao;
  @Autowired
  private CommunityAdminDao communityAdminDao;
  @Autowired
  private CachedLookups cachedLookups;
  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private Properties properties;
  @Autowired
  private TxCodesRenewalValueDao txCodesRenewalValueDao;
  @Autowired
  private ServiceOfferingDao serviceOfferingDao;
  @Autowired
  private ReasonActivityDao reasonActivityDao;
  @Autowired
  private AccountGroupsService accountGroupsService;
  @Autowired
  private AccountGroupsBitsDao accountGroupsBitsDao;

  @Autowired
  private PagesDao pagesDao;

  @Autowired
  private VIPDao vipDao;


  public LookupManager() {
    poolTaskScheduler = new ScheduledThreadPoolExecutor(2,
        new CustomThreadFactory("RefreshLookupsPool", "RefreshLookupsTask"));
    reentrantLock = new ReentrantLock();
    cachedLookupsMap = new HashMap<>();
  }

  public void init() {
    Runnable refreshLockupsTask = applicationContext.getBean(RefreshLookupsTask.class, this);
    Runnable refreshServiceClassTask = applicationContext.getBean(RefreshServiceClassTask.class, this);
    poolTaskScheduler.scheduleWithFixedDelay(refreshLockupsTask, 0, properties.getLookupsRefreshTask(), TimeUnit.MILLISECONDS);
    poolTaskScheduler.scheduleWithFixedDelay(refreshServiceClassTask, 0, properties.getLookupsRefreshTask(), TimeUnit.MILLISECONDS);
  }

  public void refreshServiceClassLookups() {
    try {
      CCATLogger.CACHE_STATISTICS_LOGGER.info("Refreshing serviceClasses");
      long start = System.currentTimeMillis();
      List<ServiceClassModel> serviceClasses = lookupDao.retrieveServiceClasses();
      if (serviceClasses != null && !serviceClasses.isEmpty()) {
        reentrantLock.lock();
        cachedLookups.setServiceClasses(serviceClasses);
      }
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "serviceClasses retrieved successfully with count [" + (serviceClasses == null ? "0"
              : serviceClasses.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - start) + "] ms");

    } catch (LookupException ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while refreshing lookups");
      CCATLogger.ERROR_LOGGER.error("Error while refreshing lookups", ex);
    } finally {
      reentrantLock.unlock();
    }
  }

  public void refreshLookups() {
    try {
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "########################## Refresh Cache ######################");
      long start = System.currentTimeMillis();
      long subStart = System.currentTimeMillis();

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve air servers");
      List<AIRServer> airservers = lookupDao.retrieveAirServers();
      cachedLookupsMap.put("airservers", airservers);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved air servers successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "AIR servers retrieved successfully with count [" + (airservers == null ? "0"
              : airservers.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve regions");
      HashMap<String, String> regions = lookupDao.retrieveRegions();
      cachedLookupsMap.put("regions", regions);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved regions successfully " + regions.get("Other"));
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Regions retrieved successfully with count [" + (Objects.isNull(regions) ? "0"
              : regions.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve voucher servers");
      List<VoucherServerModel> voucherServers = lookupDao.retrieveVoucherServers();
      cachedLookupsMap.put("voucherServers", voucherServers);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved voucher servers successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Voucher servers retrieved successfully with count [" + (voucherServers == null ? "0"
              : voucherServers.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve communities");
      HashMap<Integer, CommunityAdminModel> communities = communityAdminDao.retrieveAllCommunitiesAsMap();
      cachedLookupsMap.put("communities", communities);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved communities successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Communities retrieved successfully with count [" + (communities == null ? "0"
              : communities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve unitTypes");
      subStart = System.currentTimeMillis();
      List<LookupModel> unitTypes = lookupDao.retrieveLookup(Defines.LOOKUPS.UNIT_TYPES);
      cachedLookupsMap.put("unitTypes", unitTypes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved unitTypes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Unit Types retrieved successfully with count [" + (unitTypes == null ? "0"
              : unitTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve languages");
      subStart = System.currentTimeMillis();
      List<LookupModel> languages = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_LANGUAGES);
      cachedLookupsMap.put("languages", languages);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved languages successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Languages retrieved successfully with count [" + (languages == null ? "0"
              : languages.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve usage");
      subStart = System.currentTimeMillis();
      List<LookupModel> usage = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_USAGE_COUNTER_DESC);
      cachedLookupsMap.put("usage", usage);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved usage successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Usage retrieved successfully with count [" + (usage == null ? "0" : usage.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve refillProfiles");
      subStart = System.currentTimeMillis();
      List<RefillPaymentProfileModel> refillProfiles = lookupDao.retrieveRefillProfiles();
      cachedLookupsMap.put("refillProfiles", refillProfiles);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved refillProfiles successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Refill Profiles retrieved successfully with count [" + (refillProfiles == null ? "0"
              : refillProfiles.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve offers");
      subStart = System.currentTimeMillis();
      List<OfferModel> offers = lookupDao.retrieveOffers();
      cachedLookupsMap.put("offers", offers);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved offers successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offers retrieved successfully with count [" + (offers == null ? "0" : offers.size())
              + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve offerStates");
      subStart = System.currentTimeMillis();
      List<OfferStateModel> offerStates = lookupDao.retrieveOfferStates();
      cachedLookupsMap.put("offerStates", offerStates);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved offerStates successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offer States retrieved successfully with count [" + (offerStates == null ? "0"
              : offerStates.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve offerTypes");
      subStart = System.currentTimeMillis();
      List<OfferTypeModel> offerTypes = lookupDao.retrieveOfferTypes();
      cachedLookupsMap.put("offerTypes", offerTypes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved offerTypes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offer Types retrieved successfully with count [" + (offerTypes == null ? "0"
              : offerTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve hlr profiles");
      subStart = System.currentTimeMillis();
      List<HlrProfileModel> hlrProfiles = lookupDao.retrieveHLRProfiles();
      cachedLookupsMap.put("hlrProfiles", hlrProfiles);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved hlr profiles successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "HLR profiles retrieved successfully with count [" + (hlrProfiles == null ? "0"
              : hlrProfiles.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam classes");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamClasses = lkPamClassDao.retrievePamClasses();
      cachedLookupsMap.put("pamClasses", pamClasses);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved pam classes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Classes retrieved successfully with count [" + (pamClasses == null ? "0"
              : pamClasses.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam services");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamServices = lkPamServiceDao.retrievePamServices();
      cachedLookupsMap.put("pamServices", pamServices);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved pam services successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam services retrieved successfully with count [" + (pamServices == null ? "0"
              : pamServices.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam periods");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamPeriods = lkPamPeriodDao.retrievePamPeriods();
      cachedLookupsMap.put("pamPeriods", pamPeriods);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved pam periods successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam periods retrieved successfully with count [" + (pamPeriods == null ? "0"
              : pamPeriods.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam priorities");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamPriorities = lkPamPriorityDao.retrievePamPrioritys();
      cachedLookupsMap.put("pamPriorities", pamPriorities);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved pam priorities successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Priorities retrieved successfully with count [" + (pamPriorities == null ? "0"
              : pamPriorities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam schedules");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamSchedules = lkPamScheduleDao.retrievePamSchedules();
      cachedLookupsMap.put("pamSchedules", pamSchedules);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved pam schedules successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Schedules retrieved successfully with count [" + (pamSchedules == null ? "0"
              : pamSchedules.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve transaction types");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<LkTransactionType>> types = transactionsDao.retrieveAllTypes();
      cachedLookupsMap.put("types", types);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved transaction types successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Transaction types retrieved successfully with count [" + (types == null ? "0"
              : types.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve transaction codes");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<LkTransactionCode>> codes = transactionsDao.retrieveCodes();
      cachedLookupsMap.put("codes", codes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved transaction codes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Transaction codes retrieved successfully with count [" + (codes == null ? "0"
              : codes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Disconnection Codes");
      subStart = System.currentTimeMillis();
      List<DisconnectionCodeModel> disconnectionCodes = disconnectionDao.retrieveDisconnectionCodes();
      cachedLookupsMap.put("disconnectionCodes", disconnectionCodes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved disconnection codes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Disconnection Codes retrieved successfully with count [" + (disconnectionCodes == null
              ? "0" : disconnectionCodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Account Groups");
      subStart = System.currentTimeMillis();
      List<AdmAccountGroup> accountGroups = lookupDao.retrieveAccountGroups();
      cachedLookupsMap.put("accountGroups", accountGroups);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Account Groups successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Groups retrieved successfully with count [" + (accountGroups == null ? "0"
              : accountGroups.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Profiles Service Classes");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<AdmServiceClassResponse>> profilesSCMap = lookupDao.retrieveProfilesServiceClasses();
      cachedLookupsMap.put("profilesServiceClasses", profilesSCMap);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Profiles Service Classes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Profiles Service Classes retrieved successfully with count [" + (profilesSCMap == null
              ? "0" : profilesSCMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans with bits");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingPlan> serviceOffgPlanWithBits = lookupDao.retrieveServiceOfferingPlansWithBits();
      cachedLookupsMap.put("serviceOffPlanWithBits", serviceOffgPlanWithBits);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Plans with bits successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans with bits retrieved successfully with count [" + (
              serviceOffgPlanWithBits == null ? "0" : serviceOffgPlanWithBits.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans");
      subStart = System.currentTimeMillis();
      List<ServiceOfferingPlan> serviceOfferingPlans = lookupDao.retrieveServiceOfferingPlans();
      cachedLookupsMap.put("serviceOfferingPlans", serviceOfferingPlans);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Plans successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans retrieved successfully with count [" + (
              serviceOfferingPlans == null ? "0" : serviceOfferingPlans.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve main product types");
      subStart = System.currentTimeMillis();
      HashMap<String, String> mainProductTypes = lookupDao.retrieveMainProductTypes();
      cachedLookupsMap.put("mainProductTypes", mainProductTypes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved main product types successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Main product types retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve btStatus");
      subStart = System.currentTimeMillis();
      HashMap<String, String> btStatus = lookupDao.retrieveBtStatus();
      cachedLookupsMap.put("btStatus", btStatus);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved btStatus successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "btStatus retrieved successfully with count [" + (btStatus == null ? "0"
              : btStatus.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods activities");
      subStart = System.currentTimeMillis();
      HashMap<String, ODSActivityModel> odsActivities = oDSAccountHistoryDao.retrieveOdsActivities();
      cachedLookupsMap.put("odsActivites", odsActivities);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved ods activities successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods activities retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods activities header");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ODSActivityHeader> odsHeaders = oDSAccountHistoryDao.retrieveOdsHeaders();
      cachedLookupsMap.put("odsHeaders", odsHeaders);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved ods activities header successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods activities header retrieved successfully with count [" + (mainProductTypes == null
              ? "0" : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods header mapping");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> odsHeadersMapping = oDSAccountHistoryDao.retrieveOdsHeadersMapping();
      cachedLookupsMap.put("odsHeadersMapping", odsHeadersMapping);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved ods header mapping successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods header mapping retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods details mapping");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<ODSActivityDetailsMapping>> odsDetailsMapping = oDSAccountHistoryDao.retrieveOdsDetailsMapping();
      cachedLookupsMap.put("odsDetailsMapping", odsDetailsMapping);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve ods details successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods details retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods nodes");
      subStart = System.currentTimeMillis();
      List<ODSNodeModel> odsNodes = odsNodesDao.retrieveODSNodesForCachedLookup();
      cachedLookupsMap.put("odsNodes", odsNodes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve ods nodes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods nodes retrieved successfully with count [" + (odsNodes == null ? "0"
              : odsNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve dss nodes");
      subStart = System.currentTimeMillis();
      List<DSSNodeModel> dssNodes = dssNodesDao.retrieveDSSNodesForCachedLookup();
      cachedLookupsMap.put("dssNodes", dssNodes);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved dss nodes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "dss nodes retrieved successfully with count [" + (dssNodes == null ? "0"
              : dssNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve account flags");
      subStart = System.currentTimeMillis();
      HashMap<String, String> accountFlags = lookupDao.retrieveAccountsFlags();
      cachedLookupsMap.put("accountFlags", accountFlags);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve accountFlags");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "account Flags retrieved successfully with count [" + (accountFlags == null ? "0"
              : accountFlags.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve DSSColumnNames");
      subStart = System.currentTimeMillis();
      HashMap<String, HashMap<String, String>> retrieveDSSColumnNames = dSSColumnNameDao.retrieveDSSColumnNames();
      cachedLookupsMap.put("retrieveDSSColumnNames", retrieveDSSColumnNames);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved DSSColumnNames successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "DSSColumnNames retrieved successfully with count [" + (retrieveDSSColumnNames == null
              ? "0" : retrieveDSSColumnNames.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve dedicated accounts map");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap = lookupDao.retrieveServiceClassesDAsMap();
      cachedLookupsMap.put("dedicatedAccountsMap", dedicatedAccountsMap);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved dedicated accounts map successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Dedicated accounts map retrieved successfully with count [" + (
              dedicatedAccountsMap == null ? "0" : dedicatedAccountsMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve all FAF plans");
      subStart = System.currentTimeMillis();
      HashMap<Integer, FafPlanModel> fafPlans = fafPlansDao.retrieveAllFafPlans();
      cachedLookupsMap.put("fafPlans", fafPlans);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved all FAF plans successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "All FAF plans retrieved successfully with count [" + (fafPlans == null ? "0"
              : fafPlans.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve FAF whitelist");
      subStart = System.currentTimeMillis();
      List<RestrictionModel> fafWhiteList = fafRestrictionsDao.retrieveFafWhiteList();
      cachedLookupsMap.put("fafWhiteList", fafWhiteList);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved FAF whitelist successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "FAF whitelist retrieved successfully with count [" + (fafWhiteList == null ? "0"
              : fafWhiteList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve FAF blacklist");
      subStart = System.currentTimeMillis();
      List<RestrictionModel> fafBlackList = fafRestrictionsDao.retrieveFafBlackList();
      cachedLookupsMap.put("fafBlackList", fafBlackList);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved FAF blacklist successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "FAF blacklist retrieved successfully with count [" + (fafBlackList == null ? "0"
              : fafBlackList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve footPrintPages");
      HashMap<String, FootPrintPageModel> footPrintPagesMap = lookupDao.retrieveFootPrintPageMap();
      cachedLookupsMap.put("footPrintPages", footPrintPagesMap);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved footPrintPages successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "footPrintPages retrieved successfully with count [" + (footPrintPagesMap == null ? "0"
              : footPrintPagesMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve TX Codes Renewal value");
      subStart = System.currentTimeMillis();
      Map<Integer, TxCodeRenewalValue> renewalsValueList = txCodesRenewalValueDao.retrieveCodesRenewalsValues();
      cachedLookupsMap.put("codesRenewalsValues", renewalsValueList);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved TX Codes Renewal value successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "TX Codes Renewal value retrieved successfully with count [" + (renewalsValueList == null
              ? "0" : renewalsValueList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve mared cards");
      subStart = System.currentTimeMillis();
      List<LookupModel> maredCards = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_MAREDCARD_LIST);
      cachedLookupsMap.put("maredCards", maredCards);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved mared cards successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Mared cards retrieved successfully with count [" + (maredCards == null ? "0"
              : maredCards.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Call Activities");
      subStart = System.currentTimeMillis();
      HashMap<String, List<ReasonActivityModel>> activities = reasonActivityDao.retrieveActivitiesParentsMap();
      cachedLookupsMap.put("callActivities", activities);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Call Activities successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Call Activities retrieved successfully with count [" + (activities == null ? "0"
              : activities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans With Bits Details");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingPlanBitModel> serviceOfferingPlanBitModelHashMap = serviceOfferingDao.retrieveServiceOfferingPlanBitDetails();
      cachedLookupsMap.put("serviceOfferingPlansWithBitsDetails",
          serviceOfferingPlanBitModelHashMap);
//      CCATLogger.DEBUG_LOGGER.info(
//          "Retrieved Service Offering Plans With Bits Details successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans With Bits Details retrieved successfully with count [" + (
              renewalsValueList == null ? "0" : renewalsValueList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Bit Details");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitModelHashMap = serviceOfferingDao.retrieveServiceOfferingBitsDetails();
      cachedLookupsMap.put("serviceOfferingBitDetails", serviceOfferingBitModelHashMap);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Bits Details successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Bits Details retrieved successfully with count [" + (
              serviceOfferingBitModelHashMap == null ? "0" : serviceOfferingBitModelHashMap.size())
              + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve SMSActions");
      subStart = System.currentTimeMillis();
      List<SmsActionModel> smsActions = lookupDao.retrieveSmsActions();
      cachedLookupsMap.put("smsActions", smsActions);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved SMSActions successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service SMSActions retrieved successfully with count [" + (smsActions == null ? "0"
              : smsActions.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve SMSTemplates");
      subStart = System.currentTimeMillis();
      List<SmsTemplateModel> smsTemplates = lookupDao.retrieveSmsTemplates();
      cachedLookupsMap.put("smsTemplates", smsTemplates);

//      CCATLogger.DEBUG_LOGGER.info("Retrieved smsTemplates successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service smsTemplates retrieved successfully with count [" + (smsTemplates == null ? "0"
              : smsTemplates.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Sms TemplateParams");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateParams = lookupDao.retrieveSmsTemplateParam();
      cachedLookupsMap.put("smsTemplateParams", smsTemplateParams);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Sms TemplateParams successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Sms TemplateParams retrieved successfully with count [" + (smsTemplateParams == null
              ? "0" : smsTemplateParams.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Super Flex Offers Map");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = lookupDao.retrieveSuperFLexOffersMap();
      cachedLookupsMap.put("superFlexOffersMap", superFlexOffersMap);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Super Flex Offers Map successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Super Flex Offers Map retrieved successfully with count [" + (superFlexOffersMap == null
              ? "0" : superFlexOffersMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

//      CCATLogger.DEBUG_LOGGER.info("Start retrieve Account Groups With Bits");
      subStart = System.currentTimeMillis();
      HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits = accountGroupsService.getAccountGroupsWithBits();
      cachedLookupsMap.put("accountGroupsWithBits", accountGroupsWithBits);
//      CCATLogger.DEBUG_LOGGER.info("Retrieved Account Groups With Bits successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Groups With Bits retrieved successfully with count [" + (
              accountGroupsWithBits == null ? "0" : accountGroupsWithBits.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      subStart = System.currentTimeMillis();
      List<AccountGroupBitDescModel> accountGroupBitsLookup = accountGroupsBitsDao.retrieveAccountGroupsBitswithDesc();
      cachedLookupsMap.put("accountGroupBitsLookup", accountGroupBitsLookup);

      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Group Bits Lookup retrieved successfully with count [" + (
              accountGroupBitsLookup == null ? "0" : accountGroupBitsLookup.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      subStart = System.currentTimeMillis();
      List<FlexShareHistoryNodeModel> flexShareHistoryNodes = flexShareHistoryNodesDao.retrieveFlexShareHistoryNodesForCachedLookup();
      cachedLookupsMap.put("flexHistoryNodes", flexShareHistoryNodes);

      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "flex share history nodes retrieved successfully with count [" + (
              flexShareHistoryNodes == null ? "0" : flexShareHistoryNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");
      Map<Integer, List<Integer>> menusFeatures = pagesDao.getMenusFeatures();
      cachedLookupsMap.put("menusFeatures", menusFeatures);

      Map<String, Boolean> featuresVIPMap = vipDao.getFeatureVIPMap();
      cachedLookupsMap.put("featuresVIPMap", featuresVIPMap);


      List<PageModel> pages = pagesDao.getCustomerCarePages();
      cachedLookupsMap.put("pages", pages);

      List<String> vipSubscribers = vipDao.getVIPSubscribers();
      cachedLookupsMap.put("vipSubscribers", vipSubscribers);

      List<FAFIndicatorModel> fafIndicators = fafPlansDao.findFAFIndicators();
      cachedLookupsMap.put("fafIndicators", fafIndicators);

      swapCachedLookups(cachedLookupsMap);

      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "########################## Refresh Cache Done Successfully In [" + (
              System.currentTimeMillis() - start) + "]ms ######################");

    } catch (Exception ex) {
      CCATLogger.CACHE_STATISTICS_LOGGER.error("########################## Refresh Cache Failed ######################");
      CCATLogger.DEBUG_LOGGER.error("Error while refreshing lookups");
      CCATLogger.ERROR_LOGGER.error("Error while refreshing lookups", ex);
    }
  }

  private void swapCachedLookups(HashMap map) {
    try {
      reentrantLock.lock();
      if (Objects.nonNull(map)) {
        if (Objects.nonNull(map.get("airservers"))) {
          List<AIRServer> airservers = (List<AIRServer>) map.get("airservers");
          cachedLookups.setAirServers(airservers);
        }
        if (Objects.nonNull(map.get("regions"))) {
          HashMap<String, String> regions = (HashMap<String, String>) map.get("regions");
          cachedLookups.setRegions(regions);
        }

        if (Objects.nonNull(map.get("voucherServers"))) {
          List<VoucherServerModel> voucherServersList = (List<VoucherServerModel>) map.get("voucherServers");
          cachedLookups.setVoucherServers(voucherServersList);
        }
        if (Objects.nonNull(map.get("communities"))) {
          HashMap<Integer, CommunityAdminModel> communities = (HashMap<Integer, CommunityAdminModel>) map.get("communities");
          cachedLookups.setCommunities(communities);
        }
        if (Objects.nonNull(map.get("unitTypes"))) {
          List<LookupModel> unitTypes = (List<LookupModel>) map.get("unitTypes");
          cachedLookups.setUnitTypes(unitTypes);
        }
        if (Objects.nonNull(map.get("languages"))) {
          List<LookupModel> languages = (List<LookupModel>) map.get("languages");
          cachedLookups.setLanguage(languages);
        }
        if (Objects.nonNull(map.get("usage"))) {
          List<LookupModel> usage = (List<LookupModel>) map.get("usage");
          cachedLookups.setUsageCountersDescMap(usage);
        }
        if (Objects.nonNull(map.get("pamClasses"))) {
          List<LkPamModel> pamClasses = (List<LkPamModel>) map.get("pamClasses");
          cachedLookups.setPamClasses(pamClasses);
        }
        if (Objects.nonNull(map.get("pamServices"))) {
          List<LkPamModel> pamServices = (List<LkPamModel>) map.get("pamServices");
          cachedLookups.setPamServices(pamServices);
        }
        if (Objects.nonNull(map.get("pamPeriods"))) {
          List<LkPamModel> pamPeriods = (List<LkPamModel>) map.get("pamPeriods");
          cachedLookups.setPamPeriods(pamPeriods);
        }
        if (Objects.nonNull(map.get("pamPriorities"))) {
          List<LkPamModel> pamPriorities = (List<LkPamModel>) map.get("pamPriorities");
          cachedLookups.setPamPriorities(pamPriorities);
        }
        if (Objects.nonNull(map.get("pamSchedules"))) {
          List<LkPamModel> pamSchedules = (List<LkPamModel>) map.get("pamSchedules");
          cachedLookups.setPamSchedules(pamSchedules);
        }

        if (Objects.nonNull(map.get("refillProfiles"))) {
          List<RefillPaymentProfileModel> refillProfiles = (List<RefillPaymentProfileModel>) map.get("refillProfiles");
          cachedLookups.setRefillProfiles(refillProfiles);
        }
        if (Objects.nonNull(map.get("offers"))) {
          List<OfferModel> offers = (List<OfferModel>) map.get("offers");
          cachedLookups.setOffers(offers);
        }
        if (Objects.nonNull(map.get("offerStates"))) {
          List<OfferStateModel> offerStates = (List<OfferStateModel>) map.get("offerStates");
          cachedLookups.setOfferStates(offerStates);
        }
        if (Objects.nonNull(map.get("offerTypes"))) {
          List<OfferTypeModel> offerTypes = (List<OfferTypeModel>) map.get("offerTypes");
          cachedLookups.setOfferTypes(offerTypes);
        }
        if (Objects.nonNull(map.get("hlrProfiles"))) {
          List<HlrProfileModel> hlrProfiles = (List<HlrProfileModel>) map.get("hlrProfiles");
          cachedLookups.setHlrProfiles(hlrProfiles);
        }
        if (Objects.nonNull(map.get("disconnectionCodes"))) {
          List<DisconnectionCodeModel> disconnectionCodes = (List<DisconnectionCodeModel>) map.get("disconnectionCodes");
          cachedLookups.setDisconnectionCodes(disconnectionCodes);
        }
        if (Objects.nonNull(map.get("types"))) {
          HashMap<Integer, List<LkTransactionType>> types = (HashMap<Integer, List<LkTransactionType>>) map.get("types");
          cachedLookups.setTransactiontypes(types);
        }
        if (Objects.nonNull(map.get("codes"))) {
          HashMap<Integer, List<LkTransactionCode>> codes = (HashMap<Integer, List<LkTransactionCode>>) map.get("codes");
          cachedLookups.setTransactionCodes(codes);
        }

        if (Objects.nonNull(map.get("profilesServiceClasses"))) {
          HashMap<Integer, List<AdmServiceClassResponse>> profilesSCMap = (HashMap<Integer, List<AdmServiceClassResponse>>) map.get("profilesServiceClasses");
          cachedLookups.setProfilesServiceClasses(profilesSCMap);
        }

        if (Objects.nonNull(map.get("accountGroups"))) {
          List<AdmAccountGroup> accountGroups = (List<AdmAccountGroup>) map.get("accountGroups");
          cachedLookups.setAccountGroups(accountGroups);
        }

        if (Objects.nonNull(map.get("serviceOffPlanWithBits"))) {
          HashMap<Integer, ServiceOfferingPlan> serviceOffgPlansWithBits = (HashMap<Integer, ServiceOfferingPlan>) map.get("serviceOffPlanWithBits");
          cachedLookups.setServiceOfferingPlansWithBits(serviceOffgPlansWithBits);
        }

        if (Objects.nonNull(map.get("serviceOfferingPlans"))) {
          List<ServiceOfferingPlan> serviceOffgPlans = (List<ServiceOfferingPlan>) map.get("serviceOfferingPlans");
          cachedLookups.setServiceOfferingPlans(serviceOffgPlans);
        }

        if (Objects.nonNull(map.get("mainProductTypes"))) {
          HashMap<String, String> mainProductTypes = (HashMap<String, String>) map.get("mainProductTypes");
          cachedLookups.setMainProductTypes(mainProductTypes);
        }
        if (Objects.nonNull(map.get("odsActivites"))) {
          HashMap<String, ODSActivityModel> newMap = (HashMap<String, ODSActivityModel>) map.get("odsActivites");
          cachedLookups.setOdsActivites(newMap);
        }
        if (Objects.nonNull(map.get("odsHeaders"))) {
          HashMap<Integer, ODSActivityHeader> newMap = (HashMap<Integer, ODSActivityHeader>) map.get("odsHeaders");
          cachedLookups.setOdsActivityHeaders(newMap);
        }
        if (Objects.nonNull(map.get("odsHeadersMapping"))) {
          HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> newMap = (HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>) map.get("odsHeadersMapping");
          cachedLookups.setOdsHeadersMapping(newMap);
        }
        if (Objects.nonNull(map.get("odsDetailsMapping"))) {
          HashMap<Integer, List<ODSActivityDetailsMapping>> newMap = (HashMap<Integer, List<ODSActivityDetailsMapping>>) map.get("odsDetailsMapping");
          cachedLookups.setOdsDetailsMapping(newMap);
        }
        if (Objects.nonNull(map.get("accountFlags"))) {
          HashMap<String, String> newMap = (HashMap<String, String>) map.get("accountFlags");
          cachedLookups.setAccountFlags(newMap);
        }
        if (Objects.nonNull(map.get("retrieveDSSColumnNames"))) {
          HashMap<String, HashMap<String, String>> newMap = (HashMap<String, HashMap<String, String>>) map.get("retrieveDSSColumnNames");
          cachedLookups.setRetrieveDSSColumnNames(newMap);
        }
        if (Objects.nonNull(map.get("btStatus"))) {
          HashMap<String, String> newMap = (HashMap<String, String>) map.get("btStatus");
          cachedLookups.setBtStatus(newMap);
        }
        if (Objects.nonNull(map.get("odsNodes"))) {
          List<ODSNodeModel> odsNodeModelList = (List<ODSNodeModel>) map.get("odsNodes");
          cachedLookups.setOdsNodes(odsNodeModelList);
        }
        if (Objects.nonNull(map.get("dssNodes"))) {
          List<DSSNodeModel> dssNodeModelList = (List<DSSNodeModel>) map.get("dssNodes");
          cachedLookups.setDssNodes(dssNodeModelList);
        }
        if (Objects.nonNull(map.get("dedicatedAccountsMap"))) {
          HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap = (HashMap<Integer, HashMap<Integer, DedicatedAccount>>) map.get("dedicatedAccountsMap");
          cachedLookups.setDedicatedAccountsMap(dedicatedAccountsMap);
        }
        if (Objects.nonNull(map.get("fafPlans"))) {
          HashMap<Integer, FafPlanModel> fafPlans = (HashMap<Integer, FafPlanModel>) map.get("fafPlans");
          cachedLookups.setFafPlans(fafPlans);
        }
        if (Objects.nonNull(map.get("fafWhiteList"))) {
          List<RestrictionModel> fafWhiteList = (List<RestrictionModel>) map.get("fafWhiteList");
          cachedLookups.setFafWhiteList(fafWhiteList);
        }
        if (Objects.nonNull(map.get("fafBlackList"))) {
          List<RestrictionModel> fafBlackList = (List<RestrictionModel>) map.get("fafBlackList");
          cachedLookups.setFafBlackList(fafBlackList);
        }
        if (Objects.nonNull(map.get("footPrintPages"))) {
          HashMap<String, FootPrintPageModel> footPrintPages = (HashMap<String, FootPrintPageModel>) map.get("footPrintPages");
          cachedLookups.setFootPrintPagesMap(footPrintPages);
        }
        if (Objects.nonNull(map.get("maredCards"))) {
          List<LookupModel> maredCards = (List<LookupModel>) map.get("maredCards");
          cachedLookups.setMaredCardsList(maredCards);
        }
        if (Objects.nonNull(map.get("codesRenewalsValues"))) {
          Map<Integer, TxCodeRenewalValue> codesRenewalsValues = (Map<Integer, TxCodeRenewalValue>) map.get("codesRenewalsValues");
          cachedLookups.setTxCodeRenewalValue(codesRenewalsValues);
        }

        if (Objects.nonNull(map.get("vipSubscribers"))) {
          List<String>  pages = (List<String>) map.get("vipSubscribers");
          cachedLookups.setVipSubscribers(pages);
        }
        if (Objects.nonNull(map.get("menusFeatures"))) {
          Map<Integer, List<Integer>> menusFeatures = (Map<Integer, List<Integer>>) map.get("menusFeatures");
          cachedLookups.setMenuFeaturesMap(menusFeatures);
        }
        if (Objects.nonNull(map.get("featuresVIPMap"))) {
          Map<String, Boolean> featuresVIPMap = (Map<String, Boolean>) map.get("featuresVIPMap");
          cachedLookups.setFeaturesVIPMap(featuresVIPMap);
        }

        if (Objects.nonNull(map.get("serviceOfferingPlansWithBitsDetails"))) {
          HashMap<Integer, ServiceOfferingPlanBitModel> serviceOfferingPlanBitsWithDetails = (HashMap<Integer, ServiceOfferingPlanBitModel>) map.get("serviceOfferingPlansWithBitsDetails");
          cachedLookups.setServiceOfferingPlansWithBitsDetails(serviceOfferingPlanBitsWithDetails);
        }
        if (Objects.nonNull(map.get("serviceOfferingBitDetails"))) {
          HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitsDetails = (HashMap<Integer, ServiceOfferingBitModel>) map.get("serviceOfferingBitDetails");
          cachedLookups.setServiceOfferingBitsDetails(serviceOfferingBitsDetails);
        }
        if (Objects.nonNull(map.get("fafIndicators"))) {
          List<FAFIndicatorModel> fafIndicators = (List<FAFIndicatorModel>) map.get("fafIndicators");
          cachedLookups.setFafIndicators(fafIndicators);
        }
        if (Objects.nonNull(map.get("callActivities"))) {
          HashMap<String, List<ReasonActivityModel>> activities = (HashMap<String, List<ReasonActivityModel>>) map.get("callActivities");
          cachedLookups.setCallActivities(activities);
        }
        if (Objects.nonNull(map.get("smsActions"))) {
          List<SmsActionModel> smsActions = (List<SmsActionModel>) map.get("smsActions");
          cachedLookups.setSmsActions(smsActions);
        }
        if (Objects.nonNull(map.get("smsTemplates"))) {
          List<SmsTemplateModel> smsTemplates = (List<SmsTemplateModel>) map.get("smsTemplates");
          cachedLookups.setSmsTemplates(smsTemplates);
        }
        if (Objects.nonNull(map.get("smsTemplateParams"))) {
          HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateParams = (HashMap<Integer, List<SmsTemplateParamModel>>) map.get("smsTemplateParams");
          cachedLookups.setActionParamMap(smsTemplateParams);
        }
        if (Objects.nonNull(map.get("superFlexOffersMap"))) {
          HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = (HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>>) map.get("superFlexOffersMap");
          cachedLookups.setSuperFlexOffersMap(superFlexOffersMap);
        }
        if (Objects.nonNull(map.get("accountGroupsWithBits"))) {
          HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits = (HashMap<Integer, AccountGroupWithBitsModel>) map.get("accountGroupsWithBits");
          cachedLookups.setAccountGroupsWithBits(accountGroupsWithBits);
        }
        if (Objects.nonNull(map.get("accountGroupBitsLookup"))) {
          List<AccountGroupBitDescModel> accountGroupBitsLookup = (List<AccountGroupBitDescModel>) map.get("accountGroupBitsLookup");
          cachedLookups.setAccountGroupBitsLookup(accountGroupBitsLookup);
        }
        if (Objects.nonNull(map.get("flexHistoryNodes"))) {
          List<FlexShareHistoryNodeModel> flexHistoryNodes = (List<FlexShareHistoryNodeModel>) map.get("flexHistoryNodes");
          cachedLookups.setFlexShareHistoryNodes(flexHistoryNodes);
        }
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while refreshing users");
      CCATLogger.ERROR_LOGGER.error("Error while refreshing users", ex);
    } finally {
      reentrantLock.unlock();
    }
  }

  @EventListener
  public void startupEvent(ApplicationStartedEvent event ) {
    try {
      init();
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("ERROR while start config server " + ex);
      CCATLogger.ERROR_LOGGER.error("ERROR while start config server ", ex);
    }
  }
}
