package com.asset.ccat.lookup_service.managers;

import com.asset.ccat.lookup_service.cache.CachedLookups;
import com.asset.ccat.lookup_service.configurations.Properties;
import com.asset.ccat.lookup_service.database.dao.AccountGroupsBitsDao;
import com.asset.ccat.lookup_service.database.dao.CommunityAdminDao;
import com.asset.ccat.lookup_service.database.dao.DSSColumnNameDao;
import com.asset.ccat.lookup_service.database.dao.DSSNodesDao;
import com.asset.ccat.lookup_service.database.dao.DisconnectionCodeDao;
import com.asset.ccat.lookup_service.database.dao.FafPlansDao;
import com.asset.ccat.lookup_service.database.dao.FafRestrictionsDao;
import com.asset.ccat.lookup_service.database.dao.FlexShareHistoryNodesDao;
import com.asset.ccat.lookup_service.database.dao.LkPamClassDao;
import com.asset.ccat.lookup_service.database.dao.LkPamPeriodDao;
import com.asset.ccat.lookup_service.database.dao.LkPamPriorityDao;
import com.asset.ccat.lookup_service.database.dao.LkPamScheduleDao;
import com.asset.ccat.lookup_service.database.dao.LkPamServiceDao;
import com.asset.ccat.lookup_service.database.dao.LookupsDao;
import com.asset.ccat.lookup_service.database.dao.ODSAccountHistoryDao;
import com.asset.ccat.lookup_service.database.dao.ODSNodesDao;
import com.asset.ccat.lookup_service.database.dao.ReasonActivityDao;
import com.asset.ccat.lookup_service.database.dao.ServiceOfferingDao;
import com.asset.ccat.lookup_service.database.dao.TransactionsDao;
import com.asset.ccat.lookup_service.database.dao.TxCodesRenewalValueDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.factories.CustomThreadFactory;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import com.asset.ccat.lookup_service.models.AdmAccountGroup;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.DisconnectionCodeModel;
import com.asset.ccat.lookup_service.models.FafPlanModel;
import com.asset.ccat.lookup_service.models.FootPrintPageModel;
import com.asset.ccat.lookup_service.models.HlrProfileModel;
import com.asset.ccat.lookup_service.models.LkPamModel;
import com.asset.ccat.lookup_service.models.LkTransactionCode;
import com.asset.ccat.lookup_service.models.LkTransactionType;
import com.asset.ccat.lookup_service.models.LookupModel;
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
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityDetailsMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeader;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityHeaderMapping;
import com.asset.ccat.lookup_service.models.ods_models.ODSActivityModel;
import com.asset.ccat.lookup_service.models.ods_models.ODSNodeModel;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import com.asset.ccat.lookup_service.services.AccountGroupsService;
import com.asset.ccat.lookup_service.tasks.RefreshLookupsTask;
import com.asset.ccat.lookup_service.tasks.RefreshServiceClassTask;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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

  public LookupManager() {
    poolTaskScheduler = new ScheduledThreadPoolExecutor(2,
        new CustomThreadFactory("RefreshLookupsPool", "RefreshLookupsTask"));
    reentrantLock = new ReentrantLock();
    cachedLookupsMap = new HashMap<>();
  }

  public void init() {
    CCATLogger.DEBUG_LOGGER.info("Start retrieve serviceClasses");
    Runnable refreshLockupsTask = applicationContext.getBean(RefreshLookupsTask.class, this);
    Runnable refreshServiceClassTask = applicationContext.getBean(RefreshServiceClassTask.class,
        this);
    CCATLogger.DEBUG_LOGGER.info("Start init runnable " + refreshLockupsTask);
    CCATLogger.DEBUG_LOGGER.info("Start init serviceClassRunnable " + refreshServiceClassTask);
    poolTaskScheduler.scheduleWithFixedDelay(refreshLockupsTask, 0,
        properties.getLookupsRefreshTask(), TimeUnit.MILLISECONDS);
    poolTaskScheduler.scheduleWithFixedDelay(refreshServiceClassTask, 0,
        properties.getLookupsRefreshTask(), TimeUnit.MILLISECONDS);
  }

  public void refreshServiceClassLookups() {
    try {
      CCATLogger.DEBUG_LOGGER.info("Start retrieve serviceClasses");
      CCATLogger.CACHE_STATISTICS_LOGGER.info("Refreshing serviceClasses");
      long start = System.currentTimeMillis();
      List<ServiceClassModel> serviceClasses = lookupDao.retrieveServiceClasses();
      if (serviceClasses != null && !serviceClasses.isEmpty()) {
        reentrantLock.lock();
        cachedLookups.setServiceClasses(serviceClasses);
      }
      CCATLogger.DEBUG_LOGGER.info("Retrieve serviceClasses finished successfully");
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

      CCATLogger.DEBUG_LOGGER.info("Start retrieve air servers");
      List<AIRServer> airservers = lookupDao.retrieveAirServers();
      cachedLookupsMap.put("airservers", airservers);
      CCATLogger.DEBUG_LOGGER.info("Retrieved air servers successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "AIR servers retrieved successfully with count [" + (airservers == null ? "0"
              : airservers.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve regions");
      HashMap<String, String> regions = lookupDao.retrieveRegions();
      cachedLookupsMap.put("regions", regions);
      CCATLogger.DEBUG_LOGGER.info("Retrieved regions successfully " + regions.get("Other"));
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Regions retrieved successfully with count [" + (Objects.isNull(regions) ? "0"
              : regions.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve voucher servers");
      List<VoucherServerModel> voucherServers = lookupDao.retrieveVoucherServers();
      cachedLookupsMap.put("voucherServers", voucherServers);
      CCATLogger.DEBUG_LOGGER.info("Retrieved voucher servers successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Voucher servers retrieved successfully with count [" + (voucherServers == null ? "0"
              : voucherServers.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve communities");
      HashMap<Integer, CommunityAdminModel> communities = communityAdminDao.retrieveAllCommunitiesAsMap();
      cachedLookupsMap.put("communities", communities);
      CCATLogger.DEBUG_LOGGER.info("Retrieved communities successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Communities retrieved successfully with count [" + (communities == null ? "0"
              : communities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve unitTypes");
      subStart = System.currentTimeMillis();
      List<LookupModel> unitTypes = lookupDao.retrieveLookup(Defines.LOOKUPS.UNIT_TYPES);
      cachedLookupsMap.put("unitTypes", unitTypes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved unitTypes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Unit Types retrieved successfully with count [" + (unitTypes == null ? "0"
              : unitTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve languages");
      subStart = System.currentTimeMillis();
      List<LookupModel> languages = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_LANGUAGES);
      cachedLookupsMap.put("languages", languages);
      CCATLogger.DEBUG_LOGGER.info("Retrieved languages successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Languages retrieved successfully with count [" + (languages == null ? "0"
              : languages.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve usage");
      subStart = System.currentTimeMillis();
      List<LookupModel> usage = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_USAGE_COUNTER_DESC);
      cachedLookupsMap.put("usage", usage);
      CCATLogger.DEBUG_LOGGER.info("Retrieved usage successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Usage retrieved successfully with count [" + (usage == null ? "0" : usage.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve refillProfiles");
      subStart = System.currentTimeMillis();
      List<RefillPaymentProfileModel> refillProfiles = lookupDao.retrieveRefillProfiles();
      cachedLookupsMap.put("refillProfiles", refillProfiles);
      CCATLogger.DEBUG_LOGGER.info("Retrieved refillProfiles successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Refill Profiles retrieved successfully with count [" + (refillProfiles == null ? "0"
              : refillProfiles.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve offers");
      subStart = System.currentTimeMillis();
      List<OfferModel> offers = lookupDao.retrieveOffers();
      cachedLookupsMap.put("offers", offers);
      CCATLogger.DEBUG_LOGGER.info("Retrieved offers successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offers retrieved successfully with count [" + (offers == null ? "0" : offers.size())
              + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve offerStates");
      subStart = System.currentTimeMillis();
      List<OfferStateModel> offerStates = lookupDao.retrieveOfferStates();
      cachedLookupsMap.put("offerStates", offerStates);
      CCATLogger.DEBUG_LOGGER.info("Retrieved offerStates successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offer States retrieved successfully with count [" + (offerStates == null ? "0"
              : offerStates.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve offerTypes");
      subStart = System.currentTimeMillis();
      List<OfferTypeModel> offerTypes = lookupDao.retrieveOfferTypes();
      cachedLookupsMap.put("offerTypes", offerTypes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved offerTypes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Offer Types retrieved successfully with count [" + (offerTypes == null ? "0"
              : offerTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve hlr profiles");
      subStart = System.currentTimeMillis();
      List<HlrProfileModel> hlrProfiles = lookupDao.retrieveHLRProfiles();
      cachedLookupsMap.put("hlrProfiles", hlrProfiles);
      CCATLogger.DEBUG_LOGGER.info("Retrieved hlr profiles successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "HLR profiles retrieved successfully with count [" + (hlrProfiles == null ? "0"
              : hlrProfiles.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam classes");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamClasses = lkPamClassDao.retrievePamClasses();
      cachedLookupsMap.put("pamClasses", pamClasses);
      CCATLogger.DEBUG_LOGGER.info("Retrieved pam classes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Classes retrieved successfully with count [" + (pamClasses == null ? "0"
              : pamClasses.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam services");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamServices = lkPamServiceDao.retrievePamServices();
      cachedLookupsMap.put("pamServices", pamServices);
      CCATLogger.DEBUG_LOGGER.info("Retrieved pam services successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam services retrieved successfully with count [" + (pamServices == null ? "0"
              : pamServices.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam periods");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamPeriods = lkPamPeriodDao.retrievePamPeriods();
      cachedLookupsMap.put("pamPeriods", pamPeriods);
      CCATLogger.DEBUG_LOGGER.info("Retrieved pam periods successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam periods retrieved successfully with count [" + (pamPeriods == null ? "0"
              : pamPeriods.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam priorities");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamPriorities = lkPamPriorityDao.retrievePamPrioritys();
      cachedLookupsMap.put("pamPriorities", pamPriorities);
      CCATLogger.DEBUG_LOGGER.info("Retrieved pam priorities successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Priorities retrieved successfully with count [" + (pamPriorities == null ? "0"
              : pamPriorities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve pam schedules");
      subStart = System.currentTimeMillis();
      List<LkPamModel> pamSchedules = lkPamScheduleDao.retrievePamSchedules();
      cachedLookupsMap.put("pamSchedules", pamSchedules);
      CCATLogger.DEBUG_LOGGER.info("Retrieved pam schedules successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Pam Schedules retrieved successfully with count [" + (pamSchedules == null ? "0"
              : pamSchedules.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve transaction types");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<LkTransactionType>> types = transactionsDao.retrieveAllTypes();
      cachedLookupsMap.put("types", types);
      CCATLogger.DEBUG_LOGGER.info("Retrieved transaction types successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Transaction types retrieved successfully with count [" + (types == null ? "0"
              : types.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve transaction codes");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<LkTransactionCode>> codes = transactionsDao.retrieveCodes();
      cachedLookupsMap.put("codes", codes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved transaction codes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Transaction codes retrieved successfully with count [" + (codes == null ? "0"
              : codes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Disconnection Codes");
      subStart = System.currentTimeMillis();
      List<DisconnectionCodeModel> disconnectionCodes = disconnectionDao.retrieveDisconnectionCodes();
      cachedLookupsMap.put("disconnectionCodes", disconnectionCodes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved disconnection codes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Disconnection Codes retrieved successfully with count [" + (disconnectionCodes == null
              ? "0" : disconnectionCodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Account Groups");
      subStart = System.currentTimeMillis();
      List<AdmAccountGroup> accountGroups = lookupDao.retrieveAccountGroups();
      cachedLookupsMap.put("accountGroups", accountGroups);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Account Groups successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Groups retrieved successfully with count [" + (accountGroups == null ? "0"
              : accountGroups.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Profiles Service Classes");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<AdmServiceClassResponse>> profilesSCMap = lookupDao.retrieveProfilesServiceClasses();
      cachedLookupsMap.put("profilesServiceClasses", profilesSCMap);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Profiles Service Classes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Profiles Service Classes retrieved successfully with count [" + (profilesSCMap == null
              ? "0" : profilesSCMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans with bits");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingPlan> serviceOffgPlanWithBits = lookupDao.retrieveServiceOfferingPlansWithBits();
      cachedLookupsMap.put("serviceOffPlanWithBits", serviceOffgPlanWithBits);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Plans with bits successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans with bits retrieved successfully with count [" + (
              serviceOffgPlanWithBits == null ? "0" : serviceOffgPlanWithBits.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans");
      subStart = System.currentTimeMillis();
      List<ServiceOfferingPlan> serviceOfferingPlans = lookupDao.retrieveServiceOfferingPlans();
      cachedLookupsMap.put("serviceOfferingPlans", serviceOfferingPlans);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Plans successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans retrieved successfully with count [" + (
              serviceOfferingPlans == null ? "0" : serviceOfferingPlans.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve main product types");
      subStart = System.currentTimeMillis();
      HashMap<String, String> mainProductTypes = lookupDao.retrieveMainProductTypes();
      cachedLookupsMap.put("mainProductTypes", mainProductTypes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved main product types successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Main product types retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve btStatus");
      subStart = System.currentTimeMillis();
      HashMap<String, String> btStatus = lookupDao.retrieveBtStatus();
      cachedLookupsMap.put("btStatus", btStatus);
      CCATLogger.DEBUG_LOGGER.info("Retrieved btStatus successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "btStatus retrieved successfully with count [" + (btStatus == null ? "0"
              : btStatus.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods activities");
      subStart = System.currentTimeMillis();
      HashMap<String, ODSActivityModel> odsActivities = oDSAccountHistoryDao.retrieveOdsActivities();
      cachedLookupsMap.put("odsActivites", odsActivities);
      CCATLogger.DEBUG_LOGGER.info("Retrieved ods activities successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods activities retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods activities header");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ODSActivityHeader> odsHeaders = oDSAccountHistoryDao.retrieveOdsHeaders();
      cachedLookupsMap.put("odsHeaders", odsHeaders);
      CCATLogger.DEBUG_LOGGER.info("Retrieved ods activities header successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods activities header retrieved successfully with count [" + (mainProductTypes == null
              ? "0" : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods header mapping");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> odsHeadersMapping = oDSAccountHistoryDao.retrieveOdsHeadersMapping();
      cachedLookupsMap.put("odsHeadersMapping", odsHeadersMapping);
      CCATLogger.DEBUG_LOGGER.info("Retrieved ods header mapping successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods header mapping retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods details mapping");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<ODSActivityDetailsMapping>> odsDetailsMapping = oDSAccountHistoryDao.retrieveOdsDetailsMapping();
      cachedLookupsMap.put("odsDetailsMapping", odsDetailsMapping);
      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve ods details successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods details retrieved successfully with count [" + (mainProductTypes == null ? "0"
              : mainProductTypes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve ods nodes");
      subStart = System.currentTimeMillis();
      List<ODSNodeModel> odsNodes = odsNodesDao.retrieveODSNodesForCachedLookup();
      cachedLookupsMap.put("odsNodes", odsNodes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve ods nodes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "ods nodes retrieved successfully with count [" + (odsNodes == null ? "0"
              : odsNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve dss nodes");
      subStart = System.currentTimeMillis();
      List<DSSNodeModel> dssNodes = dssNodesDao.retrieveDSSNodesForCachedLookup();
      cachedLookupsMap.put("dssNodes", dssNodes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved dss nodes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "dss nodes retrieved successfully with count [" + (dssNodes == null ? "0"
              : dssNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve account flags");
      subStart = System.currentTimeMillis();
      HashMap<String, String> accountFlags = lookupDao.retrieveAccountsFlags();
      cachedLookupsMap.put("accountFlags", accountFlags);
      CCATLogger.DEBUG_LOGGER.info("Retrieved retrieve accountFlags");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "account Flags retrieved successfully with count [" + (accountFlags == null ? "0"
              : accountFlags.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve DSSColumnNames");
      subStart = System.currentTimeMillis();
      HashMap<String, HashMap<String, String>> retrieveDSSColumnNames = dSSColumnNameDao.retrieveDSSColumnNames();
      cachedLookupsMap.put("retrieveDSSColumnNames", retrieveDSSColumnNames);
      CCATLogger.DEBUG_LOGGER.info("Retrieved DSSColumnNames successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "DSSColumnNames retrieved successfully with count [" + (retrieveDSSColumnNames == null
              ? "0" : retrieveDSSColumnNames.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve dedicated accounts map");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap = lookupDao.retrieveServiceClassesDAsMap();
      cachedLookupsMap.put("dedicatedAccountsMap", dedicatedAccountsMap);
      CCATLogger.DEBUG_LOGGER.info("Retrieved dedicated accounts map successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Dedicated accounts map retrieved successfully with count [" + (
              dedicatedAccountsMap == null ? "0" : dedicatedAccountsMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve all FAF plans");
      subStart = System.currentTimeMillis();
      HashMap<Integer, FafPlanModel> fafPlans = fafPlansDao.retrieveAllFafPlans();
      cachedLookupsMap.put("fafPlans", fafPlans);
      CCATLogger.DEBUG_LOGGER.info("Retrieved all FAF plans successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "All FAF plans retrieved successfully with count [" + (fafPlans == null ? "0"
              : fafPlans.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve FAF whitelist");
      subStart = System.currentTimeMillis();
      List<RestrictionModel> fafWhiteList = fafRestrictionsDao.retrieveFafWhiteList();
      cachedLookupsMap.put("fafWhiteList", fafWhiteList);
      CCATLogger.DEBUG_LOGGER.info("Retrieved FAF whitelist successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "FAF whitelist retrieved successfully with count [" + (fafWhiteList == null ? "0"
              : fafWhiteList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve FAF blacklist");
      subStart = System.currentTimeMillis();
      List<RestrictionModel> fafBlackList = fafRestrictionsDao.retrieveFafBlackList();
      cachedLookupsMap.put("fafBlackList", fafBlackList);
      CCATLogger.DEBUG_LOGGER.info("Retrieved FAF blacklist successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "FAF blacklist retrieved successfully with count [" + (fafBlackList == null ? "0"
              : fafBlackList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve footPrintPages");
      HashMap<String, FootPrintPageModel> footPrintPagesMap = lookupDao.retrieveFootPrintPageMap();
      cachedLookupsMap.put("footPrintPages", footPrintPagesMap);
      CCATLogger.DEBUG_LOGGER.info("Retrieved footPrintPages successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "footPrintPages retrieved successfully with count [" + (footPrintPagesMap == null ? "0"
              : footPrintPagesMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve TX Codes Renewal value");
      subStart = System.currentTimeMillis();
      Map<Integer, TxCodeRenewalValue> renewalsValueList = txCodesRenewalValueDao.retrieveCodesRenewalsValues();
      cachedLookupsMap.put("codesRenewalsValues", renewalsValueList);
      CCATLogger.DEBUG_LOGGER.info("Retrieved TX Codes Renewal value successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "TX Codes Renewal value retrieved successfully with count [" + (renewalsValueList == null
              ? "0" : renewalsValueList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve mared cards");
      subStart = System.currentTimeMillis();
      List<LookupModel> maredCards = lookupDao.retrieveLookup(Defines.LOOKUPS.LK_MAREDCARD_LIST);
      cachedLookupsMap.put("maredCards", maredCards);
      CCATLogger.DEBUG_LOGGER.info("Retrieved mared cards successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Mared cards retrieved successfully with count [" + (maredCards == null ? "0"
              : maredCards.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Call Activities");
      subStart = System.currentTimeMillis();
      HashMap<String, List<ReasonActivityModel>> activities = reasonActivityDao.retrieveActivitiesParentsMap();
      cachedLookupsMap.put("callActivities", activities);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Call Activities successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Call Activities retrieved successfully with count [" + (activities == null ? "0"
              : activities.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Plans With Bits Details");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingPlanBitModel> serviceOfferingPlanBitModelHashMap = serviceOfferingDao.retrieveServiceOfferingPlanBitDetails();
      cachedLookupsMap.put("serviceOfferingPlansWithBitsDetails",
          serviceOfferingPlanBitModelHashMap);
      CCATLogger.DEBUG_LOGGER.info(
          "Retrieved Service Offering Plans With Bits Details successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Plans With Bits Details retrieved successfully with count [" + (
              renewalsValueList == null ? "0" : renewalsValueList.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Service Offering Bit Details");
      subStart = System.currentTimeMillis();
      HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitModelHashMap = serviceOfferingDao.retrieveServiceOfferingBitsDetails();
      cachedLookupsMap.put("serviceOfferingBitDetails", serviceOfferingBitModelHashMap);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Service Offering Bits Details successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service Offering Bits Details retrieved successfully with count [" + (
              serviceOfferingBitModelHashMap == null ? "0" : serviceOfferingBitModelHashMap.size())
              + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve SMSActions");
      subStart = System.currentTimeMillis();
      List<SmsActionModel> smsActions = lookupDao.retrieveSmsActions();
      cachedLookupsMap.put("smsActions", smsActions);
      CCATLogger.DEBUG_LOGGER.info("Retrieved SMSActions successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service SMSActions retrieved successfully with count [" + (smsActions == null ? "0"
              : smsActions.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve SMSTemplates");
      subStart = System.currentTimeMillis();
      List<SmsTemplateModel> smsTemplates = lookupDao.retrieveSmsTemplates();
      cachedLookupsMap.put("smsTemplates", smsTemplates);

      CCATLogger.DEBUG_LOGGER.info("Retrieved smsTemplates successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Service smsTemplates retrieved successfully with count [" + (smsTemplates == null ? "0"
              : smsTemplates.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Sms TemplateParams");
      subStart = System.currentTimeMillis();
      HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateParams = lookupDao.retrieveSmsTemplateParam();
      cachedLookupsMap.put("smsTemplateParams", smsTemplateParams);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Sms TemplateParams successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Sms TemplateParams retrieved successfully with count [" + (smsTemplateParams == null
              ? "0" : smsTemplateParams.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Super Flex Offers Map");
      subStart = System.currentTimeMillis();
      HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = lookupDao.retrieveSuperFLexOffersMap();
      cachedLookupsMap.put("superFlexOffersMap", superFlexOffersMap);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Super Flex Offers Map successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Super Flex Offers Map retrieved successfully with count [" + (superFlexOffersMap == null
              ? "0" : superFlexOffersMap.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Account Groups With Bits");
      subStart = System.currentTimeMillis();
      HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits = accountGroupsService.getAccountGroupsWithBits();
      cachedLookupsMap.put("accountGroupsWithBits", accountGroupsWithBits);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Account Groups With Bits successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Groups With Bits retrieved successfully with count [" + (
              accountGroupsWithBits == null ? "0" : accountGroupsWithBits.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve Account Group Bits Lookup");
      subStart = System.currentTimeMillis();
      List<AccountGroupBitDescModel> accountGroupBitsLookup = accountGroupsBitsDao.retrieveAccountGroupsBitswithDesc();
      cachedLookupsMap.put("accountGroupBitsLookup", accountGroupBitsLookup);
      CCATLogger.DEBUG_LOGGER.info("Retrieved Account Group Bits Lookup successfully ");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "Account Group Bits Lookup retrieved successfully with count [" + (
              accountGroupBitsLookup == null ? "0" : accountGroupBitsLookup.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      CCATLogger.DEBUG_LOGGER.info("Start retrieve flex share history nodes");
      subStart = System.currentTimeMillis();
      List<FlexShareHistoryNodeModel> flexShareHistoryNodes = flexShareHistoryNodesDao.retrieveFlexShareHistoryNodesForCachedLookup();
      cachedLookupsMap.put("flexHistoryNodes", flexShareHistoryNodes);
      CCATLogger.DEBUG_LOGGER.info("Retrieved flex share history nodes successfully");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "flex share history nodes retrieved successfully with count [" + (
              flexShareHistoryNodes == null ? "0" : flexShareHistoryNodes.size()) + "]"
              + " | Finished in [" + (System.currentTimeMillis() - subStart) + "]ms ");

      swapCachedLookups(cachedLookupsMap);

      CCATLogger.DEBUG_LOGGER.info(
          "Finished refresh cache successfully in [" + (System.currentTimeMillis() - start)
              + "]ms");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "########################## Refresh Cache Done Successfully In [" + (
              System.currentTimeMillis() - start) + "]ms ######################");

    } catch (LookupException ex) {
      CCATLogger.DEBUG_LOGGER.info("Refresh Cache Failed");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "########################## Refresh Cache Failed ######################");
      CCATLogger.DEBUG_LOGGER.error("Error while refreshing lookups");
      CCATLogger.ERROR_LOGGER.error("Error while refreshing lookups", ex);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Refresh Cache Failed");
      CCATLogger.CACHE_STATISTICS_LOGGER.info(
          "########################## Refresh Cache Failed ######################");
      CCATLogger.DEBUG_LOGGER.error("Error while refreshing lookups");
      CCATLogger.ERROR_LOGGER.error("Error while refreshing lookups", ex);
    }
  }

  private void swapCachedLookups(HashMap map) {
    try {
      reentrantLock.lock();
      if (Objects.nonNull(map)) {
        if (Objects.nonNull(map.get("airservers"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh air servers");
          List<AIRServer> airservers = (List<AIRServer>) map.get("airservers");
          cachedLookups.setAirServers(airservers);
          CCATLogger.DEBUG_LOGGER.info(
              "AIR servers refreshed successfully with count [" + (Objects.isNull(airservers) ? "0"
                  : airservers.size()) + "] ");
        }
        if (Objects.nonNull(map.get("regions"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh regions");
          HashMap<String, String> regions = (HashMap<String, String>) map.get("regions");
          cachedLookups.setRegions(regions);
          CCATLogger.DEBUG_LOGGER.info(
              "Regions refreshed successfully with count [" + (Objects.isNull(regions) ? "0"
                  : regions.size()) + "] ");
        }

        if (Objects.nonNull(map.get("voucherServers"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh voucher servers");
          List<VoucherServerModel> voucherServersList = (List<VoucherServerModel>) map.get(
              "voucherServers");
          cachedLookups.setVoucherServers(voucherServersList);
          CCATLogger.DEBUG_LOGGER.info(
              "Voucher Servers refreshed successfully with count [" + (
                  Objects.isNull(voucherServersList) ? "0"
                      : voucherServersList.size()) + "] ");
        }
        if (Objects.nonNull(map.get("communities"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh communities");
          HashMap<Integer, CommunityAdminModel> communities = (HashMap<Integer, CommunityAdminModel>) map.get(
              "communities");
          cachedLookups.setCommunities(communities);
          CCATLogger.DEBUG_LOGGER.info(
              "Communities refreshed successfully with count [" + (Objects.isNull(communities) ? "0"
                  : communities.size()) + "] ");
        }
        if (Objects.nonNull(map.get("unitTypes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh unitTypes");
          List<LookupModel> unitTypes = (List<LookupModel>) map.get("unitTypes");
          cachedLookups.setUnitTypes(unitTypes);
          CCATLogger.DEBUG_LOGGER.info(
              "unitTypes refreshed successfully with count [" + (unitTypes == null ? "0"
                  : unitTypes.size()) + "] ");
        }
        if (Objects.nonNull(map.get("languages"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh languages");
          List<LookupModel> languages = (List<LookupModel>) map.get("languages");
          cachedLookups.setLanguage(languages);
          CCATLogger.DEBUG_LOGGER.info(
              "languages refreshed successfully with count [" + (languages == null ? "0"
                  : languages.size()) + "] ");
        }
        if (Objects.nonNull(map.get("usage"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh usage");
          List<LookupModel> usage = (List<LookupModel>) map.get("usage");
          cachedLookups.setUsageCountersDescMap(usage);
          CCATLogger.DEBUG_LOGGER.info(
              "usage refreshed successfully with count [" + (usage == null ? "0" : usage.size())
                  + "] ");
        }
        if (Objects.nonNull(map.get("pamClasses"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh pamClasses");
          List<LkPamModel> pamClasses = (List<LkPamModel>) map.get("pamClasses");
          cachedLookups.setPamClasses(pamClasses);
          CCATLogger.DEBUG_LOGGER.info(
              "pamClasses refreshed successfully with count [" + (pamClasses == null ? "0"
                  : pamClasses.size()) + "] ");
        }
        if (Objects.nonNull(map.get("pamServices"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh pam services");
          List<LkPamModel> pamServices = (List<LkPamModel>) map.get("pamServices");
          cachedLookups.setPamServices(pamServices);
          CCATLogger.DEBUG_LOGGER.info(
              "Pam services refreshed successfully with count [" + (pamServices == null ? "0"
                  : pamServices.size()) + "] ");
        }
        if (Objects.nonNull(map.get("pamPeriods"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh pam periods");
          List<LkPamModel> pamPeriods = (List<LkPamModel>) map.get("pamPeriods");
          cachedLookups.setPamPeriods(pamPeriods);
          CCATLogger.DEBUG_LOGGER.info(
              "Pam periods refreshed successfully with count [" + (pamPeriods == null ? "0"
                  : pamPeriods.size()) + "] ");
        }
        if (Objects.nonNull(map.get("pamPriorities"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh pam priorities");
          List<LkPamModel> pamPriorities = (List<LkPamModel>) map.get("pamPriorities");
          cachedLookups.setPamPriorities(pamPriorities);
          CCATLogger.DEBUG_LOGGER.info(
              "Pam Priorities refreshed successfully with count [" + (pamPriorities == null ? "0"
                  : pamPriorities.size()) + "] ");
        }
        if (Objects.nonNull(map.get("pamSchedules"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh pam schedules");
          List<LkPamModel> pamSchedules = (List<LkPamModel>) map.get("pamSchedules");
          cachedLookups.setPamSchedules(pamSchedules);
          CCATLogger.DEBUG_LOGGER.info(
              "Pam Schedules refreshed successfully with count [" + (pamSchedules == null ? "0"
                  : pamSchedules.size()) + "] ");
        }

        if (Objects.nonNull(map.get("refillProfiles"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh refillProfiles");
          List<RefillPaymentProfileModel> refillProfiles = (List<RefillPaymentProfileModel>) map.get(
              "refillProfiles");
          cachedLookups.setRefillProfiles(refillProfiles);
          CCATLogger.DEBUG_LOGGER.info(
              "refillProfiles refreshed successfully with count [" + (refillProfiles == null ? "0"
                  : refillProfiles.size()) + "] ");
        }
        if (Objects.nonNull(map.get("offers"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh offers");
          List<OfferModel> offers = (List<OfferModel>) map.get("offers");
          cachedLookups.setOffers(offers);
          CCATLogger.DEBUG_LOGGER.info(
              "offers refreshed successfully with count [" + (offers == null ? "0" : offers.size())
                  + "] ");
        }
        if (Objects.nonNull(map.get("offerStates"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh offerStates");
          List<OfferStateModel> offerStates = (List<OfferStateModel>) map.get("offerStates");
          cachedLookups.setOfferStates(offerStates);
          CCATLogger.DEBUG_LOGGER.info(
              "offerStates refreshed successfully with count [" + (offerStates == null ? "0"
                  : offerStates.size()) + "] ");
        }
        if (Objects.nonNull(map.get("offerTypes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh offerTypes");
          List<OfferTypeModel> offerTypes = (List<OfferTypeModel>) map.get("offerTypes");
          cachedLookups.setOfferTypes(offerTypes);
          CCATLogger.DEBUG_LOGGER.info(
              "offerTypes refreshed successfully with count [" + (offerTypes == null ? "0"
                  : offerTypes.size()) + "] ");
        }
        if (Objects.nonNull(map.get("hlrProfiles"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh hlr profiles");
          List<HlrProfileModel> hlrProfiles = (List<HlrProfileModel>) map.get("hlrProfiles");
          cachedLookups.setHlrProfiles(hlrProfiles);
          CCATLogger.DEBUG_LOGGER.info(
              "Hlr profiles refreshed successfully with count [" + (hlrProfiles == null ? "0"
                  : hlrProfiles.size()) + "] ");
        }
        if (Objects.nonNull(map.get("disconnectionCodes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start retrieve Disconnection Codes");
          List<DisconnectionCodeModel> disconnectionCodes = (List<DisconnectionCodeModel>) map.get(
              "disconnectionCodes");
          cachedLookups.setDisconnectionCodes(disconnectionCodes);
          CCATLogger.DEBUG_LOGGER.info(
              "Disconnection Codes retrieved successfully with count [" + (
                  Objects.isNull(disconnectionCodes) ? "0"
                      : disconnectionCodes.size()) + "] ");
        }
        if (Objects.nonNull(map.get("types"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh transaction types");
          HashMap<Integer, List<LkTransactionType>> types = (HashMap<Integer, List<LkTransactionType>>) map.get(
              "types");
          cachedLookups.setTransactiontypes(types);
          CCATLogger.DEBUG_LOGGER.info(
              "transaction types refreshed successfully with count [" + (Objects.isNull(types) ? "0"
                  : types.size()) + "] ");
        }
        if (Objects.nonNull(map.get("codes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh transaction codes");
          HashMap<Integer, List<LkTransactionCode>> codes = (HashMap<Integer, List<LkTransactionCode>>) map.get(
              "codes");
          cachedLookups.setTransactionCodes(codes);
          CCATLogger.DEBUG_LOGGER.info(
              "transaction codes refreshed successfully with count [" + (Objects.isNull(codes) ? "0"
                  : codes.size()) + "] ");
        }

        if (Objects.nonNull(map.get("profilesServiceClasses"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh profiles service classes");
          HashMap<Integer, List<AdmServiceClassResponse>> profilesSCMap = (HashMap<Integer, List<AdmServiceClassResponse>>) map.get(
              "profilesServiceClasses");
          cachedLookups.setProfilesServiceClasses(profilesSCMap);
          CCATLogger.DEBUG_LOGGER.info(
              "Profiles service classes refreshed successfully with count [" + (
                  Objects.isNull(profilesSCMap) ? "0"
                      : profilesSCMap.size()) + "] ");
        }

        if (Objects.nonNull(map.get("accountGroups"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh account groups");
          List<AdmAccountGroup> accountGroups = (List<AdmAccountGroup>) map.get("accountGroups");
          cachedLookups.setAccountGroups(accountGroups);
          CCATLogger.DEBUG_LOGGER.info(
              "Account groups refreshed successfully with count [" + (Objects.isNull(accountGroups)
                  ? "0"
                  : accountGroups.size()) + "] ");
        }

        if (Objects.nonNull(map.get("serviceOffPlanWithBits"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh service offering plans with bits");
          HashMap<Integer, ServiceOfferingPlan> serviceOffgPlansWithBits = (HashMap<Integer, ServiceOfferingPlan>) map.get(
              "serviceOffPlanWithBits");
          cachedLookups.setServiceOfferingPlansWithBits(serviceOffgPlansWithBits);
          CCATLogger.DEBUG_LOGGER.info(
              "Service offering plans with bits refreshed successfully with count ["
                  + (Objects.isNull(serviceOffgPlansWithBits) ? "0"
                  : serviceOffgPlansWithBits.size()) + "] ");
        }

        if (Objects.nonNull(map.get("serviceOfferingPlans"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh service offering plans");
          List<ServiceOfferingPlan> serviceOffgPlans = (List<ServiceOfferingPlan>) map.get(
              "serviceOfferingPlans");
          cachedLookups.setServiceOfferingPlans(serviceOffgPlans);
          CCATLogger.DEBUG_LOGGER.info(
              "Service offering plans refreshed successfully with count [" + (
                  Objects.isNull(serviceOffgPlans) ? "0"
                      : serviceOffgPlans.size()) + "] ");
        }

        if (Objects.nonNull(map.get("mainProductTypes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh main product types");
          HashMap<String, String> mainProductTypes = (HashMap<String, String>) map.get(
              "mainProductTypes");
          cachedLookups.setMainProductTypes(mainProductTypes);
          CCATLogger.DEBUG_LOGGER.info(
              "Main product types refreshed successfully with count [" + (mainProductTypes == null
                  ? "0" : mainProductTypes.size()) + "] ");
        }
        if (Objects.nonNull(map.get("odsActivites"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh ods activities");
          HashMap<String, ODSActivityModel> newMap = (HashMap<String, ODSActivityModel>) map.get(
              "odsActivites");
          cachedLookups.setOdsActivites(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "ods activities refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("odsHeaders"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh ods headers");
          HashMap<Integer, ODSActivityHeader> newMap = (HashMap<Integer, ODSActivityHeader>) map.get(
              "odsHeaders");
          cachedLookups.setOdsActivityHeaders(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "ods headers refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("odsHeadersMapping"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh ods headers");
          HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> newMap = (HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>>) map.get(
              "odsHeadersMapping");
          cachedLookups.setOdsHeadersMapping(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "ods headers mapping refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("odsDetailsMapping"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh ods details");
          HashMap<Integer, List<ODSActivityDetailsMapping>> newMap = (HashMap<Integer, List<ODSActivityDetailsMapping>>) map.get(
              "odsDetailsMapping");
          cachedLookups.setOdsDetailsMapping(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "ods details mapping refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("accountFlags"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh accountFlags");
          HashMap<String, String> newMap = (HashMap<String, String>) map.get("accountFlags");
          cachedLookups.setAccountFlags(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "account Flags refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("retrieveDSSColumnNames"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh dss columns display");
          HashMap<String, HashMap<String, String>> newMap = (HashMap<String, HashMap<String, String>>) map.get(
              "retrieveDSSColumnNames");
          cachedLookups.setRetrieveDSSColumnNames(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh dss columns display refreshed successfully with count [" + (newMap == null
                  ? "0" : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("btStatus"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh btStatus");
          HashMap<String, String> newMap = (HashMap<String, String>) map.get("btStatus");
          cachedLookups.setBtStatus(newMap);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh btStatus refreshed successfully with count [" + (newMap == null ? "0"
                  : newMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("odsNodes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh ods nodes");
          List<ODSNodeModel> odsNodeModelList = (List<ODSNodeModel>) map.get("odsNodes");
          cachedLookups.setOdsNodes(odsNodeModelList);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh ODS Nodes refreshed successfully with count [" + (odsNodeModelList == null
                  ? "0" : odsNodeModelList.size()) + "] ");
        }
        if (Objects.nonNull(map.get("dssNodes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh dss nodes");
          List<DSSNodeModel> dssNodeModelList = (List<DSSNodeModel>) map.get("dssNodes");
          cachedLookups.setDssNodes(dssNodeModelList);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh DSS Nodes refreshed successfully with count [" + (dssNodeModelList == null
                  ? "0" : dssNodeModelList.size()) + "] ");
        }
        if (Objects.nonNull(map.get("dedicatedAccountsMap"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh dedicatedAccountsMap");
          HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap = (HashMap<Integer, HashMap<Integer, DedicatedAccount>>) map.get(
              "dedicatedAccountsMap");
          cachedLookups.setDedicatedAccountsMap(dedicatedAccountsMap);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh dedicatedAccountsMap refreshed successfully with count [" + (
                  dedicatedAccountsMap == null ? "0" : dedicatedAccountsMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("fafPlans"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh fafPlans");
          HashMap<Integer, FafPlanModel> fafPlans = (HashMap<Integer, FafPlanModel>) map.get(
              "fafPlans");
          cachedLookups.setFafPlans(fafPlans);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh fafPlans refreshed successfully with count [" + (fafPlans == null ? "0"
                  : fafPlans.size()) + "] ");
        }
        if (Objects.nonNull(map.get("fafWhiteList"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh fafWhiteList");
          List<RestrictionModel> fafWhiteList = (List<RestrictionModel>) map.get("fafWhiteList");
          cachedLookups.setFafWhiteList(fafWhiteList);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh fafWhiteList refreshed successfully with count [" + (fafWhiteList == null
                  ? "0" : fafWhiteList.size()) + "] ");
        }
        if (Objects.nonNull(map.get("fafBlackList"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh fafBlackList");
          List<RestrictionModel> fafBlackList = (List<RestrictionModel>) map.get("fafBlackList");
          cachedLookups.setFafBlackList(fafBlackList);
          CCATLogger.DEBUG_LOGGER.info(
              "refresh fafBlackList refreshed successfully with count [" + (fafBlackList == null
                  ? "0" : fafBlackList.size()) + "] ");
        }
        if (Objects.nonNull(map.get("footPrintPages"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh footPrintPages");
          HashMap<String, FootPrintPageModel> footPrintPages = (HashMap<String, FootPrintPageModel>) map.get(
              "footPrintPages");
          cachedLookups.setFootPrintPagesMap(footPrintPages);
          CCATLogger.DEBUG_LOGGER.info(
              "FootPrintPages refreshed successfully with count [" + (footPrintPages == null ? "0"
                  : footPrintPages.size()) + "] ");
        }
        if (Objects.nonNull(map.get("maredCards"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh mared cards");
          List<LookupModel> maredCards = (List<LookupModel>) map.get("maredCards");
          cachedLookups.setMaredCardsList(maredCards);
          CCATLogger.DEBUG_LOGGER.info(
              "Mared cards refreshed successfully with count [" + (maredCards == null ? "0"
                  : maredCards.size()) + "] ");
        }
        if (Objects.nonNull(map.get("codesRenewalsValues"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh codes OF Renewals Values");
          Map<Integer, TxCodeRenewalValue> codesRenewalsValues = (Map<Integer, TxCodeRenewalValue>) map.get(
              "codesRenewalsValues");
          cachedLookups.setTxCodeRenewalValue(codesRenewalsValues);
          CCATLogger.DEBUG_LOGGER.info(
              "Codes Renewals Values refreshed successfully with count [" + (
                  Objects.isNull(codesRenewalsValues) ? "0"
                      : codesRenewalsValues.size()) + "] ");
        }
        if (Objects.nonNull(map.get("serviceOfferingPlansWithBitsDetails"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh Service Offering Plans With Bits Details");
          HashMap<Integer, ServiceOfferingPlanBitModel> serviceOfferingPlanBitsWithDetails = (HashMap<Integer, ServiceOfferingPlanBitModel>) map.get(
              "serviceOfferingPlansWithBitsDetails");
          cachedLookups.setServiceOfferingPlansWithBitsDetails(serviceOfferingPlanBitsWithDetails);
          CCATLogger.DEBUG_LOGGER.info(
              "Service Offering Plans With Bits Details refreshed successfully with count ["
                  + (Objects.isNull(serviceOfferingPlanBitsWithDetails) ? "0"
                  : serviceOfferingPlanBitsWithDetails.size()) + "] ");
        }
        if (Objects.nonNull(map.get("serviceOfferingBitDetails"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh Service Offering Bits Details");
          HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitsDetails = (HashMap<Integer, ServiceOfferingBitModel>) map.get(
              "serviceOfferingBitDetails");
          cachedLookups.setServiceOfferingBitsDetails(serviceOfferingBitsDetails);
          CCATLogger.DEBUG_LOGGER.info(
              "Service Offering Bits Details refreshed successfully with count ["
                  + (Objects.isNull(serviceOfferingBitsDetails) ? "0"
                  : serviceOfferingBitsDetails.size()) + "] ");
        }
        if (Objects.nonNull(map.get("callActivities"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh call activities");
          HashMap<String, List<ReasonActivityModel>> activities = (HashMap<String, List<ReasonActivityModel>>) map.get(
              "callActivities");
          cachedLookups.setCallActivities(activities);
          CCATLogger.DEBUG_LOGGER.info(
              "call activities refreshed successfully with count [" + (Objects.isNull(activities)
                  ? "0"
                  : activities.size()) + "] ");
        }
        if (Objects.nonNull(map.get("smsActions"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh smsActions");
          List<SmsActionModel> smsActions = (List<SmsActionModel>) map.get("smsActions");
          cachedLookups.setSmsActions(smsActions);
          CCATLogger.DEBUG_LOGGER.info(
              "smsActions refreshed successfully with count [" + (smsActions == null ? "0"
                  : smsActions.size()) + "] ");
        }
        if (Objects.nonNull(map.get("smsTemplates"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh smsTemplates");
          List<SmsTemplateModel> smsTemplates = (List<SmsTemplateModel>) map.get("smsTemplates");
          cachedLookups.setSmsTemplates(smsTemplates);
          CCATLogger.DEBUG_LOGGER.info(
              "smsTemplates refreshed successfully with count [" + (smsTemplates == null ? "0"
                  : smsTemplates.size()) + "] ");
        }
        if (Objects.nonNull(map.get("smsTemplateParams"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh smsTemplateParams");
          HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateParams = (HashMap<Integer, List<SmsTemplateParamModel>>) map.get(
              "smsTemplateParams");
          cachedLookups.setActionParamMap(smsTemplateParams);
          CCATLogger.DEBUG_LOGGER.info(
              "smsTemplateParams refreshed successfully with count [" + (smsTemplateParams == null
                  ? "0" : smsTemplateParams.size()) + "] ");
        }
        if (Objects.nonNull(map.get("superFlexOffersMap"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh superFlexOffersMap");
          HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap = (HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>>) map.get(
              "superFlexOffersMap");
          cachedLookups.setSuperFlexOffersMap(superFlexOffersMap);
          CCATLogger.DEBUG_LOGGER.info(
              "superFlexOffersMap refreshed successfully with count [" + (superFlexOffersMap == null
                  ? "0" : superFlexOffersMap.size()) + "] ");
        }
        if (Objects.nonNull(map.get("accountGroupsWithBits"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh accountGroupsWithBits");
          HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits = (HashMap<Integer, AccountGroupWithBitsModel>) map.get(
              "accountGroupsWithBits");
          cachedLookups.setAccountGroupsWithBits(accountGroupsWithBits);
          CCATLogger.DEBUG_LOGGER.info("superFlexOffersMap refreshed successfully with count [" + (
              accountGroupsWithBits == null ? "0" : accountGroupsWithBits.size()) + "] ");
        }
        if (Objects.nonNull(map.get("accountGroupBitsLookup"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh accountGroupBitsLookup");
          List<AccountGroupBitDescModel> accountGroupBitsLookup = (List<AccountGroupBitDescModel>) map.get(
              "accountGroupBitsLookup");
          cachedLookups.setAccountGroupBitsLookup(accountGroupBitsLookup);
          CCATLogger.DEBUG_LOGGER.info("superFlexOffersMap refreshed successfully with count [" + (
              accountGroupBitsLookup == null ? "0" : accountGroupBitsLookup.size()) + "] ");
        }
        if (Objects.nonNull(map.get("flexHistoryNodes"))) {
          CCATLogger.DEBUG_LOGGER.info("Start refresh flexHistoryNodes");
          List<FlexShareHistoryNodeModel> flexHistoryNodes = (List<FlexShareHistoryNodeModel>) map.get(
              "flexHistoryNodes");
          cachedLookups.setFlexShareHistoryNodes(flexHistoryNodes);
          CCATLogger.DEBUG_LOGGER.info(
              "flexHistoryNodes refreshed successfully with count [" + (flexHistoryNodes == null
                  ? "0" : flexHistoryNodes.size()) + "] ");
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
  public void startupEvent(ApplicationPreparedEvent event) {
    try {
      init();
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("ERROR while start config server " + ex);
      CCATLogger.ERROR_LOGGER.error("ERROR while start config server ", ex);
    }
  }
}
