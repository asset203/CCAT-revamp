/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.cache;

import com.asset.ccat.ods_service.models.DSSNodesModel;
import com.asset.ccat.ods_service.models.ods_models.*;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class CachedLookups {

    private HashMap<String, HashMap<String, String>> retrieveDSSColumnNames;
    private HashMap<String, ODSActivityModel> accountHistoryActivities;
    private HashMap<Integer, ODSActivityHeader> accountHistoryActivitiesHeader;
    private HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> accountHistoryActivitiesHeaderMapping;
    private HashMap<Integer, List<ODSActivityDetailsMapping>> accountHistoryActivitiesDetailsMapping;
    private HashMap<String, String> accountFlags;
    private HashMap<String, String> transactionTypes;
    private HashMap<String, String> transactionCodes;
    private HashMap<String, String> transactionlinks;
    private HashMap<String, String> btStatus;
    private List<ODSNodesModel> ODSNodes;
    private List<DSSNodesModel> DSSNodes;
    private List<FlexShareHistoryNodeModel> flexHistoryNodes;

    public HashMap<String, HashMap<String, String>> getRetrieveDSSColumnNames() {
        return retrieveDSSColumnNames;
    }

    public void setRetrieveDSSColumnNames(HashMap<String, HashMap<String, String>> retrieveDSSColumnNames) {
        this.retrieveDSSColumnNames = retrieveDSSColumnNames;
    }

    public HashMap<String, ODSActivityModel> getAccountHistoryActivities() {
        return accountHistoryActivities;
    }

    public void setAccountHistoryActivities(HashMap<String, ODSActivityModel> accountHistoryActivities) {
        this.accountHistoryActivities = accountHistoryActivities;
    }

    public HashMap<Integer, ODSActivityHeader> getAccountHistoryActivitiesHeader() {
        return accountHistoryActivitiesHeader;
    }

    public void setAccountHistoryActivitiesHeader(HashMap<Integer, ODSActivityHeader> accountHistoryActivitiesHeader) {
        this.accountHistoryActivitiesHeader = accountHistoryActivitiesHeader;
    }

    public HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> getAccountHistoryActivitiesHeaderMapping() {
        return accountHistoryActivitiesHeaderMapping;
    }

    public void setAccountHistoryActivitiesHeaderMapping(HashMap<Integer, HashMap<Integer, ODSActivityHeaderMapping>> accountHistoryActivitiesHeaderMapping) {
        this.accountHistoryActivitiesHeaderMapping = accountHistoryActivitiesHeaderMapping;
    }

    public HashMap<Integer, List<ODSActivityDetailsMapping>> getAccountHistoryActivitiesDetailsMapping() {
        return accountHistoryActivitiesDetailsMapping;
    }

    public void setAccountHistoryActivitiesDetailsMapping(HashMap<Integer, List<ODSActivityDetailsMapping>> accountHistoryActivitiesDetailsMapping) {
        this.accountHistoryActivitiesDetailsMapping = accountHistoryActivitiesDetailsMapping;
    }

    public HashMap<String, String> getAccountFlags() {
        return accountFlags;
    }

    public void setAccountFlags(HashMap<String, String> accountFlags) {
        this.accountFlags = accountFlags;
    }

    public HashMap<String, String> getTransactionTypes() {
        return transactionTypes;
    }

    public void setTransactionTypes(HashMap<String, String> transactionTypes) {
        this.transactionTypes = transactionTypes;
    }

    public HashMap<String, String> getTransactionCodes() {
        return transactionCodes;
    }

    public void setTransactionCodes(HashMap<String, String> transactionCodes) {
        this.transactionCodes = transactionCodes;
    }

    public HashMap<String, String> getTransactionlinks() {
        return transactionlinks;
    }

    public void setTransactionlinks(HashMap<String, String> transactionlinks) {
        this.transactionlinks = transactionlinks;
    }

    public HashMap<String, String> getBtStatus() {
        return btStatus;
    }

    public void setBtStatus(HashMap<String, String> btStatus) {
        this.btStatus = btStatus;
    }

    public List<ODSNodesModel> getODSNodes() {
        return ODSNodes;
    }

    public void setODSNodes(List<ODSNodesModel> ODSNodes) {
        this.ODSNodes = ODSNodes;
    }

    public List<DSSNodesModel> getDSSNodes() {
        return DSSNodes;
    }

    public void setDSSNodes(List<DSSNodesModel> DSSNodes) {
        this.DSSNodes = DSSNodes;
    }

    public List<FlexShareHistoryNodeModel> getFlexHistoryNodes() {
        return flexHistoryNodes;
    }

    public void setFlexHistoryNodes(List<FlexShareHistoryNodeModel> flexHistoryNodes) {
        this.flexHistoryNodes = flexHistoryNodes;
    }

    public String getValueByKeyAndLookup(String key, String lookupName) {
        if ("ACCOUNT_STATUS_LOOKUP".equals(lookupName)) {
            return this.accountFlags.get(key) != null ? (String) this.accountFlags.get(key) : key;
        } else if ("TX_CODE_LOOKUP".equals(lookupName)) {
            return this.transactionCodes.get(key) != null ? (String) this.transactionCodes.get(key) : key;
        } else if ("TX_TYPE_LOOKUP".equals(lookupName)) {
            return this.transactionTypes.get(key) != null ? (String) this.transactionTypes.get(key) : key;
        } else if ("TX_LINK_LOOKUP".equals(lookupName)) {
            return this.transactionlinks.get(key) != null ? (String) this.transactionlinks.get(key) : null;
        }
        return key;
    }

}
