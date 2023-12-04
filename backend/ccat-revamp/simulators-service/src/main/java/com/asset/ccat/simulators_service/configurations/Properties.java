package com.asset.ccat.simulators_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
@ConfigurationProperties
public class Properties {

  private String waitTime;
  private String airPath;
  private String ciPath;

  public String getWaitTime() {
    return waitTime;
  }

  public void setWaitTime(String waitTime) {
    this.waitTime = waitTime;
  }

  public String getAirPath() {
    return airPath;
  }

  public void setAirPath(String airPath) {
    this.airPath = airPath;
  }

  public String getCiPath() {
    return ciPath;
  }

  public void setCiPath(String ciPath) {
    this.ciPath = ciPath;
  }
}
