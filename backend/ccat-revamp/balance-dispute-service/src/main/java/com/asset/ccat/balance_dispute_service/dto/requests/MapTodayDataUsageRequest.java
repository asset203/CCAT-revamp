package com.asset.ccat.balance_dispute_service.dto.requests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class MapTodayDataUsageRequest extends BaseRequest {

  private HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> todayDataUsageMap;
  private BigDecimal errorCode;

  public MapTodayDataUsageRequest() {
    this.todayDataUsageMap = new HashMap<>();
  }


  public HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> getTodayDataUsageMap() {
    return todayDataUsageMap;
  }

  public void setTodayDataUsageMap(
      HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> todayDataUsageMap) {
    this.todayDataUsageMap = todayDataUsageMap;
  }

  public BigDecimal getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(BigDecimal errorCode) {
    this.errorCode = errorCode;
  }
}
