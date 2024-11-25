package com.asset.ccat.balancedisputemapperservice.models.requests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class MapTodayDataUsageRequest extends BaseRequest {

  private HashMap<String, ArrayList<HashMap<String, Object>>> todayDataUsageMap;
  private BigDecimal errorCode;

  public MapTodayDataUsageRequest() {
    this.todayDataUsageMap = new HashMap<>();
  }

  public HashMap<String, ArrayList<HashMap<String, Object>>> getTodayDataUsageMap() {
    return todayDataUsageMap;
  }

  public void setTodayDataUsageMap(
      HashMap<String, ArrayList<HashMap<String, Object>>> todayDataUsageMap) {
    this.todayDataUsageMap = todayDataUsageMap;
  }

  public BigDecimal getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(BigDecimal errorCode) {
    this.errorCode = errorCode;
  }
}
