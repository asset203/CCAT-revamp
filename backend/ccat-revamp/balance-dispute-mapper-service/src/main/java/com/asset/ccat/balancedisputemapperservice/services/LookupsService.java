package com.asset.ccat.balancedisputemapperservice.services;

import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.balancedisputemapperservice.models.ServiceClassModel;
import com.asset.ccat.balancedisputemapperservice.proxy.LookupProxy;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Assem.Hassan
 */
@Service
public class LookupsService {


  LookupProxy lookupProxy;

  @Autowired
  public LookupsService(LookupProxy lookupProxy) {
    this.lookupProxy = lookupProxy;
  }

  public HashMap<String, String> getRegions() throws BalanceDisputeException {
    return lookupProxy.getRegions();
  }

  public LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> getBDDetailsConfiguration(
      Integer profileId)
      throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("getting BD details configurations of profileId = {}", profileId);
    return lookupProxy.getBDDetailsConfiguration(profileId);
  }

  public List<ServiceClassModel> getAllServiceClasses()
      throws BalanceDisputeException {
    return lookupProxy.getAllServiceClasses();
  }

  public LinkedHashMap<Integer, String> getBalanceDisputeDetailsColumns(
      LinkedHashMap<Integer, LkBalanceDisputeDetailsConfigModel> balanceDisputeDetailsConfig) {
    LinkedHashMap<Integer, String> res = new LinkedHashMap<>();
    int counter = 1;
    for (LkBalanceDisputeDetailsConfigModel entry : balanceDisputeDetailsConfig.values()) {
      res.put(counter++, entry.getDisplayName());
    }
    return res;
  }
}