package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.ods_models.ODSActivityHeader;
import java.util.HashMap;

public class GetODSActivityHeaderResponse {

  private HashMap<Integer, ODSActivityHeader> odsActivityHeaderMap;

  public GetODSActivityHeaderResponse() {
  }

  public GetODSActivityHeaderResponse(HashMap<Integer, ODSActivityHeader> odsActivityHeaderMap) {
    this.odsActivityHeaderMap = odsActivityHeaderMap;
  }

  public HashMap<Integer, ODSActivityHeader> getOdsActivityHeaderMap() {
    return odsActivityHeaderMap;
  }

  public void setOdsActivityHeaderMap(
      HashMap<Integer, ODSActivityHeader> odsActivityHeaderMap) {
    this.odsActivityHeaderMap = odsActivityHeaderMap;
  }
}
