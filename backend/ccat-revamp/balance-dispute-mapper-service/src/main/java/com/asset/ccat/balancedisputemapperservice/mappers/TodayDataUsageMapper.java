package com.asset.ccat.balancedisputemapperservice.mappers;

import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_EIGHTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_FIFTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_FIRST_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_FOURTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_NINTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_SECOND_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_SEVENTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_SIXTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_TENTH_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.DA_THIRD_ID;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.IN_ACCOUNT_AFTER_CALL_AMOUNT;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.IN_ACCOUNT_BEFORE_CALL_AMOUNT;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_1;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_10;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_2;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_3;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_4;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_5;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_6;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_7;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_8;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_AFTER_9;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_1;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_10;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_2;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_3;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_4;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_5;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_6;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_7;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_8;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.MULTI_DEDICATED_ACC_BEFORE_9;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.O_P_NUMBER_ADDRESS;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.RATED_FLAT_AMOUNT;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.RATED_VOLUME;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.ROUNDED_VOLUME;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.START_TIME_CHARGE_TIMESTAMP;
import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.XFILE_CHARGE_AMOUNT;

import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes.ERROR;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeModel;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeTransactionDetailsModel;
import com.asset.ccat.balancedisputemapperservice.models.BdMocPre;
import com.asset.ccat.balancedisputemapperservice.models.BdMocPreDedicatedModel;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.balancedisputemapperservice.utils.BDMUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.StringTokenizer;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class TodayDataUsageMapper {

  private final BDMUtils bdmUtils;
  private final Properties properties;


  public TodayDataUsageMapper(BDMUtils bdmUtils,
      Properties properties) {
    this.bdmUtils = bdmUtils;
    this.properties = properties;
  }


  public void mapTodayDataUsage(BalanceDisputeModel bdModel, ArrayList<HashMap<String, Object>> resultMap, BigDecimal errorCode) throws BalanceDisputeException {
    ArrayList<BdMocPre> mocPre = new ArrayList<>();
    try {
      for (var map : resultMap) {
        BdMocPre mocPreRow = new BdMocPre();
        mocPreRow.setInAccountAfterCallAmount(bdmUtils.castToDouble(map.get(IN_ACCOUNT_AFTER_CALL_AMOUNT)));
        mocPreRow.setRatingGroup(bdmUtils.castToString((map.get(BalanceDispute.RATING_GROUP_NAME))));
        mocPreRow.setInAccountBeforeCallAmount(bdmUtils.castToDouble(map.get(IN_ACCOUNT_BEFORE_CALL_AMOUNT)));

        mocPreRow.setRatedFlatAmount(bdmUtils.castToDouble(map.get(RATED_FLAT_AMOUNT)));
        mocPreRow.setRatedVolume(bdmUtils.castToDouble(map.get(RATED_VOLUME)));
        mocPreRow.setRoundedVolume(bdmUtils.castRoundedVolume(map.get(ROUNDED_VOLUME)));
        mocPreRow.setDestination(bdmUtils.castToString((map.get(BalanceDispute.RATING_GROUP_NAME))));
        Timestamp timestamp = bdmUtils.castToTimeStamp(map.get(START_TIME_CHARGE_TIMESTAMP));

        if (Objects.nonNull(timestamp))
          mocPreRow.setDateTimeNew(timestamp);

        mocPreRow.setxFileChargeAmount(bdmUtils.castToDouble(map.get(XFILE_CHARGE_AMOUNT)));
        mocPreRow.setOpNumberAddress(bdmUtils.castToString((map.get(O_P_NUMBER_ADDRESS))));
        mocPreRow.setErrorCode(errorCode.intValue());
        Integer[] dedIDs = {
            bdmUtils.castToInteger(map.get(DA_FIRST_ID)),
            bdmUtils.castToInteger(map.get(DA_SECOND_ID)),
            bdmUtils.castToInteger(map.get(DA_THIRD_ID)),
            bdmUtils.castToInteger(map.get(DA_FOURTH_ID)),
            bdmUtils.castToInteger(map.get(DA_FIFTH_ID)),
            bdmUtils.castToInteger(map.get(DA_SIXTH_ID)),
            bdmUtils.castToInteger(map.get(DA_SEVENTH_ID)),
            bdmUtils.castToInteger(map.get(DA_EIGHTH_ID)),
            bdmUtils.castToInteger(map.get(DA_NINTH_ID)),
            bdmUtils.castToInteger(map.get(DA_TENTH_ID))};

        Double[] dediAfter = {
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_1)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_2)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_3)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_4)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_5)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_6)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_7)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_8)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_9)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_AFTER_10))};

        Double[] dediBefore = {
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_1)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_2)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_3)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_4)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_5)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_6)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_7)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_8)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_9)),
            bdmUtils.castToDouble(map.get(MULTI_DEDICATED_ACC_BEFORE_10))};
        mocPreRow.setDaIds(dedIDs);
        mocPreRow.setMultiDAAfter(dediAfter);
        mocPreRow.setMultiDABefore(dediBefore);
        mocPreRow.setBdMocPreDedicatedList(dedIDs, dediAfter, dediBefore);
        mocPre.add(mocPreRow);
      }
      bdModel.setMocPre(mocPre);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while mapping Usage And Accumulators ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while mapping Usage And Accumulators ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR, null, "BD-Mapper-Service[Usage And Accumulators Mapping Error]");
    }
  }


  public void getAllUsage(BalanceDisputeModel bdModel,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap) throws Exception {
    ArrayList<BdMocPre> mocPre;
    mocPre = bdModel.getMocPre();
    HashMap<String, String> detailedAmountsMap;
    HashMap<String, String> chargingSourceMap;
    HashMap<String, Double> amountsMap;
    try {
      for (BdMocPre mocPreModel : mocPre) {
        amountsMap = getMBDAAmounts(mocPreModel);
        detailedAmountsMap = getDetailedMBDAAmounts(mocPreModel, amountsMap);
        double amount = amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_TTL);
        //Usage Details
        BalanceDisputeTransactionDetailsModel usgDet = new BalanceDisputeTransactionDetailsModel();

        Date date = mocPreModel.getDateTimeNew();
        usgDet.setDateTime(date);
        usgDet.setDate(bdmUtils.formatDate(usgDet.getDateTime()));
        usgDet.setCallTime(bdmUtils.formatTime(usgDet.getDateTime()));
        usgDet.setRatingGroup(mocPreModel.getRatingGroup());
        ///////////////////////////
        if (bdModel.isOtherPartPrivilege()) {
          usgDet.setOtherPartyNum(mocPreModel.getOpNumberAddress());
        } else {
          usgDet.setOtherPartyNum(null);
        }
        usgDet.setType(BALANCE_DISPUTE.BD_USAGE);
        usgDet.setTotalActualSecs(String.valueOf(mocPreModel.getRatedVolume()));
        double internetUsage = getInternetUsage(mocPreModel);
        usgDet.setInternetUsage(
            internetUsage == 0 ? "" : String.valueOf(internetUsage / 1024 / 1024)); //In MB

        chargingSourceMap = getChargingSourceAmountDetailed(detailedAmountsMap);
        usgDet.setChargingSource(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_SOURCE));
        usgDet.setChargingAmount(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_AMOUNT));
        setAllBalBefAfter(usgDet, mocPreModel);

        bdModel.getBdTransactions().getTransactionDetails()
            .add(bdmUtils.mapDetailsToMap(usgDet, configMap));
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while getting All Usage ");
      CCATLogger.ERROR_LOGGER.error("Error while getting All Usage ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[Get Today Data Usage Mapping Error]");
    }
  }

  private double getInternetUsage(BdMocPre mocPreModel) {
    ArrayList<Integer> packagesList = getInternetPackages();
    double internetUsage = 0;
    internetUsage = mocPreModel.getRoundedVolume();
    for (double packageValue : packagesList) {
      if (packageValue >= mocPreModel.getRoundedVolume()) {
        return packageValue;
      }
    }
    return internetUsage;
  }

  private ArrayList<Integer> getInternetPackages() {
    String packagesStr = "";

    packagesStr = properties.getInternetPackages();
    String[] packages = packagesStr.split(BALANCE_DISPUTE.BD_INTERNET_PACKAGES_DELIMITER);
    ArrayList<Integer> list = new ArrayList<>();
    for (String aPackage : packages) {
      int pk = Integer.parseInt(aPackage);
      list.add(pk);
    }
    Collections.sort(list);
    return list;
  }

  private HashMap<String, String> getChargingSourceAmountDetailed(
      HashMap<String, String> detailedAmountsMap) {
    HashMap<String, String> map = new HashMap<>();
    String chargingSource = "";
    String chargingAmount = "";
    String chargingSourceSimple = "";
    String mbSrcAmount = detailedAmountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_MB);
    String detailedDaSrcAmount = detailedAmountsMap.get(BALANCE_DISPUTE.BD_DETAILED_AMOUNT_DA);
    String tempSrc;
    String tempAmount;
    StringTokenizer st;
    if (Objects.nonNull(mbSrcAmount) && !mbSrcAmount.trim().isEmpty() && !mbSrcAmount.trim()
        .equals(":")) {
      st = new StringTokenizer(mbSrcAmount, ":");
      tempSrc = st.nextToken();
      tempAmount = st.nextToken();
      DecimalFormat df = new DecimalFormat("#.##");
      if (!tempAmount.trim().isEmpty() && Double.parseDouble(tempAmount.trim()) != 0) {
        chargingSource += tempSrc;

        chargingAmount += df.format(Double.parseDouble(tempAmount.trim()));
        chargingSourceSimple += tempSrc;
      }
    }

    if (detailedDaSrcAmount != null && !detailedDaSrcAmount.trim().isEmpty()
        && !detailedDaSrcAmount.trim().equals(":")) {
      st = new StringTokenizer(detailedDaSrcAmount, ":");
      tempSrc = st.nextToken();
      tempAmount = st.nextToken();
      if (!tempAmount.trim().isEmpty()) {
        if (!chargingSource.trim().isEmpty()) {
          chargingSource += ", ";
          chargingAmount += ", ";
          chargingSourceSimple += ", ";
        }
        chargingSource += tempSrc;
        chargingAmount += tempAmount;
        chargingSourceSimple += tempSrc;
      }
    }
    map.put(BALANCE_DISPUTE.BD_CHARGING_SOURCE, chargingSource);
    map.put(BALANCE_DISPUTE.BD_CHARGING_AMOUNT, chargingAmount);
    map.put(BALANCE_DISPUTE.BD_CHARGING_SOURCE_SIMPLE, chargingSourceSimple);
    return map;
  }

  private HashMap<String, Double> getMBDAAmounts(BdMocPre mocPreRow) {
    HashMap<String, Double> amountsMap = new HashMap<>();
    double tempAmount = 0;
    double amount;

    amount = getMbAmount(mocPreRow);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB, (double) Math.round(amount * 100) / 100);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_TTL, (double) Math.round(amount * 100) / 100);

    for (int i = 0; i < mocPreRow.getBdMocPreDedicatedList().size(); i++) {
      double returnedAmount = getDaAmount(mocPreRow, i);
      tempAmount += returnedAmount;
    }
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_DA, (double) Math.round(tempAmount * 100) / 100);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_TTL,
        (double) Math.round((tempAmount + amount) * 100) / 100);

    return amountsMap;
  }

  private double getDaAmount(BdMocPre mocPreRow, int daId) {
    int index = mocPreRow.getBdMocPreDedicatedList().indexOf(new BdMocPreDedicatedModel(daId));
    if (index != -1) {
      double dedicatedAccountBefore = mocPreRow.getBdMocPreDedicatedList().get(index)
          .getMultiDedicatedAccountBefore();
      double dedicatedAccountAfter = mocPreRow.getBdMocPreDedicatedList().get(index)
          .getMultiDedicatedAccountAfter();
      if (dedicatedAccountAfter != 0 || dedicatedAccountBefore != 0) {
        return dedicatedAccountBefore - dedicatedAccountAfter;
      } else {
        return 0;
      }
    } else {
      return 0;
    }
  }

  private HashMap<String, String> getDetailedMBDAAmounts(BdMocPre mocPreModel,
      HashMap<String, Double> amountsMap) {
    HashMap<String, String> detailedAmountsMap = new HashMap<>();
    StringBuilder DASources = new StringBuilder();
    StringBuilder DAAmounts = new StringBuilder();
    DecimalFormat df = new DecimalFormat("#.##");
    for (int i = 0; i < mocPreModel.getBdMocPreDedicatedList().size(); i++) {
      BdMocPreDedicatedModel bdMocPreDedicatedModel = mocPreModel.getBdMocPreDedicatedList().get(i);
      if (!Objects.equals(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore(),
          bdMocPreDedicatedModel.getMultiDedicatedAccountAfter())) {
        if (!DASources.toString().equals("")) {
          DASources.append(", ");
          DAAmounts.append(", ");
        }
        DASources.append(BALANCE_DISPUTE.BD_DA_ABB)
            .append(bdMocPreDedicatedModel.getDedicatedAccountID());
        double tempAmount = bdMocPreDedicatedModel.getMultiDedicatedAccountBefore()
            - bdMocPreDedicatedModel.getMultiDedicatedAccountAfter();
        DAAmounts.append(df.format(tempAmount));
      }
    }
    detailedAmountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_DA,
        "DA" + ":" + amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_DA));
    detailedAmountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB,
        "MB" + ":" + amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_MB));
    detailedAmountsMap.put(BALANCE_DISPUTE.BD_DETAILED_AMOUNT_DA, DASources + ":" + DAAmounts);

    return detailedAmountsMap;

  }

  private double getMbAmount(BdMocPre mocPreRow) {
    if (mocPreRow.getInAccountAfterCallAmount() > 0 && mocPreRow.getInAccountBeforeCallAmount() > 0
        && mocPreRow.getInAccountAfterCallAmount() == mocPreRow.getInAccountBeforeCallAmount()) {

      return 0;
    } else if ((mocPreRow.getInAccountBeforeCallAmount() == 0
        || mocPreRow.getInAccountAfterCallAmount() == 0) && mocPreRow.getxFileChargeAmount() != 0) {
      return mocPreRow.getxFileChargeAmount() - (
          (mocPreRow.getMultiDABefore()[0] - mocPreRow.getMultiDAAfter()[0]) + (
              mocPreRow.getMultiDABefore()[1] - mocPreRow.getMultiDAAfter()[1]) + (
              mocPreRow.getMultiDABefore()[2] - mocPreRow.getMultiDAAfter()[2]) + (
              mocPreRow.getMultiDABefore()[3] - mocPreRow.getMultiDAAfter()[3]) + (
              mocPreRow.getMultiDABefore()[4] - mocPreRow.getMultiDAAfter()[4]) + (
              mocPreRow.getMultiDABefore()[5] - mocPreRow.getMultiDAAfter()[5]) + (
              mocPreRow.getMultiDABefore()[6] - mocPreRow.getMultiDAAfter()[6]) + (
              mocPreRow.getMultiDABefore()[7] - mocPreRow.getMultiDAAfter()[7]) + (
              mocPreRow.getMultiDABefore()[8] - mocPreRow.getMultiDAAfter()[8]) + (
              mocPreRow.getMultiDABefore()[9] - mocPreRow.getMultiDAAfter()[9]));
    } else if (mocPreRow.getxFileChargeAmount() == 0) {
      if (mocPreRow.getMissingInFlag() != null && mocPreRow.getMissingInFlag().trim().equals("Y")) {

        return mocPreRow.getRatedFlatAmount();
      } else {
        return 0;
      }
    } else if (mocPreRow.getxFileChargeAmount() - (
        (mocPreRow.getMultiDABefore()[0] - mocPreRow.getMultiDAAfter()[0]) + (
            mocPreRow.getMultiDABefore()[1] - mocPreRow.getMultiDAAfter()[1]) + (
            mocPreRow.getMultiDABefore()[2] - mocPreRow.getMultiDAAfter()[2]) + (
            mocPreRow.getMultiDABefore()[3] - mocPreRow.getMultiDAAfter()[3]) + (
            mocPreRow.getMultiDABefore()[4] - mocPreRow.getMultiDAAfter()[4]) + (
            mocPreRow.getMultiDABefore()[5] - mocPreRow.getMultiDAAfter()[5]) + (
            mocPreRow.getMultiDABefore()[6] - mocPreRow.getMultiDAAfter()[6]) + (
            mocPreRow.getMultiDABefore()[7] - mocPreRow.getMultiDAAfter()[7]) + (
            mocPreRow.getMultiDABefore()[8] - mocPreRow.getMultiDAAfter()[8]) + (
            mocPreRow.getMultiDABefore()[9] - mocPreRow.getMultiDAAfter()[9])) < (
        mocPreRow.getInAccountBeforeCallAmount() - mocPreRow.getInAccountAfterCallAmount())
        || (mocPreRow.getInAccountBeforeCallAmount() - mocPreRow.getInAccountAfterCallAmount())
        < 0) {
      return mocPreRow.getxFileChargeAmount() - (
          (mocPreRow.getMultiDABefore()[0] - mocPreRow.getMultiDAAfter()[0]) + (
              mocPreRow.getMultiDABefore()[1] - mocPreRow.getMultiDAAfter()[1]) + (
              mocPreRow.getMultiDABefore()[2] - mocPreRow.getMultiDAAfter()[2]) + (
              mocPreRow.getMultiDABefore()[3] - mocPreRow.getMultiDAAfter()[3]) + (
              mocPreRow.getMultiDABefore()[4] - mocPreRow.getMultiDAAfter()[4]) + (
              mocPreRow.getMultiDABefore()[5] - mocPreRow.getMultiDAAfter()[5]) + (
              mocPreRow.getMultiDABefore()[6] - mocPreRow.getMultiDAAfter()[6]) + (
              mocPreRow.getMultiDABefore()[7] - mocPreRow.getMultiDAAfter()[7]) + (
              mocPreRow.getMultiDABefore()[8] - mocPreRow.getMultiDAAfter()[8]) + (
              mocPreRow.getMultiDABefore()[9] - mocPreRow.getMultiDAAfter()[9]));
    } else {
      return (mocPreRow.getInAccountBeforeCallAmount() - mocPreRow.getInAccountAfterCallAmount());
    }
  }

  private void setAllBalBefAfter(BalanceDisputeTransactionDetailsModel usgDet, BdMocPre mocPre) {
    StringBuilder dedBalBefore = new StringBuilder();
    StringBuilder dedBalAfter = new StringBuilder();

    String dedName = "";
    usgDet.setInAccBef(mocPre.getInAccountBeforeCallAmount());
    usgDet.setInAccAft(mocPre.getInAccountAfterCallAmount());
    for (int i = 0; i < mocPre.getBdMocPreDedicatedList().size(); i++) {
      BdMocPreDedicatedModel bdMocPreDedicatedModel = mocPre.getBdMocPreDedicatedList().get(i);
      dedName = BALANCE_DISPUTE.BD_DA_ABB + bdMocPreDedicatedModel.getDedicatedAccountID();
      if (!Objects.equals(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore(),
          bdMocPreDedicatedModel.getMultiDedicatedAccountAfter())) {
        DecimalFormat df = new DecimalFormat("#.##");
        dedBalBefore.append(df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore()));
        dedBalAfter.append(df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountAfter()));
      }
    }
    usgDet.setInDedBef(dedBalBefore.toString());
    usgDet.setInDedAft(dedBalAfter.toString());
  }
}
