package com.asset.ccat.balance_dispute_service.managers;

import com.asset.ccat.balance_dispute_service.cache.BalanceDisputeTemplatesCache;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceManager {

  private final BalanceDisputeTemplatesCache balanceDisputeTemplatesCache;

  @Autowired
  public ServiceManager(BalanceDisputeTemplatesCache balanceDisputeTemplatesCache) {
    this.balanceDisputeTemplatesCache = balanceDisputeTemplatesCache;
  }

  @EventListener
  public void startupEvent(ContextRefreshedEvent event) throws BalanceDisputeException {
    CCATLogger.DEBUG_LOGGER.debug("starting balance dispute service");
    CCATLogger.DEBUG_LOGGER.debug("start loading cached balance dispute report");
    balanceDisputeTemplatesCache.init();
  }
}
