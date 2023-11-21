package com.asset.ccat.ci_service.models.shared;

/**
 *
 * @author Mahmoud Shehab
 */
public class ServiceInfo {

    private String ip;
    private String port;

    public ServiceInfo(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
