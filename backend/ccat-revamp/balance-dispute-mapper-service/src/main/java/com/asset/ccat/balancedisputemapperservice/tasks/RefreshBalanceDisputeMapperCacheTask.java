package com.asset.ccat.balancedisputemapperservice.tasks;

import com.asset.ccat.balancedisputemapperservice.cache.LookupsCache;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.ServiceClassModel;
import com.asset.ccat.balancedisputemapperservice.services.LookupsService;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class RefreshBalanceDisputeMapperCacheTask implements Runnable {

  private final ReentrantLock reentrantLock;
  @Autowired
  private LookupsService lookupsService;
  @Autowired
  private LookupsCache lookupsCache;

  public RefreshBalanceDisputeMapperCacheTask() {
    this.reentrantLock = new ReentrantLock();
  }

  @Override
  public void run() {
    try {
      CCATLogger.DEBUG_LOGGER.debug("Start refreshing balance dispute mapper cache");
      long start = System.currentTimeMillis();
      HashMap<String, String> regionsMap = lookupsService.getRegions();
      List<ServiceClassModel> serviceClassList = lookupsService.getAllServiceClasses();
      if (Objects.nonNull(regionsMap) && Objects.nonNull(serviceClassList) && !regionsMap.isEmpty()
          && !serviceClassList.isEmpty()) {
        reentrantLock.lock();
        lookupsCache.setRegionsMap(regionsMap);
        lookupsCache.setServiceClassList(serviceClassList);
      }
      CCATLogger.DEBUG_LOGGER.debug(
          "Finished refreshing Balance Dispute Mapper details configs cache in [" + (
              System.currentTimeMillis() - start)
              + "]");

    } catch (Throwable th) {
      CCATLogger.DEBUG_LOGGER.debug("Failed to refresh Balance Dispute details configs cache");
      CCATLogger.ERROR_LOGGER.error("Failed to refresh Balance Dispute details configs cache", th);
    } finally {
      reentrantLock.unlock();
    }

  }
}
