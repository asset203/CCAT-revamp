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

@Component
public class AdjustmentMapper {

    private final BDMUtils bdmUtils;
    private final Properties properties;

    public AdjustmentMapper(BDMUtils bdmUtils, Properties properties) {
        this.bdmUtils = bdmUtils;
        this.properties = properties;
    }

    public void mapAdjustment(BalanceDisputeModel bdModel,
                              ArrayList<HashMap<String, Object>> resultSetMap,
                              LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap)
            throws BalanceDisputeException {
        double adjustmentTtlCredit = 0;
        double adjustmentTtlDebit = 0;
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
                adj.setType(BALANCE_DISPUTE.BD_ADJUSTMENT);
                adj.setDateTime(bdmUtils.castToTimeStamp(map.get(BalanceDispute.ADJ_DATE)));
                adj.setDate(bdmUtils.formatDate(adj.getDateTime()));
                adj.setCallTime(bdmUtils.formatTime(adj.getDateTime()));
                adj.setAdjustmentCode(bdmUtils.castToString(map.get(BalanceDispute.ADJ_CODE)));
                adj.setAdjustmentType(bdmUtils.castToString(map.get(BalanceDispute.ADJ_TYPE)));
                tempAmount = bdmUtils.castToDouble(map.get(BalanceDispute.ADJ_AMOUNT));
                adj.setSubType(bdmUtils.castToString(map.get(BalanceDispute.ADJ_CODE)));
                if (tempAmount >= 0) {
                    adj.setCredit(tempAmount);
                    adj.setDebit(0d);
                    adjustmentTtlCredit += tempAmount;
                } else {
                    adj.setCredit(0d);
                    adj.setDebit(tempAmount * -1);
                    adjustmentTtlDebit += (tempAmount * -1);
                }
                adj.setAmount(tempAmount);

                amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB, tempAmount);
                chargingSourceMap = bdmUtils.getChargingSourceAndAmount(amountsMap, "");
                adj.setChargingSource(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_SOURCE));
                adj.setChargingAmount(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_AMOUNT));
                //Summary
                ArrayList<BdSubTypeModel> summaryList = bdModel.getBdTransactions().getTransactionSummary()
                        .getMbAdjustments();
                bdmUtils.addAdjustSummaryRecord(summaryList, adj.getSubType(), adj.getDebit(),
                        adj.getCredit());

                bdModel.getBdTransactions().getTransactionDetails().add(bdmUtils.mapDetailsToMap(adj, configMap));
            }
            if (adjustmentTtlCredit > 0) {
                tempAmount = (double) Math.round(adjustmentTtlCredit * 100) / 100;
                bdModel.getBdTransactions().getTransactionSummary().setMbAdjustmentsTtlCredit(tempAmount);
                tempAmount = (double) Math.round(
                        (bdModel.getBdTransactions().getTotalMBCredit() + adjustmentTtlCredit) * 100) / 100;
                bdModel.getBdTransactions().setTotalMBCredit(tempAmount);
                tempAmount = (double) Math.round(
                        (bdModel.getBdTransactions().getTotalAmountCredit() + adjustmentTtlCredit) * 100) / 100;
                bdModel.getBdTransactions().setTotalAmountCredit(tempAmount);
            }
            if (adjustmentTtlDebit > 0) {
                tempAmount = (double) Math.round(adjustmentTtlDebit * 100) / 100;
                bdModel.getBdTransactions().getTransactionSummary().setMbAdjustmentsTtlDebit(tempAmount);
                tempAmount = (double) Math.round(
                        (bdModel.getBdTransactions().getTotalMBDebit() + adjustmentTtlDebit) * 100) / 100;
                bdModel.getBdTransactions().setTotalMBDebit(tempAmount);
                tempAmount = (double) Math.round(
                        (bdModel.getBdTransactions().getTotalAmountDebit() + adjustmentTtlDebit) * 100) / 100;
                bdModel.getBdTransactions().setTotalAmountDebit(tempAmount);
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while mapping Adjustment ");
            CCATLogger.ERROR_LOGGER.error("Error while mapping Adjustment ", ex);
            throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
                    null, "BD-Mapper-Service[Adjustment Mapping Error]");
        }
    }
}
