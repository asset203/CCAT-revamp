package com.asset.ccat.balancedisputemapperservice.mappers;

import com.asset.ccat.balancedisputemapperservice.cache.LookupsCache;
import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes.ERROR;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.*;
import com.asset.ccat.balancedisputemapperservice.utils.BDMUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static com.asset.ccat.balancedisputemapperservice.defines.DatabaseStructs.BalanceDispute.*;
import static com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE.BD_NUM_ACCUMULATORS;

/**
 * @author Assem.Hassan
 */
@Component
public class UsageAndAccumulatorsMapper {

  private final BDMUtils bdmUtils;
  private final Properties properties;
  private final LookupsCache lookupsCache;

  public UsageAndAccumulatorsMapper(BDMUtils bdmUtils,
      Properties properties, LookupsCache lookupsCache) {
    this.lookupsCache = lookupsCache;
    this.bdmUtils = bdmUtils;
    this.properties = properties;
  }


  public void mapUsageAndAccumulators(BalanceDisputeModel bdModel, ArrayList<HashMap<String, Object>> resultMap) throws BalanceDisputeException {
    ArrayList<BdMocPre> mocPre = new ArrayList<>();
    HashMap<String, String> regionsMap = lookupsCache.getRegionsMap();
    String region = null;
    try {
      int count = 0;
      for (var map : resultMap) {
        if (++count > properties.getMaxRetrievalRecords()) {
          CCATLogger.DEBUG_LOGGER.debug("Max allowed number of retrieved records is reached");
          break;
        }
        BdMocPre mocPreRow = new BdMocPre();
        mocPreRow.setInAccountAfterCallAmount(bdmUtils.castToDouble(map.get(IN_ACCOUNT_AFTER_CALL_AMOUNT)));
        mocPreRow.setInAccountBeforeCallAmount(bdmUtils.castToDouble(map.get(IN_ACCOUNT_BEFORE_CALL_AMOUNT)));
        mocPreRow.setRatedFlatAmount(bdmUtils.castToDouble(map.get(RATED_FLAT_AMOUNT)));
        mocPreRow.setRoundedVolume(bdmUtils.castRoundedVolume(map.get(ROUNDED_VOLUME)));
        mocPreRow.setCallDateTimeStr(bdmUtils.castToString(map.get(START_TIME_CHARGE_TIMESTAMP)));
        mocPreRow.setxFileChargeAmount(bdmUtils.castToDouble(map.get(XFILE_CHARGE_AMOUNT)));
        mocPreRow.setDestination(bdmUtils.castToString(map.get(DESTINATION)));
        mocPreRow.setOpNumberAddress(bdmUtils.castToString(map.get(O_P_NUMBER_ADDRESS)));

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

        ArrayList<BdAccumlator> accumulators = new ArrayList<>();
        for (int i = 0; i < BD_NUM_ACCUMULATORS; i++) {
          BdAccumlator accumulator = new BdAccumlator();
          int accId = i + 1;
          accumulator.setId(bdmUtils.castToInteger(map.get(ACC + accId)));
          accumulator.setAccBefore(bdmUtils.castToDouble(map.get(ACC_START + accId)));
          accumulator.setDeltaValue(bdmUtils.castToDouble(map.get(ACC_DELTA + accId)));
          accumulators.add(accumulator);
        }
        mocPreRow.setAccumulators(accumulators);
        mocPreRow.setDaIds(dedIDs);
        mocPreRow.setMultiDAAfter(dediAfter);
        mocPreRow.setMultiDABefore(dediBefore);
        mocPreRow.setBdMocPreDedicatedList(dedIDs, dediAfter, dediBefore);
        mocPreRow.setZone(bdmUtils.castToString(map.get(ZONE)));
        mocPreRow.setCallDuration(bdmUtils.castToDouble(map.get(CALL_DURATION)));
        mocPreRow.setCallEnd(bdmUtils.castToString(map.get(CALL_END)));
        mocPreRow.setServiceClass(bdmUtils.castToString(map.get(IN_SERVICE_CLASS)));
        mocPreRow.setMissingInFlag(bdmUtils.castToString(map.get(MISSING_IN_FLAG)));
        mocPreRow.setCallTotalActualSeconds(bdmUtils.castToString(map.get(CALL_TOTAL_SECONDS)));
        //setting the values of the two added columns after call end
        mocPreRow.setRatingGroup(bdmUtils.castToString(map.get(BalanceDispute.RATING_GROUP)));
        mocPreRow.setCipipInCommunityID(bdmUtils.castToString(map.get(CIPIP_IN_COMMUNITY_ID)));
        String tempStr = bdmUtils.castToString(map.get(CALL_REGION));
        if (Objects.nonNull(regionsMap)) {
          region = regionsMap.get(tempStr);
        }
        mocPreRow.setRegion(Objects.isNull(tempStr) || Objects.isNull(region) || region.equals(tempStr) ? BALANCE_DISPUTE.BD_REGION_UNDEFINED : region);
        mocPreRow.setActiveSOBs(bdmUtils.castToString(map.get(IN_SERVICE_OFFERING)));
        mocPreRow.setAccountGroups(bdmUtils.castToString(map.get(ACCOUNT_GROUP_ID)));

        mocPre.add(mocPreRow);
      }
      bdModel.setMocPre(mocPre);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while mapping Usage And Accumulators ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while mapping Usage And Accumulators ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR, null, "BD-Mapper-Service[Usage And Accumulators Mapping Error]");
    }
  }


  public void getAllUsage(BalanceDisputeModel bdModel, LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap) throws Exception {
    String[] destKeys = properties.getDestinationMaskedKeys().split(",");
    String[] zoneKeys = properties.getZoneWhiteListKeys().split(",");
    int maskValInt = Integer.parseInt(properties.getMaskValue());

    double totalUsageDebit = 0;
    double totalDaUsageDebit = 0;
    double totalMbUsageDebit = 0;
    double totalDuration = 0d;
    double totalActualSeconds = 0d;
    int totalFreeSMS = 0;
    double totalInternetUsage = 0d;

    HashMap<String, Double> usageSummaryMap = getUsageSummaryMap();
    ArrayList<BdMocPre> mocPre = bdModel.getMocPre();
    for (BdMocPre mocPreModel : mocPre) {
      HashMap<String, Double> amountsMap = getMBDAAmounts(mocPreModel);
      HashMap<String, String> detailedAmountsMap = getDetailedMBDAAmounts(mocPreModel, amountsMap);

      double amount = amountsMap.getOrDefault(BALANCE_DISPUTE.BD_AMOUNT_TTL, 0.0);

      totalUsageDebit += amount;
      totalDuration += mocPreModel.getCallDuration();
      totalActualSeconds += Optional.ofNullable(mocPreModel.getCallTotalActualSeconds())
              .filter(str -> !str.trim().isEmpty())
              .map(Integer::parseInt).orElse(0);

      BalanceDisputeTransactionDetailsModel usgDet = new BalanceDisputeTransactionDetailsModel();
      usgDet.setDateTime(bdmUtils.parseDate(mocPreModel.getCallDateTimeStr()));
      usgDet.setType(BALANCE_DISPUTE.BD_USAGE);
      usgDet.setSubType(mocPreModel.getDestination());
      usgDet.setDate(bdmUtils.formatDate(usgDet.getDateTime()));
      usgDet.setCallTime(bdmUtils.formatTime(usgDet.getDateTime()));
      usgDet.setRegionCellSite(mocPreModel.getRegion());
      usgDet.setTotalActualSecs(mocPreModel.getCallTotalActualSeconds());
      usgDet.setCallDurationInMin(String.valueOf((double) Math.round(mocPreModel.getCallDuration() * 100) / 100));

      double internetUsage = getInternetUsage(mocPreModel); //ROUNDED_VOLUME
      totalInternetUsage += internetUsage;
      usgDet.setInternetUsage(String.valueOf(internetUsage));
      usgDet.setDestination(mocPreModel.getZone());
      checkOtherPartyAccess(bdModel, mocPreModel, usgDet, destKeys, zoneKeys, maskValInt);
      usgDet.setCost((double) Math.round(amount * 100) / 100);
      usgDet.setCallEnd(mocPreModel.getCallEnd());
      usgDet.setRatingGroup(mocPreModel.getRatingGroup());
      usgDet.setCipipInCommunityID(mocPreModel.getCipipInCommunityID());
      usgDet.setRatePlan(getServiceClassName(mocPreModel.getServiceClass()));
      usgDet.setDebit((double) Math.round(amount * 100) / 100);

      int freeSMS = getFreeSms(mocPreModel);
      totalFreeSMS += freeSMS;
      usgDet.setFreeSMS(freeSMS == 0 ? "" : String.valueOf(freeSMS));

      HashMap<String, String> bitsMap = getBitIdFromDecimal(mocPreModel.getActiveSOBs(), "Bits: ");
      usgDet.setSobs(bitsMap.get(BALANCE_DISPUTE.BD_BIT_IDS));

      bitsMap = getBitIdFromDecimal(mocPreModel.getAccountGroups(), "Bits: ");
      usgDet.setAccountGroupBitIds(bitsMap.get(BALANCE_DISPUTE.BD_BIT_IDS));

      HashMap<String, String> chargingSourceMap = getChargingSourceAmountDetailed(detailedAmountsMap);
      usgDet.setChargingAmount(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_AMOUNT));
      usgDet.setChargingSource(chargingSourceMap.get(BALANCE_DISPUTE.BD_CHARGING_SOURCE_SIMPLE));

      HashMap<String, String> accumulatorsMap = getAccumulatorsStr(mocPreModel.getAccumulators());
      Optional.ofNullable(accumulatorsMap.get(BALANCE_DISPUTE.BD_ACC_BEFORE)).ifPresent(accBefore -> {
        usgDet.setAccBefore(accBefore);
        usgDet.setAccAfter(accumulatorsMap.get(BALANCE_DISPUTE.BD_ACC_AFTER));
      });

      setAllBalBefAfter(usgDet, mocPreModel);

      bdModel.getBdTransactions().getTransactionDetails().add(bdmUtils.mapDetailsToMap(usgDet, configMap));
      calculateUsageSummary(usgDet, usageSummaryMap);

      totalDaUsageDebit = updateUsageSummary(amountsMap, totalDaUsageDebit, BALANCE_DISPUTE.BD_AMOUNT_DA, bdModel.getBdTransactions().getTransactionSummary().getDaUsg(), usgDet);
      totalMbUsageDebit = updateUsageSummary(amountsMap, totalMbUsageDebit, BALANCE_DISPUTE.BD_AMOUNT_MB, bdModel.getBdTransactions().getTransactionSummary().getMbUsg(), usgDet);
    }

    setTransactionSummary(bdModel, totalMbUsageDebit, totalDaUsageDebit, totalUsageDebit, totalDuration, totalActualSeconds, totalFreeSMS, totalInternetUsage);
    adjustSummaryUsagePanel(bdModel.getBdTransactions().getTransactionSummary(), usageSummaryMap);
  }

  private HashMap<String, String> getBitIdFromDecimal(String decimalValue, String prefix)
      throws BalanceDisputeException {
    StringBuilder bitIds = new StringBuilder();
    String bitIdsSimple = "";
    String binaryValue;
    int index = 1;
    HashMap<String, String> map = new HashMap<>();
    try {
      if (Objects.nonNull(decimalValue) && !decimalValue.trim().isEmpty()) {
        binaryValue = Long.toBinaryString(Long.parseLong(decimalValue));
        if (binaryValue.trim().isEmpty() || binaryValue.trim().equals("0")) {
          map.put(BALANCE_DISPUTE.BD_BIT_IDS, bitIds.toString());
//          map.put(BALANCE_DISPUTE.BD_BIT_IDS_SIMPLE, bitIdsSimple);
          return map;
        }
        for (int i = binaryValue.length() - 1; i >= 0; i--) {
          char bit = binaryValue.charAt(i);

          if (bit == '1') {
            if (!bitIds.toString().trim().equals("")) {
              bitIds.append(", ");
            }
            bitIds.append(index);
          }
          index++;
        }
        bitIdsSimple = prefix + bitIds;
        bitIds.insert(0, prefix);
        map.put(BALANCE_DISPUTE.BD_BIT_IDS, bitIds.toString());
      } else {
        map.put(BALANCE_DISPUTE.BD_BIT_IDS, decimalValue);
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while getting bits id from decimal ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while getting bits id from decimal ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[Get BitId From Decimal Error]");
    }
    return map;
  }

  private int getFreeSms(BdMocPre mocPreModel) {
    int freeSms = 0;
    String smsType = properties.getSmsType();

    if (smsType.toLowerCase().contains("," + mocPreModel.getDestination().toLowerCase() + ",")) {
      if (mocPreModel.getxFileChargeAmount() == 0) {
        freeSms = 1;
      }
    }
    return freeSms;
  }

  private double getInternetUsage(BdMocPre mocPreModel) {
    String internetType = properties.getInternetType();
    ArrayList<Integer> packagesList = getInternetPackages();
    double internetUsage = 0;
    if (internetType.toLowerCase()
        .contains("," + mocPreModel.getDestination().toLowerCase() + ",")) {
      internetUsage = mocPreModel.getRoundedVolume();
      for (double packageValue : packagesList) {
        if (packageValue >= mocPreModel.getRoundedVolume()) {
          return packageValue;
        }
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

  private void checkOtherPartyAccess(BalanceDisputeModel bdModel, BdMocPre mocPreModel,
      BalanceDisputeTransactionDetailsModel usgDet,
      String[] destKeys, String[] zoneKeys, int maskValInt) throws BalanceDisputeException {

    boolean containDesKey = false;
    boolean containZoneWhiteListKey = false;
    String subType = usgDet.getSubType();
    String destination = usgDet.getDestination();
    try {
      if (!bdModel.isOtherPartPrivilege()) {
        for (String destKey : destKeys) {
          if (subType.toLowerCase().contains(destKey.trim().toLowerCase())) {
            containDesKey = true;
            break;
          }
        }
        if (containDesKey) {
          for (String zoneKey : zoneKeys) {
            if (destination.toLowerCase().contains(zoneKey.trim().toLowerCase())) {
              containZoneWhiteListKey = true;
              break;
            }
          }
        }

        if (containDesKey && !containZoneWhiteListKey) {
          usgDet.setOtherPartyNum(mask(mocPreModel.getOpNumberAddress(), maskValInt));
          return;
        }
      }
      usgDet.setOtherPartyNum(mocPreModel.getOpNumberAddress());
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while getting all Usage ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while getting all Usage ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[Get All Usage Error]");
    }
  }

  private void calculateUsageSummary(BalanceDisputeTransactionDetailsModel usgDet,
                                     HashMap<String, Double> usageSummaryMap) {
    if (Optional.ofNullable(usgDet.getType()).map(String::trim).orElse("").equals(BALANCE_DISPUTE.BD_USAGE)) {
      Optional<String> subTypeOpt = Optional.ofNullable(usgDet.getSubType()).map(String::trim);

      subTypeOpt.ifPresent(subType -> {
        if (subType.equalsIgnoreCase(BALANCE_DISPUTE.BD_GPRS_BASIC_SERVICE))
          updateUsageSummary(usageSummaryMap, BALANCE_DISPUTE.BD_GPRS_BASIC_SERVICE, usgDet.getInternetUsage());

        else if (subType.equalsIgnoreCase(BALANCE_DISPUTE.BD_MOBILE_TELEPHONY))
          handleMobileTelephony(usgDet, usageSummaryMap);

        else if (subType.equalsIgnoreCase(BALANCE_DISPUTE.BD_SHORT_MESSAGE_MO_PP))
          usageSummaryMap.merge(BALANCE_DISPUTE.BD_SMS_COUNT, 1.0, Double::sum);
      });
    }
  }

  private void handleMobileTelephony(BalanceDisputeTransactionDetailsModel usgDet, HashMap<String, Double> usageSummaryMap) {
    String destination = Optional.ofNullable(usgDet.getDestination()).map(String::trim).orElse("");
    String callDuration = usgDet.getCallDurationInMin();

    if (destination.equalsIgnoreCase(BALANCE_DISPUTE.BD_VODAFONE))
      updateUsageSummary(usageSummaryMap, BALANCE_DISPUTE.BD_VODAFONE, callDuration);

    else if (destination.equalsIgnoreCase(BALANCE_DISPUTE.BD_MOBILE))
      updateUsageSummary(usageSummaryMap, BALANCE_DISPUTE.BD_MOBINIL, callDuration);

    else if (destination.equalsIgnoreCase(BALANCE_DISPUTE.BD_MOBILE_TO_ETISALAT))
      updateUsageSummary(usageSummaryMap, BALANCE_DISPUTE.BD_ETISALAT, callDuration);

    updateUsageSummary(usageSummaryMap, BALANCE_DISPUTE.BD_MOBILE_TELEPHONY_TOTAL, callDuration);
  }

  private void updateUsageSummary(HashMap<String, Double> usageSummaryMap, String key, String usage) {
    usageSummaryMap.merge(key, Double.parseDouble(Optional.ofNullable(usage).map(String::trim).orElse("0.0")), Double::sum);
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

  private HashMap<String, String> getAccumulatorsStr(ArrayList<BdAccumlator> accumulators) {

    HashMap<String, String> accMap = new HashMap<>();
    StringBuilder accBefore = new StringBuilder();
    StringBuilder accAfter = new StringBuilder();
    StringBuilder accBeforeSimple = new StringBuilder();
    StringBuilder accAfterSimple = new StringBuilder();
//    boolean isAccBeforeEmpty = !accBefore.toString().trim().isEmpty();
    for (BdAccumlator accumulator : accumulators) {
      int accId = accumulator.getId();
      String accName = BALANCE_DISPUTE.BD_ACC_ABB + accId;
      double tempAmount;
      if (accumulator.getDeltaValue() != 0) {
        if (!accBefore.toString().trim().isEmpty()) {
          //check if breakpoints are displayed correctly in web
          accBefore.append("\n");
          accAfter.append("\n");
        }
        accBefore.append(accName).append(": ").append(accumulator.getAccBefore());
        accBeforeSimple.append(accName).append(": ").append(accumulator.getAccBefore()).append(" ");
        tempAmount = accumulator.getAccBefore() + accumulator.getDeltaValue();
        accAfter.append(accName).append(": ").append(tempAmount);
        accAfterSimple.append(accName).append(": ").append(tempAmount).append(" ");
      }
    }

    if (!accBefore.toString().trim().isEmpty()) {
      accMap.put(BALANCE_DISPUTE.BD_ACC_BEFORE, accBefore.toString());
      accMap.put(BALANCE_DISPUTE.BD_ACC_AFTER, accAfter.toString());
      accMap.put(BALANCE_DISPUTE.BD_ACC_BEFORE_SIMPLE, accBeforeSimple.toString());
      accMap.put(BALANCE_DISPUTE.BD_ACC_AFTER_SIMPLE, accAfterSimple.toString());
    }
    return accMap;
  }

  private HashMap<String, Double> getMBDAAmounts(BdMocPre mocPreRow) {
    HashMap<String, Double> amountsMap = new HashMap<>();
    double tempAmount = 0;

    double amount = getMbAmount(mocPreRow);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB, (double) Math.round(amount * 100) / 100);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_TTL, (double) Math.round(amount * 100) / 100);

    for (int i = 0; i < mocPreRow.getBdMocPreDedicatedList().size(); i++) {
      double returnedAmount = getDaAmount(mocPreRow, i);
      tempAmount += returnedAmount;
    }

    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_DA, (double) Math.round(tempAmount * 100) / 100);
    amountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_TTL, (double) Math.round((tempAmount + amount) * 100) / 100);

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

  private HashMap<String, String> getDetailedMBDAAmounts(BdMocPre mocPreModel, HashMap<String, Double> amountsMap) {
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
    detailedAmountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_DA, "DA" + ":" + amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_DA));
    detailedAmountsMap.put(BALANCE_DISPUTE.BD_AMOUNT_MB, "MB" + ":" + amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_MB));
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
/*    StringBuilder dedBalBeforeSimple = new StringBuilder();
    StringBuilder dedBalAfterSimple = new StringBuilder();*/
    /*String dedName = "";*/
    usgDet.setInAccBef(mocPre.getInAccountBeforeCallAmount());
