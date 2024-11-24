/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayFilesException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.customer_care.history.ExportSubscriberActivities;
import com.asset.ccat.gateway.models.requests.customer_care.history.ExportSubscriberActivityDetails;
import com.asset.ccat.gateway.models.requests.customer_care.history.GetAllSubscriberActivityRequest;
import com.asset.ccat.gateway.models.requests.customer_care.history.GetSubscriberActivitiesRequest;
import com.asset.ccat.gateway.models.responses.customer_care.history.GetSubscriberActivitiesResponse;
import com.asset.ccat.gateway.models.shared.Filter;
import com.asset.ccat.gateway.proxy.AccountHistoryProxy;
import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;
import com.asset.ccat.gateway.redis.repository.AccountHistoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author wael.mohamed
 */
@Service
public class AccountHistoryService {

    @Autowired
    private AccountHistoryProxy accountHistoryProxy;

    @Autowired
    private AccountHistoryRepository repository;

    @Autowired
    private Properties properties;

    private HashMap<String, Function<Filter, Predicate<SubscriberActivityModel>>> filterPredicatesMap;

    public GetSubscriberActivitiesResponse getSubscriberActivities(GetSubscriberActivitiesRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving getSubscriberActivities request");
        return (request.getIsGetAll() != null && request.getIsGetAll()) ?
                getAllSubscriberActivity(request) : getFilteredSubscriberActivities(request);
    }

    public void deleteSubscriberHistory(String msisdn) {
        repository.deleteBySubscriber(msisdn);
    }

