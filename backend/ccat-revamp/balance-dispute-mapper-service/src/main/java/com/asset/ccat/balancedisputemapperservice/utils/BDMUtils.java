package com.asset.ccat.balancedisputemapperservice.utils;

import com.asset.ccat.balancedisputemapperservice.comparator.BdTransactionDetailsComparator;
import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeTransactionDetailsModel;
import com.asset.ccat.balancedisputemapperservice.models.BdSubTypeModel;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class BDMUtils {


  private final Properties properties;

  private final BdTransactionDetailsComparator comparator;


  @Autowired
  public BDMUtils(Properties properties, BdTransactionDetailsComparator comparator) {
    this.properties = properties;

    this.comparator = comparator;
  }


  public Date parseDate(String dateStr) throws ParseException {
    if (Objects.nonNull(dateStr)) {
      SimpleDateFormat sdf = new SimpleDateFormat(properties.getDateTimeFormat());

      return sdf.parse(dateStr);
    } else {
      return null;
    }
  }

  public String formatDate(Date date) {
    if (Objects.nonNull(date)) {
      String dateString;
      DateFormat df = new SimpleDateFormat(properties.getDateFormat());
      dateString = df.format(date);
      return dateString;
    } else {
      return "";
    }
  }

  public String formatTime(Date date) {
    if (Objects.nonNull(date)) {
      String dateString;
      DateFormat df = new SimpleDateFormat(properties.getTimeFormat());
      dateString = df.format(date);
      return dateString;
    } else {
      return "";
    }
  }

  public void addAdjustSummaryRecord(ArrayList<BdSubTypeModel> summaryList, String subType,
      Double debit, Double credit) {
    BdSubTypeModel bdSubTypeModel = new BdSubTypeModel();
    bdSubTypeModel.setSubType(subType);
    int index = summaryList.indexOf(bdSubTypeModel);
    if (index >= 0) {
      bdSubTypeModel = summaryList.get(index);
      bdSubTypeModel.setCredit(
          (double) Math.round((bdSubTypeModel.getCredit() + credit) * 100) / 100);
      bdSubTypeModel.setDebit((double) Math.round((bdSubTypeModel.getDebit() + debit) * 100) / 100);
    } else {
      bdSubTypeModel.setCredit(credit);
      bdSubTypeModel.setDebit(debit);
      summaryList.add(bdSubTypeModel);
    }
  }

  public HashMap<String, String> getChargingSourceAndAmount(HashMap<String, Double> amountsMap,
      String daId) {
    HashMap<String, String> map = new HashMap<>();
    String chargingSource = "";
    String chargingAmount = ""; //will be "100,50" or "100"
    String chargingSourceSimple = "";
    Double daAmount;
    Double mbAmount;
    String dedName;
    daAmount = amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_DA);
    mbAmount = amountsMap.get(BALANCE_DISPUTE.BD_AMOUNT_MB);
    DecimalFormat df = new DecimalFormat("#.##");
    if (mbAmount != null && mbAmount != 0) {
      chargingSource = BALANCE_DISPUTE.BD_MB_ABB;
      chargingAmount = df.format(mbAmount) + "";
      //chargingSourceSimple = Defines.BALANCE_DISPUTE.BD_MB_ABB + ": " + mbAmount;
      chargingSourceSimple = BALANCE_DISPUTE.BD_MB_ABB;
    }

    if (daAmount != null && daAmount != 0) {

      dedName = daId != null && !daId.trim().equals("") ? (BALANCE_DISPUTE.BD_DA_ABB
          + daId) : "DA";
      if (!chargingSource.trim().equals("")) {
        chargingSource += ", ";
        chargingAmount += ", ";
        chargingSourceSimple += ", ";
      }
      chargingSource += dedName;
      chargingAmount = daAmount + "";
      // chargingSourceSimple += dedName + ": " + daAmount;
      chargingSourceSimple += dedName;
    }
    map.put(BALANCE_DISPUTE.BD_CHARGING_SOURCE, chargingSource);
    map.put(BALANCE_DISPUTE.BD_CHARGING_AMOUNT, chargingAmount);
    map.put(BALANCE_DISPUTE.BD_CHARGING_SOURCE_SIMPLE, chargingSourceSimple);

    return map;
  }

  public Double castToDouble(Object obj) {
    double res = 0d;
    try {
      if (Objects.isNull(obj)) {
        res = 0d;
      } else if (obj instanceof Integer) {
        res = (double) (Integer) obj;
      } else if (obj instanceof Double) {
        res = (Double) obj;
      } else if (obj instanceof BigDecimal) {
        res = ((BigDecimal) obj).doubleValue();
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while casting object to Double ");
      CCATLogger.ERROR_LOGGER.error("Error while casting object to Double ", ex);
      ex.printStackTrace();
    }
    return res;
  }

  public Double castRoundedVolume(Object obj) {
    return obj.getClass() == Integer.class ? (double) (int) obj
        : ((BigInteger) obj).doubleValue();
  }

  public Integer castToInteger(Object obj) {
    return Objects.isNull(obj) ? 0
        : (Integer) obj;
  }

  public Timestamp castToTimeStamp(Object obj) {
    return Objects.isNull(obj) ? null
        : new Timestamp((Long) obj);
  }


  public String castToString(Object val) {
    return Objects.isNull(val) ? ""
        : String.valueOf(val);
  }


  public HashMap<String, String> mapDetailsToMap(BalanceDisputeTransactionDetailsModel adj,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> map) {
    return adj.toMap(map);
  }

  public void sortBdDetailsList(ArrayList<HashMap<String, String>> details,
      LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> configMap) {
    if (Objects.nonNull(configMap.get("date"))) {
      details.sort(Comparator.comparing(m -> m.get("Date"),
          comparator.reversed()));
    }
  }
}

