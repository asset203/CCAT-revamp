package com.asset.ccat.history_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wael.mohamed
 */
@Component
@ConfigurationProperties
public class Properties {

    private String accessTokenKey;
    private long accessTokenValidity;
    private String airPrefixesMappings;
    private Integer subpartitions;

    private Integer footprintDequeuersNumber;
    private Integer footprintBatchSize;
    private Integer footprintExecutorPoolSize;
    private Integer footprintExecutorPoolKeepAliveTime;

    private Integer maxNoOfTransactions;
    private Integer transactionsValidationTimePeriod;

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public String getAirPrefixesMappings() {
        return airPrefixesMappings;
    }

    public void setAirPrefixesMappings(String airPrefixesMappings) {
        this.airPrefixesMappings = airPrefixesMappings;
    }

    public Integer getSubpartitions() {
        return subpartitions;
    }

    public void setSubpartitions(Integer subpartitions) {
        this.subpartitions = subpartitions;
    }

    public Integer getFootprintDequeuersNumber() {
        return footprintDequeuersNumber;
    }

    public void setFootprintDequeuersNumber(Integer footprintDequeuersNumber) {
        this.footprintDequeuersNumber = footprintDequeuersNumber;
    }

    public Integer getFootprintBatchSize() {
        return footprintBatchSize;
    }

    public void setFootprintBatchSize(Integer footprintBatchSize) {
        this.footprintBatchSize = footprintBatchSize;
    }

    public Integer getFootprintExecutorPoolSize() {
        return footprintExecutorPoolSize;
    }

    public void setFootprintExecutorPoolSize(Integer footprintExecutorPoolSize) {
        this.footprintExecutorPoolSize = footprintExecutorPoolSize;
    }

    public Integer getFootprintExecutorPoolKeepAliveTime() {
        return footprintExecutorPoolKeepAliveTime;
    }

    public void setFootprintExecutorPoolKeepAliveTime(Integer footprintExecutorPoolKeepAliveTime) {
        this.footprintExecutorPoolKeepAliveTime = footprintExecutorPoolKeepAliveTime;
    }

    public Integer getMaxNoOfTransactions() {
        return maxNoOfTransactions;
    }

    public void setMaxNoOfTransactions(Integer maxNoOfTransactions) {
        this.maxNoOfTransactions = maxNoOfTransactions;
    }

    public Integer getTransactionsValidationTimePeriod() {
        return transactionsValidationTimePeriod;
    }

    public void setTransactionsValidationTimePeriod(Integer transactionsValidationTimePeriod) {
        this.transactionsValidationTimePeriod = transactionsValidationTimePeriod;
    }

}
