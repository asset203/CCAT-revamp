package com.asset.ccat.balance_dispute_service.services;

import com.asset.ccat.balance_dispute_service.annotation.LogExecutionTime;
import com.asset.ccat.balance_dispute_service.cache.BalanceDisputeTemplatesCache;
import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.constant.AdjustmentType;
import com.asset.ccat.balance_dispute_service.database.dao.BalanceDisputeDao;
import com.asset.ccat.balance_dispute_service.defines.Defines;
import com.asset.ccat.balance_dispute_service.defines.Defines.BALANCE_DISPUTE;
import com.asset.ccat.balance_dispute_service.defines.Defines.CSV_COLUMNS;
import com.asset.ccat.balance_dispute_service.defines.Defines.CSV_HEADERS;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.dto.models.*;
import com.asset.ccat.balance_dispute_service.dto.requests.GetBalanceDisputeReportRequest;
import com.asset.ccat.balance_dispute_service.dto.requests.MapBalanceDisputeServiceRequest;
import com.asset.ccat.balance_dispute_service.dto.requests.MapTodayDataUsageRequest;
import com.asset.ccat.balance_dispute_service.dto.requests.SubscriberRequest;
import com.asset.ccat.balance_dispute_service.dto.responses.BalanceDisputeReportResponse;
import com.asset.ccat.balance_dispute_service.dto.responses.BdGetTodayUsageMapperResponse;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeFileException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import com.asset.ccat.balance_dispute_service.managers.BalanceDisputeServiceManager;
import com.asset.ccat.balance_dispute_service.proxy.BalanceDisputeMapperProxy;
import com.asset.ccat.balance_dispute_service.redis.repositary.BalanceDisputeReportRepository;
import com.asset.ccat.balance_dispute_service.utils.BDUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.asset.ccat.balance_dispute_service.defines.Defines.SEVERITY.ERROR;

@Service
public class BalanceDisputeService {

  private final SimpleDateFormat formatter;
  private final Properties properties;
  private final BalanceDisputeTemplatesCache templatesCache;
  private final BalanceDisputeDao balanceDisputeDAO;
  private final BalanceDisputeMapperProxy balanceDisputeMapperProxy;
  private final BalanceDisputeReportRepository balanceDisputeReportRepositary;

  public BalanceDisputeService(Properties properties, BalanceDisputeTemplatesCache templatesCache,
      BalanceDisputeDao balanceDisputeDAO
      , BalanceDisputeMapperProxy balanceDisputeMapperProxy
      , BalanceDisputeReportRepository balanceDisputeReportRepositary) {
    this.properties = properties;
    this.templatesCache = templatesCache;
    this.balanceDisputeDAO = balanceDisputeDAO;
    this.balanceDisputeMapperProxy = balanceDisputeMapperProxy;
    this.formatter = new SimpleDateFormat(BALANCE_DISPUTE.DATE_FORMAT_PATTERN);
    this.formatter.setTimeZone(TimeZone.getTimeZone("Africa/Cairo"));
    this.balanceDisputeReportRepositary = balanceDisputeReportRepositary;
  }

