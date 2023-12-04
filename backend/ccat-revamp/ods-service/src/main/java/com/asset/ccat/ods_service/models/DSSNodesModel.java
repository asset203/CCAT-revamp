package com.asset.ccat.ods_service.models;

import java.util.Objects;

/**
 * @author assem hassan
 */
public class DSSNodesModel {
    private Integer id;
    private String address;
    private Integer port;
    private String userName;
    private String password;
    private Integer numberOfSessions;
    private Integer concurrentCalls;
    private Integer connectionTimeout;
    private String extraConf;
    private String schema;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Integer numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public Integer getConcurrentCalls() {
        return concurrentCalls;
    }

    public void setConcurrentCalls(Integer concurrentCalls) {
        this.concurrentCalls = concurrentCalls;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getExtraConf() {
        return extraConf;
    }

    public void setExtraConf(String extraConf) {
        this.extraConf = extraConf;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DSSNodesModel that = (DSSNodesModel) o;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(port, that.port) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(numberOfSessions, that.numberOfSessions) && Objects.equals(concurrentCalls, that.concurrentCalls) && Objects.equals(connectionTimeout, that.connectionTimeout) && Objects.equals(extraConf, that.extraConf) && Objects.equals(schema, that.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, port, userName, password, numberOfSessions, concurrentCalls, connectionTimeout, extraConf, schema);
    }
}
