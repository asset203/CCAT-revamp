package com.asset.ccat.balancedisputemapperservice.models.requests;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Assem.Hassan
 */
public class BalanceDisputeServiceRequest extends BaseRequest {

  private HashMap<String, ArrayList<HashMap<String,Object>>> balanceDisputeServiceMap;

  public HashMap<String, ArrayList<HashMap<String, Object>>> getBalanceDisputeServiceMap() {
    return balanceDisputeServiceMap;
  }

  public void setBalanceDisputeServiceMap(
      HashMap<String, ArrayList<HashMap<String, Object>>> balanceDisputeServiceMap) {
    this.balanceDisputeServiceMap = balanceDisputeServiceMap;
  }
}