    public byte[] exportSubscriberActivities(ExportSubscriberActivities request) throws GatewayFilesException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start getting subscriber activities from redis for [" + request.getMsisdn() + "] ");
            List<SubscriberActivityModel> activitiesList = repository.findBySubscriber(request.getMsisdn());
            if (activitiesList == null || activitiesList.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("No subscriber activities were found");
                throw new GatewayFilesException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            CCATLogger.DEBUG_LOGGER.debug("Retrieved [" + activitiesList.size() + "] subscriber activities from redis for [" + request.getMsisdn() + "] ");

            //Writing activities to csv sheet
            CCATLogger.DEBUG_LOGGER.debug("Start exporting subscriber activities to csv file");

            String[] headers = {"Subscriber", "Date", "Type", "Subtype",
                    "Amount", "Balance", "Account Status",
                    "Trx Type", "Trx Code"};
            String[][] data = new String[activitiesList.size()][9];
            for (int i = 0; i < activitiesList.size(); i++) {
                SubscriberActivityModel activity = activitiesList.get(i);
                data[i][0] = activity.getSubscriber();
                data[i][1] = new Date(activity.getDate()).toString();
                data[i][2] = activity.getActivityType();
                data[i][3] = activity.getSubType();
                data[i][4] = activity.getAmount();
                data[i][5] = activity.getBalance();
                data[i][6] = activity.getAccountStatus();
                data[i][7] = activity.getTransactionType();
                data[i][8] = activity.getTransactionCode();
            }
            //create a CSV printer
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
                // create headers row
                printer.printRecord(headers);

                // create data rows
                for (String[] line : data) {
                    printer.printRecord(line);
                }

                // flushing printer content to output stream
                printer.flush();

                // return content of output stream
                CCATLogger.DEBUG_LOGGER.info("Finished serving export subscriber activities request successfully");
                byte[] result = out.toByteArray();
                if (result.length == 0)
                    throw new GatewayFilesException(ErrorCodes.ERROR.EXPORT_FAILED, Defines.SEVERITY.ERROR);
                return result;
            }
        } catch (GatewayFilesException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception Occurred while constructing subscriberHistoryFile. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception Occurred while constructing subscriberHistoryFile. ", ex);
            throw new GatewayFilesException(ErrorCodes.ERROR.PARSING_FAILED);
        }
    }

    public byte[] exportSubscriberActivityDetails(ExportSubscriberActivityDetails request) throws GatewayFilesException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start getting subscriber activities from redis for [" + request.getMsisdn() + "] ");
            List<SubscriberActivityModel> activitiesList = repository.findBySubscriber(request.getMsisdn());
            if (activitiesList == null || activitiesList.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("No subscriber activities were found");
                throw new GatewayFilesException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            CCATLogger.DEBUG_LOGGER.debug("Retrieved [" + activitiesList.size() + "] subscriber activities from redis for [" + request.getMsisdn() + "] ");

            sortDetails(activitiesList);

            //Writing activities to csv sheet
            CCATLogger.DEBUG_LOGGER.debug("Start exporting subscriber activities to csv file");

            //create a CSV printer
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
                String currentActivityType = null;
                Object[] headers = null;
                Object[] line = null;

                for (SubscriberActivityModel activity : activitiesList) {
                    if (activity.getDetails() == null || activity.getDetails().isEmpty()) {
                        //skip record
                        continue;
                    }

                    if (currentActivityType == null || !currentActivityType.equals(activity.getActivityType())) {
                        //skip two line before new type
                        printer.println();
                        printer.println();

                        currentActivityType = activity.getActivityType();
                        headers = activity.getDetails().keySet().toArray();

                        //print activty type
                        printer.printRecord(currentActivityType);
                        printer.printRecord(headers);
                    }

                    // write new record
                    line = new Object[headers.length];
                    for (int j = 0; j < headers.length; j++) {
                        line[j] = activity.getDetails().get(headers[j]);
                    }
                    printer.printRecord(line);
                }

                // flushing printer content to output stream
                printer.flush();

                // return content of output stream
                CCATLogger.DEBUG_LOGGER.info("Finished serving export subscriber activities request successfully");
                byte[] result = out.toByteArray();
                if (result.length == 0)
                    throw new GatewayFilesException(ErrorCodes.ERROR.EXPORT_FAILED, Defines.SEVERITY.ERROR);
                return result;
            }
        } catch (GatewayFilesException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception Occurred while constructing subscriberHistoryFile. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception Occurred while constructing subscriberHistoryFile. ", ex);
            throw new GatewayFilesException(ErrorCodes.ERROR.EXPORT_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private GetSubscriberActivitiesResponse getAllSubscriberActivity(GetSubscriberActivitiesRequest request) throws GatewayException {

        //Clear redis for any previously retrieved data for same subscriber
        CCATLogger.DEBUG_LOGGER.debug("Start deleting all subscriber activities from redis for [" + request.getMsisdn() + "]");
        deleteSubscriberHistory(request.getMsisdn());

        //Retrieve all subscriber activities from ods-service
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all subscriber activities from ODS-service");
        GetAllSubscriberActivityRequest getAllSubscriberActivityRequest = new GetAllSubscriberActivityRequest();
        getAllSubscriberActivityRequest.setToken(request.getToken());
        getAllSubscriberActivityRequest.setRequestId(request.getRequestId());
        getAllSubscriberActivityRequest.setSessionId(request.getSessionId());
        getAllSubscriberActivityRequest.setMsisdn(request.getMsisdn());
        getAllSubscriberActivityRequest.setDateFrom(request.getDateFrom());
        getAllSubscriberActivityRequest.setDateTo(request.getDateTo());
        List<SubscriberActivityModel> subscriberActivityList = accountHistoryProxy.getAllSubscriberActivity(getAllSubscriberActivityRequest);
        if (subscriberActivityList == null || subscriberActivityList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("No subscriber activities were found");
            throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        CCATLogger.DEBUG_LOGGER.debug("Retrieved [" + subscriberActivityList.size() + "] subscriber activities from ODS-service");

        CCATLogger.DEBUG_LOGGER.debug("Start inserting subscriber activities to redis");
        HashMap<Integer, SubscriberActivityModel> history = new HashMap<>();
        // sort then put in map
        subscriberActivityList.stream().sorted(((activity1, activity2) -> { // rename o1 , o2
            return activity2.getDate() == null ? -1 : activity2.getDate().compareTo(activity1.getDate());
        })).forEach(activity -> history.put(activity.getIdentifier(), activity));
        repository.saveAll(request.getMsisdn(), history);
        CCATLogger.DEBUG_LOGGER.debug("Done inserting subscriber activities to redis");

        Integer count = subscriberActivityList.size();
        return new GetSubscriberActivitiesResponse(request.getFetchCount() >= subscriberActivityList.size() ? subscriberActivityList :
                new ArrayList<>(subscriberActivityList.subList(0, request.getFetchCount())),
                count);
    }

    private GetSubscriberActivitiesResponse getFilteredSubscriberActivities(GetSubscriberActivitiesRequest request) throws GatewayException {
        List<SubscriberActivityModel> activitiesList = repository.findBySubscriber(request.getMsisdn());
        if (activitiesList == null || activitiesList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("No subscriber activities were found");
            throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        CCATLogger.DEBUG_LOGGER.debug("Retrieved [" + activitiesList.size() + "] subscriber activities from redis for [" + request.getMsisdn() + "] ");

        CCATLogger.DEBUG_LOGGER.debug("Start filtering retrieved activities list");
        Predicate<SubscriberActivityModel> compositeFilter = null;
        if (request.getQueryString() != null && !request.getQueryString().isEmpty() && !request.getQueryString().isBlank()) {
            CCATLogger.DEBUG_LOGGER.debug("Start composing activities filter");
            List<Filter> filters = request.getFilters();
            for (Filter filter : filters) {
                String filterBy = filter.getField();
                if (filterPredicatesMap.get(filterBy) == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Filter by [" + filter.getField() + "] is not supported");
                    continue;
                }
                if (compositeFilter == null) {
                    compositeFilter = filterPredicatesMap.get(filterBy).apply(filter);
                } else {
                    compositeFilter = compositeFilter.and(filterPredicatesMap.get(filterBy).apply(filter));
                }
            }
        }

        CCATLogger.DEBUG_LOGGER.debug("Start composing activities sort function");
        String sortField;
        if (request.getSortedBy() != null && !request.getSortedBy().isEmpty() && !request.getSortedBy().isBlank()) {
            sortField = request.getSortedBy();
        } else {
            // sort by date by default
            sortField = "date";
        }

        // sort method
        Comparator<SubscriberActivityModel> sortFunction = (activity1, activity2) -> {
            if (activity1.getField(sortField) == null && activity2.getField(sortField) == null) return 0;
            if (activity1.getField(sortField) == null) return 1;
            if (activity2.getField(sortField) == null) return -1;
            if (request.getOrder() != null && request.getOrder().equals(1)) {
                // sort in ascending` order
                return activity1.getField(sortField).compareTo(activity2.getField(sortField));
            } else {
                // sort in descending order
                return activity2.getField(sortField).compareTo(activity1.getField(sortField));
            }
        };

        if (compositeFilter != null) {
            CCATLogger.DEBUG_LOGGER.debug("Start filtering");
            activitiesList = activitiesList.stream()
                    .filter(compositeFilter)
                    .collect(Collectors.toList());
        }

        if (activitiesList == null || activitiesList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("No subscriber activities were found");
            throw new GatewayException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }

        Integer count = activitiesList.size();
        CCATLogger.DEBUG_LOGGER.debug("Start sorting activities");
        List<SubscriberActivityModel> page = activitiesList.stream()
                .sorted(sortFunction)
                .skip(request.getOffset())
                .limit(request.getFetchCount())
                .collect(Collectors.toList());
        return new GetSubscriberActivitiesResponse(page, count);
    }


    @PostConstruct
    public void init() {
        this.filterPredicatesMap = new HashMap<>();

        // Date filter predicate
        filterPredicatesMap.put("date", filter ->
                (activity -> {
                    if (activity.getDate() == null) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case EQUALS:
                            return activity.getDate().equals(Long.valueOf(filter.getValue()));
                        default:
                            return false;
                    }
                }));

        // Type filter predicate
        filterPredicatesMap.put("type", filter ->
                (activity -> {
                    if (activity.getActivityType() == null || activity.getActivityType().isEmpty() || activity.getActivityType().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case START_WITH:
                            return activity.getActivityType().toLowerCase().startsWith(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case CONTAINS:
                            return activity.getActivityType().toLowerCase().contains(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case EQUALS:
                            return activity.getActivityType().equalsIgnoreCase(filter.getValue());
                        default:
                            return false;
                    }
                }));

        // SubType filter predicate
        filterPredicatesMap.put("subType", filter ->
                (activity -> {
                    if (activity.getSubType() == null || activity.getSubType().isEmpty() || activity.getSubType().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case START_WITH:
                            return activity.getSubType().toLowerCase().startsWith(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case CONTAINS:
                            return activity.getSubType().toLowerCase().contains(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case EQUALS:
                            return activity.getSubType().equalsIgnoreCase(filter.getValue());
                        default:
                            return false;
                    }
                }));

        // Amount filter predicate
        filterPredicatesMap.put("amount", filter ->
                (activity -> {
                    if (activity.getAmount() == null || activity.getAmount().isEmpty() || activity.getAmount().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case EQUALS:
                            return Double.valueOf(activity.getAmount()).equals(Double.valueOf(filter.getValue()));
                        default:
                            return false;
                    }
                }));

        // Balance filter predicate
        filterPredicatesMap.put("balance", filter ->
                (activity -> {
                    if (activity.getBalance() == null || activity.getBalance().isEmpty() || activity.getBalance().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case EQUALS:
                            return Double.valueOf(activity.getBalance()).equals(Double.valueOf(filter.getValue()));
                        default:
                            return false;
                    }
                }));

        // Account status filter predicate
        filterPredicatesMap.put("accountStatus", filter ->
                (activity -> {
                    if (activity.getAccountStatus() == null || activity.getAccountStatus().isEmpty() || activity.getAccountStatus().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case START_WITH:
                            return activity.getAccountStatus().toLowerCase().startsWith(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case CONTAINS:
                            return activity.getAccountStatus().toLowerCase().contains(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case EQUALS:
                            return activity.getAccountStatus().equalsIgnoreCase(filter.getValue());
                        default:
                            return false;
                    }
                }));

        // Tx Code filter predicate
        filterPredicatesMap.put("trxCode", filter ->
                (activity -> {
                    if (activity.getTransactionCode() == null || activity.getTransactionCode().isEmpty() || activity.getTransactionCode().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case START_WITH:
                            return activity.getTransactionCode().toLowerCase().startsWith(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case CONTAINS:
                            return activity.getTransactionCode().toLowerCase().contains(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case EQUALS:
                            return activity.getTransactionCode().equalsIgnoreCase(filter.getValue());
                        default:
                            return false;
                    }
                }));

        // Tx Type filter predicate
        filterPredicatesMap.put("trxType", filter ->
                (activity -> {
                    if (activity.getTransactionType() == null || activity.getTransactionType().isEmpty() || activity.getTransactionType().isBlank()) {
                        return false;
                    }
                    switch (filter.getFilterType()) {
                        case START_WITH:
                            return activity.getTransactionType().toLowerCase().startsWith(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case CONTAINS:
                            return activity.getTransactionType().toLowerCase().contains(filter.getValue() == null ? null : filter.getValue().toLowerCase());
                        case EQUALS:
                            return activity.getTransactionType().equalsIgnoreCase(filter.getValue());
                        default:
                            return false;
                    }
                }));

    }

    private void sortDetails(List<SubscriberActivityModel> activitiesList){
        CCATLogger.DEBUG_LOGGER.debug("Sort activities by activity type and date");
        activitiesList.sort((activity1, activity2) -> {
            String activityType1 = activity1.getActivityType();
            String activityType2 = activity2.getActivityType();

            int typeComparison;
            if (activityType1 == null && activityType2 == null) {
                typeComparison = 0;
            } else if (activityType1 == null) {
                typeComparison = -1;
            } else if (activityType2 == null) {
                typeComparison = 1;
            } else {
                typeComparison = activityType1.compareTo(activityType2);
            }

            if (typeComparison != 0) {
                return typeComparison;
            }

            Long date1 = activity1.getDate();
            Long date2 = activity2.getDate();

            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null) {
                return -1;
            } else if (date2 == null) {
                return 1;
            } else {
                return date1.compareTo(date2);
            }
        });
    }
}
