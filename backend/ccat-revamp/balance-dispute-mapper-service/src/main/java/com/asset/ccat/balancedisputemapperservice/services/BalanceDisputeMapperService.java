package com.asset.ccat.balancedisputemapperservice.services;


import com.asset.ccat.balancedisputemapperservice.cache.LookupsCache;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BD_FN_MAPS;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes.ERROR;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.mappers.AdjustmentMapper;
import com.asset.ccat.balancedisputemapperservice.mappers.DedicationMapper;
import com.asset.ccat.balancedisputemapperservice.mappers.PaymentMapper;
import com.asset.ccat.balancedisputemapperservice.mappers.RechargesMapper;
import com.asset.ccat.balancedisputemapperservice.mappers.TodayDataUsageMapper;
import com.asset.ccat.balancedisputemapperservice.mappers.UsageAndAccumulatorsMapper;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeModel;
import com.asset.ccat.balancedisputemapperservice.models.BdSubTypeModel;
import com.asset.ccat.balancedisputemapperservice.models.BdSummaryUsageModel;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.balancedisputemapperservice.models.requests.BalanceDisputeServiceRequest;
import com.asset.ccat.balancedisputemapperservice.models.requests.MapTodayDataUsageRequest;
import com.asset.ccat.balancedisputemapperservice.models.response.BalanceDisputeReportResponse;
import com.asset.ccat.balancedisputemapperservice.models.response.BdGetTodayUsageMapperResponse;
import com.asset.ccat.balancedisputemapperservice.utils.BDMUtils;
import com.asset.ccat.balancedisputemapperservice.utils.JwtTokenUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class BalanceDisputeMapperService {

  private final BDMUtils bdmUtils;
  private final JwtTokenUtil jwtTokenUtil;
  private final LookupsService lookupsService;
  private final PaymentMapper paymentMapper;
  private final RechargesMapper rechargesMapper;
  private final AdjustmentMapper adjustmentMapper;
  private final DedicationMapper dedicationMapper;
  private final TodayDataUsageMapper todayDataUsageMapper;
  private final UsageAndAccumulatorsMapper usageAndAccumulatorsMapper;
  private final UserManagementService userManagementService;


  @Autowired
  public BalanceDisputeMapperService(
      BDMUtils bdmUtils, JwtTokenUtil jwtTokenUtil, LookupsService lookupsService,
      LookupsCache lookupsCache,
      AdjustmentMapper adjustmentMapper,
      DedicationMapper dedicationMapper,
      PaymentMapper paymentMapper, RechargesMapper rechargesMapper,
      TodayDataUsageMapper todayDataUsageMapper,
      UsageAndAccumulatorsMapper usageAndAccumulatorsMapper,
      UserManagementService userManagementService) {
    this.lookupsService = lookupsService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.bdmUtils = bdmUtils;
    this.adjustmentMapper = adjustmentMapper;
    this.dedicationMapper = dedicationMapper;
    this.paymentMapper = paymentMapper;
    this.rechargesMapper = rechargesMapper;
    this.todayDataUsageMapper = todayDataUsageMapper;
    this.usageAndAccumulatorsMapper = usageAndAccumulatorsMapper;
    this.userManagementService = userManagementService;
  }

  public BalanceDisputeReportResponse getBalanceDisputeReport(BalanceDisputeServiceRequest request) throws Exception {
    Integer profileId = jwtTokenUtil.extractDataFromToken(request.getToken()); // 478 is the feature ID for VIEW_OTHER_PARTY
    CCATLogger.DEBUG_LOGGER.debug("Checking the eligibility of ProfileID={}", profileId);
    BalanceDisputeModel balanceDisputeModel = new BalanceDisputeModel();
    balanceDisputeModel.setOtherPartPrivilege(userManagementService.checkUserPrivilege(request, profileId, 478));

    HashMap<String, ArrayList<HashMap<String, Object>>> balanceDisputeResultMap = request.getBalanceDisputeServiceMap();
    LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> detailsColumnsMap = lookupsService.getBDDetailsConfiguration(profileId);

    if (Objects.nonNull(balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_USAGE_AND_ACCUMULATORS))) {
      CCATLogger.DEBUG_LOGGER.debug("Start Mapping UsageAndAccumulators -- [{}]", BD_FN_MAPS.SL_GET_USAGE_AND_ACCUMULATORS);
      ArrayList<HashMap<String, Object>> usageAndAccumulatorsMap = balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_USAGE_AND_ACCUMULATORS);
      usageAndAccumulatorsMapper.mapUsageAndAccumulators(balanceDisputeModel, usageAndAccumulatorsMap);
      usageAndAccumulatorsMapper.getAllUsage(balanceDisputeModel, detailsColumnsMap);
    }
    if (Objects.nonNull(balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_DEDICATION))) {
      CCATLogger.DEBUG_LOGGER.debug("Start Mapping AdjustmentDedication -- [{}]", BD_FN_MAPS.SL_GET_ADJ_FN_DEDICATION);
      ArrayList<HashMap<String, Object>> dedicationMap = balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_DEDICATION);
      dedicationMapper.mapDedication(balanceDisputeModel, dedicationMap, detailsColumnsMap);
    }
    if (Objects.nonNull(balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_RECHARGES))) {
      CCATLogger.DEBUG_LOGGER.debug("Start Mapping Recharges -- [{}]", BD_FN_MAPS.SL_GET_ADJ_FN_RECHARGES);
      ArrayList<HashMap<String, Object>> rechargesMap = balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_RECHARGES);
      rechargesMapper.mapRecharges(balanceDisputeModel, rechargesMap, detailsColumnsMap);
    }
    if (Objects.nonNull(balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_PAYMENT))) {
      CCATLogger.DEBUG_LOGGER.debug("Start Mapping Payments -- [{}]", BD_FN_MAPS.SL_GET_ADJ_FN_PAYMENT);
      ArrayList<HashMap<String, Object>> paymentMap = balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_PAYMENT);
      paymentMapper.mapPayment(balanceDisputeModel, paymentMap, detailsColumnsMap);
    }
    if (Objects.nonNull(balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_ADJUSTMENT))) {
      CCATLogger.DEBUG_LOGGER.debug("Start Mapping Adjustments -- [{}]", BD_FN_MAPS.SL_GET_ADJ_FN_ADJUSTMENT);
      ArrayList<HashMap<String, Object>> adjustmentMap = balanceDisputeResultMap.get(BD_FN_MAPS.SL_GET_ADJ_FN_ADJUSTMENT);
      adjustmentMapper.mapAdjustment(balanceDisputeModel, adjustmentMap, detailsColumnsMap);
    }
    return prepareReport(balanceDisputeModel, detailsColumnsMap);
  }


  private BalanceDisputeReportResponse prepareReport(BalanceDisputeModel bdModel,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap)
      throws BalanceDisputeException {
    BalanceDisputeReportResponse response = new BalanceDisputeReportResponse();
    bdmUtils.sortBdDetailsList(bdModel.getBdTransactions().getTransactionDetails(), configMap);
    prepareBalanceSheetSummary(bdModel, response);
    prepareUsageSheetSummary(bdModel, response);
    prepareDetailsSheetSummary(bdModel, response, configMap);

    return response;
  }

  private void prepareBalanceSheetSummary(BalanceDisputeModel bdModel,
      BalanceDisputeReportResponse response)
      throws BalanceDisputeException {
    try {
      ArrayList<BdSubTypeModel> tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getMbRecharges();
      response.getBalanceSummarySheet().setMbRecharges(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getMbPayments();
      response.getBalanceSummarySheet().setMbPayment(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getMbAdjustments();
      response.getBalanceSummarySheet().setMbAdjustment(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getMbUsg();
      response.getBalanceSummarySheet().setMbUsage(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getDaAdjustments();
      response.getBalanceSummarySheet().setDaAdjustments(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getDaBonusAdj();
      response.getBalanceSummarySheet().setDaBonusAdj(tempBdSubTypeList);

      tempBdSubTypeList = bdModel.getBdTransactions()
          .getTransactionSummary().getDaUsg();
      response.getBalanceSummarySheet().setDaUsg(tempBdSubTypeList);

      prepareSummeryTotals(bdModel, response);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while preparing Balance Sheet Summary ");
      CCATLogger.ERROR_LOGGER.error("Error while preparing Balance Sheet Summary ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[BalanceDispute Mapper Service Error]");
    }
  }

  private void prepareUsageSheetSummary(BalanceDisputeModel bdModel,
      BalanceDisputeReportResponse response)
      throws BalanceDisputeException {
    try {
      ArrayList<BdSummaryUsageModel> tempBdSummaryUsageList = bdModel.getBdTransactions()
          .getTransactionSummary().getInternetUsages();
      response.getUsageSummarySheet().setInternetUsage(tempBdSummaryUsageList);

      tempBdSummaryUsageList = bdModel.getBdTransactions()
          .getTransactionSummary().getMobileTelephony();
      response.getUsageSummarySheet().setMobileTelephony(tempBdSummaryUsageList);

      tempBdSummaryUsageList = bdModel.getBdTransactions()
          .getTransactionSummary().getShortMessages();
      response.getUsageSummarySheet().setShortMessages(tempBdSummaryUsageList);

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while preparing Usage Sheet Summary ");
      CCATLogger.ERROR_LOGGER.error("Error while preparing Usage Sheet Summary ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[BalanceDispute Mapper Service Error]");
    }
  }

  private void prepareSummeryTotals(BalanceDisputeModel bdModel,
      BalanceDisputeReportResponse response) {

    response.getBalanceSummarySheet()
        .setDaAdjustmentsTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getDaAdjustmentsTtlCredit());
    response.getBalanceSummarySheet()
        .setDaBonusAdjTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getDaBonusAdjTtlCredit());
    response.getBalanceSummarySheet()
        .setMbRechargesTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbRechargesTtlCredit());
    response.getBalanceSummarySheet()
        .setMbPaymentsTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbPaymentsTtlCredit());
    response.getBalanceSummarySheet()
        .setMbAdjustmentsTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbAdjustmentsTtlCredit());
    response.getBalanceSummarySheet()
        .setDaAdjustmentsTtlDebit(bdModel.getBdTransactions().getTransactionSummary()
            .getDaAdjustmentsTtlDebit());
    response.getBalanceSummarySheet()
        .setMbRechargesTtlCredit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbRechargesTtlCredit());
    response.getBalanceSummarySheet()
        .setDaUsgTtlDebit(bdModel.getBdTransactions().getTransactionSummary()
            .getDaUsgTtlDebit());
    response.getBalanceSummarySheet()
        .setMbAdjustmentsTtlDebit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbAdjustmentsTtlDebit());
    response.getBalanceSummarySheet()
        .setMbUsgTtlDebit(bdModel.getBdTransactions().getTransactionSummary()
            .getMbUsgTtlDebit());
    response.getBalanceSummarySheet()
        .setMobileTelephonyTotal(bdModel.getBdTransactions().getTransactionSummary()
            .getMobileTelephonyTotal());
  }

  private void prepareDetailsSheetSummary(BalanceDisputeModel bdModel,
      BalanceDisputeReportResponse response,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap)
      throws BalanceDisputeException {
    try {
      response.getDetails().setTransactionDetailsList(
          bdModel.getBdTransactions().getTransactionDetails());
      response.getDetails()
          .setColumnNames(
              configMap.values().stream()
                  .map(LkBalanceDisputeDetailsConfigModel::getDisplayName).collect(
                      Collectors.toCollection(ArrayList::new)));
      //details totals
      response.getDetails()
          .setTotalMBCredit(bdModel.getBdTransactions().getTotalMBCredit());
      response.getDetails()
          .setTotalMBDebit(bdModel.getBdTransactions().getTotalMBDebit());

      response.getDetails()
          .setTotalDACredit(bdModel.getBdTransactions().getTotalDACredit());
      response.getDetails()
          .setTotalDADebit(bdModel.getBdTransactions().getTotalDADebit());

      response.getDetails()
          .setTotalAmountCredit(bdModel.getBdTransactions().getTotalAmountCredit());
      response.getDetails()
          .setTotalAmountDebit(bdModel.getBdTransactions().getTotalAmountDebit());

      response.getDetails()
          .setTotalDuration(bdModel.getBdTransactions().getTotalDuration());
      response.getDetails()
          .setTotalCost(bdModel.getBdTransactions().getTotalCost());

      response.getDetails()
          .setTotalActualSeconds(bdModel.getBdTransactions().getTotalActualSeconds());

      response.getDetails()
          .setTotalInternetUsage(bdModel.getBdTransactions().getTotalInternetUsage());
      response.getDetails()
          .setTotalFreeSms(bdModel.getBdTransactions().getTotalFreeSms());

      response.getDetails()
          .setTotalAmountCredit(bdModel.getBdTransactions().getTotalAmountCredit());
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while preparing Usage Sheet Summary ");
      CCATLogger.ERROR_LOGGER.error("Error while preparing Usage Sheet Summary ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[BalanceDispute Mapper Service Error]");
    }
  }

  public BdGetTodayUsageMapperResponse getTodayDataUsage(MapTodayDataUsageRequest request)
      throws Exception {
    ArrayList<HashMap<String, Object>> partialCDRS;
    partialCDRS = request.getTodayDataUsageMap().get(BALANCE_DISPUTE.PARTIAL_CDRS);
    BigDecimal errorCode = request.getErrorCode();
    LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> detailsColumnsMap =
        lookupsService.getBDDetailsConfiguration(
            jwtTokenUtil.extractDataFromToken(request.getToken()));
    return prepareGetTodayUsageDetails(partialCDRS, errorCode, detailsColumnsMap);
  }


  private BdGetTodayUsageMapperResponse prepareGetTodayUsageDetails(
      ArrayList<HashMap<String, Object>> partialCDRS, BigDecimal errorCode,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap)
      throws BalanceDisputeException {
    BalanceDisputeModel balanceDisputeModel = new BalanceDisputeModel();
    BdGetTodayUsageMapperResponse response = new BdGetTodayUsageMapperResponse();
    try {
      todayDataUsageMapper.mapTodayDataUsage(balanceDisputeModel, partialCDRS, errorCode);
      todayDataUsageMapper.getAllUsage(balanceDisputeModel, configMap);
      bdmUtils.sortBdDetailsList(
          balanceDisputeModel.getBdTransactions().getTransactionDetails(),
          configMap);
      response.getDetails().setTransactionDetailsList(
          balanceDisputeModel.getBdTransactions().getTransactionDetails());
      response.getDetails()
          .setColumnNames(
              configMap.values().stream()
                  .map(LkBalanceDisputeDetailsConfigModel::getDisplayName).collect(
                      Collectors.toCollection(ArrayList::new)));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while preparing GetTodayUsageDetails ");
      CCATLogger.ERROR_LOGGER.error("Error while preparing GetTodayUsageDetails ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[BalanceDispute Mapper Service Error]");

    }
    return response;
  }
}
