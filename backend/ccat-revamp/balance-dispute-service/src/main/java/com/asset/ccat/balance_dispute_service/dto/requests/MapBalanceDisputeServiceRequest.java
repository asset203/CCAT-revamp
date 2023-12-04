package com.asset.ccat.balance_dispute_service.dto.requests;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class MapBalanceDisputeServiceRequest extends BaseRequest {

  private HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> balanceDisputeServiceMap;

  public HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> getBalanceDisputeServiceMap() {
    return balanceDisputeServiceMap;
  }

  public void setBalanceDisputeServiceMap(
      HashMap<String, ArrayList<LinkedCaseInsensitiveMap<Object>>> balanceDisputeServiceMap) {
    this.balanceDisputeServiceMap = balanceDisputeServiceMap;
  }
}
