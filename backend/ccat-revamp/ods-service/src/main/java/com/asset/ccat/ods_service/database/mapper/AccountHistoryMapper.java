/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.database.mapper;

import com.asset.ccat.ods_service.cache.CachedLookups;
import com.asset.ccat.ods_service.database.mapper.custom_mappers.CustomMapper;
import com.asset.ccat.ods_service.database.mapper.custom_mappers.CustomMapperFactory;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityDetailsMapping;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityHeader;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityHeaderMapping;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityModel;
import com.asset.ccat.ods_service.utils.OdsUtils;
import com.asset.ccat.ods_service.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wael.mohamed
 */
@Component
public class AccountHistoryMapper {

    @Autowired
    CachedLookups cachedLookups;

    @Autowired
    CustomMapperFactory customMapperFactory;

    public AccountHistoryMapper(CachedLookups cachedLookups) {
        this.cachedLookups = cachedLookups;
    }

    public SubscriberActivityModel mapRow(Struct activityObject, String msisdn) throws SQLException {
        Object[] columns = activityObject.getAttributes();
        SubscriberActivityModel accountHistoryModel = null;
        HashMap<String, ODSActivityModel> activities = cachedLookups.getAccountHistoryActivities();

        String activityType = ((String) columns[0]).trim();
        ODSActivityModel activity = activities.get(activityType);

        if (activity == null) {
            CCATLogger.DEBUG_LOGGER.debug("No activity type found, record with activity type " + activityType + " will be skipped");
        } else {
            accountHistoryModel = new SubscriberActivityModel();
            setModelHeaders(accountHistoryModel, msisdn, columns, activity);
            setModelDetails(accountHistoryModel, columns, activity);
        }

        return accountHistoryModel;
    }

    private void setModelHeaders(SubscriberActivityModel accountHistoryModel, String msisdn, Object[] columns, ODSActivityModel activity) {
        HashMap<Integer, ODSActivityHeader> headers = cachedLookups.getAccountHistoryActivitiesHeader();
        HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> headersMapping = cachedLookups.getAccountHistoryActivitiesHeaderMapping();
        HashMap<Integer, ODSActivityHeaderMapping> localHeadersMapping = headersMapping.get(activity.getActivityId());
        Integer activityTypeColIdx = null;
        for (Integer key : localHeadersMapping.keySet()) {
            try {
                ODSActivityHeaderMapping headerMappingObject = localHeadersMapping.get(key);
                ODSActivityHeader headerInfoModel = headers.get(headerMappingObject.getHeaderId());
                activityTypeColIdx = headerMappingObject.getColumnIdx();
                String value = "";
                if (activityTypeColIdx != null) {
                    value = (String) columns[activityTypeColIdx]; // returned already from the DB.
                } else if (headerMappingObject.getPreConditions() != null) {
                    if (!headerMappingObject.getPreConditions().contains("CUSTOM") &&
                            OdsUtils.checkPreCondition(headerMappingObject.getPreConditions(), columns)) {
                        value = headerMappingObject.getPreConditionsValue();
                        CCATLogger.DEBUG_LOGGER.debug("Preconditioned value = {}", value);
                    } else {
                        value = headerMappingObject.getDefaultValue();
                    }
                } else if (headerMappingObject.getIsStatic() == 1) {
                    value = headerMappingObject.getDefaultValue();
                }

                if ("activityType".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    accountHistoryModel.setActivityType(value);
                } else if ("activityId".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    Integer activityId = Integer.parseInt(value);
                    accountHistoryModel.setActivityId(activityId);
                } else if ("subscriber".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    accountHistoryModel.setSubscriber(value);
                } else if ("date".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    String formattedDate = DateFormatter.convertToStandardFormat(value, headerMappingObject.getCustomFormat(), TimeZone.getTimeZone("Africa/Cairo"), TimeZone.getTimeZone("Africa/Cairo"));
                    CCATLogger.DEBUG_LOGGER.debug("formattedDate = {}", formattedDate);
                    Date date = new SimpleDateFormat(headerMappingObject.getCustomFormat()).parse(formattedDate);
                    accountHistoryModel.setDate(date);
                } else if ("subType".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    accountHistoryModel.setSubType(value);
                } else if ("accountStatus".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    String lookupValue = cachedLookups.getValueByKeyAndLookup(value, headerInfoModel.getHeaderType());
                    accountHistoryModel.setAccountStatus(lookupValue);
                } else if ("transactionCode".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    accountHistoryModel.setTransactionCode(value);
                } else if ("transactionType".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    accountHistoryModel.setTransactionType(value);
                } else if ("amount".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    Double doubleVal = Double.parseDouble(value);
                    accountHistoryModel.setAmount(doubleVal);
                } else if ("balance".equalsIgnoreCase(headerInfoModel.getHeaderName())) {
                    Double doubleVal = Double.parseDouble(value);
                    accountHistoryModel.setBalance(doubleVal);
                }

                if (headerMappingObject.getPreConditions() != null
                        && headerMappingObject.getPreConditions().contains("CUSTOM")) {
                    handleCustomLogic(accountHistoryModel, msisdn, columns, activity, headerMappingObject);
                }
            } catch (Exception e) {
                CCATLogger.DEBUG_LOGGER.error("Exception while parsing record with type [{}]  and column index is [{}] --> {}",  activity.getActivityName(), activityTypeColIdx, e.getMessage());
                CCATLogger.ERROR_LOGGER.error("Exception while parsing activity record: ", e);
                if(columns != null && activityTypeColIdx != null)
                    CCATLogger.DEBUG_LOGGER.error("{}", columns[activityTypeColIdx]);
            }
        }
    }

