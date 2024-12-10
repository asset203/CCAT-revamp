package com.asset.ccat.balancedisputemapperservice.cache;

import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.models.ServiceClassModel;
import com.asset.ccat.balancedisputemapperservice.services.LookupsService;
import java.util.HashMap;
import java.util.List;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LookupsCache {

  private final LookupsService lookupsService;
  private HashMap<String, String> regionsMap;
  private List<ServiceClassModel> serviceClassList;

  @Autowired
  public LookupsCache(LookupsService lookupsService) {
    this.lookupsService = lookupsService;
  }

  @PostConstruct
  public void init() throws BalanceDisputeException {
    regionsMap = lookupsService.getRegions();
    serviceClassList = lookupsService.getAllServiceClasses();
  }

  public HashMap<String, String> getRegionsMap() {
    return regionsMap;
  }

  public void setRegionsMap(HashMap<String, String> regionsMap) {
    this.regionsMap = regionsMap;
  }

  public List<ServiceClassModel> getServiceClassList() {
    return serviceClassList;
  }

  public void setServiceClassList(
      List<ServiceClassModel> serviceClassList) {
    this.serviceClassList = serviceClassList;
  }

  public LookupsService getLookupsService() {
    return lookupsService;
  }
}
