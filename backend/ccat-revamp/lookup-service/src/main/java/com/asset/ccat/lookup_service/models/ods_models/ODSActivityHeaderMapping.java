/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.models.ods_models;

/**
 * @author Mahmoud Shehab
 */
public class ODSActivityHeaderMapping {

  private Integer activityId;
  private Integer headerId;
  private Integer columnIdx;
  private Integer isStatic;
  private String customFormat;
  private String preConditions;
  private String defaultValue;

  private String preConditionsValue;

  public Integer getHeaderId() {
    return headerId;
  }

  public ODSActivityHeaderMapping withHeaderId(Integer activityId) {
    this.headerId = activityId;
    return this;
  }

  public Integer getActivityId() {
    return activityId;
  }

  public ODSActivityHeaderMapping withActivityId(Integer activityId) {
    this.activityId = activityId;
    return this;
  }

  public Integer getColumnIdx() {
    return columnIdx;
  }

  public ODSActivityHeaderMapping withColumnIdx(Integer columnIdx) {
    this.columnIdx = columnIdx;
    return this;

  }

  public Integer getIsStatic() {
    return isStatic;
  }

  public ODSActivityHeaderMapping withIsStatic(Integer isStatic) {
    this.isStatic = isStatic;
    return this;

  }

  public String getCustomFormat() {
    return customFormat;
  }

  public ODSActivityHeaderMapping withCustomFormat(String customFormat) {
    this.customFormat = customFormat;
    return this;

  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public ODSActivityHeaderMapping withDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;

  }

  public String getPreConditions() {
    return preConditions;
  }

  public ODSActivityHeaderMapping withPreConditions(String preConditions) {
    this.preConditions = preConditions;
    return this;
  }

  public String getPreConditionsValue() {
    return preConditionsValue;
  }

  public ODSActivityHeaderMapping withPreConditionsValue(String preConditionsValue) {
    this.preConditionsValue = preConditionsValue;
    return this;
  }
}
