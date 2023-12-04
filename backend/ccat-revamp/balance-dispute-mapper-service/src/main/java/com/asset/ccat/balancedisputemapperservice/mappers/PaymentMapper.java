package com.asset.ccat.balancedisputemapperservice.mappers;

import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
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
public class PaymentMapper {

  private final BDMUtils bdmUtils;
  private final Properties properties;

  public PaymentMapper(BDMUtils bdmUtils, Properties properties) {
    this.bdmUtils = bdmUtils;
    this.properties = properties;
  }

  public void mapPayment(BalanceDisputeModel bdModel,
      ArrayList<HashMap<String, Object>> resultSetMap,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap)
      throws BalanceDisputeException {
    double paymentTtlCredit = 0;
    double tempAmount;
    HashMap<String, Double> amountsMap = new HashMap<>();
    HashMap<String, String> chargingSourceMap;
    try {
      int count = 0;
      for (var map : resultSetMap) {
        if (++count > properties.getMaxRetrievalRecords()) {
          CCATLogger.DEBUG_LOGGER.debug("Max allowed number of retrieved records is reached");
          break;
        }
        BalanceDisputeTransactionDetailsModel adj = new BalanceDisputeTransactionDetailsModel();
        adj.setDate(bdmUtils.formatDate(adj.getDateTime()));
        adj.setCallTime(bdmUtils.formatTime(adj.getDateTime()));
        adj.setDateTime(bdmUtils.castToTimeStamp(map.get(BalanceDispute.TRX_DATE)));
        adj.setType(BALANCE_DISPUTE.BD_PAYMENT);
        adj.setSubType(bdmUtils.castToString(map.get(BalanceDispute.CARD_TYPE)));
        tempAmount = bdmUtils.castToDouble(map.get(BalanceDispute.TOPUP_VALUE));
        adj.setCredit(tempAmount);
        adj.setAmount(tempAmount);
        amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB, tempAmount);
        chargingSourceMap = bdmUtils.getChargingSourceAndAmount(amountsMap, "");
        adj.setChargingSource(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_SOURCE));
        adj.setChargingAmount(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_AMOUNT));

        paymentTtlCredit += tempAmount;

        //Summary
        ArrayList<BdSubTypeModel> summaryList = bdModel.getBdTransactions()
            .getTransactionSummary()
            .getMbPayments();
        bdmUtils.addAdjustSummaryRecord(summaryList, adj.getSubType(), 0d, adj.getCredit());
        bdModel.getBdTransactions().getTransactionDetails().add(bdmUtils.mapDetailsToMap(adj, configMap));

      }
      if (paymentTtlCredit > 0) {

        bdModel.getBdTransactions().getTransactionSummary()
            .setMbPaymentsTtlCredit((double) Math.round(paymentTtlCredit * 100) / 100);
        tempAmount = (double) Math.round(
            (bdModel.getBdTransactions().getTotalMBCredit() + paymentTtlCredit) * 100) / 100;
        bdModel.getBdTransactions().setTotalMBCredit(tempAmount);
        tempAmount = (double) Math.round(
            (bdModel.getBdTransactions().getTotalAmountCredit() + paymentTtlCredit) * 100) / 100;
        bdModel.getBdTransactions().setTotalAmountCredit(tempAmount);
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while mapping Payment ");
      CCATLogger.ERROR_LOGGER.error("Error while mapping Payment ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[Payment Mapping Error]");
    }
  }
}