//    String temp = String.valueOf(mocPre.getInAccountBeforeCallAmount());
//    usgDet.setInAccBefAsStr(temp);
//    usgDet.setInAccBefAsStrSimple("" + mocPre.getInAccountBeforeCallAmount());
    usgDet.setInAccAft(mocPre.getInAccountAfterCallAmount());
/*    temp = String.valueOf(mocPre.getInAccountAfterCallAmount());
    usgDet.setInAccAftAsStr(temp);*/
//    usgDet.setInAccAftAsStrSimple(String.valueOf(mocPre.getInAccountAfterCallAmount()));
    for (int i = 0; i < mocPre.getBdMocPreDedicatedList().size(); i++) {
      BdMocPreDedicatedModel bdMocPreDedicatedModel = mocPre.getBdMocPreDedicatedList().get(i);
      /*dedName = BALANCE_DISPUTE.BD_DA_ABB + bdMocPreDedicatedModel.getDedicatedAccountID();*/
      if (!Objects.equals(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore(),
          bdMocPreDedicatedModel.getMultiDedicatedAccountAfter())) {
        DecimalFormat df = new DecimalFormat("#.##");
        dedBalBefore.append(df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore()));
        dedBalAfter.append(df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountAfter()));

    /*    dedBalBeforeSimple.append(!dedBalBeforeSimple.toString().trim().equals("") ? "," : " ");
        dedBalBeforeSimple.append(
            df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountBefore()));
        dedBalAfterSimple.append(!dedBalAfterSimple.toString().trim().equals("") ? "," : " ");
        dedBalAfterSimple.append(df.format(bdMocPreDedicatedModel.getMultiDedicatedAccountAfter()));*/
      }
    }
    usgDet.setInDedBef(dedBalBefore.toString());
    usgDet.setInDedAft(dedBalAfter.toString());
  }

  private void adjustSummaryUsagePanel(BdSummary summaryModel, HashMap<String, Double> usageSummaryMap) {
    ArrayList<BdSummaryUsageModel> gprsBasicServiceList = new ArrayList<>();
    BdSummaryUsageModel summaryUsageModel = new BdSummaryUsageModel();
    summaryUsageModel.setType(BALANCE_DISPUTE.BD_GPRS_BASIC_SERVICE);
    DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.CEILING);
    double roundedAmountValue = Double.parseDouble(
        df.format(usageSummaryMap.get(BALANCE_DISPUTE.BD_GPRS_BASIC_SERVICE)));
    BigDecimal bigDecimal = new BigDecimal(roundedAmountValue);
    summaryUsageModel.setTotal(bigDecimal);
    gprsBasicServiceList.add(summaryUsageModel);

    ArrayList<BdSummaryUsageModel> mobileTelephonyList = new ArrayList<>();
    summaryUsageModel = new BdSummaryUsageModel();
    summaryUsageModel.setType(BALANCE_DISPUTE.BD_VODAFONE);
    summaryUsageModel.setIntTotal((usageSummaryMap.get(BALANCE_DISPUTE.BD_VODAFONE)).intValue());
    summaryUsageModel.setTotal(BigDecimal.valueOf((usageSummaryMap.get(BALANCE_DISPUTE.BD_VODAFONE)).intValue()));

    mobileTelephonyList.add(summaryUsageModel);
    summaryUsageModel = new BdSummaryUsageModel();
    summaryUsageModel.setType(BALANCE_DISPUTE.BD_MOBINIL);
    summaryUsageModel.setIntTotal((usageSummaryMap.get(BALANCE_DISPUTE.BD_MOBINIL)).intValue());
    summaryUsageModel.setTotal(BigDecimal.valueOf((usageSummaryMap.get(BALANCE_DISPUTE.BD_MOBINIL)).intValue()));

    mobileTelephonyList.add(summaryUsageModel);
    summaryUsageModel = new BdSummaryUsageModel();
    summaryUsageModel.setType(BALANCE_DISPUTE.BD_ETISALAT);
    summaryUsageModel.setIntTotal(usageSummaryMap.get(BALANCE_DISPUTE.BD_ETISALAT).intValue());
    summaryUsageModel.setTotal(BigDecimal.valueOf(usageSummaryMap.get(BALANCE_DISPUTE.BD_ETISALAT).intValue()));

    mobileTelephonyList.add(summaryUsageModel);
    int totalMobileTelephony = usageSummaryMap.get(BALANCE_DISPUTE.BD_MOBILE_TELEPHONY_TOTAL).intValue();

    ArrayList<BdSummaryUsageModel> shortMessagesList = new ArrayList<>();
    summaryUsageModel = new BdSummaryUsageModel();
    summaryUsageModel.setType(BALANCE_DISPUTE.BD_SMS_COUNT);
    summaryUsageModel.setIntTotal(usageSummaryMap.get(BALANCE_DISPUTE.BD_SMS_COUNT).intValue());
    summaryUsageModel.setTotal(BigDecimal.valueOf(usageSummaryMap.get(BALANCE_DISPUTE.BD_SMS_COUNT).intValue()));

    shortMessagesList.add(summaryUsageModel);

    summaryModel.setInternetUsages(gprsBasicServiceList);
    summaryModel.setMobileTelephony(mobileTelephonyList);
    summaryModel.setMobileTelephonyTotal(totalMobileTelephony);
    summaryModel.setShortMessages(shortMessagesList);
  }

  private String getServiceClassName(String scId) throws BalanceDisputeException {
    String scName = scId;
    List<ServiceClassModel> scList;
    try {
      scList = lookupsCache.getServiceClassList();
      if (Objects.isNull(scId) || scId.equals("0")) {
        return "";
      }
      for (var serviceClass : scList) {
        if (serviceClass.getCode().equals(Integer.parseInt(scName))) {
          scName = serviceClass.getName();
          break;
        }
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while getting service class name ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while getting service class name ", ex);
      throw new BalanceDisputeException(ERROR.MAPPING_ERROR,
          null, "BD-Mapper-Service[Get Service Class Name Error]");
    }
    return scName;
  }

  private String mask(String val, int maskLen) {
    String maskedVal = "";
    if (Objects.nonNull(val)) {
      char[] masked = val.toCharArray();
      for (int i = maskLen; i < masked.length; i++) {
        masked[i] = '*';
      }
      maskedVal = String.valueOf(masked);
    }
    return maskedVal;
  }

  private HashMap<String, Double> getUsageSummaryMap() {

    HashMap<String, Double> usageSummaryMap = new HashMap<>();
    usageSummaryMap.put(BALANCE_DISPUTE.BD_GPRS_BASIC_SERVICE, 0d);
    usageSummaryMap.put(BALANCE_DISPUTE.BD_VODAFONE, 0d);
    usageSummaryMap.put(BALANCE_DISPUTE.BD_MOBINIL, 0d);
    usageSummaryMap.put(BALANCE_DISPUTE.BD_ETISALAT, 0d);
    usageSummaryMap.put(BALANCE_DISPUTE.BD_MOBILE_TELEPHONY_TOTAL, 0d);
    usageSummaryMap.put(BALANCE_DISPUTE.BD_SMS_COUNT, 0d);
    return usageSummaryMap;
  }


  private double updateUsageSummary(HashMap<String, Double> amountsMap, double totalUsageDebit, String key,
                                    ArrayList<BdSubTypeModel> summaryList, BalanceDisputeTransactionDetailsModel usgDet) {
    return Optional.ofNullable(amountsMap.get(key))
            .filter(val -> !val.equals(0d))
            .map(val -> {
              double updatedTotal = Math.round((totalUsageDebit + val) * 100) / 100.0;
              bdmUtils.addAdjustSummaryRecord(summaryList, usgDet.getSubType(), val, 0d);
              return updatedTotal;
            }).orElse(totalUsageDebit);
  }

  private void setTransactionSummary(BalanceDisputeModel bdModel, double totalMbUsageDebit, double totalDaUsageDebit, double totalUsageDebit, double totalDuration, double totalActualSeconds, int totalFreeSMS, double totalInternetUsage) {
    bdModel.getBdTransactions().getTransactionSummary().setMbUsgTtlDebit(totalMbUsageDebit);
    bdModel.getBdTransactions().getTransactionSummary().setDaUsgTtlDebit(totalDaUsageDebit);

    double totalMbDebit = Math.round((bdModel.getBdTransactions().getTotalMBDebit() + totalMbUsageDebit) * 100) / 100.0;
    double totalDaDebit = Math.round((bdModel.getBdTransactions().getTotalDADebit() + totalDaUsageDebit) * 100) / 100.0;

    bdModel.getBdTransactions().setTotalMBDebit(totalMbDebit);
    bdModel.getBdTransactions().setTotalDADebit(totalDaDebit);

    bdModel.getBdTransactions().setTotalCost(Math.round(totalUsageDebit * 100) / 100.0);
    bdModel.getBdTransactions().setTotalDuration(Math.round(totalDuration * 100) / 100.0);
    bdModel.getBdTransactions().setTotalActualSeconds(Math.round(totalActualSeconds * 100) / 100.0);
    bdModel.getBdTransactions().setTotalFreeSms(Math.round(totalFreeSMS * 100) / 100.0);
    bdModel.getBdTransactions().setTotalInternetUsage(Math.round(totalInternetUsage * 100) / 100.0);
  }
}