    private void setModelDetails(SubscriberActivityModel accountHistoryModel, Object[] columns, ODSActivityModel activity) {
        HashMap<Integer, List<ODSActivityDetailsMapping>> detailsMapping = cachedLookups.getAccountHistoryActivitiesDetailsMapping();
        List<ODSActivityDetailsMapping> localDetailsMapping = detailsMapping.get(activity.getActivityId());
        Integer activityColIdx;

        if (accountHistoryModel.getDetails() == null) {
            accountHistoryModel.setDetails(new LinkedHashMap<>());
        }

        for (ODSActivityDetailsMapping mappingObject : localDetailsMapping) {
            try {

                String displayName = mappingObject.getDisplayName();
                String paramColumnIdx = OdsUtils.getColumnIndexFromString(displayName);
                if (paramColumnIdx != null) {
                    Integer colIdx = Integer.parseInt(paramColumnIdx);
                    String valueForReplacement = (String) columns[colIdx];
                    displayName = OdsUtils.replaceParameters(valueForReplacement, paramColumnIdx, displayName);
                }

                activityColIdx = mappingObject.getColumnIdx();
                String value = "";
                if (activityColIdx != null) {
                    value = (String) columns[activityColIdx];
                    if (mappingObject.getDetailType() != null) {
                        value = cachedLookups.getValueByKeyAndLookup(value, mappingObject.getDetailType());
                    }
                }
                accountHistoryModel.getDetails().put(displayName, value);
            } catch (Exception e) {
                CCATLogger.DEBUG_LOGGER.error("Error while mapping activity detail. ", e);
                CCATLogger.ERROR_LOGGER.error("Error while mapping activity detail. ", e);
            }
        }
    }

    private void handleCustomLogic(SubscriberActivityModel accountHistoryModel, String msisdn, Object[] columns, ODSActivityModel activity, ODSActivityHeaderMapping headerMappingObject) {
        HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> headersMapping = cachedLookups.getAccountHistoryActivitiesHeaderMapping();
        HashMap<Integer, ODSActivityHeaderMapping> localHeadersMapping = headersMapping.get(activity.getActivityId());
        CustomMapper customMapper = customMapperFactory.getCustomMapper(activity.getTableType());
        if (customMapper != null) {
            //need here did set the data directly
            customMapper.handleCustomMapping(accountHistoryModel, msisdn, columns, headerMappingObject);
        }

    }
}
