/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.cache;

import com.asset.ccat.lookup_service.models.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class CachedLookups {

  List<DisconnectionCodeModel> disconnectionCodes;
  private List<AIRServer> airServers;
  private List<VoucherServerModel> voucherServers;
  private List<LookupModel> unitTypes;
  private List<ServiceClassModel> serviceClasses;
  private List<LookupModel> language;
  private List<OfferModel> offers;
  private List<OfferStateModel> offerStates;
  private List<OfferTypeModel> offerTypes;
  private List<LookupModel> usageCountersDescMap;
  private List<LkPamModel> pamClasses;
  private List<LkPamModel> pamPeriods;
  private List<LkPamModel> pamPriorities;
  private List<LkPamModel> pamSchedules;
  private List<LkPamModel> pamServices;
  private List<RefillPaymentProfileModel> refillProfiles;
  private List<HlrProfileModel> hlrProfiles;
  private List<ODSNodeModel> odsNodes;
  private List<DSSNodeModel> dssNodes;
  private List<FlexShareHistoryNodeModel> flexShareHistoryNodes;
  private HashMap<Integer, List<LkTransactionType>> transactiontypes;
  private HashMap<Integer, List<LkTransactionCode>> transactionCodes;
  private HashMap<Integer, List<AdmServiceClassResponse>> profilesServiceClasses;
  private List<ServiceOfferingPlan> serviceOfferingPlans;
  private HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlansWithBits;
  private HashMap<String, String> mainProductTypes;
  private HashMap<String, ODSActivityModel> odsActivites;
  private HashMap<Integer, ODSActivityHeader> odsActivityHeaders;
  private HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> odsHeadersMapping;
  private HashMap<Integer, List<ODSActivityDetailsMapping>> odsDetailsMapping;
  private HashMap<String, String> accountFlags;
  private HashMap<String, String> btStatus;
  private HashMap<String, String> regions;
  private HashMap<String, HashMap<String, String>> retrieveDSSColumnNames;
  private HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap;
  private HashMap<Integer, CommunityAdminModel> communities;
  private List<RestrictionModel> fafBlackList;
  private List<RestrictionModel> fafWhiteList;
  private HashMap<Integer, FafPlanModel> fafPlans;
  private HashMap<String, FootPrintPageModel> footPrintPagesMap;
  private List<LookupModel> maredCardsList;
  private Map<Integer, TxCodeRenewalValue> txCodeRenewalValue;
  private HashMap<String, List<ReasonActivityModel>> callActivities;

  private HashMap<Integer, ServiceOfferingPlanBitModel> ServiceOfferingPlansWithBitsDetails;
  private HashMap<Integer, ServiceOfferingBitModel> ServiceOfferingBitsDetails;

  private List<AdmAccountGroup> accountGroups;
  private HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits;
  private List<SmsActionModel> smsActions;
  private List<SmsTemplateModel> smsTemplates;
  private HashMap<Integer, List<SmsTemplateParamModel>> actionParamMap;
  private HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap;
  private List<AccountGroupBitDescModel> accountGroupBitsLookup;

  private Map<Integer, List<Integer>> menuFeaturesMap;
  private List<String> vipSubscribers;
  private Map<String, Boolean> featuresVIPMap;

  public Map<String, Boolean> getFeaturesVIPMap() {
    return featuresVIPMap;
  }

  public void setFeaturesVIPMap(Map<String, Boolean> featuresVIPMap) {
    this.featuresVIPMap = featuresVIPMap;
  }

  public List<String> getVipSubscribers() {
    return vipSubscribers;
  }

  public void setVipSubscribers(List<String> vipSubscribers) {
    this.vipSubscribers = vipSubscribers;
  }


  public Map<Integer, List<Integer>> getMenuFeaturesMap() {
    return menuFeaturesMap;
  }

  public void setMenuFeaturesMap(Map<Integer, List<Integer>> menuFeaturesMap) {
    this.menuFeaturesMap = menuFeaturesMap;
  }

  public Map<Integer, TxCodeRenewalValue> getTxCodeRenewalValue() {
    return txCodeRenewalValue;
  }

  public void setTxCodeRenewalValue(Map<Integer, TxCodeRenewalValue> txCodeRenewalValue) {
    this.txCodeRenewalValue = txCodeRenewalValue;
  }
  public HashMap<String, FootPrintPageModel> getFootPrintPagesMap() {
    return footPrintPagesMap;
  }

  public void setFootPrintPagesMap(HashMap<String, FootPrintPageModel> footPrintPagesMap) {
    this.footPrintPagesMap = footPrintPagesMap;
  }

  public List<AIRServer> getAirServers() {
    return airServers;
  }

  public void setAirServers(List<AIRServer> airServers) {
    this.airServers = airServers;
  }

  public List<VoucherServerModel> getVoucherServers() {
    return voucherServers;
  }

  public void setVoucherServers(List<VoucherServerModel> voucherServers) {
    this.voucherServers = voucherServers;
  }

  public List<LookupModel> getUnitTypes() {
    return unitTypes;
  }

  public void setUnitTypes(List<LookupModel> unitTypes) {
    this.unitTypes = unitTypes;
  }

  public List<ServiceClassModel> getServiceClasses() {
    return serviceClasses;
  }

  public void setServiceClasses(List<ServiceClassModel> serviceClasses) {
    this.serviceClasses = serviceClasses;
  }

  public List<LookupModel> getLanguage() {
    return language;
  }

  public void setLanguage(List<LookupModel> language) {
    this.language = language;
  }

  public List<OfferModel> getOffers() {
    return offers;
  }

  public void setOffers(List<OfferModel> offers) {
    this.offers = offers;
  }

  public List<LookupModel> getUsageCountersDesc() {
    return usageCountersDescMap;
  }

  public void setUsageCountersDescMap(List<LookupModel> usageCountersDescMap) {
    this.usageCountersDescMap = usageCountersDescMap;
  }

  public List<RefillPaymentProfileModel> getRefillProfiles() {
    return refillProfiles;
  }

  public void setRefillProfiles(List<RefillPaymentProfileModel> refillProfiles) {
    this.refillProfiles = refillProfiles;
  }

  public List<OfferStateModel> getOfferStates() {
    return offerStates;
  }

  public void setOfferStates(List<OfferStateModel> offerStates) {
    this.offerStates = offerStates;
  }

  public List<OfferTypeModel> getOfferTypes() {
    return offerTypes;
  }

  public void setOfferTypes(List<OfferTypeModel> offerTypes) {
    this.offerTypes = offerTypes;
  }

  public List<HlrProfileModel> getHlrProfiles() {
    return hlrProfiles;
  }

  public void setHlrProfiles(List<HlrProfileModel> hlrProfiles) {
    this.hlrProfiles = hlrProfiles;
  }

  public List<LkTransactionType> getTransactiontypes(Integer featureId) {
    return transactiontypes.get(featureId);
  }

  public void setTransactiontypes(HashMap<Integer, List<LkTransactionType>> transactiontypes) {
    this.transactiontypes = transactiontypes;
  }

  public List<LkTransactionCode> getTransactionCodes(Integer typeId) {
    return transactionCodes.get(typeId);
  }

  public void setTransactionCodes(HashMap<Integer, List<LkTransactionCode>> transactionCodes) {
    this.transactionCodes = transactionCodes;
  }

  public List<LkPamModel> getPamClasses() {
    return pamClasses;
  }

  public void setPamClasses(List<LkPamModel> pamClasses) {
    this.pamClasses = pamClasses;
  }

  public List<LkPamModel> getPamPeriods() {
    return pamPeriods;
  }

  public void setPamPeriods(List<LkPamModel> pamPeriods) {
    this.pamPeriods = pamPeriods;
  }

  public List<LkPamModel> getPamPriorities() {
    return pamPriorities;
  }

  public void setPamPriorities(List<LkPamModel> pamPriorities) {
    this.pamPriorities = pamPriorities;
  }

  public List<LkPamModel> getPamSchedules() {
    return pamSchedules;
  }

  public void setPamSchedules(List<LkPamModel> pamSchedules) {
    this.pamSchedules = pamSchedules;
  }

  public List<LkPamModel> getPamServices() {
    return pamServices;
  }

  public void setPamServices(List<LkPamModel> pamServices) {
    this.pamServices = pamServices;
  }

  public List<DisconnectionCodeModel> getDisconnectionCodes() {
    return disconnectionCodes;
  }

  public void setDisconnectionCodes(List<DisconnectionCodeModel> disconnectionCodes) {
    this.disconnectionCodes = disconnectionCodes;
  }

  public List<ODSNodeModel> getOdsNodes() {
    return odsNodes;
  }

  public void setOdsNodes(List<ODSNodeModel> odsNodes) {
    this.odsNodes = odsNodes;
  }

  public List<DSSNodeModel> getDssNodes() {
    return dssNodes;
  }

  public void setDssNodes(List<DSSNodeModel> dssNodes) {
    this.dssNodes = dssNodes;
  }

  public HashMap<Integer, List<AdmServiceClassResponse>> getProfilesServiceClasses() {
    return profilesServiceClasses;
  }

  public void setProfilesServiceClasses(
      HashMap<Integer, List<AdmServiceClassResponse>> profilesServiceClasses) {
    this.profilesServiceClasses = profilesServiceClasses;
  }

  public List<AdmAccountGroup> getAccountGroups() {
    return accountGroups;
  }

  public void setAccountGroups(List<AdmAccountGroup> accountGroups) {
    this.accountGroups = accountGroups;
  }

  public List<ServiceOfferingPlan> getServiceOfferingPlans() {
    return serviceOfferingPlans;
  }

  public void setServiceOfferingPlans(List<ServiceOfferingPlan> serviceOfferingPlans) {
    this.serviceOfferingPlans = serviceOfferingPlans;
  }

  public HashMap<Integer, ServiceOfferingPlan> getServiceOfferingPlansWithBits() {
    return serviceOfferingPlansWithBits;
  }

  public void setServiceOfferingPlansWithBits(
      HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlansWithBits) {
    this.serviceOfferingPlansWithBits = serviceOfferingPlansWithBits;
  }

  public HashMap<String, String> getMainProductTypes() {
    return mainProductTypes;
  }

  public void setMainProductTypes(HashMap<String, String> mainProductTypes) {
    this.mainProductTypes = mainProductTypes;
  }

  public HashMap<String, ODSActivityModel> getOdsActivites() {
    return odsActivites;
  }

  public void setOdsActivites(HashMap<String, ODSActivityModel> odsActivites) {
    this.odsActivites = odsActivites;
  }

  public HashMap<Integer, ODSActivityHeader> getOdsActivityHeaders() {
    return odsActivityHeaders;
  }

  public void setOdsActivityHeaders(HashMap<Integer, ODSActivityHeader> odsActivityHeaders) {
    this.odsActivityHeaders = odsActivityHeaders;
  }

  public HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> getOdsHeadersMapping() {
    return odsHeadersMapping;
  }

  public void setOdsHeadersMapping(
      HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> odsHeadersMapping) {
    this.odsHeadersMapping = odsHeadersMapping;
  }

  public HashMap<Integer, List<ODSActivityDetailsMapping>> getOdsDetailsMapping() {
    return odsDetailsMapping;
  }

  public void setOdsDetailsMapping(
      HashMap<Integer, List<ODSActivityDetailsMapping>> odsDetailsMapping) {
    this.odsDetailsMapping = odsDetailsMapping;
  }

  public HashMap<String, String> getAccountFlags() {
    return accountFlags;
  }

  public void setAccountFlags(HashMap<String, String> accountFlags) {
    this.accountFlags = accountFlags;
  }

  public HashMap<String, HashMap<String, String>> getRetrieveDSSColumnNames() {
    return retrieveDSSColumnNames;
  }

  public void setRetrieveDSSColumnNames(
      HashMap<String, HashMap<String, String>> retrieveDSSColumnNames) {
    this.retrieveDSSColumnNames = retrieveDSSColumnNames;
  }

  public HashMap<String, String> getBtStatus() {
    return btStatus;
  }

  public void setBtStatus(HashMap<String, String> btStatus) {
    this.btStatus = btStatus;
  }

  public HashMap<String, String> getRegions() {
    return regions;
  }

  public void setRegions(HashMap<String, String> regions) {
    this.regions = regions;
  }

  public HashMap<Integer, HashMap<Integer, DedicatedAccount>> getDedicatedAccountsMap() {
    return dedicatedAccountsMap;
  }

  public void setDedicatedAccountsMap(
      HashMap<Integer, HashMap<Integer, DedicatedAccount>> dedicatedAccountsMap) {
    this.dedicatedAccountsMap = dedicatedAccountsMap;
  }

  public HashMap<Integer, CommunityAdminModel> getCommunities() {
    return communities;
  }

  public void setCommunities(HashMap<Integer, CommunityAdminModel> communities) {
    this.communities = communities;
  }

  public List<RestrictionModel> getFafBlackList() {
    return fafBlackList;
  }

  public void setFafBlackList(List<RestrictionModel> fafBlackList) {
    this.fafBlackList = fafBlackList;
  }

  public List<RestrictionModel> getFafWhiteList() {
    return fafWhiteList;
  }

  public void setFafWhiteList(List<RestrictionModel> fafWhiteList) {
    this.fafWhiteList = fafWhiteList;
  }

  public HashMap<Integer, FafPlanModel> getFafPlans() {
    return fafPlans;
  }

  public void setFafPlans(HashMap<Integer, FafPlanModel> fafPlans) {
    this.fafPlans = fafPlans;
  }

  public List<LookupModel> getMaredCardsList() {
    return maredCardsList;
  }

  public void setMaredCardsList(List<LookupModel> maredCardsList) {
    this.maredCardsList = maredCardsList;
  }

  public HashMap<String, List<ReasonActivityModel>> getCallActivities() {
    return callActivities;
  }

  public void setCallActivities(HashMap<String, List<ReasonActivityModel>> callActivities) {
    this.callActivities = callActivities;
  }

  public HashMap<Integer, ServiceOfferingPlanBitModel> getServiceOfferingPlansWithBitsDetails() {
    return ServiceOfferingPlansWithBitsDetails;
  }

  public void setServiceOfferingPlansWithBitsDetails(
      HashMap<Integer, ServiceOfferingPlanBitModel> serviceOfferingPlansWithBitsDetails) {
    ServiceOfferingPlansWithBitsDetails = serviceOfferingPlansWithBitsDetails;
  }

  public HashMap<Integer, ServiceOfferingBitModel> getServiceOfferingBitsDetails() {
    return ServiceOfferingBitsDetails;
  }

  public void setServiceOfferingBitsDetails(
      HashMap<Integer, ServiceOfferingBitModel> serviceOfferingBitsDetails) {
    ServiceOfferingBitsDetails = serviceOfferingBitsDetails;
  }

  public List<SmsActionModel> getSmsActions() {
    return smsActions;
  }

  public void setSmsActions(List<SmsActionModel> smsActions) {
    this.smsActions = smsActions;
  }

  public List<SmsTemplateModel> getSmsTemplates() {
    return smsTemplates;
  }

  public void setSmsTemplates(List<SmsTemplateModel> smsTemplates) {
    this.smsTemplates = smsTemplates;
  }

  public HashMap<Integer, List<SmsTemplateParamModel>> getActionParamMap() {
    return actionParamMap;
  }

  public void setActionParamMap(HashMap<Integer, List<SmsTemplateParamModel>> actionParamMap) {
    this.actionParamMap = actionParamMap;
  }

  public HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> getSuperFlexOffersMap() {
    return superFlexOffersMap;
  }

  public void setSuperFlexOffersMap(
      HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> superFlexOffersMap) {
    this.superFlexOffersMap = superFlexOffersMap;
  }

  public HashMap<Integer, AccountGroupWithBitsModel> getAccountGroupsWithBits() {
    return accountGroupsWithBits;
  }

  public void setAccountGroupsWithBits(
      HashMap<Integer, AccountGroupWithBitsModel> accountGroupsWithBits) {
    this.accountGroupsWithBits = accountGroupsWithBits;
  }

  public List<AccountGroupBitDescModel> getAccountGroupBitsLookup() {
    return accountGroupBitsLookup;
  }

  public void setAccountGroupBitsLookup(List<AccountGroupBitDescModel> accountGroupBitsLookup) {
    this.accountGroupBitsLookup = accountGroupBitsLookup;
  }

  public List<FlexShareHistoryNodeModel> getFlexShareHistoryNodes() {
    return flexShareHistoryNodes;
  }

  public void setFlexShareHistoryNodes(List<FlexShareHistoryNodeModel> flexShareHistoryNodes) {
    this.flexShareHistoryNodes = flexShareHistoryNodes;
  }
}