  public BalanceDisputeReportResponse getBalanceDisputeMap(GetBalanceDisputeReportRequest request)
      throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("is GetAll request = {}", request.getIsGetAll());
    BalanceDisputeReportResponse response = (request.getIsGetAll() != null && request.getIsGetAll()) ?
            getAllBalanceDisputeReport(request) : getFilteredBalanceDisputeReport(request);
    CCATLogger.DEBUG_LOGGER.debug("BalanceDispute report: {}", response);
    return response;
  }

  @LogExecutionTime
  public byte[] getTodayDataUsageReport(SubscriberRequest request) throws BalanceDisputeFileException {
    MapTodayDataUsageRequest mapperRequest = callAndStorePartialCDR(request);
    BdGetTodayUsageMapperResponse report = balanceDisputeMapperProxy.mapTodayDataUsage(mapperRequest);
    return exportTodayDataUsageTransactionDetails(report);
  }

  @LogExecutionTime
  private BalanceDisputeReportResponse getAllBalanceDisputeReport(GetBalanceDisputeReportRequest request) throws BalanceDisputeException {
    HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> result = new HashMap<>();

    CCATLogger.DEBUG_LOGGER.debug("Deleting old report from redis");
    balanceDisputeReportRepositary.deleteBySubscriber(request.getMsisdn());

    HashMap<Integer, BalanceDisputeInterfaceDataModel> balanceDisputeInterfaceDataModeMap = BalanceDisputeServiceManager.BALANCE_DISPUTE_INTERFACE_DATA_MODEL_LIST;
    callAndStoreFunctionResult(result, balanceDisputeInterfaceDataModeMap, Defines.STORED_FUNCTION_NAMES.GET_ADJ_FN_DWH_TST_ADJUSTMENT, "SL_GET_ADJ_FN_ADJUSTMENT", request);
    callAndStoreFunctionResult(result, balanceDisputeInterfaceDataModeMap, Defines.STORED_FUNCTION_NAMES.GET_ADJ_FN_DWH_TST_RECHARGES, "SL_GET_ADJ_FN_RECHARGES", request);
    callAndStoreFunctionResult(result, balanceDisputeInterfaceDataModeMap, Defines.STORED_FUNCTION_NAMES.GET_ADJ_FN_DWH_TST_PAYMENT, "SL_GET_ADJ_FN_PAYMENT", request);
    callAndStoreFunctionResult(result, balanceDisputeInterfaceDataModeMap, Defines.STORED_FUNCTION_NAMES.GET_ADJ_FN_DWH_TST_DEDICATION, "SL_GET_ADJ_FN_DEDICATION", request);
    callAndStoreFunctionResult(result, balanceDisputeInterfaceDataModeMap, Defines.STORED_FUNCTION_NAMES.GET_MOC_PRE_FN_RA_NEW4, "SL_GET_USAGE_AND_ACCUMULATORS", request);

    MapBalanceDisputeServiceRequest mapperRequest = prepareMapperRequestModel(request, result);
    BalanceDisputeReportResponse getBalanceDisputeResponse = balanceDisputeMapperProxy.mapBalanceDisputeReport(mapperRequest);

    CCATLogger.DEBUG_LOGGER.debug("Filtering and sort balance dispute report");
    if (getBalanceDisputeResponse != null
            && getBalanceDisputeResponse.getDetails() != null
        && getBalanceDisputeResponse.getDetails().getTransactionDetailsList() != null
        && !getBalanceDisputeResponse.getDetails().getTransactionDetailsList().isEmpty()) {

      sortAndFetchTransactionDetails(getBalanceDisputeResponse, request);
      ArrayList<HashMap<String, String>> detailsList = getBalanceDisputeResponse.getDetails().getTransactionDetailsList();

      List<HashMap<String, String>> page = detailsList.stream()
              .skip(request.getOffset())
              .limit(request.getFetchCount())
              .toList();

      getBalanceDisputeResponse.setTotalCount(detailsList.size());
      getBalanceDisputeResponse.getDetails()
              .setTransactionDetailsList(new ArrayList<>(page));
    }
    else
      storeResponseInRedis(request.getMsisdn(), getBalanceDisputeResponse);

    return getBalanceDisputeResponse;
  }

  private void sortAndFetchTransactionDetails(BalanceDisputeReportResponse bdReport, GetBalanceDisputeReportRequest request){
    CCATLogger.DEBUG_LOGGER.debug("Setting total count and fetching part of the data");
    ArrayList<HashMap<String, String>> detailsList = bdReport.getDetails().getTransactionDetailsList();
    bdReport.setTotalCount(detailsList.size());

    CCATLogger.DEBUG_LOGGER.debug("Sorting details by Column={}", request.getSortedBy());
    String sortedBy = Optional.ofNullable(request.getSortedBy()).orElse("Date");
    int order = Optional.ofNullable(request.getOrder()).orElse(2); // 1 for ASC, 2 for DESC
    boolean isDescending = order != 1;

    List<HashMap<String, String>> sortedDetailsList = detailsList.stream()
            .sorted((detail1, detail2) -> {
              String value1 = detail1.getOrDefault(sortedBy, "").trim();
              String value2 = detail2.getOrDefault(sortedBy, "").trim();

              try {
                // Handle null or empty values
                if (value1.isEmpty() && value2.isEmpty()) return 0;
                if (value1.isEmpty()) return isDescending ? 1 : -1;
                if (value2.isEmpty()) return isDescending ? -1 : 1;

                // Handle Date & Time Sorting
                if (sortedBy.equalsIgnoreCase("Date") || sortedBy.equalsIgnoreCase("Time")) {
                  String dateTime1 = detail1.getOrDefault("Date", "") + " " + detail1.getOrDefault("Time", "");
                  String dateTime2 = detail2.getOrDefault("Date", "") + " " + detail2.getOrDefault("Time", "");

                  if (dateTime1.contains("null") || dateTime2.contains("null")) return 0;

                  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(properties.getFileDateTimeFormat());
                  LocalDateTime dt1 = LocalDateTime.parse(dateTime1, dateTimeFormatter);
                  LocalDateTime dt2 = LocalDateTime.parse(dateTime2, dateTimeFormatter);

                  return isDescending ? dt2.compareTo(dt1) : dt1.compareTo(dt2);
                }

                // Handle Numeric Sorting
                if (value1.matches("-?\\d+(\\.\\d+)?") && value2.matches("-?\\d+(\\.\\d+)?")) {
                  double num1 = Double.parseDouble(value1);
                  double num2 = Double.parseDouble(value2);
                  return isDescending ? Double.compare(num2, num1) : Double.compare(num1, num2);
                }

                // Handle String Sorting (Case-Insensitive)
                return isDescending ? value2.compareToIgnoreCase(value1) : value1.compareToIgnoreCase(value2);
              } catch (Exception e) {
                CCATLogger.DEBUG_LOGGER.error("Sorting error for column: " + sortedBy, e);
                CCATLogger.ERROR_LOGGER.error("Sorting error for column: " + sortedBy, e);
                return 0;
              }
            })
            .toList();

    bdReport.getDetails().setTransactionDetailsList(new ArrayList<>(sortedDetailsList));
    storeResponseInRedis(request.getMsisdn(), bdReport);
  }

  private BalanceDisputeReportResponse getFilteredBalanceDisputeReport(GetBalanceDisputeReportRequest request) throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("Start getting balance dispute report from redis");
    BalanceDisputeReportResponse report = Optional.ofNullable(
            balanceDisputeReportRepositary.findById(request.getMsisdn(), 1))
            .orElseThrow(() -> new BalanceDisputeException(ErrorCodes.ERROR.NO_REPORTS_FOUND));
    CCATLogger.DEBUG_LOGGER.debug("The retrieved report: {}", report);

    if (report.getDetails() != null
        && report.getDetails().getTransactionDetailsList() != null
        && !report.getDetails().getTransactionDetailsList().isEmpty())
    {

      CCATLogger.DEBUG_LOGGER.debug("Start preparing sorting function");
      sortAndFetchTransactionDetails(report, request);
      ArrayList<HashMap<String, String>> detailsList = report.getDetails().getTransactionDetailsList();

      List<HashMap<String, String>> page = detailsList.stream()
          .skip(request.getOffset())
          .limit(request.getFetchCount())
          .toList();

      report.setTotalCount(detailsList.size());
      report.getDetails()
              .setTransactionDetailsList(new ArrayList<>(page));
    }
    return report;
  }


  private List<SPParameterModel> getParameters(BalanceDisputeInterfaceDataModel bModel,
      GetBalanceDisputeReportRequest request) {

    String parametersStr = bModel.getInputParameters();
    String[] splitParameters = parametersStr.split(",");
    List<SPParameterModel> parametersList = new ArrayList<>();

    for (String parameter : splitParameters) {
      String[] currentParameter = parameter.split("=");
      SPParameterModel spParameter = new SPParameterModel();

      String parameterName = currentParameter[0];
      String parameterValue = currentParameter[1];
      if (parameterValue.contains("$")) {

        switch (parameterValue) {
          case Defines.STORED_FUNCTION_PARAMETERS.MSISDN:
            parameterValue = request.getMsisdn();
            break;
          case Defines.STORED_FUNCTION_PARAMETERS.DATE1:
            Date dateFrom = new Date(request.getDateFrom());
            parameterValue = formatter.format(dateFrom);
            break;
          case Defines.STORED_FUNCTION_PARAMETERS.DATE2:
            Date dateTo = new Date(request.getDateTo());
            parameterValue = formatter.format(dateTo);
            break;
          default:
            break;
        }
      }
      spParameter.setParameterName(parameterName);
      spParameter.setParameterValue(parameterValue);
      parametersList.add(spParameter);
    }
    return parametersList;
  }


  @LogExecutionTime
  public byte[] exportBalanceDisputeExcelReport(SubscriberRequest request) throws BalanceDisputeFileException {
    CCATLogger.DEBUG_LOGGER.debug("Start getting balance dispute report from redis");
    BalanceDisputeReportResponse report = balanceDisputeReportRepositary.findById(request.getMsisdn(), 1);
    CCATLogger.INTERFACE_LOGGER.info("MSISDN=[{}] the cached report: {} ", request.getMsisdn(), report);
    if (Objects.isNull(report))
      throw new BalanceDisputeFileException(ErrorCodes.ERROR.NO_REPORTS_FOUND, ERROR);

    try (XSSFWorkbook workbook = new XSSFWorkbook(
            new FileInputStream(templatesCache.getBalanceDisputeReportsCache()
                    .get(Defines.BALANCE_DISPUTE.DB_CALCULATION_FILE_XLSM)));
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      XSSFSheet sheet = workbook.getSheet(properties.getDbCalculationSheetName());
      Integer numberOfRows = 0;
      String printSummaryRows = getSummaryString(report);
      numberOfRows = createRowsAndCells(sheet, printSummaryRows, numberOfRows);
      sheet.createRow(numberOfRows++);
      String transactionDetailsRows = getTransactionDetailsString(report);
      createRowsAndCells(sheet, transactionDetailsRows, numberOfRows);
      workbook.write(out);
      return out.toByteArray();
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while preparing Balance Sheet Excel Report. ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while preparing Balance Sheet Excel Report. ", ex);
      throw new BalanceDisputeFileException(ErrorCodes.ERROR.EXPORT_FAILED, ERROR);
    }
  }


  private String getSummaryString(BalanceDisputeReportResponse report) {
    ArrayList<BdSubTypeModel> sumTemp;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Balance Sheet");
    stringBuilder.append("#%#");
    //---
    stringBuilder.append("Type");
    stringBuilder.append("@@");
    stringBuilder.append("Credit");
    stringBuilder.append("@@");
    stringBuilder.append("Debit");
    //--
    BalanceSummarySheetModel summarySheet = report.getBalanceSummarySheet();
    UsageSummarySheetModel usageSheet = report.getUsageSummarySheet();
    sumTemp = summarySheet.getMbRecharges();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Main Balance Recharges");
      stringBuilder.append("#%#");
      for (BdSubTypeModel record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? "" : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getCreditStr());
        stringBuilder.append("@@");
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Recharges");
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getMbRechargesTtlCredit() == 0 ? ""
              : report.getBalanceSummarySheet().getMbRechargesTtlCredit());
      stringBuilder.append("#%#");
    }

    //--
    sumTemp = summarySheet.getMbPayment();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Main Balance Payment");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {

        stringBuilder.append(Objects.isNull(record.getSubType()) ? ""
            : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getCreditStr());
        stringBuilder.append("@@");
        stringBuilder.append(" ");
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Payment");
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getMbPaymentsTtlCredit() == 0 ? ""
              : report.getBalanceSummarySheet().getMbPaymentsTtlCredit());
      stringBuilder.append("#%#");
    }
    //--
    sumTemp = summarySheet.getMbAdjustment();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Main Balance Adjustment");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? ""
            : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getCreditStr());
        stringBuilder.append("@@");
        stringBuilder.append(record.getDebitStr());
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Adjustment");
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getMbAdjustmentsTtlCredit() == 0
              ? ""
              : report.getBalanceSummarySheet().getMbAdjustmentsTtlCredit());
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getMbAdjustmentsTtlDebit() == 0 ? ""
              : report.getBalanceSummarySheet().getMbAdjustmentsTtlDebit());
      stringBuilder.append("#%#");
    }
    //--
    sumTemp = summarySheet.getMbUsage();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Main Balance Usage");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? ""
            : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append("@@");
        stringBuilder.append(record.getDebitStr());
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Usage");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getMbUsgTtlDebit() == 0 ? ""
              : report.getBalanceSummarySheet().getMbUsgTtlDebit());
      stringBuilder.append("#%#");
    }

    sumTemp = summarySheet.getDaAdjustments();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("DA Adjustment");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? "" : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getCreditStr());
        stringBuilder.append("@@");
        stringBuilder.append(record.getDebitStr());
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Adjustment");
      stringBuilder.append("@@");
      stringBuilder.append(
          report.getBalanceSummarySheet().getDaAdjustmentsTtlCredit() == 0 ? ""
              : report.getBalanceSummarySheet().getDaAdjustmentsTtlCredit());
      stringBuilder.append("@@");
      stringBuilder.append(report.getBalanceSummarySheet().getDaAdjustmentsTtlDebit() == 0 ? ""
          : report.getBalanceSummarySheet().getDaAdjustmentsTtlDebit());
      stringBuilder.append("#%#");
    }

    sumTemp = summarySheet.getDaBonusAdj();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("DA Bonus Adjustment");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? "" : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getCreditStr());
        stringBuilder.append("@@");
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Bonus Adjustment");
      stringBuilder.append("@@");
      stringBuilder.append(report.getBalanceSummarySheet().getDaBonusAdjTtlCredit() == 0 ? ""
          : report.getBalanceSummarySheet().getDaBonusAdjTtlCredit());
      stringBuilder.append("@@");
      stringBuilder.append("#%#");
    }

    sumTemp = summarySheet.getDaUsg();
    if (Objects.nonNull(sumTemp) && !sumTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("DA Usage");
      stringBuilder.append("#%#");
      for (var record : sumTemp) {
        stringBuilder.append(Objects.isNull(record.getSubType()) ? ""
            : record.getSubType());
        stringBuilder.append("@@");
        stringBuilder.append("@@");
        stringBuilder.append(record.getDebitStr());
        stringBuilder.append("#%#");
      }
      stringBuilder.append("Total Usage");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append(report.getBalanceSummarySheet().getDaUsgTtlDebit() == 0 ? ""
          : report.getBalanceSummarySheet().getDaUsgTtlDebit());
      stringBuilder.append("#%#");
    }
    //--
    if (report.getDetails().getTotalMBCredit() != 0 || report.getDetails().getTotalMBDebit() != 0) {
      stringBuilder.append("Main Balance Grand Total");
      stringBuilder.append("@@");
      stringBuilder.append(report.getDetails().getTotalMBCredit() == 0 ? ""
          : report.getDetails().getTotalMBCredit());
      stringBuilder.append("@@");
      stringBuilder.append(report.getDetails().getTotalMBDebit() == 0 ? ""
          : report.getDetails().getTotalMBDebit());
      stringBuilder.append("#%#");
    }

    if (report.getDetails().getTotalDACredit() != 0 || report.getDetails().getTotalDADebit() != 0) {
      stringBuilder.append("Dedicated Accounts Grand Total");
      stringBuilder.append("@@");
      stringBuilder.append(report.getDetails().getTotalDACredit() == 0 ? ""
          : report.getDetails().getTotalDACredit());
      stringBuilder.append("@@");
      stringBuilder.append(report.getDetails().getTotalDADebit() == 0 ? ""
          : report.getDetails().getTotalDADebit());
      stringBuilder.append("#%#");
    }
    //--
    stringBuilder.append("#%#");
    stringBuilder.append("Usage Summary Sheet");
    stringBuilder.append("#%#");
    stringBuilder.append("Type");
    stringBuilder.append("@@");
    stringBuilder.append("Total");

    ArrayList<BdSummaryUsageModel> usageTemp = usageSheet.getInternetUsage();
    if (Objects.nonNull(usageTemp) && !usageTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Internet Usage (In KB)");
      stringBuilder.append("#%#");
      for (var record : usageTemp) {
        stringBuilder.append(Objects.isNull(record.getType()) ? "" : record.getType());
        stringBuilder.append("@@");
        stringBuilder.append("=\"").append(record.getTotal()).append("\"");
        stringBuilder.append("#%#");
      }
    }
    usageTemp = usageSheet.getMobileTelephony();
    if (Objects.nonNull(usageTemp) && !usageTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Mobile Telephony");
      stringBuilder.append("#%#");
       for (var record : usageTemp) {
        stringBuilder.append(Objects.isNull(record.getType()) ? "" : record.getType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getTotal());
        stringBuilder.append("#%#");
      }

       
      stringBuilder.append("Total");
      stringBuilder.append("@@");
      stringBuilder.append(report.getBalanceSummarySheet().getMobileTelephonyTotal());
      stringBuilder.append("#%#");
    }
    usageTemp = usageSheet.getShortMessages();
    if (Objects.nonNull(usageTemp) && !usageTemp.isEmpty()) {
      stringBuilder.append("#%#");
      stringBuilder.append("Short Message MO/PP");
      stringBuilder.append("#%#");
      for (var record : usageTemp) {
        stringBuilder.append(Objects.isNull(record.getType()) ? "" : record.getType());
        stringBuilder.append("@@");
        stringBuilder.append(record.getTotal());
        stringBuilder.append("#%#");
      }
    }

    return stringBuilder.toString();
  }

  private String getTransactionDetailsString(BalanceDisputeReportResponse report) {
    StringBuilder stringBuilder = new StringBuilder();
    BalanceDisputeDetailsModel details = report.getDetails();
    ArrayList<HashMap<String, String>> detailsTemp = details.getTransactionDetailsList();
    if (Objects.nonNull(detailsTemp) && !detailsTemp.isEmpty() && !report.getDetails()
        .getColumnNames().isEmpty()) {
      stringBuilder.append("Transaction Details");
      stringBuilder.append("#%#");
      //---
      for (String cName : report.getDetails().getColumnNames()) {
        stringBuilder.append("@@");
        stringBuilder.append(cName);
      }
      stringBuilder.append("#%#");

      for (HashMap<String, String> record : detailsTemp) {
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Type"))) {
          stringBuilder.append(record.get("Type"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Sub-Type"))) {
          stringBuilder.append(record.get("Sub-Type"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Date"))) {
          stringBuilder.append(record.get("Date"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Time"))) {
          stringBuilder.append(record.get("Time"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Region/Cell Site"))) {
          stringBuilder.append(record.get("Region/Cell Site"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Total Actual Seconds"))) {
          stringBuilder.append(record.get("Total Actual Seconds"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Duration (in mins)"))) {
          stringBuilder.append(record.get("Duration (in mins)"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Internet Usage (in KB)"))) {
          stringBuilder.append(record.get("Internet Usage (in KB)"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Other Party Number"))) {
          stringBuilder.append(record.get("Other Party Number"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Destination"))) {
          stringBuilder.append(record.get("Destination"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Cost"))) {
          stringBuilder.append(record.get("Cost"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Call End"))) {
          stringBuilder.append(record.get("Call End"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Rating Group"))) {
          stringBuilder.append(record.get("Rating Group"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Community Id"))) {
          stringBuilder.append(record.get("Community Id"));
        }
        ////////////////
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Amount"))) {
          stringBuilder.append(record.get("Amount"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Adjustment Code"))) {
          stringBuilder.append(record.get("Adjustment Code"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Adjustment Type"))) {
          stringBuilder.append(record.get("Adjustment Type"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Balance Before"))) {
          stringBuilder.append(record.get("Balance Before").replaceAll(",0", ""));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Balance After"))) {
          stringBuilder.append(record.get("Balance After").replaceAll(",0", ""));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("DA Before"))) {
          stringBuilder.append(record.get("DA Before").replaceAll(",0", ""));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("DA After"))) {
          stringBuilder.append(record.get("DA After").replaceAll(",0", ""));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Charging Source"))) {
          stringBuilder.append(record.get("Charging Source"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Charging Amount"))) {
          stringBuilder.append(record.get("Charging Amount"));
        }
        stringBuilder.append("@@");

        if (Objects.nonNull(record.get("Free SMS"))) {
          stringBuilder.append(record.get("Free SMS"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Accumulator Before"))) {
          stringBuilder.append(record.get("Accumulator Before"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Accumulator After"))) {
          stringBuilder.append(record.get("Accumulator After"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Bit Numbers"))) {
          stringBuilder.append(record.get("Bit Numbers"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Account Group"))) {
          stringBuilder.append(record.get("Account Group"));
        }
        stringBuilder.append("@@");
        if (Objects.nonNull(record.get("Rate Plan"))) {
          stringBuilder.append(record.get("Rate Plan"));
        }
        stringBuilder.append("#%#");
      }

      stringBuilder.append("Total");
      stringBuilder.append("@@"); //TYPE
      stringBuilder.append("@@");//SUBTYPE
      stringBuilder.append("@@");//DATE
      stringBuilder.append("@@");//TIME
      stringBuilder.append("@@");//REGION
      stringBuilder.append("@@");//TOTALaCTUALsECONDS
      if (report.getDetails().getTotalActualSeconds() != 0) {
        stringBuilder.append(report.getDetails().getTotalActualSeconds());
      }
      stringBuilder.append("@@");//DURATION
      if (report.getDetails().getTotalDuration() != 0) {
        stringBuilder.append(report.getDetails().getTotalDuration());
      }
      stringBuilder.append("@@");//INTERNET USAGE
      if (report.getDetails().getTotalInternetUsage() != 0) {
        stringBuilder.append(report.getDetails().getTotalInternetUsage());
      }
      stringBuilder.append("@@");//OTHERPARTYNUM
      stringBuilder.append("@@");//DESTINATION
      stringBuilder.append("@@");//COST
      if (report.getDetails().getTotalCost() != 0) {
        stringBuilder.append(report.getDetails().getTotalCost());
      }
      stringBuilder.append("@@");//CALL END
      stringBuilder.append("@@");//AMOUNT
      if (report.getDetails().getTotalAmountCredit() != 0) {
        stringBuilder.append("Credit: ")
            .append(report.getDetails().getTotalAmountCredit());
      }
      if (report.getDetails().getTotalAmountDebit() != 0) {
        stringBuilder.append(" Debit: ")
            .append(report.getDetails().getTotalAmountDebit());
      }
      stringBuilder.append("@@");//ADJ CODE
      stringBuilder.append("@@");//ADJ TYPE
      stringBuilder.append("@@");//B BEFORE
      stringBuilder.append("@@");//B AFTER
      stringBuilder.append("@@");//DA BEFORE
      stringBuilder.append("@@");//DA AFTER
      stringBuilder.append("@@");//CHARGING SOURCE
      stringBuilder.append("@@");//FREE SMS
      if (report.getDetails().getTotalFreeSms() != 0) {
        stringBuilder.append(report.getDetails().getTotalFreeSms());
      }
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("@@");
      stringBuilder.append("#%#");
      stringBuilder.append("#%#");
    }
    return stringBuilder.toString();
  }

  private Integer createRowsAndCells(XSSFSheet sheet, String stringRows, Integer numberOfRows) {
    String[] rows = stringRows.split("#%#");
    for (String row : rows) {
      Row excelRow = sheet.createRow(numberOfRows++);
      String[] cell = row.split("@@");
      for (int j = 0; j < cell.length; j++) {
        String cellValue = cell[j];
        Integer integerValue;
        Double doubleValue;
        if ((integerValue = BDUtil.isInteger(cellValue)) != null) {
          excelRow.createCell(j).setCellValue(integerValue);
        } else if ((doubleValue = BDUtil.isDouble(cellValue)) != null) {
          excelRow.createCell(j).setCellValue(doubleValue);
        } else {
          excelRow.createCell(j).setCellValue(cellValue);
        }
      }
    }
    return numberOfRows;
  }

  private MapTodayDataUsageRequest callAndStorePartialCDR(
      SubscriberRequest request) throws BalanceDisputeFileException {
    MapTodayDataUsageRequest mapTodayDataUsageRequest = new MapTodayDataUsageRequest();
    mapTodayDataUsageRequest.setRequestId(request.getRequestId());
    mapTodayDataUsageRequest.setSessionId(request.getSessionId());
    mapTodayDataUsageRequest.setUsername(request.getUsername());
    mapTodayDataUsageRequest.setUserId(request.getUserId());
    mapTodayDataUsageRequest.setToken(request.getToken());
    Map<String, Object> result = balanceDisputeDAO.callStoredProcedure(request);

    ArrayList<LinkedCaseInsensitiveMap<Object>> partialCDRS =
            (ArrayList<LinkedCaseInsensitiveMap<Object>>) result.get(BALANCE_DISPUTE.PARTIAL_CDRS);

    mapTodayDataUsageRequest.getTodayDataUsageMap().put(BALANCE_DISPUTE.PARTIAL_CDRS, partialCDRS);
    mapTodayDataUsageRequest.setErrorCode((BigDecimal) result.get(BALANCE_DISPUTE.ERROR_CODE));

    return mapTodayDataUsageRequest;
  }

  @LogExecutionTime
  private byte[] exportTodayDataUsageTransactionDetails(BdGetTodayUsageMapperResponse response) throws BalanceDisputeFileException {
    CCATLogger.DEBUG_LOGGER.info("Start export Get Today Data Usage mapper response");
    try {
      byte[] csv = null;
      if (Objects.nonNull(response.getDetails().getTransactionDetailsList())
              && !response.getDetails().getTransactionDetailsList().isEmpty()
              && !response.getDetails().getColumnNames().isEmpty()) {

        String[] headers = {CSV_HEADERS.DATE_AND_TIME, CSV_HEADERS.TOTAL_ACTUAL_SEC,
                CSV_HEADERS.INTERNET_USAGE,
                CSV_HEADERS.OTHER_PARTY_NO, CSV_HEADERS.DESTINATION, CSV_HEADERS.BALANCE_BEFORE,
                CSV_HEADERS.BALANCE_AFTER,
                CSV_HEADERS.DA_BEFORE, CSV_HEADERS.DA_AFTER, CSV_HEADERS.CHARGING_SOURCE,
                CSV_HEADERS.CHARGING_AMOUNT};

        String[][] data = new String[response.getDetails().getTransactionDetailsList()
                .size()][11];
        for (int i = 0; i < response.getDetails().getTransactionDetailsList().size(); i++) {
          HashMap<String, String> transactionDetails = response.getDetails()
                  .getTransactionDetailsList().get(i);
          data[i][0] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.DATE)) && Objects.nonNull(
                  transactionDetails.get(CSV_COLUMNS.TIME)) ?
                  transactionDetails.get(CSV_COLUMNS.DATE) + " " + transactionDetails.get(
                          CSV_COLUMNS.TIME)
                  : "";
          data[i][1] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.TOTAL_ACTUAL_SEC))
                  ? transactionDetails.get(CSV_COLUMNS.TOTAL_ACTUAL_SEC) : "";
          data[i][2] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.INTERNET_USAGE))
                  ? transactionDetails.get(CSV_COLUMNS.INTERNET_USAGE) : "";
          data[i][3] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.OTHER_PARTY_NO))
                  ? transactionDetails.get(CSV_COLUMNS.OTHER_PARTY_NO) : "";
          data[i][4] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.RATING_GROUP))
                  ? transactionDetails.get(CSV_COLUMNS.RATING_GROUP) : "";
          data[i][5] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.BALANCE_BEFORE))
                  ? transactionDetails.get(CSV_COLUMNS.BALANCE_BEFORE) : "";
          data[i][6] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.BALANCE_AFTER))
                  ? transactionDetails.get(CSV_COLUMNS.BALANCE_AFTER) : "";
          data[i][7] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.DA_BEFORE))
                  && !transactionDetails.get(CSV_COLUMNS.DA_BEFORE).isBlank() ?
                  transactionDetails.get(CSV_COLUMNS.DA_BEFORE) : "";
          data[i][8] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.DA_AFTER)) ?
                  transactionDetails.get(CSV_COLUMNS.DA_AFTER) : "";
          data[i][9] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.CHARGING_SOURCE)) ?
                  transactionDetails.get(CSV_COLUMNS.CHARGING_SOURCE) : "";
          data[i][10] = Objects.nonNull(transactionDetails.get(CSV_COLUMNS.CHARGING_AMOUNT)) ?
                  transactionDetails.get(CSV_COLUMNS.CHARGING_AMOUNT) : "";
        }
        //create a CSV printer
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
          // create headers row
          printer.println();
          printer.printRecord(BALANCE_DISPUTE.CSV_FILE_HEADLINE);
          printer.printRecord(headers);
          // create data rows
          printer.printRecords(data);
          printer.print(CSV_COLUMNS.COMMA);
          printer.println();
          printer.println();
          // flushing printer content to output stream
          printer.flush();
          // return content of output stream
          csv = out.toByteArray();
          CCATLogger.DEBUG_LOGGER.info("Done exporting Get Today Data Usage mapper response into csv byteArray");
        }
      }
      if(csv == null)
        throw new BalanceDisputeFileException(ErrorCodes.ERROR.EXPORT_FAILED, ERROR);
      return csv;
    } catch (BalanceDisputeFileException ex){
      throw  ex;
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Exception occurred while exporting Get Today Data Usage Report ", ex);
      CCATLogger.ERROR_LOGGER.error("Exception occurred while exporting Get Today Data Usage Report ", ex);
      throw new BalanceDisputeFileException(ErrorCodes.ERROR.EXPORT_FAILED, ERROR);
    }
  }

  private void callAndStoreFunctionResult(Map<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> result,
                                          Map<Integer, BalanceDisputeInterfaceDataModel> dataModelMap,
                                          Integer adjId, String resultKey, GetBalanceDisputeReportRequest request) throws BalanceDisputeException {
    BalanceDisputeInterfaceDataModel model = dataModelMap.get(adjId);
    CCATLogger.DEBUG_LOGGER.debug("AdjustmentType = [{}] || SP = [{}] -- ", AdjustmentType.getAdjustment(adjId-1), model.getSpName());

    List<SPParameterModel> parametersList = getParameters(model, request);
    CCATLogger.INTERFACE_LOGGER.debug("Parameters List = {}", parametersList);

    Map<String, Object> functionResponse = balanceDisputeDAO.callStoredFunction(model.getSpName(), parametersList);
    CCATLogger.DEBUG_LOGGER.debug("#Records = {} \nfunctionResponse = {}", ((ArrayList<?>)functionResponse.get("RESULTS")).size(), functionResponse);

    if (functionResponse.get("RESULTS") != null)
      result.put(resultKey, (ArrayList<LinkedCaseInsensitiveMap<Object>>) functionResponse.get("RESULTS"));
    else
      CCATLogger.DEBUG_LOGGER.warn("No results found for stored function {}", model.getSpName());
  }

  private MapBalanceDisputeServiceRequest prepareMapperRequestModel(GetBalanceDisputeReportRequest request, HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> result){
    CCATLogger.DEBUG_LOGGER.debug("Preparing Mapper request model");
    MapBalanceDisputeServiceRequest mapperRequest = new MapBalanceDisputeServiceRequest();
    mapperRequest.setBalanceDisputeServiceMap(result);
    mapperRequest.setRequestId(request.getRequestId());
    mapperRequest.setSessionId(request.getSessionId());
    mapperRequest.setUsername(request.getUsername());
    mapperRequest.setUserId(request.getUserId());
    mapperRequest.setToken(request.getToken());
    return mapperRequest;
  }
  private void storeResponseInRedis(String msisdn, BalanceDisputeReportResponse response) {
    CCATLogger.DEBUG_LOGGER.debug("Storing report in Redis for MSISDN: {}", msisdn);
    balanceDisputeReportRepositary.saveAll(msisdn,
            new HashMap<>() {{
              put(1, response);
            }});  }
}


