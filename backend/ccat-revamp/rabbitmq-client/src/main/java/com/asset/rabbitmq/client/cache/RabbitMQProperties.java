package com.asset.rabbitmq.client.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author islam.said
 */
@Component
@ConfigurationProperties
public class RabbitMQProperties {

    private String rabbitmqUsername;
    private String rabbitmqPassword;
    private String rabbitmqHost;

    private int maxNumberOfChannelsPerNode;
    private int channelCheckoutTimeout;

    private String exchangeName;

    private int batchSize;
    private int bufferLimit;
    private int timeout;

    private String rabbitmqApiUrl;

    public String getRabbitmqUsername() {
        return rabbitmqUsername;
    }

    public void setRabbitmqUsername(String rabbitmqUsername) {
        this.rabbitmqUsername = rabbitmqUsername;
    }

    public String getRabbitmqPassword() {
        return rabbitmqPassword;
    }

    public void setRabbitmqPassword(String rabbitmqPassword) {
        this.rabbitmqPassword = rabbitmqPassword;
    }

    public String getRabbitmqHost() {
        return rabbitmqHost;
    }

    public void setRabbitmqHost(String rabbitmqHost) {
        this.rabbitmqHost = rabbitmqHost;
    }

    public int getMaxNumberOfChannelsPerNode() {
        return maxNumberOfChannelsPerNode;
    }

    public void setMaxNumberOfChannelsPerNode(int maxNumberOfChannelsPerNode) {
        this.maxNumberOfChannelsPerNode = maxNumberOfChannelsPerNode;
    }

    public int getChannelCheckoutTimeout() {
        return channelCheckoutTimeout;
    }

    public void setChannelCheckoutTimeout(int channelCheckoutTimeout) {
        this.channelCheckoutTimeout = channelCheckoutTimeout;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getBufferLimit() {
        return bufferLimit;
    }

    public void setBufferLimit(int bufferLimit) {
        this.bufferLimit = bufferLimit;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getRabbitmqApiUrl() {
        return rabbitmqApiUrl;
    }

    public void setRabbitmqApiUrl(String rabbitmqApiUrl) {
        this.rabbitmqApiUrl = rabbitmqApiUrl;
    }
}
