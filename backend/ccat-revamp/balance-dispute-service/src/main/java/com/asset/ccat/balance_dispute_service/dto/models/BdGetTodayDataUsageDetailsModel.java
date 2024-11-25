package com.asset.ccat.balance_dispute_service.dto.models;

import java.util.ArrayList;
import java.util.HashMap;

public class BdGetTodayDataUsageDetailsModel {

  private ArrayList<HashMap<String, String>> bdTodayDataUsageDetailsList;


  public BdGetTodayDataUsageDetailsModel() {
    this.bdTodayDataUsageDetailsList = new ArrayList<>();
  }

  public ArrayList<HashMap<String, String>> getBdTodayDataUsageDetailsList() {
    return bdTodayDataUsageDetailsList;
  }

  public void setBdTodayDataUsageDetailsList(
      ArrayList<HashMap<String, String>> bdTodayDataUsageDetailsList) {
    this.bdTodayDataUsageDetailsList = bdTodayDataUsageDetailsList;
  }
}
