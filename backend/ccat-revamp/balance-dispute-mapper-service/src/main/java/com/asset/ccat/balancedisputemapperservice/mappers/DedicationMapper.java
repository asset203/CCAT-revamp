package com.asset.ccat.balancedisputemapperservice.mappers;

import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs;
import com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes.ERROR;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeModel;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeTransactionDetailsModel;
import com.asset.ccat.balancedisputemapperservice.models.BdSubTypeModel;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.balancedisputemapperservice.utils.BDMUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class DedicationMapper {

  private final BDMUtils bdmUtils;
  private final Properties properties;

  public DedicationMapper(BDMUtils bdmUtils, Properties properties) {
    this.bdmUtils = bdmUtils;
    this.properties = properties;
  }

  public void mapDedication(BalanceDisputeModel bdModel, ArrayList<HashMap<String, Object>> resultSetMap, LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap) throws BalanceDisputeException {
    double ttlAdjCredit = 0d;
    double ttlAdjDebit = 0d;
    double ttlBAdjCredit = 0d;
    double tempAmount;
    HashMap<String, Double> amountsMap = new HashMap<>();
    HashMap<String, String> chargingSourceMap;
    try {
      int count = 0;
      for (var map : resultSetMap) {
        int i = 0;
        CCATLogger.DEBUG_LOGGER.debug("map #{}", i++);
        if (++count > properties.getMaxRetrievalRecords()) {
          CCATLogger.DEBUG_LOGGER.debug("Max allowed number of retrieved records is reached");
          break;
        }
        BalanceDisputeTransactionDetailsModel adj = new BalanceDisputeTransactionDetailsModel();
        adj.setDateTime(bdmUtils.castToTimeStamp(map.get(BalanceDispute.TRX_DATE)));
        adj.setDate(bdmUtils.formatDate(adj.getDateTime()));
        adj.setCallTime(bdmUtils.formatTime(adj.getDateTime()));
        adj.setSubType(bdmUtils.castToString(map.get(BalanceDispute.TRANS_CODE)));

        adj.setAdjustmentType(
            bdmUtils.castToString(map.get(DatabaseStructs.BalanceDispute.TRANS_TYPE)));
        adj.setAdjustmentCode(
            bdmUtils.castToString(map.get(DatabaseStructs.BalanceDispute.TRANS_CODE)));

        double amount = bdmUtils.castToDouble(map.get(BalanceDispute.DA_AMOUNTS));
        int id = (int) map.get(DatabaseStructs.BalanceDispute.DA_IDS);
        String daName = BALANCE_DISPUTE.BD_DA_ABB + id;
        adj.setAmount(amount);

        //check read from db
        int reportTypeId = BALANCE_DISPUTE.BD_ADJUSTMENT_REPORT;
        adj.setTypeId(reportTypeId);
        if (reportTypeId == BALANCE_DISPUTE.BD_ADJUSTMENT_REPORT) {
          adj.setType(BALANCE_DISPUTE.BD_ADJUSTMENT_REPORT_NAME);
          if (amount > 0) {
            adj.setCredit(amount);
            ttlAdjCredit += amount;
          } else if (amount < 0) {
            adj.setDebit(amount * -1);
            ttlAdjDebit += (amount * -1);
          }
          //Summary
          ArrayList<BdSubTypeModel> summaryList = bdModel.getBdTransactions()
              .getTransactionSummary()
              .getDaAdjustments();
          bdmUtils.addAdjustSummaryRecord(summaryList, adj.getSubType(), adj.getDebit(),
              adj.getCredit());
        } else if (reportTypeId == BALANCE_DISPUTE.BD_BONUS_REPORT) {
          adj.setType(BALANCE_DISPUTE.BD_BONUS_REPORT_NAME);
          adj.setCredit(amount);
          ttlBAdjCredit += amount;

          //Summary
          ArrayList<BdSubTypeModel> summaryList = bdModel.getBdTransactions()
              .getTransactionSummary()
              .getDaBonusAdj();
          bdmUtils.addAdjustSummaryRecord(summaryList, adj.getSubType(), 0d, adj.getCredit());
        }
        amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_DA,
            ((double) Math.round(amount * 100) / 100));
        chargingSourceMap = bdmUtils.getChargingSourceAndAmount(amountsMap,
            String.valueOf(id));
        adj.setChargingSource(
            chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_SOURCE));
        adj.setChargingAmount(
            chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_AMOUNT));
        bdModel.getBdTransactions().getTransactionDetails()
            .add(bdmUtils.mapDetailsToMap(adj, configMap));
      }

      bdModel.getBdTransactions().getTransactionSummary().setDaAdjustmentsTtlCredit(ttlAdjCredit);
      bdModel.getBdTransactions().getTransactionSummary().setDaAdjustmentsTtlDebit(ttlAdjDebit);
      bdModel.getBdTransactions().getTransactionSummary().setDaBonusAdjTtlCredit(ttlBAdjCredit);

      tempAmount = bdModel.getBdTransactions().getTotalDACredit();
      tempAmount += ttlBAdjCredit + ttlAdjCredit;
      bdModel.getBdTransactions().setTotalDACredit(tempAmount);

      tempAmount = bdModel.getBdTransactions().getTotalDADebit();
      tempAmount += ttlAdjDebit;
      bdModel.getBdTransactions().setTotalDADebit(tempAmount);

      tempAmount = (double) Math.round(
          (bdModel.getBdTransactions().getTotalAmountCredit() + ttlBAdjCredit + ttlAdjCredit) * 100)
          / 100;
      bdModel.getBdTransactions().setTotalAmountCredit(tempAmount);

      tempAmount =
          (double) Math.round(
              (bdModel.getBdTransactions().getTotalAmountDebit() + ttlAdjDebit) * 100)
              / 100;
      bdModel.getBdTransactions().setTotalAmountDebit(tempAmount);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.warn("Error while mapping Dedication Maps={} ", resultSetMap);
      CCATLogger.DEBUG_LOGGER.error("Error while mapping Dedication maps: {}", ex.getMessage());
      CCATLogger.ERROR_LOGGER.error("Error while mapping Dedication ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR, null, "BD-Mapper-Service[Dedication Mapping Error]");
    }
  }
}
